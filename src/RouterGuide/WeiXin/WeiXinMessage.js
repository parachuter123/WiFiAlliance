importClass(Packages.smny.MyBatisFactory);
importClass(Packages.smny.util.StringToolkit);
//importPackage(Packages.smny.net.tcp);
		//��Ϣ���
function WechatProcess(Param,WechProc){
		var sqlSession = MyBatisFactory.openSession();
		try{
				var Reply;
				var MsgType = Param.get("MsgType");
				if("location" == MsgType) {                                                            //�ϴ�����λ��
		  			Reply = LocationMessage(sqlSession,Param,WechProc);
				}else if("event" == MsgType) {                                                         //�¼�����
		  			var EventType = Param.get("Event");
		  			if("CLICK" == EventType){                                                                    //�Զ���˵��¼�
		  					Reply = ClickEvent(sqlSession,Param,WechProc);
		  			}else if("LOCATION" == EventType){                                                           //�ϱ�����λ���¼�
		  					Reply = LocationEvent(sqlSession,Param,WechProc);
		  			}else if("subscribe" == EventType){                                                          //��ע�¼�
		  					Reply = SubscribeEvent(sqlSession,Param,WechProc);
		  			}else if("unsubscribe" == EventType){                                                        //ȡ����ע�¼�
		  					Reply = UnSubscribeEvent(sqlSession,Param,WechProc);
		  			}else if("SCAN" == EventType){                                                               //ɨ���������ά���¼�
		  					Reply = ScanEvent(sqlSession,Param,WechProc);
		  			}else{                                                                                        //δ֪�¼�
		  					Reply = UnknownEvent(sqlSession,Param,WechProc);
		  			}
				}else if("text" == MsgType) {                                                          //�ı���Ϣ
		  			Reply = TextMessage(sqlSession,Param,WechProc);
				}else if("voice" == MsgType) {                                                         //������Ϣ
		  			Reply = VoiceMessage(sqlSession,Param,WechProc);
				}else if("image" == MsgType) {                                                         //ͼƬ��Ϣ
		  			Reply = ImageMessage(sqlSession,Param,WechProc);
				}else if("video" == MsgType) {                                                         //��Ƶ��Ϣ
		  			Reply = VideoMessage(sqlSession,Param,WechProc);
				}else if("link" == MsgType) {                                                          //������Ϣ
		  			Reply = LinkMessage(sqlSession,Param,WechProc);
				}else{                                                                                      //δ֪��Ϣ
		  			Reply = UnknownMessage(sqlSession,Param,WechProc);
				}
				if(typeof(Reply) == "string"){
						return Reply;
				}
				return WechProc.ThirdAppForward(Param);
		}catch(err){
				return "��������:" + err.name +
				       "\r\n��������:" + err.message +
				       "\r\n������:" + err.lineNumber +
				       "\r\n�ļ���:" + FileName;
		}finally{
				sqlSession.close();
		}
}




		//ȡ��ע�� Followers
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
		//����ע�� Followers
function UpdataFollowers(sqlSession,Followers){
		Followers.put("InitiativeNewsTime",java.util.Date());
    sqlSession.update("RouterBatis.FollowersMapper.updateFollowers",Followers);
    sqlSession.commit();
}
		//�ϴ�����λ�� LocationMessage
function LocationMessage(sqlSession,Param,WechProc){
		var Followers = getFollowers(sqlSession,Param,WechProc);
		
		Followers.put("EndLatitude",Number(Param.get("Location_X")));
		Followers.put("EndLongitude",Number(Param.get("Location_Y")));
		
		UpdataFollowers(sqlSession,Followers);
}
		//�ı���Ϣ TextMessage
function TextMessage(sqlSession,Param,WechProc){
		var Followers = getFollowers(sqlSession,Param,WechProc);
		UpdataFollowers(sqlSession,Followers);
		
		var Content = Param.get("Content");
		if(Content=="WIFI" || Content=="wifi"){
				//��ȡ��֤��
				return ValidationCode(sqlSession,Param,Followers);
		}else if(Content=="0" || Content=="����"){
				//��ȡ��֤����
				return aKeyBinding(sqlSession,Param,Followers);
		}
}
		//������Ϣ VoiceMessage
