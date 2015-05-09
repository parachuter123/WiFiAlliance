importClass(Packages.smny.MyBatisFactory);
importClass(Packages.smny.util.StringToolkit);
//importPackage(Packages.smny.net.tcp);
		//消息入口
function WechatProcess(Param,WechProc){
		var sqlSession = MyBatisFactory.openSession();
		try{
				var Reply;
				var MsgType = Param.get("MsgType");
				if("location" == MsgType) {                                                            //上传地理位置
		  			Reply = LocationMessage(sqlSession,Param,WechProc);
				}else if("event" == MsgType) {                                                         //事件推送
		  			var EventType = Param.get("Event");
		  			if("CLICK" == EventType){                                                                    //自定义菜单事件
		  					Reply = ClickEvent(sqlSession,Param,WechProc);
		  			}else if("LOCATION" == EventType){                                                           //上报地理位置事件
		  					Reply = LocationEvent(sqlSession,Param,WechProc);
		  			}else if("subscribe" == EventType){                                                          //关注事件
		  					Reply = SubscribeEvent(sqlSession,Param,WechProc);
		  			}else if("unsubscribe" == EventType){                                                        //取消关注事件
		  					Reply = UnSubscribeEvent(sqlSession,Param,WechProc);
		  			}else if("SCAN" == EventType){                                                               //扫描带参数二维码事件
		  					Reply = ScanEvent(sqlSession,Param,WechProc);
		  			}else{                                                                                        //未知事件
		  					Reply = UnknownEvent(sqlSession,Param,WechProc);
		  			}
				}else if("text" == MsgType) {                                                          //文本消息
		  			Reply = TextMessage(sqlSession,Param,WechProc);
				}else if("voice" == MsgType) {                                                         //语音消息
		  			Reply = VoiceMessage(sqlSession,Param,WechProc);
				}else if("image" == MsgType) {                                                         //图片消息
		  			Reply = ImageMessage(sqlSession,Param,WechProc);
				}else if("video" == MsgType) {                                                         //视频消息
		  			Reply = VideoMessage(sqlSession,Param,WechProc);
				}else if("link" == MsgType) {                                                          //链接消息
		  			Reply = LinkMessage(sqlSession,Param,WechProc);
				}else{                                                                                      //未知消息
		  			Reply = UnknownMessage(sqlSession,Param,WechProc);
				}
				if(typeof(Reply) == "string"){
						return Reply;
				}
				return WechProc.ThirdAppForward(Param);
		}catch(err){
				return "错误名称:" + err.name +
				       "\r\n错误描述:" + err.message +
				       "\r\n错误行:" + err.lineNumber +
				       "\r\n文件名:" + FileName;
		}finally{
				sqlSession.close();
		}
}




		//取关注者 Followers
function getFollowers(sqlSession,Param,WechProc){
		var AppEntrance = sqlSession.selectOne("RouterBatis.AppEntranceMapper.SelectAppUid",WechProc.getAppUid());
		AppEntrance.put("FansID",Param.get("FromUserName"));
		var Followers = sqlSession.selectOne("RouterBatis.FollowersMapper.SelectWeiXinOpenID",AppEntrance);
		if(Followers == null){
        Followers = java.util.HashMap();
        Followers.put("GroupID",AppEntrance.get("DefaultGroupID"));
        Followers.put("FansID",Param.get("FromUserName"));
        Followers.put("AppEntranceID",AppEntrance.get("AppEntranceID"));
        Followers.put("SubscribeTime",java.util.Date());
        Followers.put("StatusID",1);
        sqlSession.insert("RouterBatis.FollowersMapper.deleteOnlineID",Followers);
		}
		return Followers;
}
		//更新注者 Followers
function UpdataFollowers(sqlSession,Followers){
		Followers.put("InitiativeNewsTime",java.util.Date());
    sqlSession.update("RouterBatis.FollowersMapper.updateFollowers",Followers);
    sqlSession.commit();
}
		//上传地理位置 LocationMessage
function LocationMessage(sqlSession,Param,WechProc){
		var Followers = getFollowers(sqlSession,Param,WechProc);
		
		Followers.put("EndLatitude",Number(Param.get("Location_X")));
		Followers.put("EndLongitude",Number(Param.get("Location_Y")));
		
		UpdataFollowers(sqlSession,Followers);
}
		//文本消息 TextMessage
