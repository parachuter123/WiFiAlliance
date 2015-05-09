package RouterGuide.WeiXin;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.io.FileReader;
import java.io.File;



import org.xml.sax.InputSource;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import java.net.URI;

import javax.script.ScriptEngine;
import javax.script.Invocable;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.ScriptEngine;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import smny.MyBatisFactory;
import smny.util.FileToolkit;
import smny.util.StringToolkit;
import RouterGuide.WeiXin.mp.SHA1;
import RouterGuide.WeiXin.mp.AesException;
import RouterGuide.WeiXin.mp.WXBizMsgCrypt;

import org.apache.ibatis.session.SqlSession;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.entity.StringEntity;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
/** 
 * 微信xml消息处理流程逻辑类 
 * @author pamchen-1 
 * 
 */ 
public class WechatProcess {
		private final static String TextTpl = "<xml><ToUserName><![CDATA[%1$s]]></ToUserName><FromUserName><![CDATA[%2$s]]></FromUserName><CreateTime>%3$s</CreateTime><MsgType><![CDATA[%4$s]]></MsgType><Content><![CDATA[%5$s]]></Content></xml>";
		
    private static String ScriptFileName = "Config/WeiXinMessage.js";
    private static Invocable Callback;
    static{
	      try{
	      		init();
	      }catch (Exception e){
	         throw new AssertionError(e);
	      }
    }
		public static void init(){
	      try{
	      		File ScriptFile = new File(WechatProcess.class.getResource("WeiXinMessage.js").toURI());
	      		init(ScriptFile);
	      }catch (Exception e){
	         throw new RuntimeException(e);
	      }
		}
		private static void init(File ScriptFile)throws FileNotFoundException,ScriptException{
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
        FileReader FR = null;
        try{
        		FR = new FileReader(ScriptFile);
		        ScriptEngine Script = new ScriptEngineManager().getEngineByName("JavaScript");
		        Script.eval(FR);
		        Script.put("FileName",ScriptFile.toString());
		        Callback = (Invocable)Script;
        }catch(ScriptException e){
        		throw e;
        }catch(Exception e){
        		throw new RuntimeException(e);
        }finally{
        		if(FR != null)try{
        				FR.close();
        		}catch(Exception e){
        			
        		}
        }
		}
	
	
	
	
	
	
	
	
    /**
     * 微信接口标识（本地）
     * @AppUid 接口URL多微信共享标识
    */
    private String AppUid;
    /**
     * 消息体是否加密
    */
    private boolean isAes;
    /**
     * 加密签名
     * 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
    */
    private String MsgSignature;
    /**
     * 随机字符串，验证是否链接是否有效
     * 开发者通过检验signature对请求进行校验（下面有校验方式）。若确认此次GET请求来自微信服务器，请原样返回echostr参数内容，则接入生效，成为开发者成功，否则接入失败。
    */
    private String EchoStr;
    /**
     * 时间戳，验证签名是否有效
    */
    private String TimeStamp;
    /**
     * 随机数，验证签名是否有效
    */
    private String Nonce;
    /**
     * 一般签名
     * 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
    */
    private String SignAture;
		
		
    /**
     * 微信加解密算法实现
    */
    private WXBizMsgCrypt WMC;
		
		
		
		
    /**
     * 解密必要参数
    */
    private String AppID;
    private String AppToken;
    private String EncodingAesKey;
		
		//转发地址
		StringBuilder ThiUrl;
		
		
		
		public WechatProcess(String AppUid){
				this.AppUid = AppUid;
		}
		public String getAppUid(){
				return AppUid;
		}
		public WechatProcess setEchoStr(String echostr){
				this.EchoStr = echostr;
				return this;
		}
		
		public WechatProcess setEncryptType(String encrypt_type,String msg_signature){
				isAes = "aes".equals(encrypt_type);
				if(isAes){
						this.MsgSignature = msg_signature;
				}
				return this;
		}
		
