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
    
    
    private final static Timer Timeout = new Timer("�����ն���ʱ�˳�");
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
				            //�ű����д���
				            
				            //sun.org.mozilla.javascript.internal.WrappedException: Wrapped
				            //JAVA �쳣��װ
				            
				            //sun.org.mozilla.javascript.internal.EcmaError: ReferenceError: 
				            //δ֪����(����δ����)
				            
								}catch(NoSuchMethodException NoS){
										NoS.printStackTrace();
										//JS�ļ�δ���嵱ǰ����
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
            System.out.println("�߳��˳��ص�������");
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
        System.out.println("��·���������� " + socket.getInetAddress() + ":" + socket.getPort());
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
										//TcpDataPacketImp:������� null ���췽���ǲ��ɷ��ʵ�
										break;
								}catch(InstantiationException Ins){
										Ins.printStackTrace();
										//����� Class ��ʾһ�������ࡢ�ӿڡ������ࡢ�������ͻ� void�� ���߸���û�� null ���췽���� ������������ĳ��ԭ����ʵ����ʧ�ܡ�
										break;
								}catch(ExceptionInInitializerError Exc){
										Exc.getCause().printStackTrace();
										Exc.getException().printStackTrace();
										Exc.printStackTrace();
										//����÷��������ĳ�ʼ��ʧ�ܡ�
										continue;
								}catch(SecurityException Sec){
										Sec.printStackTrace();
										//�ɰ�ȫ�������׳����쳣��ָʾ���ڰ�ȫ�ַ���
										break;
								}catch(Exception Sec){
										Sec.printStackTrace();
										//�ɰ�ȫ�������׳����쳣��ָʾ���ڰ�ȫ�ַ���
										break;
								}
								
								try{
										Stream.ParsingTcpDataPacket(DataImp);
								}catch(SocketTimeoutException Soc){
										Soc.printStackTrace();
										//����ͨѶ��ʱ
										break;
								}catch(EOFException EOF){
										//EOF.printStackTrace();
										//������ĩβ
										break;
								}catch(IOException IOE){
										IOE.printStackTrace();
										//ͨѶ���쳣
										break;
								}catch(Throwable IOE){
										IOE.printStackTrace();
										//ͨѶ���쳣
										System.out.println("Stream.ParsingTcpDataPacket");
										printlnData(DataImp);
										
										break;
								}
								
								
								
								
								PacketHead packetHead = DataImp.getPacketStructure().getPacketHead();
								
								//ȷ��������
								String TypeElementName = packetHead.getBodyTypeElementName();
								Object obj = DataImp.get(TypeElementName);
								if(obj == null){
										throw new NullPointerException("���ݰ���������Ϊ��");
								}
								if(!(obj instanceof Number)){
										throw new NumberFormatException("���ݰ��������ݲ�������");
								}
								int bodyType = ((Number)obj).intValue();
								PacketBodyType packetBodyType = packetHead.getPacketBodyType(bodyType);
								
								
								
								try{
										invokeFunction(packetBodyType.getBodyCallbackName(),DataImp);
								}catch(ScriptException Scr){
										Scr.printStackTrace();
				            //sun.org.mozilla.javascript.internal.EvaluatorException:
				            //�ű����д���
				            
				            
				            //sun.org.mozilla.javascript.internal.WrappedException: Wrapped
				            //JAVA �쳣��װ
				            
				            
				            //sun.org.mozilla.javascript.internal.EcmaError: ReferenceError: 
				            //δ֪����(����δ����)
				            
				            
				            
								}catch(NoSuchMethodException NoS){
										NoS.printStackTrace();
										//JS�ļ�δ���嵱ǰ����
								}
								
								
								
								
								
								
								
        		} catch (Exception e) {
		            e.printStackTrace();
		            //�ű�����
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