function TextMessage(sqlSession,Param,WechProc){
		var Followers = getFollowers(sqlSession,Param,WechProc);
		UpdataFollowers(sqlSession,Followers);
		
		var Content = Param.get("Content");
		if(Content=="WIFI" || Content=="wifi"){
				//获取验证码
				return ValidationCode(sqlSession,Param,Followers);
		}else if(Content=="0" || Content=="激活"){
				//获取验证链接
				return aKeyBinding(sqlSession,Param,Followers);
		}
}
		//语音消息 VoiceMessage
function VoiceMessage(sqlSession,Param,WechProc){
		var Followers = getFollowers(sqlSession,Param,WechProc);
		UpdataFollowers(sqlSession,Followers);
		
		var Content = Param.get("Recognition");
		if(Content=="WIFI" || Content=="wifi"){
				//获取验证码
				return ValidationCode(sqlSession,Param,Followers);
		}else if(Content=="0" || Content=="激活"){
				//获取验证链接
				return aKeyBinding(sqlSession,Param,Followers);
		}
}
		//图片消息 ImageMessage
function ImageMessage(sqlSession,Param,WechProc){
		var Followers = getFollowers(sqlSession,Param,WechProc);
		UpdataFollowers(sqlSession,Followers);
}
		//视频消息 VideoMessage
function VideoMessage(sqlSession,Param,WechProc){
		var Followers = getFollowers(sqlSession,Param,WechProc);
		UpdataFollowers(sqlSession,Followers);
}
		//链接消息 
function LinkMessage(sqlSession,Param,WechProc){
		var Followers = getFollowers(sqlSession,Param,WechProc);
		UpdataFollowers(sqlSession,Followers);
}
		//未知消息 
function UnknownMessage(sqlSession,Param,WechProc){
		var Followers = getFollowers(sqlSession,Param,WechProc);
		UpdataFollowers(sqlSession,Followers);
		print("用户“"+Param.get("FromUserName")+"”发来未知消息:"+Param.get("MsgType"));
}
		
		//自定义菜单事件 
function ClickEvent(sqlSession,Param,WechProc){
		var Followers = getFollowers(sqlSession,Param,WechProc);
		UpdataFollowers(sqlSession,Followers);
		
		var EventKey = Param.get("EventKey");
		if(EventKey=="WIFI" || EventKey=="wifi"){
				//获取验证码
				return ValidationCode(sqlSession,Param,Followers);
		}else if(EventKey == "Distribution"){
				//查看WIFI分布
				return DistributionMap(sqlSession,Param,Followers);
		}else if(EventKey == "WifiConfirm"){
				//获取验证链接
				return aKeyBinding(sqlSession,Param,Followers);
		}
}
		//上报地理位置事件 
function LocationEvent(sqlSession,Param,WechProc){
		var Followers = getFollowers(sqlSession,Param,WechProc);
		
		Followers.put("EndLatitude",java.lang.Double.valueOf(Param.get("Latitude")));
		Followers.put("EndLongitude",java.lang.Double.valueOf(Param.get("Longitude")));
		
		UpdataFollowers(sqlSession,Followers);
		
}
		
/**
 * 生成验证码消息
 * @Format     ValidationCode(sqlSession,Param,Followers)
 * @param %1$s ToUserName   接收者
 * @param %2$s FromUserName 发送者
 * @param %3$s CreateTime   发送时间
 * @param %4$s Content      验证码
 * @return 文本消息XML字符串
 */
/**
 * 生成WIFI分布的图文消息
 * @Format     DistributionMap(sqlSession,Param,Followers)
 * @param %1$s ToUserName   接收者
 * @param %2$s FromUserName 发送者
 * @param %3$s CreateTime   发送时间
 * @param %4$s Location_X   接收者纬度
 * @param %5$s Location_Y   接收者经度
 * @return 图文消息XML字符串
 */
/**
 * 生成一键验证图文消息
 * @Format     aKeyBinding(sqlSession,Param,Followers)
 * @param %1$s ToUserName   接收者
 * @param %2$s FromUserName 发送者
 * @param %3$s CreateTime   发送时间
 * @param %4$s Captcha      验证码
 * @return 图文消息XML字符串
 * @param %4$s PicUrl       提示图片URL  -----暂时统一
 */
		//关注事件 
