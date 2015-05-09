package RouterGuide.WeiXin;

import smny.util.SetSystemProperty;
import smny.util.StringToolkit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import RouterGuide.WeiXin.WxWebPage;
import RouterGuide.WeiXin.mp.WXBizMsgCrypt;
import smny.MyBatisFactory;
/**
 * PACKAGE_NAME： webaction
 * <p/>
 * <公众平台设置向导>
 * <p/>
 * 作者：  SatanWang
 * 创建时间： 14-2-12.
 */
@WebServlet(name = "PPWizard", urlPatterns = "/PPWizard",loadOnStartup=1)
public class PPWizard extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        //response.setContentType("text/html; charset=UTF-8");
        
        HttpSession session = request.getSession();
        Object ut = session.getAttribute("UserType");
        SetSystemProperty properties = new SetSystemProperty();
        if (ut == null) {
            response.sendRedirect("/login.html");
            return;
        }
        String UserType = ut.toString();

        //商户
        if ("4".equals(UserType)) {

            String BusinesID = request.getParameter("BusinesID");
            String LoginName = request.getParameter("LoginName");
            String Password = request.getParameter("Password");
            if (StringToolkit.isNullOrEmpty(BusinesID)) {
                response.getWriter().println("商家ID为空!");
                return;
            }
            if (StringToolkit.isNullOrEmpty(LoginName, Password)) {
                response.getWriter().println("账户名密码为空!");
                return;
            }
            Map<String,Object> appEntrance = new HashMap<String,Object>();
                appEntrance.put("Email",LoginName);
                appEntrance.put("LoginPassword",Password);
                appEntrance.put("AppType",1);
                appEntrance.put("BusinesShopID",BusinesID);

            WeiXinPlatformSynchronous WXPS = new WeiXinPlatformSynchronous(appEntrance);
            try {
                WXPS.Create();
                request.setAttribute("Operat","1");
				request.setAttribute("ErrorMessage","消息接口创建成功！！");
				request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
            } catch (Exception e) {
    			request.setAttribute("Operat","-5");
    			request.setAttribute("ErrorMessage",e.getMessage().replace("\r\n", ""));
    			request.setAttribute("ShowBug",properties.getValue("ShowBug"));
    			request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
    			request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
                return;
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        /*response.setContentType("text/html; charset=UTF-8");*/
        String AppEntranceID = request.getParameter("AppEntranceID");
        SetSystemProperty properties = new SetSystemProperty();
        if (StringToolkit.isNullOrEmpty(AppEntranceID)) {
        	/*response.getWriter().println("alert('无此接口！');window.location='/AppEntranceManager.htm';");*/
        	request.setAttribute("Operat","3");
			request.setAttribute("ErrorMessage","无此消息接口");
			request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
            return;
        }
        SqlSession sqlSession = MyBatisFactory.openSession();
        try {
            Map<String,Object> appEntrance = sqlSession.selectOne("RouterBatis.AppEntranceMapper.SelectEntranceID",AppEntranceID);
            WeiXinPlatformSynchronous WXPS = new WeiXinPlatformSynchronous(appEntrance);
            WXPS.Update();
            sqlSession.update("RouterBatis.AppEntranceMapper.updateEntrance",appEntrance);
            sqlSession.commit();
			request.setAttribute("Operat","1");
			request.setAttribute("ErrorMessage","同步更新成功");
			request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
        } catch (Exception e) {
           /* response.getWriter().println("alert('更新错误："+e.toString()+"');window.location='/AppEntranceManager.htm';");*/
        	request.setAttribute("Operat","-4");
			request.setAttribute("ErrorMessage","同步更新错误：" + e.getMessage().replace("\r\n", ""));
			request.setAttribute("ShowBug",properties.getValue("ShowBug"));
			request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
			request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
            return;
        }finally{
	          sqlSession.close();
	    }
        /*response.getWriter().println("alert('更新成功!');window.location='/AppEntranceManager.htm';");*/
        return;
    }
	public static class WeiXinPlatformSynchronous implements Runnable {
		//数据库连接代理对象  
		private Map<String, Object> AppEntrance;
		//数据库连接代理对象  
		private WxWebPage wxWebPage;
		
		public WeiXinPlatformSynchronous(Map<String,Object> appEntrance){
			this.AppEntrance = appEntrance;
			wxWebPage = new WxWebPage(appEntrance.get("Email").toString(),appEntrance.get("LoginPassword").toString());
			//System.out.println("Email="+appEntrance.get("Email").toString()+"\r\nLoginPassword="+appEntrance.get("LoginPassword").toString());
		}
		public boolean Login()throws Exception{
            if (!wxWebPage.Login()) {
            		AppEntrance.put("State",-1);
                throw new Exception("账户名密码验证不通过,请检测后输入正确账户名密码!");
            }
            return true;
		}
	    
		public boolean Create()throws Exception{
        Login();
        SqlSession sqlSession = MyBatisFactory.openSession();
          try {
            Map<String,Object> WxConfig = null;
            Map<String,Object> DevConfig = null;
            try{
	              WxConfig = wxWebPage.getWxConfig();
		            DevConfig = wxWebPage.getDevConfig();
		            wxWebPage.OpenDevMode();
		            if(wxWebPage.isAuthenticate() && wxWebPage.isServerNo()){
		            		wxWebPage.setOauthDomain();
		            }
            }catch (Exception ex) {
                AppEntrance.put("State",-2);
                ex.printStackTrace();
                throw new Exception("信息获取失败:",ex);
            }
              String Dev = DevConfig.get("dev").toString();
              String AppID = DevConfig.get("AppId").toString();
              String ThirdURL = DevConfig.get("URL").toString();
              String ThirdToken = DevConfig.get("Token").toString();
              String EncodingAesKey = DevConfig.get("EncodingAesKey").toString().trim();
              
              String SearchAccount = WxConfig.get("微信号").toString();
              String OriginNumber = WxConfig.get("原始ID").toString();
              String AppName = WxConfig.get("名称").toString();
              String Introduction = WxConfig.get("介绍").toString();
              
              Map<String,Object> Entrance = sqlSession.selectOne("RouterBatis.AppEntranceMapper.SelectOriginNumber",OriginNumber);
              if(Entrance != null){
              		Entrance.putAll(AppEntrance);
              }else{
              		Entrance = AppEntrance;
              }
              Entrance.put("AppID",AppID);
              
              Entrance.put("AppName",AppName);
              Entrance.put("Introduction",Introduction);
              Entrance.put("OriginNumber",OriginNumber);
              Entrance.put("SearchAccount",SearchAccount);
              Entrance.put("Authenticate",wxWebPage.isAuthenticate()&&wxWebPage.isServerNo());
              
              if(StringToolkit.isNullOrEmpty(EncodingAesKey) || EncodingAesKey.length()!=43){
	              	Entrance.put("EncodingAesKey",WXBizMsgCrypt.getRandomStr(43));
              }else{
	              	Entrance.put("EncodingAesKey",EncodingAesKey);
              }
              if(StringToolkit.isNullOrEmpty(ThirdToken) || ThirdToken.length()<3 || ThirdToken.length()>32){
	              	Entrance.put("AppToken",WXBizMsgCrypt.getRandomStr(17));
              }else{
	              	Entrance.put("AppToken",ThirdToken);
              }
              
	            File HeadPortrait = new File(new File(WxWebPage.class.getResource("PPWizard.class").toURI()).getParentFile().getParentFile().getParentFile().getParentFile().getParentFile(),"HeadPortrait");
	            
	            if(!HeadPortrait.exists()){
	            		HeadPortrait.mkdirs();
	            }
	            wxWebPage.DownloadFile((String)WxConfig.get("头像"),new File(HeadPortrait,OriginNumber+"_LoGo.jpg"));
	            wxWebPage.DownloadFile((String)WxConfig.get("二维码"),new File(HeadPortrait,OriginNumber+"_QrCode.jpg"));
              
              
              
              
              if(Entrance == AppEntrance){
              		sqlSession.insert("RouterBatis.AppEntranceMapper.insertEntrance",Entrance);
              		sqlSession.commit();
              }else{
              		sqlSession.update("RouterBatis.AppEntranceMapper.ReplacementBusinesShop",Entrance);
              		sqlSession.commit();
              		Map<String,Object> NowEntrance = sqlSession.selectOne("RouterBatis.AppEntranceMapper.SelectOriginNumber",OriginNumber);
              		NowEntrance.put("NowAppEntranceID",NowEntrance.get("AppEntranceID"));
              		NowEntrance.put("OriginalAppEntranceID",Entrance.get("AppEntranceID"));
              		sqlSession.update("RouterBatis.FollowersMapper.ReplacementAppEntranceID",NowEntrance);
              		sqlSession.commit();
              		Entrance.putAll(NowEntrance);
              		NowEntrance.clear();
              }
              
              
              String TemThirdURL = "http://sys.txly.com/wxapi?uid="+AppID;
              if(!ThirdURL.equals(TemThirdURL) || !EncodingAesKey.equals(Entrance.get("EncodingAesKey")) || !ThirdToken.equals(Entrance.get("ThirdToken"))){
	                try{
			                /*
			                	System.out.println("TemThirdURL=" + TemThirdURL + ",");
			                	System.out.println("AppToken=" + Entrance.get("AppToken") + ",");
			                	System.out.println("EncodingAesKey=" + Entrance.get("EncodingAesKey") + ",");
			                */
	                    //String uRL, String Token,int CallbackEncryptMode,String EncodingAesKey
	                    if(!wxWebPage.ChangeURLToken(TemThirdURL,Entrance.get("AppToken").toString(),0,Entrance.get("EncodingAesKey").toString())){
	                        Entrance.put("State",-4);
	                        AppEntrance.put("State",-4);
	                        throw new RuntimeException("设置URLToken失败,请手动设置");
	                    }
	                    if(!ThirdURL.startsWith("http://sys.txly.com/wxapi?uid=")){
	                    		Entrance.put("AppURL",ThirdURL);
	                    }
	                }catch (RuntimeException ex) {
	                    throw ex;
	                }catch (Exception ex) {
	                    ex.printStackTrace();
	                    Entrance.put("State",-5);
	                    AppEntrance.put("State",-5);
	                    throw new Exception("设置URLToken失败:",ex);
	                }
              }
              Entrance.put("State",1);
              AppEntrance.put("State",1);
              AppEntrance.put("LogoURL","http://sys.txly.com/HeadPortrait/"+OriginNumber+"_LoGo.jpg");
              System.out.println(AppEntrance.get("BusinesShopID")+".LogoURL==>"+AppEntrance.get("LogoURL"));
              sqlSession.update("RouterBatis.AppEntranceMapper.updateEntrance",Entrance);
              sqlSession.update("RouterBatis.BusinesShopMapper.updateLogoURL",AppEntrance);
              sqlSession.commit();
              return true;
          } catch (Exception e) {
              e.printStackTrace();
              throw e;
          }finally{
          		sqlSession.close();
          }
			}
		public boolean Update()throws Exception{
        Login();
        SqlSession sqlSession = MyBatisFactory.openSession();
          try {
            Map<String,Object> WxConfig = null;
            Map<String,Object> DevConfig = null;
            try{
		              WxConfig = wxWebPage.getWxConfig();
			            DevConfig = wxWebPage.getDevConfig();
			            wxWebPage.OpenDevMode();
			            if(wxWebPage.isAuthenticate() && wxWebPage.isServerNo()){
			            		wxWebPage.setOauthDomain();
			            }
            }catch (Exception ex) {
                AppEntrance.put("State",-2);
                ex.printStackTrace();
                throw new Exception("信息获取失败:",ex);
            }
              String Dev = DevConfig.get("dev").toString();
              String AppID = DevConfig.get("AppId").toString();
              String ThirdURL = DevConfig.get("URL").toString();
              String ThirdToken = DevConfig.get("Token").toString();
              String EncodingAesKey = DevConfig.get("EncodingAesKey").toString().trim();
              
              String SearchAccount = WxConfig.get("微信号").toString();
              String OriginNumber = WxConfig.get("原始ID").toString();
              String AppName = WxConfig.get("名称").toString();
              String Introduction = WxConfig.get("介绍").toString();
	            
              AppEntrance.put("AppID",AppID);
              AppEntrance.put("AppName",AppName);
              AppEntrance.put("Introduction",Introduction);
              AppEntrance.put("OriginNumber",OriginNumber);
              AppEntrance.put("SearchAccount",SearchAccount);
              AppEntrance.put("Authenticate",wxWebPage.isAuthenticate()&&wxWebPage.isServerNo());
              
              if(StringToolkit.isNullOrEmpty(EncodingAesKey) || EncodingAesKey.length()!=43){
	              	String TemAesKey = AppEntrance.get("EncodingAesKey").toString();
	              	if(StringToolkit.isNullOrEmpty(TemAesKey) || TemAesKey.length()!=43){
			              	AppEntrance.put("EncodingAesKey",WXBizMsgCrypt.getRandomStr(43));
		              }
              }else if(!EncodingAesKey.equals(AppEntrance.get("EncodingAesKey"))){
              		AppEntrance.put("EncodingAesKey",EncodingAesKey);
              }
              
              if(StringToolkit.isNullOrEmpty(ThirdToken) || ThirdToken.length()<3 || ThirdToken.length()>32){
	              	String TemToken = AppEntrance.get("ThirdToken").toString();
	              	if(StringToolkit.isNullOrEmpty(TemToken) || TemToken.length()<3 || TemToken.length()>32){
			              	AppEntrance.put("ThirdToken",WXBizMsgCrypt.getRandomStr(17));
		              }
              }else if(!ThirdToken.equals(AppEntrance.get("ThirdToken"))){
              		AppEntrance.put("ThirdToken",ThirdToken);
              }
	            File HeadPortrait = new File(new File(WxWebPage.class.getResource("PPWizard.class").toURI()).getParentFile().getParentFile().getParentFile().getParentFile().getParentFile(),"HeadPortrait");
	            
	            if(!HeadPortrait.exists()){
	            		HeadPortrait.mkdirs();
	            }
	            wxWebPage.DownloadFile((String)WxConfig.get("头像"),new File(HeadPortrait,OriginNumber+"_LoGo.jpg"));
	            wxWebPage.DownloadFile((String)WxConfig.get("二维码"),new File(HeadPortrait,OriginNumber+"_QrCode.jpg"));
              
              AppEntrance.put("LogoURL","http://sys.txly.com/HeadPortrait/"+OriginNumber+"_LoGo.jpg");
              System.out.println(AppEntrance.get("BusinesShopID")+".LogoURL==>"+AppEntrance.get("LogoURL"));
              sqlSession.update("RouterBatis.BusinesShopMapper.updateLogoURL",AppEntrance);
              sqlSession.update("RouterBatis.AppEntranceMapper.updateEntrance",AppEntrance);
              sqlSession.commit();
              
              String TemThirdURL = "http://sys.txly.com/wxapi?uid="+AppID;
              if(!ThirdURL.equals(TemThirdURL) || !EncodingAesKey.equals(AppEntrance.get("EncodingAesKey")) || !ThirdToken.equals(AppEntrance.get("ThirdToken"))){
	                try{
	                    //String uRL, String Token,int CallbackEncryptMode,String EncodingAesKey
	                    if(!wxWebPage.ChangeURLToken(TemThirdURL,AppEntrance.get("AppToken").toString(),0,AppEntrance.get("EncodingAesKey").toString())){
	                        AppEntrance.put("State",-4);
	                        throw new RuntimeException("设置URLToken失败,请手动设置");
	                    }
	                    if(!ThirdURL.startsWith("http://sys.txly.com/wxapi?uid=")){
	                    		AppEntrance.put("AppURL",ThirdURL);
	                    }
	                }catch (RuntimeException ex) {
	                    throw ex;
	                }catch (Exception ex) {
	                    ex.printStackTrace();
	                    AppEntrance.put("State",-5);
	                    throw new Exception("设置URLToken失败:",ex);
	                }
              }
              AppEntrance.put("State",1);
              return true;
          } catch (Exception e) {
              //e.printStackTrace();
              throw e;
          }finally{
          		sqlSession.close();
          }
			}
			public void run(){
          try {
            Update();
          } catch (Exception e) {
              e.printStackTrace();
          }
			}
	}
    public static void main(String args[]){
                String LoginName = "qinwen133@163.com";
                String Password = "265913jie";
                
            Map<String,Object> appEntrance = new HashMap<String,Object>();
                appEntrance.put("Email",LoginName);
                appEntrance.put("LoginPassword",Password);
                appEntrance.put("AppType",1);
                appEntrance.put("BusinesShopID","1_smny");
                WeiXinPlatformSynchronous WXPS = new WeiXinPlatformSynchronous(appEntrance);
                try {
                    WXPS.Create();		                
		                System.out.println("State="+appEntrance.get("State"));
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                System.out.println("创建成功!");
        
    }
}