		public WechatProcess setTimestamp(String timestamp){
				this.TimeStamp = timestamp;
				return this;
		}
		
		public WechatProcess setNonce(String nonce){
				this.Nonce = nonce;
				return this;
		}
		
		public WechatProcess setSignature(String signature){
				this.SignAture = signature;
				return this;
		}
		/**验证URL
		 * @param msgSignature 签名串，对应URL参数的msg_signature
		 * @param timeStamp 时间戳，对应URL参数的timestamp
		 * @param nonce 随机串，对应URL参数的nonce
		 * @param echoStr 随机串，对应URL参数的echostr
		 * 
		 * @return 解密之后的echostr
		 * @throws AesException 执行失败，请查看该异常的错误码和具体的错误信息
		 */
		public WechatProcess verifyUrl(){
        if(!StringToolkit.isNullOrEmpty(EchoStr)){
        		throw new NullPointerException(EchoStr);
        }
				SqlSession sqlSession = MyBatisFactory.openSession();
				try{
						Map<String,Object> AppEntrance = sqlSession.selectOne("RouterBatis.AppEntranceMapper.SelectAppUid",AppUid);
						if(AppEntrance == null){
								throw new NullPointerException("接口不存在");
						}
						AppID = (String)AppEntrance.get("AppID");
						AppToken = (String)AppEntrance.get("AppToken");
						String signature = SHA1.getSHA1(AppToken,TimeStamp,Nonce);
						if (!signature.equals(SignAture)) {
								throw new NullPointerException("验证失败！");
						}
						EncodingAesKey = (String)AppEntrance.get("EncodingAesKey");
						if(isAes){
								WMC = new WXBizMsgCrypt(AppToken,EncodingAesKey,AppID);
						}
						String ThirdUrl = (String)AppEntrance.get("AppURL");
						if (!StringToolkit.isNullOrEmpty(ThirdUrl) && (ThirdUrl.startsWith("http://") || ThirdUrl.startsWith("https://"))){
								ThiUrl = new StringBuilder(255);
								ThiUrl.append(ThirdUrl);
								if(!ThirdUrl.contains("?")){
										ThiUrl.append("?");
								}else if(!ThirdUrl.endsWith("&")){
										ThiUrl.append("&");
								}
						}
				}catch(Exception e) {
		        e.printStackTrace();
		        throw new NullPointerException("");
				}finally{
						sqlSession.close();
				}
				return this;
		}
		