function SubscribeEvent(sqlSession,Param,WechProc){
		var Followers = getFollowers(sqlSession,Param,WechProc);
		UpdataFollowers(sqlSession,Followers);
		if(Param.get("EventKey") != null) {
				print("扫描关注:"+Param.get("EventKey"));
				//获取验证链接
				return aKeyBinding(sqlSession,Param,Followers);
		}
		
		var ThirdReply = WechProc.getThirdForwardMap(Param);
		if(null == ThirdReply) {                       //无三方转发或转发失败
				//获取验证链接
				return aKeyBinding(sqlSession,Param,Followers);
		}
		
		var MsgType = ThirdReply.get("MsgType");
		
		
		if(null == MsgType) {                       //消息类型空
				return aKeyBinding(sqlSession,Param,Followers);
		}else if("text" == MsgType) {                       //回复文本消息
				/*
				 <Content><![CDATA[你好]]></Content>
				*/
				return aKeyBinding(sqlSession,Param,Followers);
		}else if("image" == MsgType) {                //回复图片消息
				/*
					<Image>
						<MediaId><![CDATA[media_id]]></MediaId>
					</Image>
				*/
				return aKeyBinding(sqlSession,Param,Followers);
		}else if("voice" == MsgType) {                //回复语音消息
				/*
					<Voice>
						<MediaId><![CDATA[media_id]]></MediaId>
					</Voice>
				*/
				return aKeyBinding(sqlSession,Param,Followers);
		}else if("video" == MsgType) {                //回复视频消息
				/*
					<Video>
						<MediaId><![CDATA[media_id]]></MediaId>
						<Title><![CDATA[title]]></Title>
						<Description><![CDATA[description]]></Description>
					</Video>
				*/
				return aKeyBinding(sqlSession,Param,Followers);
		}else if("music" == MsgType) {                //回复音乐消息
				/*
					<Music>
						<Title><![CDATA[title]]></Title>
						<Description><![CDATA[DESCRIPTION]]></Description>
						<MusicUrl><![CDATA[MUSIC_Url]]></MusicUrl>
						<HQMusicUrl><![CDATA[HQ_MUSIC_Url]]></HQMusicUrl>
						<ThumbMediaId><![CDATA[media_id]]></ThumbMediaId>
					</Music>
				*/
				return aKeyBinding(sqlSession,Param,Followers);
		}else if("news" == MsgType) {                 //回复图文消息
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
				var Articles = Param.get("Articles");
				if(Articles==null || Articles.size()<=0){
						return aKeyBinding(sqlSession,Param,Followers);
				}
				if(Articles.size()>=10){
						for(var i=9;i<Articles.size();i++){
								Articles.remove(i);
						}
				}
				var FigMessage = java.util.HashMap();
				FigMessage.put("Title","点击开启无线WIFI");
				FigMessage.put("Description","点击开启无线WIFI");
				FigMessage.put("PicUrl","http://images.enet.com.cn/2008/0331/60/9331916.jpg");
				FigMessage.put("Url","http://sys.wanwifi.com/MacBinding.do?MacBinding=Manual&Captcha="+BindingMapper.getCaptcha(Followers.get("FollowersID")));
				Articles.add(1,FigMessage);
		}else{                                             //未知消息
  			return aKeyBinding(sqlSession,Param,Followers);
		}
		return WechatP.PackingMap(ThirdReply);
}
		//取消关注事件 
function UnSubscribeEvent(sqlSession,Param,WechProc){
		var Followers = getFollowers(sqlSession,Param,WechProc);
		Followers.put("StatusID",-2);
		UpdataFollowers(sqlSession,Followers);
}
		//扫描带参数二维码事件 
function ScanEvent(sqlSession,Param,WechProc){
		
}
		//未知事件 
function UnknownEvent(sqlSession,Param,WechProc){
		
}
/**
 * 生成验证码消息
 * @Format     ValidationCode(sqlSession,Param,Followers)
 * @param %1$s ToUserName   接收者
 * @param %2$s FromUserName 发送者
 * @param %3$s CreateTime   发送时间
 * @param %4$s Content      验证码
 * @return 文本消息XML字符串
 */
