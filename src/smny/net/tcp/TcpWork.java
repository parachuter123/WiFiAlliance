package smny.net.tcp;
import java.io.IOException;
import java.io.EOFException;
import java.io.FileNotFoundException;

import java.net.Socket;
import java.net.SocketTimeoutException;

import java.util.Map;
import java.util.Timer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TimerTask;
import java.util.Collections;
import smny.util.SoftObjectReuse;
import smny.util.SoftObjectCache;
import smny.util.CacheObjectReuseManager;
    

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.apache.log4j.Logger;
import org.apache.log4j.NDC;



import java.io.FileReader;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngineManager;
    
public class TcpWork implements Runnable{
    
    private final static Logger log= Logger.getLogger(TcpWork.class);
    
    
    private final static Timer Timeout = new Timer("在线终端延时退出");
	  private final Map<String,TimerTask> WaitTaskPool = Collections.synchronizedMap(new HashMap<String,TimerTask>());
    public TimerTask setTimeout(final String CallBackName,final String WaitKey,long WaitTime,final Object args){
    		final Invocable ecriptengine = Callback;
    		TimerTask Task = new TimerTask(){
	    			public void run(){
								try{
										/*
										if(args.length>0 && args.length==1){
												ecriptengine.invokeFunction(CallBackName,args[0]);
										}else if(args.length > 0){
												ecriptengine.invokeFunction(CallBackName,args);
										}
										*/
										ecriptengine.invokeFunction(CallBackName,args);
										if(WaitKey != null){
												WaitTaskPool.remove(CallBackName+"("+WaitKey+")");
										}
								}catch(ScriptException Scr){
										Scr.printStackTrace();
				            //sun.org.mozilla.javascript.internal.EvaluatorException:
				            //脚本运行错误
				            
				            //sun.org.mozilla.javascript.internal.WrappedException: Wrapped
				            //JAVA 异常包装
				            
				            //sun.org.mozilla.javascript.internal.EcmaError: ReferenceError: 
				            //未知变量(变量未定义)
				            
								}catch(NoSuchMethodException NoS){
										NoS.printStackTrace();
										//JS文件未定义当前方法
								}
	    			}
    		};
    		Timeout.schedule(Task,WaitTime);
				if(WaitKey != null){
						WaitTaskPool.put(CallBackName+"("+WaitKey+")",Task);
				}
    		return Task;
    }
    public void clearTimeout(String CallBackName,String WaitKey){
    		TimerTask Task = WaitTaskPool.remove(CallBackName+"("+WaitKey+")");
    		if(Task != null){
    				Task.cancel();
    		}
    }
    
    private Socket socket;
    private boolean isAlive;
    private TcpStream Stream;
    private SoftObjectCache<SoftObjectReuse> ObjectReuse;
    
    private Class<? extends TcpDataPacket> Clazz = TcpDataPacketImp.class;
    
    private Invocable Callback;

