package RouterGuide;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.apache.log4j.Logger;

import smny.net.tcp.smnyTcpServer;


public class GuideRouterServer extends smnyTcpServer{
    
    public static void main(String[] args)throws Exception{
        GuideRouterServer Guide = GuideRouterServer.getGuideRouterServer();
        
        Guide.start();
        
        
    }
    
    private final static Logger log= Logger.getLogger(GuideRouterServer.class);
    private final static AuthenPool AutPool = new AuthenPool();
    
    
  	//�û�����·�ɴ��ŵõ���֤��ַ
  	public static Authen getAuthen(String HardSeq){
    		return AutPool.getAuthen(HardSeq);
  	}
    
    
    
    private static GuideRouterServer Guide;
    private synchronized static void newInstance()throws IOException{
        if(Guide == null){
        		Guide = new GuideRouterServer(8323);
        		Guide.put("AuthenPool",AutPool);
        		//Guide.init("Config/GuideScript.js");
			      try{
		      			File ScriptFile = new File(GuideRouterServer.class.getResource("GuideScript.js").toURI());
		        		Guide.init(ScriptFile);
			      }catch (Exception e){
			         throw new AssertionError(e);
			      }
        }
    }
    public static GuideRouterServer getGuideRouterServer()throws IOException{
        if(Guide == null){
        		newInstance();
        }
        return Guide;
    }
    
    public GuideRouterServer(int port)throws IOException{
        super(port);
    }
    static class AuthenPool{
	    	//�õ�����е���֤������
	    	private final LinkedList<Authen> AuthenPool = new LinkedList<Authen>();
	    	//·�ɴ�������֤�Ĺ�ϵ���û������õ���֤��ַ��
	    	private final HashMap<String,Authen> RouterPool = new HashMap<String,Authen>();
	    	
	    	//��֤����
	    	public void AuthenStart(Map<String,Object> PMap){
		  			Authen Aut = new Authen();
		        Aut.setAuthenID((Integer)PMap.get("AuthenID"));
		        Aut.setAuthenName(new String((byte[])PMap.get("AuthenName")));
		        Aut.setConnectNumber((Integer)PMap.get("ConnectNumber"));
		        Aut.setMaxConnectNumber((Integer)PMap.get("MaxConnectNumber"));
		        Aut.setNetworkAddr(new String((byte[])PMap.get("NetworkAddr")));
		        Aut.setReportCode(new String((byte[])PMap.get("ReportCode")));
		        Aut.setWebPort((Integer)PMap.get("WebPort"));
		        Aut.setAuthenPort((Integer)PMap.get("AuthenPort"));
		  			AuthenStart(Aut);
	    	}
	    	//��֤����
	    	public void AuthenStart(Authen Aut){
		    		synchronized(AuthenPool){
				    		Iterator<Authen> ite = AuthenPool.iterator();
				    		while(ite.hasNext()){
				    				Authen AutTem = ite.next();
				    				if(AutTem.getAuthenID() == AutTem.getAuthenID()){
				    						ite.remove();
				    				}
				    		}
				    		AuthenPool.add(Aut);
		    		}
	    	}
	    	//��֤�˳�
	    	public void AuthenSignOut(String ReportCode){
		    		if(ReportCode == null){
		    				return;
		    		}
		    		synchronized(AuthenPool){
				    		Iterator<Authen> ite = AuthenPool.iterator();
				    		while(ite.hasNext()){
				    				Authen Aut = ite.next();
				    				if(ReportCode.equals(Aut.getReportCode())){
				    						ite.remove();
				    						return;
				    				}
				    		}
		    		}
		    		synchronized(RouterPool){
				    		Iterator<Map.Entry<String,Authen>> ite = RouterPool.entrySet().iterator();
				    		while(ite.hasNext()){
					    			Map.Entry<String,Authen> EntEle = ite.next();
				    				Authen Aut = EntEle.getValue();
				    				if(ReportCode.equals(Aut.getReportCode())){
				    						ite.remove();
				    				}
				    		}
		    		}
	    	}
	    	//�õ�����е���֤������
	    	public Authen getAuthen(){
		    		synchronized(AuthenPool){
				    		if(AuthenPool.size() > 0){
					    			Collections.sort(AuthenPool); 
					    			return AuthenPool.get(0);
				    		}
		    		}
		    		return null;
	    	}
	    	//�û�����·�ɴ��ŵõ���֤��ַ
	    	public Authen getAuthen(String HardSeq){
		    		synchronized(RouterPool){
				    		return RouterPool.get(HardSeq);
		    		}
	    	}
	    	//·�����ӳɹ�������֤����
	    	public void RouterAccess(String ReportCode,String HardSeq){
		    		if(ReportCode==null || HardSeq==null){
		    				return;
		    		}
		    		Authen AutTem = null;
		    		synchronized(AuthenPool){
				    		for(Authen Aut:AuthenPool){
				    				if(ReportCode.equals(Aut.getReportCode())){
				    						AutTem = Aut;
				    						break;
				    				}
				    		}
		    		}
		    		if(AutTem == null){
		    				return;
		    		}
		    		synchronized(RouterPool){
				    		RouterPool.put(HardSeq,AutTem);
		    		}
	    	}
	    	//·���˳����ӣ�����֤����
	    	public void RouterSignOut(String HardSeq){
		    		synchronized(RouterPool){
				    		RouterPool.remove(HardSeq);
		    		}
	    	}
	    	
	    	
	    	
	    	
	    	
	    	
    }
  	public static class Authen implements Comparable<Authen>{
  			private int AuthenID;
  			private String AuthenName;
  			private int ConnectNumber;
  			private int MaxConnectNumber;
  			private String NetworkAddr;
  			private String ReportCode;
  			private int WebPort;
  			private int AuthenPort;
  			
