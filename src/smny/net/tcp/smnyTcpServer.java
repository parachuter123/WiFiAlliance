package smny.net.tcp;
import smny.util.FileToolkit;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.net.Socket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptContext;
import javax.script.ScriptException;
import javax.script.ScriptEngineManager;

import org.apache.log4j.Logger;
import org.apache.log4j.NDC;

public class smnyTcpServer extends ServerSocket implements Runnable {
    
    private final static int POOL_SIZE = 500;//单个CPU线程池大小
    private final static Logger log= Logger.getLogger(smnyTcpServer.class);
    private final static ScriptEngineManager Manager = new ScriptEngineManager();
    private final static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * POOL_SIZE);
    
    
    public static Bindings getBindings(){
    		return Manager.getBindings();
    }
    public static void put(String key, Object value){
    		Manager.put(key,value);
    }
    public static void Submit(Runnable task){
    		executorService.execute(task);
    }
    
		
    public void init(String scriptFileName)throws FileNotFoundException{
    		init(FileToolkit.getConfigFile(scriptFileName));
    }
    public void init(File ScriptFile)throws FileNotFoundException{
	  		if(ScriptFile==null){
	  				throw new FileNotFoundException("脚本文件对象为空！");
	  		}
	  		//测试此抽象路径名表示的文件或目录是否存在。
	  		if(!ScriptFile.exists()){
	  				throw new FileNotFoundException("脚本文件:"+ScriptFile+" 不存在！");
	  		}
	  		//测试此抽象路径名表示的文件是否是一个标准文件。
	  		if(!ScriptFile.isFile()){
	  				throw new FileNotFoundException("脚本文件:"+ScriptFile+" 对象表示的不是一个标准文件！");
	  		}
	  		//测试应用程序是否可以读取此抽象路径名表示的文件。
	  		if(!ScriptFile.canRead()){
	  				throw new FileNotFoundException("脚本文件:"+ScriptFile+" 无法读取！");
	  		}
    		this.ScriptFile = ScriptFile;
    }
    
    
    
    private boolean isAlive;
    private Thread WorkThread;
		private File ScriptFile;
    
    public smnyTcpServer() throws IOException {
        super();
    }
    public smnyTcpServer(int port) throws IOException {
        super(port);
    }
    public smnyTcpServer(int port, int backlog) throws IOException {
        super(port,backlog);
    }
    public smnyTcpServer(int port, int backlog, InetAddress bindAddr) throws IOException {
        super(port,backlog,bindAddr);
    }
    
    public Socket accept()throws IOException{
    		return null;
    }
    public void bind(SocketAddress endpoint)throws IOException{
    		super.bind(endpoint);
    }
    public void bind(SocketAddress endpoint,int backlog)throws IOException{
    		super.bind(endpoint,backlog);
    }
    public void close()throws IOException{
    		isAlive = false;
    		super.close();
    		executorService.shutdown();
    }
    public void start(){
        if (isAlive) {
            return;
        }
        WorkThread = new Thread(this);
        isAlive = true;
        WorkThread.start();
    }
    public ScriptEngine getScriptEngine()throws ScriptException,IOException{
        FileReader FR = null;
        try{
        		FR = new FileReader(ScriptFile);
		        ScriptEngine engine = Manager.getEngineByName("JavaScript");
		        engine.eval(FR);
		        return engine;
        }finally{
        		if(FR != null){
        				FR.close();
        		}
        }
    }
    public TcpWork getWorkThread(Socket socket)throws ScriptException,IOException{
        TcpWork WorkThread = new TcpWork(socket);
        ScriptEngine engine = getScriptEngine();
        WorkThread.setCallback(engine);
        return WorkThread;
    }
    
    
    
    
    public void run() {
        System.out.println("服务器启动");
        Socket socket = null;
        
        while (isAlive) {
            try {
                //接收客户连接,只要客户进行了连接,就会触发accept();从而建立连接
                socket = super.accept();
                Submit(getWorkThread(socket));
            } catch (SocketTimeoutException e) {
                //e.printStackTrace();
                log.info("网络超时",e);
            } catch (SocketException e) {
                e.printStackTrace();
		            if (socket != null) try {
		                socket.close();
		            } catch (IOException ex) {
		                ex.printStackTrace();
		            }
            } catch (IOException e) {
                e.printStackTrace();
		            if (socket != null) try {
		                socket.close();
		            } catch (IOException ex) {
		                ex.printStackTrace();
		            }
            } catch (Exception e) {
                e.printStackTrace();
		            if (socket != null) try {
		                socket.close();
		            } catch (IOException ex) {
		                ex.printStackTrace();
		            }
            }
        }
        try {
            close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}