function VoiceMessage(sqlSession,Param,WechProc){
		var Followers = getFollowers(sqlSession,Param,WechProc);
		UpdataFollowers(sqlSession,Followers);
		
		var Content = Param.get("Recognition");
		if(Content=="WIFI" || Content=="wifi"){
				//��ȡ��֤��
				return ValidationCode(sqlSession,Param,Followers);
		}else if(Content=="0" || Content=="����"){
				//��ȡ��֤����
				return aKeyBinding(sqlSession,Param,Followers);
		}
}
		//ͼƬ��Ϣ ImageMessage
function ImageMessage(sqlSession,Param,WechProc){
		var Followers = getFollowers(sqlSession,Param,WechProc);
		UpdataFollowers(sqlSession,Followers);
}
		//��Ƶ��Ϣ VideoMessage
function VideoMessage(sqlSession,Param,WechProc){
		var Followers = getFollowers(sqlSession,Param,WechProc);
		UpdataFollowers(sqlSession,Followers);
}
		//������Ϣ 
function LinkMessage(sqlSession,Param,WechProc){
		var Followers = getFollowers(sqlSession,Param,WechProc);
		UpdataFollowers(sqlSession,Followers);
}
		//δ֪��Ϣ 
function UnknownMessage(sqlSession,Param,WechProc){
		var Followers = getFollowers(sqlSession,Param,WechProc);
		UpdataFollowers(sqlSession,Followers);
		print("�û���"+Param.get("FromUserName")+"������δ֪��Ϣ:"+Param.get("MsgType"));
}
		
		//�Զ���˵��¼� 
function ClickEvent(sqlSession,Param,WechProc){
		var Followers = getFollowers(sqlSession,Param,WechProc);
		UpdataFollowers(sqlSession,Followers);
		
		var EventKey = Param.get("EventKey");
		if(EventKey=="WIFI" || EventKey=="wifi"){
				//��ȡ��֤��
				return ValidationCode(sqlSession,Param,Followers);
		}else if(EventKey == "Distribution"){
				//�鿴WIFI�ֲ�
				return DistributionMap(sqlSession,Param,Followers);
		}else if(EventKey == "WifiConfirm"){
				//��ȡ��֤����
				return aKeyBinding(sqlSession,Param,Followers);
		}
}
		//�ϱ�����λ���¼� 
function LocationEvent(sqlSession,Param,WechProc){
		var Followers = getFollowers(sqlSession,Param,WechProc);
		
		Followers.put("EndLatitude",java.lang.Double.valueOf(Param.get("Latitude")));
		Followers.put("EndLongitude",java.lang.Double.valueOf(Param.get("Longitude")));
		
		UpdataFollowers(sqlSession,Followers);
		
}
		
/**
 * ������֤����Ϣ
 * @Format     ValidationCode(sqlSession,Param,Followers)
 * @param %1$s ToUserName   ������
 * @param %2$s FromUserName ������
 * @param %3$s CreateTime   ����ʱ��
 * @param %4$s Content      ��֤��
 * @return �ı���ϢXML�ַ���
 */
/**
 * ����WIFI�ֲ���ͼ����Ϣ
 * @Format     DistributionMap(sqlSession,Param,Followers)
 * @param %1$s ToUserName   ������
 * @param %2$s FromUserName ������
 * @param %3$s CreateTime   ����ʱ��
 * @param %4$s Location_X   ������γ��
 * @param %5$s Location_Y   �����߾���
 * @return ͼ����ϢXML�ַ���
 */
/**
 * ����һ����֤ͼ����Ϣ
 * @Format     aKeyBinding(sqlSession,Param,Followers)
 * @param %1$s ToUserName   ������
 * @param %2$s FromUserName ������
 * @param %3$s CreateTime   ����ʱ��
 * @param %4$s Captcha      ��֤��
 * @return ͼ����ϢXML�ַ���
 * @param %4$s PicUrl       ��ʾͼƬURL  -----��ʱͳһ
 */
		//��ע�¼� 
