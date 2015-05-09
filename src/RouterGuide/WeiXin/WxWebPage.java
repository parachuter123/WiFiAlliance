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
 * PACKAGE_NAME�� com.stan.wxwebpage
 *
 * ΢����ҳץȡ
 *
 * ���ߣ�  Satan
 * ����ʱ�䣺 14-6-23.
 */
public class WxWebPage {


    private CloseableHttpClient HttpClient;
    private String token;
    private String loginname;
    private String password;
    private Random random = new Random();
    private boolean islogin = false;
    
    
    private boolean selfMenu;								//�Զ���˵�����
    private boolean editOpen;								//�༭ģʽ�Ƿ���
    private boolean ServerNo;								//�����رտ�����ģʽ
    private boolean open;								//�����رտ�����ģʽ
    private int dev;										//������״̬0�ǿ�����1��2������3��ҵ������
    
    private boolean Authenticate;				//�Ƿ���֤
    private String authUrl;							//OAuth2.0��ҳ��Ȩ��ַ
    
    
    private String operation_seq;							//OAuth2.0��ҳ��Ȩ��ַ
    
    
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





    //httpʵ�����ݴ���
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



    //��¼
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
		            //System.out.println("��¼�ɹ�!");
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
     * ������Ȩ��ַ
     *
     * @param domain ��ַ����
     *
     * @return �Ƿ��¼�ɹ�
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
		            System.out.println("��Ȩ��ַ���óɹ�!");
		            token = getToken(Content);
		            return true;
		        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //��ȡ�����ʺ�������Ϣ
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
     * �����ȡ�����ʺ���Ϣ
     *
     * @param con
     * @return ͷ��==XXXX
     * ����==XXXX
     * ��¼����==XXXX
     * ԭʼID==XXXX
     * ΢�ź�==XXXX
     * ��˽����==�������Ƿ������û�ͨ���ǳ��ѵ����ʺ�,��ͨ��id�Ͷ�ά������ѵ���
     * ����==�����
     * ��֤���==XXXX
     * ���ܽ���==XXXX
     * ��Ѷ΢��==δ�󶨰���Ѷ΢��������Խ�Ⱥ������Ϣͬ������Ѷ΢����
     * ��ά��==
     */
    public Map<String, Object> getWxConfigInfo(String con) {
        if (StringToolkit.isNullOrEmpty(con)) {
            return null;
        }
        Map<String, Object> resualt = new HashMap<String, Object>();
        con = con.replaceAll("\r|\n|\\s|\"", "");

				String gkxx = con.replaceAll(".*<h3class=sub_title>������Ϣ</h3><ul>(.*)</ul></div><divclass=account_setting_area>.*", "$1");
				
        Pattern lipattern = Pattern.compile("<liclass=account_setting_item><h4>(.*?)[</h4><divclass=meta_opr>|</h4><divclass=meta_oprverify>](.*?)</div><divclass=meta_content>(.*?)</div></li");
        Matcher limather = lipattern.matcher(gkxx.replace("account_setting_itemwrp_pic_item_spe2","account_setting_item").replace("account_setting_itemwrp_pic_item_spe1","account_setting_item"));
        while (limather.find()) {
            String Name = limather.group(1).replaceAll("<.+?>","");
            String Value = limather.group(3);
            //System.out.println(Value);
            if("ͷ��".equals(Name) && Value.contains("src=/misc/getheadimg")){
            		//<imgclass=meta_picsrc=/misc/getheadimg?token=1117114762&fakeid=3072156916&r=48877/>
            		Value = "https://mp.weixin.qq.com" + Value.replaceAll(".*<imgclass=meta_picsrc=(.*)/>.*","$1");
            }else if("��ά��".equals(Name) && Value.contains("src=/misc/getqrcode")){
            		Value = "https://mp.weixin.qq.com" + Value.replaceAll(".*<imgsrc=(.*)class.*","$1");
            }else{
            		Value = Value.replaceAll("<.+?>", "");
            }
            //System.out.println(Name+"="+Value);
            resualt.put(Name,Value);
        }
        Authenticate = "΢����֤".equals(resualt.get("��֤���"));
        ServerNo = "�����".equals(resualt.get("����"));
				
				
				//System.out.println("----------------------------------------------------------------------");
				
				String zcxx = con.replaceAll(".*<h3class=sub_title>ע����Ϣ</h3><ul>(.*)</ul></div></div></div></div><divclass=faq>.*", "$1");
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

    //��ȡ�����߲���
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
		        			editOpen:"0"==="1"?true:false,//�༭ģʽ�Ƿ���
									open:"1"==="1"?true:false,//�����رտ�����ģʽ
									authUrl:"sys.wanwifi.com",
									selfMenu : '0' === "1" ? true : false,//�Զ���˵�����
									operation_seq: "201351078",
									dev :'2',//������״̬0�ǿ�����1��2������3��ҵ������
									//��������Ϣ
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

    //������������
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

    //��ȡָ��FakeID���û���Ϣ
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

    //��������ģʽ
    public boolean OpenDevMode() throws Exception {
    		if(open){
    				return true;
    		}
    		return SwitchMode("2",true);
    }
    //�رտ���ģʽ
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
     * ���������ƽ̨URL��Token
     *
     * @param URL                    --������http://��ͷ��Ŀǰ֧��80�˿�
     * @param Token                  --����ΪӢ�Ļ����֣�����Ϊ3-32�ַ���
     * @param callback_encrypt_mode  --����ģʽ:0,����ģʽ:1,��ȫģʽ:2
     * @param encoding_aeskey        --��Ϣ������Կ��43λ�ַ���ɣ�������޸ģ��ַ���ΧΪA-Z��a-z��0-9��
     * @throws Exception �������{
     *                   "-201" : "��Ч��URL",
     *                   "-202" : "��Ч��Token",
     *                   "-203" : "����Ƶ��̫�죬����Ϣһ�¡�",
     *                   "-204" : "����������ҳ�����Ƶ�ǰ�ʺ���Ϣ",
     *                   "-301" : "����URL��ʱ",
     *                   "-302" : "��ķ�����û����ȷ��ӦToken��֤�����Ķ���Ϣ�ӿ�ʹ��ָ��",
     *                   "-205" : "��URL���ܴ��ڰ�ȫ���գ�����"
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
            throw new NullPointerException("��¼ʧ��!");
        }
    }
    
    //�����ȡ��ҳ��֤��ʶToken
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
    
    
    //File HeadPortrait = new File(new File(WxWebPage.class.getResource("WxWebPage.java").toURI()).getParentFile().getParentFile().getParentFile().getParentFile().getParentFile(),"HeadPortrait/"+WxConfig.get("ԭʼID")+".jpg");
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
            
            
	            wxWebPage.DownloadFile((String)WxConfig.get("ͷ��"),new File(HeadPortrait,WxConfig.get("ԭʼID")+"_LoGo.jpg"));
	            wxWebPage.DownloadFile((String)WxConfig.get("��ά��"),new File(HeadPortrait,WxConfig.get("ԭʼID")+"_QrCode.jpg"));
            
            
            
            
            
            
            
            
            
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
    //�����ȡ��ҳ��֤��ʶToken
    public static void println(Map<String, Object> map) {
        Iterator<Map.Entry<String,Object>> Ite = map.entrySet().iterator();
        while (Ite.hasNext()) {
            Map.Entry<String,Object> entry = Ite.next();
            System.out.println(entry.getKey()+"="+entry.getValue());
        }
    }

}
