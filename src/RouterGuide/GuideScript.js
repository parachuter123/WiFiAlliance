importClass(Packages.smny.MyBatisFactory);
importClass(Packages.smny.util.StringToolkit);
importClass(Packages.org.apache.log4j.NDC);
//importPackage(Packages.smny.net.tcp);

var srv_seq;
var router_seq;

function ConnectRequest(mapp){
		router_seq = mapp.get("router_seq").longValue();
		mapp.put("srv_seq",Math.floor(Math.random()*(3294967295+1)));
		srv_seq = mapp.get("srv_seq").longValue();
		mapp.put("order",2);
		mapp.put("mode",2);
		SendOut.Reply(mapp);
}
function ConnectConfirm(mapp){
		var HardSeq = java.lang.String(mapp.get("hard_seq")).trim();
		var DevName = java.lang.String(mapp.get("dev_name")).trim();
		var RouterAgents = java.lang.String(mapp.get("router_agents")).trim();
		if(srv_seq!=mapp.get("srv_seq").longValue() || router_seq!=mapp.get("router_seq").longValue()){
				//与路由通讯握手出错
				SendOut.Stop();
				java.lang.System.out.println("路由器“"+HardSeq+"”通讯握手出错:srv_seq("+srv_seq+","+mapp.get("srv_seq")+");router_seq=("+router_seq+","+mapp.get("router_seq")+")");
				Logger.warn("路由器“"+HardSeq+"”通讯握手出错:srv_seq("+srv_seq+","+mapp.get("srv_seq")+");router_seq=("+router_seq+","+mapp.get("router_seq")+")");
				return;
		}
		NDC.push(HardSeq);
		var Router = MyBatisFactory.selectOne("RouterBatis.WifiRouterMapper.SelectAuthorizeCode",DevName);
		if(Router == null){
				Router = MyBatisFactory.selectOne("RouterBatis.WifiRouterMapper.SelectFactoryRouterID",mapp.get("router_id"));
				if(Router == null){
						//路由未创建或未审核
						SendOut.Stop();
						java.lang.System.out.println("路由器请求引导,处理结果:拒绝;原因:路由未创建或未审核");
						Logger.info("路由器请求引导,处理结果:拒绝;原因:路由未创建或未审核");
						return;
				}
				DevName = Router.get("WifiRouterID");
				mapp.put("dev_name",DevName);
		}
		
		Router.put("RouterAgents",RouterAgents);
		Router.put("DevName",DevName);
		Router.put("HardSeq",HardSeq);
		Router.put("FactoryRouterID",mapp.get("router_id"));
		
		
		var RouterDinate = java.lang.String(mapp.get("router_dinate")).trim();
		var RouterTel = java.lang.String(mapp.get("router_tel")).trim();
		var RouterAddr = java.lang.String(mapp.get("router_addr")).trim();
		var RouterCont = java.lang.String(mapp.get("router_cont")).trim();
		var HeartbeatTime = mapp.get("time");
		
		if(Router.get("RouterAddr")==null || StringToolkit.isNullOrEmpty(Router.get("RouterAddr"))){
				Router.put("RouterAddr",RouterAddr);
		}else{
				mapp.put("router_addr",Router.get("RouterAddr"));
		}
		if(Router.get("RouterCont")==null || StringToolkit.isNullOrEmpty(Router.get("RouterCont"))){
				Router.put("RouterCont",RouterAddr);
		}else{
				mapp.put("router_cont",Router.get("RouterCont"));
		}
		if(Router.get("RouterTel")==null || StringToolkit.isNullOrEmpty(Router.get("RouterTel"))){
				Router.put("RouterTel",RouterAddr);
		}else{
				mapp.put("router_tel",Router.get("RouterTel"));
		}
		if(Router.get("RouterDinate")==null || StringToolkit.isNullOrEmpty(Router.get("RouterDinate"))){
				Router.put("RouterDinate",RouterAddr);
		}else{
				mapp.put("router_dinate",Router.get("RouterDinate"));
		}
		if(Router.get("HeartbeatTime")==null || Router.get("HeartbeatTime").intValue()<10){
				Router.put("HeartbeatTime",HeartbeatTime);
		}else{
				mapp.put("time",Router.get("HeartbeatTime").longValue());
		}
		Router.put("CsType",mapp.get("cs_type"));
		
		//仅更新列：HardID,HardSeq,RouterAddr,RouterCont,RouterTel,RouterAgents,DevName,RouterDinate,FactoryRouterID,BusinesShopID,HeartbeatTime,,
		MyBatisFactory.update("RouterBatis.WifiRouterMapper.updateRouter",Router);
		
		
		var Auth = AuthenPool.getAuthen();
		if(Auth == null){
				//无认证或认证全忙
				SendOut.Stop();
				java.lang.System.out.println("路由器请求引导,处理结果:拒绝;原因:无认证或认证全忙");
				Logger.warn("路由器请求引导,处理结果:拒绝;原因:无认证或认证全忙");
				return;
		}
		
		mapp.put("mode",2);
		mapp.put("order",4);
		mapp.put("auth_srv",Auth.getNetworkAddr()+":"+Auth.getAuthenPort());
		//mapp.put("sj_srv",Auth.getSjSrv().getBytes());
		mapp.put("def_redirect","http://192.168.10.10:80/MacBinding.do");
		//url_data   url_len   UrlData
		mapp.put("url_data","wanwifi.com;smny.cn;txly.com;weixin.qq.com;"+Auth.getNetworkAddr()+":"+Auth.getWebPort());
		
		
		
		
		
		if(!StringToolkit.isNullOrEmpty(Router.get("UrlData"))){
				var UrlDatas = Router.get("UrlData").split(";");
				for(var i=0;i<UrlDatas.length;i++){
						if(!mapp.get("url_data").contains(UrlDatas[i])){
								mapp.put("url_data",mapp.get("url_data")+";"+UrlDatas[i]);
						}
				}
		}
		
		java.lang.System.out.println("url_data:"+mapp.get("url_data"));
		java.lang.System.out.println("auth_srv:"+mapp.get("auth_srv"));
		
		mapp.put("url_data",mapp.get("url_data").getBytes());
		mapp.put("url_len",mapp.get("url_data").length);
		SendOut.Reply(mapp);
		Logger.info("路由器请求引导,处理结果:成功");
		SendOut.Stop();
}
function close(){
    NDC.pop();
    NDC.remove();
}



function AuthenStart(mapp){
		AuthenPool.AuthenStart(mapp);
}
function StartResult(mapp){
		AuthenPool.AuthenStart(mapp);
}
function AuthenQuit(mapp){
		AuthenPool.AuthenSignOut(mapp.get("ReportCode"));
}
function RouterStart(mapp){
		AuthenPool.RouterAccess(mapp.get("ReportCode"),mapp.get("HardSeq"));
}
function RouterQuit(mapp){
		AuthenPool.AuthenSignOut(mapp.get("HardSeq"));
}


function UserAccess(mapp){
		java.lang.System.out.println(java.lang.String(mapp.get("HardSeq")).trim()+"==>"+java.lang.String(mapp.get("TerminalID")).trim()+":上线");
}
function UserDiscon(mapp){
		java.lang.System.out.println(java.lang.String(mapp.get("HardSeq")).trim()+"==>"+java.lang.String(mapp.get("TerminalID")).trim()+":下线");
}