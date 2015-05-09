package RouterGuide.WeiXin;


import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


import java.io.File;
import java.io.InputStream;
import java.io.FileOutputStream;


import java.net.URI;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import smny.util.JSON2Java;
import smny.util.StringToolkit;



/**
 * PACKAGE_NAME： com.stan.wxwebpage
 *
 * 微信网页抓取
 *
 * 作者：  Satan
 * 创建时间： 14-6-23.
 */
public class WxWebPage {


    private CloseableHttpClient HttpClient;
    private String token;
    private String loginname;
    private String password;
    private Random random = new Random();
    private boolean islogin = false;
    
    
    private boolean selfMenu;								//自定义菜单可用
    private boolean editOpen;								//编辑模式是否开启
    private boolean ServerNo;								//开启关闭开发者模式
    private boolean open;								//开启关闭开发者模式
    private int dev;										//开发者状态0非开发者1、2开发者3企业开发者
    
    private boolean Authenticate;				//是否认证
    private String authUrl;							//OAuth2.0网页授权地址
    
    
    private String operation_seq;							//OAuth2.0网页授权地址
    
    
    public boolean isServerNo() {
        return ServerNo;
    }
    public boolean isAuthenticate() {
        return Authenticate;
    }
    public int getType() {
        return dev;
    }
    public String getAuthUrl() {
        return authUrl;
    }
    
    private void init() {
        HttpClient = HttpClients.custom().build();
        token = "";
    }

    public WxWebPage(String name, String password) {
        this.loginname = name;
        this.password = password;
        init();
    }

    public boolean islogin() {
        return islogin;
    }





    //http实体内容处理
    public String HttpResponseToString(HttpUriRequest Request)throws Exception{
        String Content = "";
        CloseableHttpResponse response = HttpClient.execute(Request);
        if(response == null){
        		return Content;
        }
				try{
						HttpEntity entity = response.getEntity();
						if(entity == null){
								return Content;
						}
						Content = EntityUtils.toString(entity);
						EntityUtils.consume(entity);
				} finally {
						response.close();
				}
        return Content;
    }