    public TcpWork(Socket socket) {
        this.socket = socket;
        this.isAlive = true;
    }
    public void Stop(){
    		isAlive = false;
    }
    public void close(){
    		isAlive = false;
        Stream = null;
        if (socket != null)try{
            socket.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        try{
            Callback.invokeFunction("close");
            System.out.println("线程退出回调结束。");
        }catch(Exception ex){
            ex.printStackTrace();
        }
        ObjectReuse = null;
        Callback = null;
    }
    public void setCallback(ScriptEngine callback){
    		callback.put("SendOut",this);
    		callback.put("Logger",log);
    		callback.put("WanIp",socket.getInetAddress().toString());
    		this.Callback = (Invocable)callback;
    }
  	
    public TcpDataPacket getTcpDataPacket()throws IllegalAccessException,InstantiationException{
    		//return (TcpDataPacket)ObjectReuse.get(Clazz);
    		return new TcpDataPacketImp();
    }
    public void setTcpDataPacket(Class<? extends TcpDataPacket> Clazz){
    		this.Clazz = Clazz;
    }
    public void run() {
        System.out.println("新路由请求引导 " + socket.getInetAddress() + ":" + socket.getPort());
        try {
            socket.setSoTimeout(60000);
            Stream = TcpStreamManager.getTcpStream(socket);
            //ObjectReuse = CacheObjectReuseManager.getObjectCache();
        } catch (IOException e) {
            e.printStackTrace();
            close();
            return;
        }
        //ScriptException,NoSuchMethodException
        while (isAlive){
        		TcpDataPacket DataImp = null;
        		try{
								try{
										DataImp = getTcpDataPacket();
								}catch(IllegalAccessException Ill){
										Ill.printStackTrace();
										//TcpDataPacketImp:该类或其 null 构造方法是不可访问的
										break;
								}catch(InstantiationException Ins){
										Ins.printStackTrace();
										//如果此 Class 表示一个抽象类、接口、数组类、基本类型或 void； 或者该类没有 null 构造方法； 或者由于其他某种原因导致实例化失败。
										break;
								}catch(ExceptionInInitializerError Exc){
										Exc.getCause().printStackTrace();
										Exc.getException().printStackTrace();
										Exc.printStackTrace();
										//如果该方法引发的初始化失败。
										continue;
								}catch(SecurityException Sec){
										Sec.printStackTrace();
										//由安全管理器抛出的异常，指示存在安全侵犯。
										break;
								}catch(Exception Sec){
										Sec.printStackTrace();
										//由安全管理器抛出的异常，指示存在安全侵犯。
										break;
								}
								
								try{
										Stream.ParsingTcpDataPacket(DataImp);
								}catch(SocketTimeoutException Soc){
										Soc.printStackTrace();
										//网络通讯超时
										break;
								}catch(EOFException EOF){
										//EOF.printStackTrace();
										//到达流末尾
										break;
								}catch(IOException IOE){
										IOE.printStackTrace();
										//通讯流异常
										break;
								}catch(Throwable IOE){
										IOE.printStackTrace();
										//通讯流异常
										System.out.println("Stream.ParsingTcpDataPacket");
										printlnData(DataImp);
										
										break;
								}
								
								
								
								
								PacketHead packetHead = DataImp.getPacketStructure().getPacketHead();
								
								//确定包类型
								String TypeElementName = packetHead.getBodyTypeElementName();
								Object obj = DataImp.get(TypeElementName);
								if(obj == null){
										throw new NullPointerException("数据包长度数据为空");
								}
								if(!(obj instanceof Number)){
										throw new NumberFormatException("数据包长度数据不是数字");
								}
								int bodyType = ((Number)obj).intValue();
								PacketBodyType packetBodyType = packetHead.getPacketBodyType(bodyType);
								
								
								
								try{
										invokeFunction(packetBodyType.getBodyCallbackName(),DataImp);
								}catch(ScriptException Scr){
										Scr.printStackTrace();
				            //sun.org.mozilla.javascript.internal.EvaluatorException:
				            //脚本运行错误
				            
				            
				            //sun.org.mozilla.javascript.internal.WrappedException: Wrapped
				            //JAVA 异常包装
				            
				            
				            //sun.org.mozilla.javascript.internal.EcmaError: ReferenceError: 
				            //未知变量(变量未定义)
				            
				            
				            
								}catch(NoSuchMethodException NoS){
										NoS.printStackTrace();
										//JS文件未定义当前方法
								}
								
								
								
								
								
								
								
        		} catch (Exception e) {
		            e.printStackTrace();
		            //脚本错误
		        }
        }
        close();
    }
  	
  	
  	
    public synchronized void Reply(TcpDataPacket DataImp)throws IOException{
        
				try{
						Stream.Send(DataImp);
						java.lang.Thread.sleep(50);
				}catch(IOException Soc){
						Soc.printStackTrace();
						throw Soc;
				}catch(NullPointerException Soc){
						Soc.printStackTrace();
						throw Soc;
				}catch(Exception Soc){
						Soc.printStackTrace();
				}
        
    }
  	
    public synchronized Object invokeFunction(String name,Object... args)throws ScriptException,NoSuchMethodException{
        return Callback.invokeFunction(name,args);
    }
  	
    public void printlnData(TcpDataPacket DataImp){
        System.out.println(this.getClass().getName()+".printlnData()");
    }
  	
}