  			public Authen(){
    				
    				
  			}
  			public int getAuthenID(){
  					return AuthenID;
  			}
  			public void setAuthenID(int AuthenID){
  					this.AuthenID = AuthenID;
  			}
  			
  			public String getAuthenName(){
  					return AuthenName;
  			}
  			public void setAuthenName(String AuthenName){
  					this.AuthenName = AuthenName.trim();
  			}
  			
  			public int getConnectNumber(){
  					return ConnectNumber;
  			}
  			public void setConnectNumber(int ConnectNumber){
  					this.ConnectNumber = ConnectNumber;
  			}
  			
  			public int getMaxConnectNumber(){
  					return MaxConnectNumber;
  			}
  			public void setMaxConnectNumber(int MaxConnectNumber){
  					this.MaxConnectNumber = MaxConnectNumber;
  			}
  			
  			public String getNetworkAddr(){
  					return NetworkAddr;
  			}
  			public void setNetworkAddr(String NetworkAddr){
  					this.NetworkAddr = NetworkAddr.trim();
  			}
  			
  			public String getReportCode(){
  					return ReportCode;
  			}
  			public void setReportCode(String ReportCode){
  					this.ReportCode = ReportCode.trim();
  			}
  			
  			public int getSurplConnectNumber(){
  					return MaxConnectNumber-ConnectNumber;
  			}
  			public int getWebPort(){
  					return WebPort;
  			}
  			public void setWebPort(int WebPort){
  					this.WebPort = WebPort;
  			}
  			public int getAuthenPort(){
  					return AuthenPort;
  			}
  			public void setAuthenPort(int AuthenPort){
  					this.AuthenPort = AuthenPort;
  			}
  			
  			public int compareTo(Authen Aut){
    				if(Aut == this){
    						return 0;
    				}
    				if(Aut == null){
    						return 1;
    				}
    				if(this.getSurplConnectNumber() > Aut.getSurplConnectNumber()){
    						return -1;
    				}
    				return ReportCode.compareTo(Aut.getReportCode());
  			}
  			
  			
  	}
}