    /** 
     * 微信消息入口
     * @param XmlData 接收到的微信原始数据
     * @return  最终的回复微信数据
     */ 
    public String processWechatMag(String XmlData){
				try{
						String Result = "";
						//
						DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
						DocumentBuilder db = dbf.newDocumentBuilder();
						StringReader sr = new StringReader(DecryptMag(XmlData));
						InputSource is = new InputSource(sr);
						Document document = db.parse(is);
						Element root = document.getDocumentElement();
						//
						Map<String,String> Param = new HashMap<String,String>();
						NodeList list = root.getChildNodes();
						for(int i=0;i<list.getLength();i++){
								Node node = list.item(i);
								if(node.getNodeType() == Node.ELEMENT_NODE){
										Param.put(node.getNodeName(),node.getTextContent());
								}
						}
						Param.put("encrypt_type",isAes?"aes":"raw");
						Param.put("msg_signature",MsgSignature);
						Param.put("timestamp",TimeStamp);
						Param.put("nonce",Nonce);
						Param.put("signature",SignAture);
						Param.put("PostStr",XmlData);
						//
						try{
								Object ReplyParam = Callback.invokeFunction("WechatProcess",Param,this);
								if(ReplyParam == null){
										Result = "";
								}else if(ReplyParam instanceof String){
										Result = (String)ReplyParam;
								}else if(ReplyParam instanceof Map){
										Result = PackingMap((Map<?,?>)ReplyParam);
								}else{
										Result = ReplyParam.toString();
								}
						}catch(ScriptException Scr){
								//Scr.printStackTrace();
		            Result = String.format(TextTpl,Param.get("FromUserName"),Param.get("ToUserName"),Long.toString(System.currentTimeMillis()),"text","脚本运行错误:"+Scr.getMessage());
						}catch(NoSuchMethodException NoS){
								//NoS.printStackTrace();
		            Result = String.format(TextTpl,Param.get("FromUserName"),Param.get("ToUserName"),Long.toString(System.currentTimeMillis()),"text","入口方法未定义:Config/WeiXinMessage.js==>>WechatProcess(Map<String,String> Param,WechatProcess WechProc)");
						}catch(NullPointerException NoS){
								//NoS.printStackTrace();
		            Result = String.format(TextTpl,Param.get("FromUserName"),Param.get("ToUserName"),Long.toString(System.currentTimeMillis()),"text","没找到脚本文件:Config/WeiXinMessage.js");
		            init();
						}catch(Exception Scr){
								//Scr.printStackTrace();
		            Result = String.format(TextTpl,Param.get("FromUserName"),Param.get("ToUserName"),Long.toString(System.currentTimeMillis()),"text","未知错误:"+Scr.getMessage());
						}
						return EncryptMag(Result);
				}catch(Exception e) {
						e.printStackTrace();
				}
        return "";  
    }
    /** 
     * 解密微信原始数据
     * @param XmlData 接收到的微信原始数据
     * @return 解析后的Xml消息（String格式数据） 
     */ 
    public String DecryptMag(String XmlData)throws AesException{
				if(isAes){
						return WMC.decryptMsg(MsgSignature,TimeStamp,Nonce,XmlData);
				}
				return XmlData;
    }
    /** 
     * 加密Xml消息
     * @param XmlString Xml消息
     * @return 加密封装后的Xml（String格式数据） 
     */ 
    public String EncryptMag(String XmlString)throws AesException{
				if(isAes){
						return WMC.encryptMsg(XmlString,TimeStamp,Nonce);
				}
				return XmlString;
    }
		
		
    /**封装响应消息
     * @param XmlString 接收到的微信解密数据 
     * @return  未加密的响应消息（String格式数据） 
					<xml>
						<ToUserName><![CDATA[toUser]]></ToUserName>
						<FromUserName><![CDATA[fromUser]]></FromUserName>
						<CreateTime>12345678</CreateTime>
						<MsgType><![CDATA[text]]></MsgType>
						<Content><![CDATA[你好]]></Content>
					</xml>
					1 回复文本消息
						text
					2 回复图片消息
						image
					3 回复语音消息
						voice
					4 回复视频消息
						video
					5 回复音乐消息
						music
					6 回复图文消息
						news
     */ 
    public String PackingMap(Map<?,?> Param){
				if(Param==null || !Param.isEmpty()){
						return "";
				}
				StringBuilder StringXml = new StringBuilder(1000);
				StringXml.append("<?xml version=\"1.0\"?><xml>");
				StringXml.append("<ToUserName><![CDATA[")
								 .append(Param.get("ToUserName"))
								 .append("]]></ToUserName>")
								 .append("<FromUserName><![CDATA[")
								 .append(Param.get("FromUserName"))
								 .append("]]></FromUserName>")
								 .append("<CreateTime><![CDATA[")
								 .append(Param.get("CreateTime"))
								 .append("]]></CreateTime>")
								 .append("<MsgType><![CDATA[")
								 .append(Param.get("MsgType"))
								 .append("]]></MsgType>");
				String MsgType = (String)Param.get("MsgType");
    		if("text".equals(MsgType)) {                       //回复文本消息
						/*
						 <Content><![CDATA[你好]]></Content>
						*/
						StringXml.append("<Content><![CDATA[")
										 .append(Param.get("Content"))
										 .append("]]></Content>");
    		}else if("image".equals(MsgType)) {                //回复图片消息
						/*
							<Image>
								<MediaId><![CDATA[media_id]]></MediaId>
							</Image>
						*/
						StringXml.append("<Image><MediaId><![CDATA[")
										 .append(Param.get("MediaId"))
										 .append("]]></MediaId></Image>");
    		}else if("voice".equals(MsgType)) {                //回复语音消息
						/*
							<Voice>
								<MediaId><![CDATA[media_id]]></MediaId>
							</Voice>
						*/
						StringXml.append("<Voice><MediaId><![CDATA[")
										 .append(Param.get("MediaId"))
										 .append("]]></MediaId></Voice>");
    		}else if("video".equals(MsgType)) {                //回复视频消息
						/*
							<Video>
								<MediaId><![CDATA[media_id]]></MediaId>
								<Title><![CDATA[title]]></Title>
								<Description><![CDATA[description]]></Description>
							</Video>
						*/
						StringXml.append("<Video>")
										 .append("<MediaId><![CDATA[")
										 .append(Param.get("MediaId"))
										 .append("]]></MediaId>")
										 .append("<Title><![CDATA[")
										 .append(Param.get("Title"))
										 .append("]]></Title>")
										 .append("<Description><![CDATA[")
										 .append(Param.get("Description"))
										 .append("]]></Description>")
										 .append("</Video>");
    		}else if("music".equals(MsgType)) {                //回复音乐消息
						/*
							<Music>
								<Title><![CDATA[title]]></Title>
								<Description><![CDATA[DESCRIPTION]]></Description>
								<MusicUrl><![CDATA[MUSIC_Url]]></MusicUrl>
								<HQMusicUrl><![CDATA[HQ_MUSIC_Url]]></HQMusicUrl>
								<ThumbMediaId><![CDATA[media_id]]></ThumbMediaId>
							</Music>
						*/
						StringXml.append("<Music>")
										 .append("<Title><![CDATA[")
										 .append(Param.get("Title"))
										 .append("]]></Title>")
										 .append("<Description><![CDATA[")
										 .append(Param.get("Description"))
										 .append("]]></Description>")
										 .append("<MusicUrl><![CDATA[")
										 .append(Param.get("MusicUrl"))
										 .append("]]></MusicUrl>")
										 .append("<HQMusicUrl><![CDATA[")
										 .append(Param.get("HQMusicUrl"))
										 .append("]]></HQMusicUrl>")
										 .append("<ThumbMediaId><![CDATA[")
										 .append(Param.get("ThumbMediaId"))
										 .append("]]></ThumbMediaId>")
										 .append("</Music>");
    		}else if("news".equals(MsgType)) {                 //回复图文消息
						/*
							<ArticleCount>2</ArticleCount>
							<Articles>
								<item>
									<Title><![CDATA[title1]]></Title>
									<Description><![CDATA[description1]]></Description>
									<PicUrl><![CDATA[picurl]]></PicUrl>
									<Url><![CDATA[url]]></Url>
								</item>
								<item>
									<Title><![CDATA[title1]]></Title>
									<Description><![CDATA[description1]]></Description>
									<PicUrl><![CDATA[picurl]]></PicUrl>
									<Url><![CDATA[url]]></Url>
								</item>
							</Articles>
						*/
						List<?> Articles = (List<?>)Param.get("Articles");
						if(Articles!=null && Articles.size()>0){
								StringXml.append("<ArticleCount>")
												 .append(Articles.size())
												 .append("</ArticleCount>")
												 .append("<Articles>");
								for(Object Item:Articles){
										Map<?,?> item = (Map<?,?>)Item;
										StringXml.append("<item>")
														 .append("<Title><![CDATA[")
														 .append(item.get("Title"))
														 .append("]]></Title>")
														 .append("<Description><![CDATA[")
														 .append(item.get("Description"))
														 .append("]]></Description>")
														 .append("<PicUrl><![CDATA[")
														 .append(item.get("PicUrl"))
														 .append("]]></PicUrl>")
														 .append("<Url><![CDATA[")
														 .append(item.get("Url"))
														 .append("]]></Url>")
														 .append("</item>");
								}
								StringXml.append("</Articles>");
						}else{
								StringXml.append("<ArticleCount>0</ArticleCount><Articles></Articles>");
						}
    		}else{                                             //未知消息
      			return "";
    		}
				return StringXml.append("</xml>").toString();
    }
		
		
		
		
		
		
		