var ValidationCodeXml = "<xml>"+
					 								"<ToUserName><![CDATA[%1$s]]></ToUserName>"+
					 								"<FromUserName><![CDATA[%2$s]]></FromUserName>"+
					 								"<CreateTime>%3$s</CreateTime>"+
					 								"<MsgType><![CDATA[text]]></MsgType>"+
					 								"<Content><![CDATA[WanWifi验证为:<a>%4$s</a>,有效时间为十分钟]]></Content>"+
					 							"</xml>";
function ValidationCode(sqlSession,Param,Followers){
		return java.lang.String.format(ValidationCodeXml,Param.get("FromUserName"),Param.get("ToUserName"),Param.get("CreateTime"),BindingMapper.getCaptcha(Followers.get("FollowersID")));
}
/**
 * 生成WIFI分布的图文消息
 * @Format     DistributionMap(sqlSession,Param,Followers)
 * @param %1$s ToUserName   接收者
 * @param %2$s FromUserName 发送者
 * @param %3$s CreateTime   发送时间
 * @param %4$s Location_X   接收者纬度
 * @param %5$s Location_Y   接收者经度
 * @return 图文消息XML字符串
 */
var DistributionMapXml = "<xml>"+
					 								"<ToUserName><![CDATA[%1$s]]></ToUserName>"+
					 								"<FromUserName><![CDATA[%2$s]]></FromUserName>"+
					 								"<CreateTime>%3$s</CreateTime>"+
					 								"<MsgType><![CDATA[news]]></MsgType>"+
					 								"<ArticleCount>1</ArticleCount>"+
					 								"<Articles>"+
					 									"<item>"+
					 										"<Title><![CDATA[附近WIFI]]></Title>"+
					 										"<Description><![CDATA[点击查看附近可用无线WIFI]]></Description>"+
					 										"<PicUrl><![CDATA[http://sys.wanwifi.com/img/WIFI.jpg]]></PicUrl>"+
					 										"<Url><![CDATA[http://sys.wanwifi.com/Distribution?Location_X=%4$s&Location_Y=%5$s]]></Url>"+
					 									"</item>"+
					 								"</Articles>"+
					 							"</xml>";
function DistributionMap(sqlSession,Param,Followers){
		return java.lang.String.format(DistributionMapXml,Param.get("FromUserName"),Param.get("ToUserName"),Param.get("CreateTime"),Followers.get("EndLatitude"),Followers.get("EndLongitude"));
}
/**
 * 生成一键验证图文消息
 * @Format     aKeyBinding(sqlSession,Param,Followers)
 * @param %1$s ToUserName   接收者
 * @param %2$s FromUserName 发送者
 * @param %3$s CreateTime   发送时间
 * @param %4$s Captcha      验证码
 * @return 图文消息XML字符串
 * @param %4$s PicUrl       提示图片URL  -----暂时统一
 */
var aKeyBindingMap = "<xml>"+
		 								"<ToUserName><![CDATA[%1$s]]></ToUserName>"+
		 								"<FromUserName><![CDATA[%2$s]]></FromUserName>"+
		 								"<CreateTime>%3$s</CreateTime>"+
		 								"<MsgType><![CDATA[news]]></MsgType>"+
		 								"<ArticleCount>1</ArticleCount>"+
		 								"<Articles>"+
		 									"<item>"+
		 										"<Title><![CDATA[点击开启无线WIFI]]></Title>"+
		 										"<Description><![CDATA[点击开启无线WIFI]]></Description>"+
		 										"<PicUrl><![CDATA[http://images.enet.com.cn/2008/0331/60/9331916.jpg]></PicUrl>"+
		 										"<Url><![CDATA[http://sys.wanwifi.com/MacBinding.do?MacBinding=Manual&Captcha=%5$s]]></Url>"+
		 									"</item>"+
		 								"</Articles>"+
		 							"</xml>";
function aKeyBinding(sqlSession,Param,Followers){
		return java.lang.String.format(DistributionMapXml,Param.get("FromUserName"),Param.get("ToUserName"),Param.get("CreateTime"),BindingMapper.getCaptcha(Followers.get("FollowersID")));
}