function SubscribeEvent(sqlSession,Param,WechProc){
		var Followers = getFollowers(sqlSession,Param,WechProc);
		UpdataFollowers(sqlSession,Followers);
		if(Param.get("EventKey") != null) {
				print("ɨ���ע:"+Param.get("EventKey"));
				//��ȡ��֤����
				return aKeyBinding(sqlSession,Param,Followers);
		}
		
		var ThirdReply = WechProc.getThirdForwardMap(Param);
		if(null == ThirdReply) {                       //������ת����ת��ʧ��
				//��ȡ��֤����
				return aKeyBinding(sqlSession,Param,Followers);
		}
		
		var MsgType = ThirdReply.get("MsgType");
		
		
		if(null == MsgType) {                       //��Ϣ���Ϳ�
				return aKeyBinding(sqlSession,Param,Followers);
		}else if("text" == MsgType) {                       //�ظ��ı���Ϣ
				/*
				 <Content><![CDATA[���]]></Content>
				*/
				return aKeyBinding(sqlSession,Param,Followers);
		}else if("image" == MsgType) {                //�ظ�ͼƬ��Ϣ
				/*
					<Image>
						<MediaId><![CDATA[media_id]]></MediaId>
					</Image>
				*/
				return aKeyBinding(sqlSession,Param,Followers);
		}else if("voice" == MsgType) {                //�ظ�������Ϣ
				/*
					<Voice>
						<MediaId><![CDATA[media_id]]></MediaId>
					</Voice>
				*/
				return aKeyBinding(sqlSession,Param,Followers);
		}else if("video" == MsgType) {                //�ظ���Ƶ��Ϣ
				/*
					<Video>
						<MediaId><![CDATA[media_id]]></MediaId>
						<Title><![CDATA[title]]></Title>
						<Description><![CDATA[description]]></Description>
					</Video>
				*/
				return aKeyBinding(sqlSession,Param,Followers);
		}else if("music" == MsgType) {                //�ظ�������Ϣ
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
		}else if("news" == MsgType) {                 //�ظ�ͼ����Ϣ
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
				FigMessage.put("Title","�����������WIFI");
				FigMessage.put("Description","�����������WIFI");
				FigMessage.put("PicUrl","http://images.enet.com.cn/2008/0331/60/9331916.jpg");
				FigMessage.put("Url","http://sys.wanwifi.com/MacBinding.do?MacBinding=Manual&Captcha="+BindingMapper.getCaptcha(Followers.get("FollowersID")));
				Articles.add(1,FigMessage);
		}else{                                             //δ֪��Ϣ
  			return aKeyBinding(sqlSession,Param,Followers);
		}
		return WechatP.PackingMap(ThirdReply);
}
		//ȡ����ע�¼� 
function UnSubscribeEvent(sqlSession,Param,WechProc){
		var Followers = getFollowers(sqlSession,Param,WechProc);
		Followers.put("StatusID",-2);
		UpdataFollowers(sqlSession,Followers);
}
		//ɨ���������ά���¼� 
function ScanEvent(sqlSession,Param,WechProc){
		
}
		//δ֪�¼� 
function UnknownEvent(sqlSession,Param,WechProc){
		
}
/**
 * ������֤����Ϣ
 * @Format     ValidationCode(sqlSession,Param,Followers)
 * @param %1$s ToUserName   ������
 * @param %2$s FromUserName ������
 * @param %3$s CreateTime   ����ʱ��
 * @param %4$s Content      ��֤��
 * @return �ı���ϢXML�ַ���
 */