    //登录
    public boolean Login() {
        String Content = "";
        try {
            HttpUriRequest login = RequestBuilder.post()
                    .setUri(new URI("https://mp.weixin.qq.com/cgi-bin/login?lang=zh_CN"))
                    .setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.172 Safari/537.22")
                    .setHeader("Referer", "https://mp.weixin.qq.com/")
                    .addParameter("username", loginname)
                    .addParameter("pwd", DigestUtils.md5Hex(password.getBytes()))
                    .addParameter("imagecode", "")
                    .build();
            Content = HttpResponseToString(login);
		        if (Content.contains("ok")) {
		            //System.out.println("登录成功!");
		            token = getToken(Content);
		            islogin = true;
		        }
            return islogin;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 设置授权网址
     *
     * @param domain 网址域名
     *
     * @return 是否登录成功
     */
    public boolean setOauthDomain() throws Exception {
        CheckStatus();
        if(Authenticate && ServerNo && !"sys.wanwifi.com".equals(authUrl)){
        		return true;
        }
        try {
            String Content = "";
            HttpUriRequest set_oauth = RequestBuilder.post()
                    .setUri(new URI("https://mp.weixin.qq.com/merchant/myservice?action=set_oauth_domain&f=json"))
                    .setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.172 Safari/537.22")
                    .setHeader("Referer", "https://mp.weixin.qq.com/")
                    .addParameter("token", token)
                    .addParameter("lang", "zh_CN")
                    .addParameter("ajax", "1")
                    .addParameter("random", Double.toString(random.nextDouble()))
                    .addParameter("domain", "sys.wanwifi.com")
                    .build();
            Content = HttpResponseToString(set_oauth);
		        if (Content.contains("ok")) {
		            System.out.println("授权网址设置成功!");
		            token = getToken(Content);
		            return true;
		        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //获取公众帐号配置信息
    public Map<String, Object> getWxConfig() throws Exception {
        CheckStatus();
        String Content = "";
        try {
            HttpUriRequest getWxConfig = RequestBuilder.get()
                    .setUri(new URI("https://mp.weixin.qq.com/cgi-bin/settingpage?t=setting/index&action=index&lang=zh_CN&token=" + token))
                    .setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.172 Safari/537.22")
                    .setHeader("Referer", "https://mp.weixin.qq.com/")
                    .build();
            Content = HttpResponseToString(getWxConfig);
            Map<String, Object> WxConfig = getWxConfigInfo(Content);
            return WxConfig;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    
    /**
     * 正则获取公众帐号信息
     *
     * @param con
     * @return 头像==XXXX
     * 名称==XXXX
     * 登录邮箱==XXXX
     * 原始ID==XXXX
     * 微信号==XXXX
     * 隐私设置==已允许（是否允许用户通过昵称搜到该帐号,但通过id和二维码可以搜到）
     * 类型==服务号
     * 认证情况==XXXX
     * 功能介绍==XXXX
     * 腾讯微博==未绑定绑定腾讯微博后，你可以将群发的消息同步到腾讯微博。
     * 二维码==
     */
    public Map<String, Object> getWxConfigInfo(String con) {
        if (StringToolkit.isNullOrEmpty(con)) {
            return null;
        }
        Map<String, Object> resualt = new HashMap<String, Object>();
        con = con.replaceAll("\r|\n|\\s|\"", "");

				String gkxx = con.replaceAll(".*<h3class=sub_title>公开信息</h3><ul>(.*)</ul></div><divclass=account_setting_area>.*", "$1");
				
        Pattern lipattern = Pattern.compile("<liclass=account_setting_item><h4>(.*?)[</h4><divclass=meta_opr>|</h4><divclass=meta_oprverify>](.*?)</div><divclass=meta_content>(.*?)</div></li");
        Matcher limather = lipattern.matcher(gkxx.replace("account_setting_itemwrp_pic_item_spe2","account_setting_item").replace("account_setting_itemwrp_pic_item_spe1","account_setting_item"));
        while (limather.find()) {
            String Name = limather.group(1).replaceAll("<.+?>","");
            String Value = limather.group(3);
            //System.out.println(Value);
            if("头像".equals(Name) && Value.contains("src=/misc/getheadimg")){
            		//<imgclass=meta_picsrc=/misc/getheadimg?token=1117114762&fakeid=3072156916&r=48877/>
            		Value = "https://mp.weixin.qq.com" + Value.replaceAll(".*<imgclass=meta_picsrc=(.*)/>.*","$1");
            }else if("二维码".equals(Name) && Value.contains("src=/misc/getqrcode")){
            		Value = "https://mp.weixin.qq.com" + Value.replaceAll(".*<imgsrc=(.*)class.*","$1");
            }else{
            		Value = Value.replaceAll("<.+?>", "");
            }
            //System.out.println(Name+"="+Value);
            resualt.put(Name,Value);
        }
        Authenticate = "微信认证".equals(resualt.get("认证情况"));
        ServerNo = "服务号".equals(resualt.get("类型"));
				
				
				//System.out.println("----------------------------------------------------------------------");
				
				String zcxx = con.replaceAll(".*<h3class=sub_title>注册信息</h3><ul>(.*)</ul></div></div></div></div><divclass=faq>.*", "$1");
				//System.out.println(zcxx);

        //lipattern = Pattern.compile("<liclass=account_setting_item><h4>(.*?)[</h4><divclass=meta_opr>|</h4><divclass=meta_oprverify>](.*?)</div><divclass=meta_content>(.*?)</div></li");
        limather = lipattern.matcher(zcxx);
        while (limather.find()) {
            resualt.put(limather.group(1).replaceAll("<.+?>", ""), limather.group(3).replaceAll("<.+?>", ""));
        }
        
        
        /*
        
        
        
        */
        
        
        
        return resualt;
    }

    //获取开发者参数
    public Map<String, Object> getDevConfig() throws Exception {
        CheckStatus();
        String Content = "";
        try {
            HttpUriRequest getWxConfig = RequestBuilder.get()
                    .setUri(new URI("https://mp.weixin.qq.com/advanced/advanced?action=dev&t=advanced/dev&lang=zh_CN&token=" + token))
                    .setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.172 Safari/537.22")
                    .setHeader("Referer", "https://mp.weixin.qq.com/")
                    .build();
            Content = HttpResponseToString(getWxConfig);
		        String ScriptString = Content.replaceAll("\r|\n", "/r/n");
		        Map<?,?> DevConfig = (Map<?,?>)JSON2Java.parseJSON(ScriptString.replaceAll(".*wx.cgiData =(.*?);.*", "$1").replaceAll("/r/n", "\r\n"));
		        /*
		        			editOpen:"0"==="1"?true:false,//编辑模式是否开启
									open:"1"==="1"?true:false,//开启关闭开发者模式
									authUrl:"sys.wanwifi.com",
									selfMenu : '0' === "1" ? true : false,//自定义菜单可用
									operation_seq: "201351078",
									dev :'2',//开发者状态0非开发者1、2开发者3企业开发者
									//开发者信息
									devInfo:[
											{name:"URL",value:"http://sys.wanwifi.com/wxapi?uid=gh48cab5f4289e"},
											{name:"Token",value:"531ba6dd95339c0a6b174d18c05a6c9a"},
											{name:"AppId",value:"wxc5f5d9fe09e87269"},
											{name:"AppSecret",value:""},
											{name:"Type",value:"2"}
									],
		        */
		        operation_seq = DevConfig.get("operation_seq").toString();
		        dev = Integer.parseInt(DevConfig.get("dev").toString());
		        open = ((Boolean)DevConfig.get("open")).booleanValue();
		        authUrl = DevConfig.get("authUrl").toString();
		        
		        HashMap<String, Object> Resualt = new HashMap<String, Object>();
		        Resualt.put("dev",String.valueOf(dev));
		        Resualt.put("open",String.valueOf(open));
		        Resualt.put("authUrl",authUrl);
		        Resualt.put("operation_seq",operation_seq);
		        Resualt.put("EncodingAesKey",ScriptString.replaceAll(".*EncodingAESKey(.*?)</div>.*","$1").replaceAll("/r/n|\"|\\s", "").replaceAll(".*frm_controlsfrm_vertical_pt>(.*?)$","$1"));
		        
	        	Object[] devInfo = (Object[])DevConfig.get("devInfo");
	        	for(Object Tem:devInfo){
		        		Map<?,?> Info = (Map<?,?>)Tem;
		        		Resualt.put(Info.get("name").toString(),Info.get("value").toString());
	        	}
            return Resualt;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //发送心跳请求
    public void SendBeatingRequest() throws Exception {
        CheckStatus();
        try {
            HttpUriRequest SendBeatingRequest = RequestBuilder.get()
                    .setUri(new URI("https://mp.weixin.qq.com/advanced/advanced?action=dev&t=advanced/dev&lang=zh_CN&f=json&token=" + token))
                    .setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.172 Safari/537.22")
                    .addParameter("action", "dev")
                    .addParameter("t", "advanced/dev")
                    .addParameter("token",token)
                    .addParameter("lang", "zh_CN")
                    .addParameter("f", "json")
                    .setHeader("Referer", "https://mp.weixin.qq.com/advanced/advanced?action=dev&t=advanced/dev&lang=zh_CN&token=" + token)
                    .build();
            HttpResponseToString(SendBeatingRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取指定FakeID的用户信息
    @SuppressWarnings("unchecked")
    public Map<String, Object> getUserInfo(String fakeid) throws Exception {
        CheckStatus();
        try {
            HttpUriRequest getuserinfo = RequestBuilder.post()
                    .setUri(new URI("https://mp.weixin.qq.com/cgi-bin/getcontactinfo?t=ajax-getcontactinfo&lang=zh_CN&fakeid="+fakeid))
                    .setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.172 Safari/537.22")
                    .setHeader("Referer", "https://mp.weixin.qq.com/cgi-bin/contactmanage?t=user/index&pagesize=10&pageidx=0&type=0&lang=zh_CN&token=" + token)
                    .addParameter("token", token)
                    .addParameter("random", Double.toString(random.nextDouble()))
                    .addParameter("f", "json")
                    .addParameter("ajax", "1")
                    .build();
            String Content = HttpResponseToString(getuserinfo);
            Map<?,?> UserInfo = (Map<?,?>)JSON2Java.parseJSON(Content);
            return (Map<String, Object>)UserInfo.get("contact_info");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(content);
        return null;
    }

    //开启开发模式
    public boolean OpenDevMode() throws Exception {
    		if(open){
    				return true;
    		}
    		return SwitchMode("2",true);
    }
    //关闭开发模式
    public boolean CloseDevMode() throws Exception {
    		if(!open){
    				return true;
    		}
    		return SwitchMode("2",false);
    }
    public boolean SwitchMode(String type,boolean flag) throws Exception {
        CheckStatus();
        try {
            String Content = "";
            HttpUriRequest OpenDevMode = RequestBuilder.post()
                    .setUri(new URI("https://mp.weixin.qq.com/misc/skeyform?form=advancedswitchform&lang=zh_CN"))
                    .setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.172 Safari/537.22")
                    .setHeader("Referer", "https://mp.weixin.qq.com/advanced/advanced?action=dev&t=advanced/dev&lang=zh_CN&token=" + token)
                    .addParameter("token", token)
                    .addParameter("lang", "zh_CN")
                    .addParameter("ajax", "1")
                    .addParameter("random",Double.toString(random.nextDouble()))
                    .addParameter("flag", flag?"1":"0")
                    .addParameter("type", type)
                    .build();
            open = flag;
            Content = HttpResponseToString(OpenDevMode);
		        if (Content.contains("ok")) {
		            SendBeatingRequest();
		            return true;
		        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 变更第三方平台URL和Token
     *
     * @param URL                    --必须以http://开头，目前支持80端口
     * @param Token                  --必须为英文或数字，长度为3-32字符。
     * @param callback_encrypt_mode  --明文模式:0,兼容模式:1,安全模式:2
     * @param encoding_aeskey        --消息加密密钥由43位字符组成，可随机修改，字符范围为A-Z，a-z，0-9。
     * @throws Exception 错误代码{
     *                   "-201" : "无效的URL",
     *                   "-202" : "无效的Token",
     *                   "-203" : "操作频率太快，请休息一下。",
     *                   "-204" : "请先在设置页面完善当前帐号信息",
     *                   "-301" : "请求URL超时",
     *                   "-302" : "你的服务器没有正确响应Token验证，请阅读消息接口使用指南",
     *                   "-205" : "该URL可能存在安全风险，请检查"
     *                   }
     */
    public boolean ChangeURLToken(String uRL, String Token,int CallbackEncryptMode,String EncodingAesKey) throws Exception {
        try {
            String Content = "";
            HttpUriRequest URLToken = RequestBuilder.post()
                    .setUri(new URI("https://mp.weixin.qq.com/advanced/callbackprofile?t=ajax-response&lang=zh_CN&token=" + token))
                    .setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.172 Safari/537.22")
                    .setHeader("Referer", "https://mp.weixin.qq.com/advanced/advanced?action=interface&t=advanced/interface&lang=zh_CN&token=" + token)
                    .addParameter("url", uRL)
                    .addParameter("callback_token", Token)
                    .addParameter("operation_seq", operation_seq)
                    .addParameter("callback_encrypt_mode",String.valueOf(CallbackEncryptMode))
                    .addParameter("encoding_aeskey",EncodingAesKey)
                    .build();
            Content = HttpResponseToString(URLToken);
		        if (Content.contains("ok")) {
		            return true;
		        }
		        System.out.println(Content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public void CheckStatus(){
        if (!islogin && !Login()) {
            throw new NullPointerException("登录失败!");
        }
    }
    
    //正则获取网页验证标识Token
    public static String getToken(String str) {
        Pattern p = Pattern.compile("token=(\\d+)\"");
        Matcher m = p.matcher(str);
        String TEM = "";
        while (m.find()) {
            TEM = m.group(1);
        }
        //System.out.println(str+"\r\n\t"+TEM);
        return TEM;
    }
    
    
    //File HeadPortrait = new File(new File(WxWebPage.class.getResource("WxWebPage.java").toURI()).getParentFile().getParentFile().getParentFile().getParentFile().getParentFile(),"HeadPortrait/"+WxConfig.get("原始ID")+".jpg");
    public void DownloadFile(String FileUrl, File SaveFile) throws Exception {
        HttpUriRequest GetHeadPortrait = RequestBuilder.get()
                .setUri(new URI(FileUrl))
                .setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.172 Safari/537.22")
                .setHeader("X-DevTools-Emulate-Network-Conditions-Client-Id", "5DA9868C-056C-433A-8F6F-9EA2D191C294")
                .build();
        FileOutputStream out = null;
        CloseableHttpResponse response = HttpClient.execute(GetHeadPortrait);
        if(response != null)try{
						HttpEntity entity = response.getEntity();
						if(entity != null){
				        if(!SaveFile.getParentFile().exists()){
				        		SaveFile.getParentFile().mkdirs();
				        }
								out=new FileOutputStream(SaveFile);
								entity.writeTo(out);
								out.flush();
								EntityUtils.consume(entity);
						}
				} catch (Exception ex){
						ex.printStackTrace();
				} finally {
						if(out != null)try{
								out.close();
						}catch (Exception ex){}
						response.close();
				}
    }

    public static void main(String args[])throws Exception{
    	/*
        
        
    	*/
    		
        WxWebPage wxWebPage = new WxWebPage("heaven1982515@hotmail.com", "yeshan1010");
        wxWebPage.Login();
        Map<String, Object> WxConfig = null;
        Map<String, Object> DevConfig = null;
        try{
            WxConfig = wxWebPage.getWxConfig();
            println(WxConfig);
            
            System.out.println("========================================================================");
            
            File HeadPortrait = new File(new File(WxWebPage.class.getResource("WxWebPage.class").toURI()).getParentFile().getParentFile().getParentFile().getParentFile().getParentFile(),"HeadPortrait");
            
            if(!HeadPortrait.exists()){
            		HeadPortrait.mkdirs();
            }
            
            
	            wxWebPage.DownloadFile((String)WxConfig.get("头像"),new File(HeadPortrait,WxConfig.get("原始ID")+"_LoGo.jpg"));
	            wxWebPage.DownloadFile((String)WxConfig.get("二维码"),new File(HeadPortrait,WxConfig.get("原始ID")+"_QrCode.jpg"));
            
            
            
            
            
            
            
            
            
            //DevConfig = wxWebPage.getDevConfig();
            //println(DevConfig);
            
            
            //System.out.println("========================================================================");
            //println(wxWebPage.ChangeURLToken("http://sys.wanwifi.com/wxapi?uid=gh48cab5f4289e","531ba6dd95339c0a6b174d18c05a6c9a",0,"uk77ag0ox7rpFP1WsnDjKzoBzBNniUSeCIa3sXf2jRX"));
            //System.out.println(wxWebPage.ChangeURLToken("http://sys.wanwifi.com/wxapi?uid=gh48cab5f4289e","531ba6dd95339c0a6b174d18c05a6c9a",0,"uk77ag0ox7rpFP1WsnDjKzoBzBNniUSeCIa3sXf2jRX"));
            
            //uk77ag0ox7rpFP1WsnDjKzoBzBNniUSeCIa3sXf2jRX
            
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        
        
        
        
        
        
        
        
        
        
		    
    }
    //正则获取网页验证标识Token
    public static void println(Map<String, Object> map) {
        Iterator<Map.Entry<String,Object>> Ite = map.entrySet().iterator();
        while (Ite.hasNext()) {
            Map.Entry<String,Object> entry = Ite.next();
            System.out.println(entry.getKey()+"="+entry.getValue());
        }
    }

}