    /** 
     * 解析响应消息
     * @param XmlString 第三方响应消息
     * @return  最终的解析结果（Map格式数据） 
     */ 
    public Map<String,Object> AnalyticalXml(String XmlString){
				if(StringToolkit.isNullOrEmpty(XmlString) || !XmlString.startsWith("<xml>") || !XmlString.endsWith("</xml>")){
						return null;
				}
				try{
						DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
						DocumentBuilder db = dbf.newDocumentBuilder();
						StringReader sr = new StringReader(XmlString);
						InputSource is = new InputSource(sr);
						Document document = db.parse(is);
						Element root = document.getDocumentElement();
						
						NodeList Encrypt = root.getElementsByTagName("Encrypt");
						
						if(Encrypt.getLength() > 0){
								XmlString = WMC.decrypt(Encrypt.item(0).getTextContent());
								dbf = DocumentBuilderFactory.newInstance();
								db = dbf.newDocumentBuilder();
								sr = new StringReader(XmlString);
								is = new InputSource(sr);
								document = db.parse(is);
								root = document.getDocumentElement();
						}
						
						String ToUserName = root.getElementsByTagName("ToUserName").item(0).getTextContent();
						String FromUserName = root.getElementsByTagName("FromUserName").item(0).getTextContent();
						String CreateTime = root.getElementsByTagName("CreateTime").item(0).getTextContent();
						String MsgType = root.getElementsByTagName("MsgType").item(0).getTextContent();
						
						
						Map<String,Object> Param = new HashMap<String,Object>();
						
						Param.put("ToUserName",ToUserName);
						Param.put("FromUserName",FromUserName);
						Param.put("CreateTime",CreateTime);
						Param.put("MsgType",MsgType);
						
						
						
						
		    		if("text".equals(MsgType)) {                       //回复文本消息
								/*
								 <Content><![CDATA[你好]]></Content>
								*/
								String Content = root.getElementsByTagName("Content").item(0).getTextContent();
								Param.put("Content",Content);
		    		}else if("image".equals(MsgType)) {                //回复图片消息
								/*
									<Image>
										<MediaId><![CDATA[media_id]]></MediaId>
									</Image>
								*/
								NodeList ImageChild = root.getElementsByTagName("Image").item(0).getChildNodes();
								for(int i=0;i<ImageChild.getLength();i++){
										Node node = ImageChild.item(i);
										if(node.getNodeType() == Node.ELEMENT_NODE){
												Param.put(node.getNodeName(),node.getTextContent());
										}
								}
		    		}else if("voice".equals(MsgType)) {                //回复语音消息
								/*
									<Voice>
										<MediaId><![CDATA[media_id]]></MediaId>
									</Voice>
								*/
								NodeList VoiceChild = root.getElementsByTagName("Voice").item(0).getChildNodes();
								for(int i=0;i<VoiceChild.getLength();i++){
										Node node = VoiceChild.item(i);
										if(node.getNodeType() == Node.ELEMENT_NODE){
												Param.put(node.getNodeName(),node.getTextContent());
										}
								}
		    		}else if("video".equals(MsgType)) {                //回复视频消息
								/*
									<Video>
										<MediaId><![CDATA[media_id]]></MediaId>
										<Title><![CDATA[title]]></Title>
										<Description><![CDATA[description]]></Description>
									</Video>
								*/
								NodeList VideoChild = root.getElementsByTagName("Video").item(0).getChildNodes();
								for(int i=0;i<VideoChild.getLength();i++){
										Node node = VideoChild.item(i);
										if(node.getNodeType() == Node.ELEMENT_NODE){
												Param.put(node.getNodeName(),node.getTextContent());
										}
								}
		    		}else if("music".equals(MsgType)) {                //回复音乐消息
								/*
									<Music>
										<Title><![CDATA[title]]></Title>
										<Description><![CDATA[DESCRIPTION]]></Description>
										<MusicUrl><![CDATA[MUSIC_Url]]></MusicUrl>
										<HQMusicUrl><![CDATA[HQ_MUSIC_Url]]></HQMusicUrl>
										<ThumbMediaId><![CDATA[media_id]]></ThumbMediaId>
									</Music>
								*/
								NodeList MusicChild = root.getElementsByTagName("Music").item(0).getChildNodes();
								for(int i=0;i<MusicChild.getLength();i++){
										Node node = MusicChild.item(i);
										if(node.getNodeType() == Node.ELEMENT_NODE){
												Param.put(node.getNodeName(),node.getTextContent());
										}
								}
		    		}else if("news".equals(MsgType)) {                 //回复图文消息
								/*
									<ArticleCount>2</ArticleCount>
									<Articles>
										<item>
											<Title><![CDATA[title1]]></Title>
											<Description><![CDATA[description1]]></Description>
											<PicUrl><![CDATA[picurl]]></PicUrl>
											<Url><![CDATA[url]]></Url>
										</item>
										<item>
											<Title><![CDATA[title1]]></Title>
											<Description><![CDATA[description1]]></Description>
											<PicUrl><![CDATA[picurl]]></PicUrl>
											<Url><![CDATA[url]]></Url>
										</item>
									</Articles>
								*/
								
								String ArticleCount = root.getElementsByTagName("ArticleCount").item(0).getTextContent();
								Param.put("ArticleCount",ArticleCount);
								List<Map<String,String>> Articles = new ArrayList<Map<String,String>>(Integer.parseInt(ArticleCount));
								Param.put("Articles",Articles);
								
								NodeList ArticlesChild = root.getElementsByTagName("Articles").item(0).getChildNodes();
								for(int i=0;i<ArticlesChild.getLength();i++){
										Node itemNode = ArticlesChild.item(i);
										if(itemNode.getNodeType() == Node.ELEMENT_NODE){
												Map<String,String> item = new HashMap<String,String>();
												Articles.add(item);
												NodeList itemChild = itemNode.getChildNodes();
												for(int j=0;j<itemChild.getLength();j++){
														Node node = itemChild.item(j);
														if(node.getNodeType() == Node.ELEMENT_NODE){
																item.put(node.getNodeName(),node.getTextContent());
														}
												}
										}
								}
		    		}
						return Param;
				}catch(Exception e) {
						return null;
				}
    }
		/**
		 * 第三方微信平台转发
		 *
		 * @param request  初始请求
		 * @param ThirdUrl 第三方平台URL
		 * @param PostStr  数据
		 * @return
		 * Example   :   http://wx.smny.cn/index.php/api/4867ff79e566ee3f
		 
		 
		 */
		public Map<String,Object> getThirdForwardMap(Map<String,String> Param){
				return AnalyticalXml(ThirdAppForward(Param));
		}
		public String ThirdAppForward(Map<String,String> Param){
				String content = "";
				String PostStr = Param.get("PostStr");
				if (ThiUrl == null || StringToolkit.isNullOrEmpty(PostStr)){
						System.out.println("PostStr=="+PostStr+"\r\nThiUrl="+ThiUrl);
						return content;
				}
				String timestamp = Param.get("timestamp");
				String nonce = Param.get("nonce");
				String signature = Param.get("signature");
				if (StringToolkit.isNullOrEmpty(timestamp,nonce,signature)){
						System.out.println("timestamp="+timestamp+"\r\nnonce="+nonce+"\r\nsignature="+signature);
						return content;
				}
				String encrypt_type = Param.get("encrypt_type");
				if (StringToolkit.isNullOrEmpty(encrypt_type)){
						ThiUrl.append("encrypt_type=raw");
				}else{
						ThiUrl.append("encrypt_type=");
						ThiUrl.append(encrypt_type);
						String msg_signature = Param.get("msg_signature");
						if (!StringToolkit.isNullOrEmpty(msg_signature)){
								ThiUrl.append("&msg_signature=");
								ThiUrl.append(msg_signature);
						}
				}
				ThiUrl.append("&timestamp=");
				ThiUrl.append(timestamp);
				ThiUrl.append("&nonce=");
				ThiUrl.append(nonce);
				ThiUrl.append("&signature=");
				ThiUrl.append(signature);
				String echostr = Param.get("echostr");
				if (!StringToolkit.isNullOrEmpty(echostr)){
						ThiUrl.append("&echostr=");
						ThiUrl.append(echostr);
				}
        try {
            HttpUriRequest ForwardHttp = RequestBuilder.post()
                    .setUri(new URI(ThiUrl.toString()))
                    .setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.172 Safari/537.22")
                    .setEntity(new StringEntity(PostStr))
                    .setConfig(RequestConfig.custom()
                               .setSocketTimeout(1200)
                               .setConnectTimeout(1200)
                               .build()
                              )
                    .build();
            CloseableHttpResponse response = HttpClients.createDefault().execute(ForwardHttp);
            
            try {
                HttpEntity entity = response.getEntity();
                content = EntityUtils.toString(entity);
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }
    public static void main(String args[])throws Exception{
    		String xml = "<xml>"+
					 								"<ToUserName><![CDATA[wx2dab31a3d1648a1d]]></ToUserName>"+
					 								"<FromUserName><![CDATA[o9GQdt5XwWS7l0X80a3RI8GqPoXo]]></FromUserName>"+
					 								"<CreateTime>1417094615296</CreateTime>"+
					 								"<MsgType><![CDATA[image]]></MsgType>"+
					 								"<PicUrl><![CDATA[this is a url]]></PicUrl>"+
					 								"<MediaId><![CDATA[media_id]]></MediaId>"+
					 								"<MsgId>1234567890123456</MsgId>"+
					 							"</xml>";
				
    		String appID = "wxc5f5d9fe09e87269";
    		String Nonce = "cfomy5MCXNG9jJTE";
    		String AppToken = "31ef69ccadeb6dba";
    		String timestamp = "1417096446171";
    		String signature = "babf6d40a972498f057406ed1d39ef6b5d94a2f6";
    		String MsgSignature = "14668a25edae4b84f88606c01583f6c5af26cd91";
    		String EncodingAesKey = "yZ4A8iNbdjQYBwGsMg9Ya0FN0AZ35QR0uQX3D84x502";
    		
    		
    		
    		WXBizMsgCrypt WMC = new WXBizMsgCrypt(AppToken,EncodingAesKey,appID);
    		
    		
    		//String replyMsg, String timeStamp, String nonce
    		
    		long tamp = System.currentTimeMillis();
    		String Reply = new WechatProcess("wxc5f5d9fe09e87269")
						.setNonce(Nonce)
						.setEchoStr("")
						.setSignature(signature)
						.setTimestamp(timestamp)
						.setEncryptType("",MsgSignature)
						.verifyUrl()
						.processWechatMag(WMC.encryptMsg(xml,timestamp,Nonce));
					 							
					 							
			System.out.println(Reply);
			System.out.println(System.currentTimeMillis()-tamp);
    		
    }
}

 
 