var ValidationCodeXml = "<xml>"+
					 								"<ToUserName><![CDATA[%1$s]]></ToUserName>"+
					 								"<FromUserName><![CDATA[%2$s]]></FromUserName>"+
					 								"<CreateTime>%3$s</CreateTime>"+
					 								"<MsgType><![CDATA[text]]></MsgType>"+
					 								"<Content><![CDATA[WanWifi��֤Ϊ:<a>%4$s</a>,��Чʱ��Ϊʮ����]]></Content>"+
					 							"</xml>";
function ValidationCode(sqlSession,Param,Followers){
		return java.lang.String.format(ValidationCodeXml,Param.get("FromUserName"),Param.get("ToUserName"),Param.get("CreateTime"),BindingMapper.getCaptcha(Followers.get("FollowersID")));
}
/**
 * ����WIFI�ֲ���ͼ����Ϣ
 * @Format     DistributionMap(sqlSession,Param,Followers)
 * @param %1$s ToUserName   ������
 * @param %2$s FromUserName ������
 * @param %3$s CreateTime   ����ʱ��
 * @param %4$s Location_X   ������γ��
 * @param %5$s Location_Y   �����߾���
 * @return ͼ����ϢXML�ַ���
 */
var DistributionMapXml = "<xml>"+
					 								"<ToUserName><![CDATA[%1$s]]></ToUserName>"+
					 								"<FromUserName><![CDATA[%2$s]]></FromUserName>"+
					 								"<CreateTime>%3$s</CreateTime>"+
					 								"<MsgType><![CDATA[news]]></MsgType>"+
					 								"<ArticleCount>1</ArticleCount>"+
					 								"<Articles>"+
					 									"<item>"+
					 										"<Title><![CDATA[����WIFI]]></Title>"+
					 										"<Description><![CDATA[����鿴������������WIFI]]></Description>"+
					 										"<PicUrl><![CDATA[http://sys.wanwifi.com/img/WIFI.jpg]]></PicUrl>"+
					 										"<Url><![CDATA[http://sys.wanwifi.com/Distribution?Location_X=%4$s&Location_Y=%5$s]]></Url>"+
					 									"</item>"+
					 								"</Articles>"+
					 							"</xml>";
function DistributionMap(sqlSession,Param,Followers){
		return java.lang.String.format(DistributionMapXml,Param.get("FromUserName"),Param.get("ToUserName"),Param.get("CreateTime"),Followers.get("EndLatitude"),Followers.get("EndLongitude"));
}
/**
 * ����һ����֤ͼ����Ϣ
 * @Format     aKeyBinding(sqlSession,Param,Followers)
 * @param %1$s ToUserName   ������
 * @param %2$s FromUserName ������
 * @param %3$s CreateTime   ����ʱ��
 * @param %4$s Captcha      ��֤��
 * @return ͼ����ϢXML�ַ���
 * @param %4$s PicUrl       ��ʾͼƬURL  -----��ʱͳһ
 */
var aKeyBindingMap = "<xml>"+
		 								"<ToUserName><![CDATA[%1$s]]></ToUserName>"+
		 								"<FromUserName><![CDATA[%2$s]]></FromUserName>"+
		 								"<CreateTime>%3$s</CreateTime>"+
		 								"<MsgType><![CDATA[news]]></MsgType>"+
		 								"<ArticleCount>1</ArticleCount>"+
		 								"<Articles>"+
		 									"<item>"+
		 										"<Title><![CDATA[�����������WIFI]]></Title>"+
		 										"<Description><![CDATA[�����������WIFI]]></Description>"+
		 										"<PicUrl><![CDATA[http://images.enet.com.cn/2008/0331/60/9331916.jpg]></PicUrl>"+
		 										"<Url><![CDATA[http://sys.wanwifi.com/MacBinding.do?MacBinding=Manual&Captcha=%5$s]]></Url>"+
		 									"</item>"+
		 								"</Articles>"+
		 							"</xml>";
function aKeyBinding(sqlSession,Param,Followers){
		return java.lang.String.format(DistributionMapXml,Param.get("FromUserName"),Param.get("ToUserName"),Param.get("CreateTime"),BindingMapper.getCaptcha(Followers.get("FollowersID")));
}