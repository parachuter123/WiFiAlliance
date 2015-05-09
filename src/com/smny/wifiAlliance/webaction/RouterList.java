package com.smny.wifiAlliance.webaction;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import smny.MyBatisFactory;
import smny.util.ServletRefactorMethod;
import smny.util.SetSystemProperty;
import smny.util.StringToolkit;

/**
 * PACKAGE_NAME： webaction
 * <p/>
 * <路由器列表>
 * <p/>
 * 作者：  parachuter
 * 创建时间： 14-12-10.
 * 最后修改时间：15-01-16
 */
@WebServlet(name = "RouterList", urlPatterns = "/routerlist.htm")
public class RouterList extends HttpServlet {
	private static Logger logger = Logger.getLogger(RouterList.class.getName());
	private static final long serialVersionUID = 1L;
	//默认页码
    private static final int DEFAULTPAGEINDEX = 1;
    //默认页大小
    private static final int DEFAULTPAGESIZE = 8;
	//消息接口设置 修改
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String,Object> ServletRefactorMethodMap = ServletRefactorMethod.start(request, response);
    	if(ServletRefactorMethodMap==null){
    		return;
    	}
        Integer UserType = (Integer)ServletRefactorMethodMap.get("UserType");
        String Operator = (String)ServletRefactorMethodMap.get("Operator");
        SetSystemProperty properties = (SetSystemProperty) ServletRefactorMethodMap.get("ssp");
		SqlSession sqlSession = MyBatisFactory.openSession();
		String Type = request.getParameter("Type");
		try {
			switch(UserType.intValue()){
				case 3:
				case 2:
				case 4:
					if("creatRouter".equals(Type)){
						String DevName = request.getParameter("DevName");
						String RouterCont = request.getParameter("RouterCont");
						String RouterTel = request.getParameter("RouterTel");
						String RouterAddr = request.getParameter("RouterAddr");
						String AppEntranceID = request.getParameter("AddAppEntrance");
						String AddBrowseGuide = request.getParameter("AddBrowseGuide");
						String AddPCBrowseGuide = request.getParameter("AddPCBrowseGuide");
						String AddBrowseAuthGuide = request.getParameter("AddBrowseAuthGuide");
						if (StringToolkit.isNullOrEmpty(DevName)) {
							DevName = StringToolkit.getRandomString();
						}
						if (StringToolkit.isNullOrEmpty(AppEntranceID)) {
							AppEntranceID = "";
						}
						if (StringToolkit.isNullOrEmpty(AddBrowseGuide)) {
							AddBrowseGuide = "";
						}
						if (StringToolkit.isNullOrEmpty(AddBrowseAuthGuide)) {
							AddBrowseAuthGuide = "";
						}
						if (StringToolkit.isNullOrEmpty(AddPCBrowseGuide)) {
							AddPCBrowseGuide = "";
						}
						if (StringToolkit.isNullOrEmpty(RouterCont)) {
								request.setAttribute("Operat","2");
								request.setAttribute("ErrorMessage","户主名称不能为空！");
								request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
								return;
						}
						if (StringToolkit.isNullOrEmpty(RouterTel)) {
								request.setAttribute("Operat","3");
								request.setAttribute("ErrorMessage","联系电话不能为空！");
								request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
								return;
						}
						if (StringToolkit.isNullOrEmpty(RouterAddr)) {
								request.setAttribute("Operat","4");
								request.setAttribute("ErrorMessage","地址不能为空！");
								request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
								return;
						}
						Map<String,Object> WifiRouter = new HashMap<String,Object>();
						
						WifiRouter.put("DevName", DevName);
						WifiRouter.put("RouterCont", RouterCont);
						WifiRouter.put("RouterTel", RouterTel);
						WifiRouter.put("RouterAddr", RouterAddr);
						WifiRouter.put("BusinesShopID",Operator);
						WifiRouter.put("HardSeq","");
						WifiRouter.put("FactoryRouterID","");
						WifiRouter.put("Longitude",Double.parseDouble("114.36350"));
						WifiRouter.put("Latitude",Double.parseDouble("36.09841"));
						WifiRouter.put("AppEntranceID",AppEntranceID);
						WifiRouter.put("BrowseGuide",AddBrowseGuide);
						WifiRouter.put("ComputerGuide",AddPCBrowseGuide);
						WifiRouter.put("BrowseGuideType",AddBrowseAuthGuide);
						try {
							sqlSession.insert("RouterBatis.WifiRouterMapper.insertRouter",WifiRouter);
							sqlSession.commit();
							request.setAttribute("Operat","1");
							request.setAttribute("ErrorMessage","路由创建成功！！");
						} catch (Exception e) {
							request.setAttribute("Operat","-4");
							request.setAttribute("ErrorMessage",e.getMessage().replace("\r\n", ""));
							request.setAttribute("ShowBug",properties.getValue("ShowBug"));
							request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
						} 
						request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
						return;
					}
					//修改路由器参数
					if ("Change".equals(Type)) {
							String RouterID = request.getParameter("RouterID");
							String DefaultAppid = request.getParameter("DefaultAppid");
							String DevName = request.getParameter("DevName");
							String RouterAddr = request.getParameter("RouterAddr");
							String RouterCont = request.getParameter("RouterCont");
							String RouterTel = request.getParameter("RouterTel");
							String IntroductionURL = request.getParameter("IntroductionURL");
							String Longitude = request.getParameter("Longitude");
							String Latitude = request.getParameter("Latitude");
							String BrowseGuide = request.getParameter("BrowseGuide");
							String PCBrowseGuide = request.getParameter("PCBrowseGuide");
							String BrowseAuthGuide = request.getParameter("BrowseAuthGuide");
							String ShareTaskID = request.getParameter("ShareTaskID");
							String UrlData = request.getParameter("UrlData");
							if (StringToolkit.isNullOrEmpty(ShareTaskID)) {
								ShareTaskID="0";
							}
							BrowseGuide = java.net.URLDecoder.decode(BrowseGuide,"UTF-8");
							PCBrowseGuide = java.net.URLDecoder.decode(PCBrowseGuide,"UTF-8");
							if (StringToolkit.isNullOrEmpty(RouterID)) {
									request.setAttribute("Operat","5");
									request.setAttribute("ErrorMessage","路由ID不能为空");
									request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
									return;
							}
							Map<String,Object> WifiRouter = sqlSession.selectOne("RouterBatis.WifiRouterMapper.SelectRouterID", RouterID);

							if (WifiRouter == null) {
									request.setAttribute("Operat","3");
									request.setAttribute("ErrorMessage","指定的路由不存在");
									request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
									return;
							}
							boolean isChange = false;
							if (!BrowseGuide.equals(WifiRouter.get("BrowseGuide"))) {
									WifiRouter.put("BrowseGuide", BrowseGuide);
									isChange = true;
							}
							if (!PCBrowseGuide.equals(WifiRouter.get("PCBrowseGuide"))) {
								WifiRouter.put("PCBrowseGuide", PCBrowseGuide);
								isChange = true;
							}
							if (!StringToolkit.isNullOrEmpty(DevName) && !DevName.equals(WifiRouter.get("DevName"))) {
									WifiRouter.put("DevName",DevName);
									isChange = true;
							}
							if (!StringToolkit.isNullOrEmpty(RouterAddr) && !RouterAddr.equals(WifiRouter.get("RouterAddr"))) {
									WifiRouter.put("RouterAddr",RouterAddr);
									isChange = true;
							}
							if (!StringToolkit.isNullOrEmpty(RouterCont) && !RouterCont.equals(WifiRouter.get("RouterCont"))) {
									WifiRouter.put("RouterCont",RouterCont);
									isChange = true;
							}
							if (!StringToolkit.isNullOrEmpty(RouterTel) && !RouterTel.equals(WifiRouter.get("RouterTel"))) {
									WifiRouter.put("RouterTel",RouterTel);
									isChange = true;
							}
							if (!StringToolkit.isNullOrEmpty(IntroductionURL) && !IntroductionURL.equals(WifiRouter.get("IntroductionURL"))) {
									WifiRouter.put("IntroductionURL",IntroductionURL);
									isChange = true;
							}
							if (!StringToolkit.isNullOrEmpty(Longitude)) {
									Double lon = Double.valueOf(Longitude);
									if (lon != WifiRouter.get("Longitude")) {
											WifiRouter.put("Longitude",lon);
											isChange = true;
									}
							}else{
								WifiRouter.put("Longitude",Double.parseDouble("114.36350"));
								isChange = true;
							}
							if (!StringToolkit.isNullOrEmpty(Latitude)) {
									Double lat = Double.valueOf(Latitude);
									if (lat != WifiRouter.get("Latitude")) {
											WifiRouter.put("Latitude",lat);
											isChange = true;
									}
							}else{
								WifiRouter.put("Latitude",Double.parseDouble("36.09841"));
								isChange = true;
							}
							if (!DefaultAppid.equals(WifiRouter.get("AppEntranceID"))) {
									WifiRouter.put("AppEntranceID",DefaultAppid);
									isChange = true;
							}
							if (WifiRouter.get("TaskID")==null || !ShareTaskID.equals(WifiRouter.get("TaskID").toString())) {
								WifiRouter.put("TaskID",Integer.parseInt(ShareTaskID));
								isChange = true;
							}
							if (WifiRouter.get("UrlData")==null || !UrlData.equals((String)WifiRouter.get("UrlData"))) {
								WifiRouter.put("UrlData",UrlData);
								isChange = true;
							}
							if (WifiRouter.get("BrowseGuideType")==null || !BrowseAuthGuide.equals((String)WifiRouter.get("BrowseGuideType"))) {
								WifiRouter.put("BrowseGuideType",BrowseAuthGuide);
								isChange = true;
							}
							if(isChange){
								try {
									sqlSession.update("RouterBatis.WifiRouterMapper.updateRouter",WifiRouter);
									sqlSession.commit();
									request.setAttribute("Operat","1");
									request.setAttribute("ErrorMessage","修改成功");
								}catch(Exception e){
									request.setAttribute("Operat","-4");
									request.setAttribute("ErrorMessage",e.getMessage().replace("\r\n", ""));
									request.setAttribute("ShowBug",properties.getValue("ShowBug"));
									request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
								}
							}else{
								request.setAttribute("Operat","3");
								request.setAttribute("ErrorMessage","数据无变更!");
							}
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
					}
					if ("getRouterInfo".equals(Type)) {
						String RouterID = request.getParameter("RouterID");
						if (StringToolkit.isNullOrEmpty(RouterID)) {
								request.setAttribute("Operat","5");
								request.setAttribute("ErrorMessage","路由ID不能为空");
								request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
								return;
						}
						Map<String,Object> WifiRouter = sqlSession.selectOne("RouterBatis.WifiRouterMapper.SelectRouterID",RouterID);
						if (WifiRouter == null) {
								request.setAttribute("Operat","6");
								request.setAttribute("ErrorMessage","指定的路由不存在");
								request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
								return;
						}
						Map<String,Object> InfoTemplate = sqlSession.selectOne("RouterBatis.InfoTemplateMapper.SelectKey",WifiRouter.get("BrowseGuide"));
						if(InfoTemplate == null){
							InfoTemplate = null;
						}
						StringBuilder json = new StringBuilder("{");
		                json.append("\"FactoryRouterID\": \"").append(WifiRouter.get("FactoryRouterID")).append("\",");
		                json.append("\"HardID\": \"").append(WifiRouter.get("HardID")).append("\",");
		                json.append("\"HardSeq\": \"").append(WifiRouter.get("HardSeq")).append("\",");
		                json.append("\"RouterAddr\": \"").append(URLEncoder.encode((WifiRouter.get("RouterAddr") == null ? "" : WifiRouter.get("RouterAddr")).toString(), "UTF-8")).append("\",");
		                json.append("\"RouterCont\": \"").append(URLEncoder.encode((WifiRouter.get("RouterCont") == null ? "" : WifiRouter.get("RouterCont")).toString(), "UTF-8")).append("\",");
		                json.append("\"RouterTel\": \"").append(WifiRouter.get("RouterTel")).append("\",");
		                json.append("\"RouterDinate\": \"").append(WifiRouter.get("RouterDinate")).append("\",");
		                json.append("\"RouterAgents\": \"").append(WifiRouter.get("RouterAgents")).append("\",");
		                json.append("\"DevName\": \"").append(URLEncoder.encode((WifiRouter.get("DevName") == null ? "" : WifiRouter.get("DevName")).toString(), "UTF-8")).append("\",");
		                //json.append("\"AuthSrv\": \"").append(wr.getAuthSrv()).append("\",");
		                //json.append("\"SjSrv\": \"").append(wr.getSjSrv()).append("\",");
		                //json.append("\"MngSrv\": \"").append(wr.getMngSrv()).append("\",");
		                //json.append("\"DefRedirect\": \"").append(wr.getDefRedirect()).append("\",");
		                json.append("\"CsType\": \"").append(WifiRouter.get("CsType")).append("\",");
		                json.append("\"UrlData\": \"").append(WifiRouter.get("UrlData")).append("\",");
		                json.append("\"HeartbeatTime\": \"").append(WifiRouter.get("HeartbeatTime")).append("\",");
		                json.append("\"Longitude\": \"").append(WifiRouter.get("Longitude")).append("\",");
		                json.append("\"Latitude\": \"").append(WifiRouter.get("Latitude")).append("\",");
		                json.append("\"MaxConnectCount\": \"").append(WifiRouter.get("MaxConnectCount")).append("\",");
		                json.append("\"IntroductionURL\": \"").append(WifiRouter.get("IntroductionURL")).append("\",");
		                json.append("\"ShareTaskID\": \"").append(WifiRouter.get("TaskID")).append("\",");
		                json.append("\"BrowseGuide\": \"").append(InfoTemplate == null ? "" : (String)InfoTemplate.get("Key")).append("\",");
		                json.append("\"BrowseAuthGuide\": \"").append(WifiRouter.get("BrowseGuideType")).append("\",");
		                json.append("\"AppEntranceID\": \"").append(WifiRouter.get("AppEntranceID")).append("\"");
		                //json.append("\"WanIp\": \"").append(wr.getWanIp()).append("\",");
		                //json.append("\"PortNumber\": \"").append(wr.getPortNumber()).append("\",");
		                //json.append("\"LoginName\": \"").append(URLEncoder.encode(wr.getLoginName() == null ? "" : wr.getLoginName(), "UTF-8")).append("\",");
		                //json.append("\"PassWord\": \"").append(URLEncoder.encode(wr.getPassWord() == null ? "" : wr.getPassWord(), "UTF-8")).append("\"");
		                json.append("}");
		                response.setContentType("text/json; charset=utf-8"); 
		                response.getWriter().print(json.toString());
						//request.setAttribute("WifiRouter","WifiRouter");
						//request.getRequestDispatcher("/RouterJsonData.jsp").forward(request, response);
						return;
					}
					if ("Delete".equals(Type)) {
						if (4 != UserType) {
							request.setAttribute("Operat","7");
							request.setAttribute("ErrorMessage","无此权限！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
						}
						String RouterID = request.getParameter("RouterID");
						if (StringToolkit.isNullOrEmpty(RouterID)) {
							request.setAttribute("Operat","8");
							request.setAttribute("ErrorMessage","路由ID不能为空");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
						}
						try {
							sqlSession.delete("RouterBatis.WifiRouterMapper.deleteRouterID",RouterID);
							sqlSession.commit();
							request.setAttribute("Operat","1");
							request.setAttribute("ErrorMessage","删除成功");
						} catch (Exception e) {
							request.setAttribute("Operat","-4");
							request.setAttribute("ErrorMessage",e.getMessage().replace("\r\n", ""));
							request.setAttribute("ShowBug",properties.getValue("ShowBug"));
							request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
						}
						request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
						return;
					}
					break;
					default:
						request.setAttribute("Operat","-2");
						request.setAttribute("ErrorMessage","角色选择错误:"+UserType);
						request.setAttribute("ShowBug",properties.getValue("ShowBug"));
						request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
						request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
					break;
			}
		} catch (Exception e) {
			request.setAttribute("Operat","-5");
			request.setAttribute("ErrorMessage",e.getMessage().replace("\r\n", ""));
			request.setAttribute("ShowBug",properties.getValue("ShowBug"));
			request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
			request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
		}finally{
			sqlSession.close();
		}
    }

    //消息接口列表
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Map<String,Object> ServletRefactorMethodMap = ServletRefactorMethod.start(request, response);
    	if(ServletRefactorMethodMap==null){
    		return;
    	}
        Integer UserType = (Integer)ServletRefactorMethodMap.get("UserType");
        String Operator = (String)ServletRefactorMethodMap.get("Operator");
        SetSystemProperty properties = (SetSystemProperty) ServletRefactorMethodMap.get("ssp");
        String PageIndex = request.getParameter("PageIndex");
        int pageIndex = DEFAULTPAGEINDEX;
        if (!StringToolkit.isNullOrEmpty(PageIndex)) {
        	pageIndex = Integer.parseInt(PageIndex);
        }
        SqlSession sqlSession = MyBatisFactory.openSession();
        try {
        	List<Map<String,Object>> WifiRouterList = null;
			switch(UserType.intValue()){
				case 1:
					//获取路由器数量
					int  WifiRouterCount1 = (Integer)sqlSession.selectOne("RouterBatis.WifiRouterMapper.SelectWifiRouterCount");
					int StartNumber1 = (pageIndex-1)*DEFAULTPAGESIZE;
					if(WifiRouterCount1 <= StartNumber1){
						pageIndex = (int)Math.ceil((double)WifiRouterCount1/DEFAULTPAGESIZE);
						StartNumber1 = (pageIndex-1)*DEFAULTPAGESIZE;
					}
					WifiRouterList = sqlSession.selectList("RouterBatis.WifiRouterMapper.SelectRouterAll",new RowBounds(StartNumber1, DEFAULTPAGESIZE));
					request.setAttribute("list", WifiRouterList);
					request.setAttribute("WifiRouterCount", WifiRouterCount1);
					request.setAttribute("PageIndex", pageIndex);
			        request.getRequestDispatcher("routerlist.jsp").forward(request, response);
					break;
				//高级代理
				case 2:
				//代理
				case 3:
					Map<String,Object> Agent = sqlSession.selectOne("RouterBatis.AgentMapper.SelectAgentID",Operator);
					if(Agent == null){
						request.setAttribute("Operat","9");
						request.setAttribute("ErrorMessage","不存在指定代理");
						request.getRequestDispatcher("/AuthHtmlError.jsp").forward(request, response);
						return;
					}
					//获取路由器数量
					int  WifiRouterCount3 = (Integer)sqlSession.selectOne("RouterBatis.WifiRouterMapper.SelectWifiRouterCountByAgentID",Operator);
					int StartNumber3 = (pageIndex-1)*DEFAULTPAGESIZE;
					if(WifiRouterCount3 <= StartNumber3){
						pageIndex = (int)Math.ceil((double)WifiRouterCount3/DEFAULTPAGESIZE);
						StartNumber3 = (pageIndex-1)*DEFAULTPAGESIZE;
					}
					//获取路由器列表
					WifiRouterList = sqlSession.selectList("RouterBatis.WifiRouterMapper.SelectAgentID",Operator,new RowBounds(StartNumber3, DEFAULTPAGESIZE));
					//WifiRouterList = sqlSession.selectList("RouterBatis.WifiRouterMapper.SelectAgentID",Operator);
					request.setAttribute("list", WifiRouterList);
					request.setAttribute("WifiRouterCount", WifiRouterCount3);
					request.setAttribute("PageIndex", pageIndex);
			        request.getRequestDispatcher("routerlist.jsp").forward(request, response);
					break;
				//商户
				case 4:
					Map<String,Object> BusinessShop = sqlSession.selectOne("RouterBatis.BusinesShopMapper.SelectBusinesShopID", Operator);
					if (BusinessShop == null) {
						request.setAttribute("Operat","10");
						request.setAttribute("ErrorMessage","不存在指定商家");
						request.getRequestDispatcher("/AuthHtmlError.jsp").forward(request, response);
						return;
		            }
					//获取路由器数量
					int  WifiRouterCount = (Integer)sqlSession.selectOne("RouterBatis.WifiRouterMapper.SelectWifiRouterCountByBusiID", BusinessShop.get("BusinesShopID"));
					int StartNumber = (pageIndex-1)*DEFAULTPAGESIZE;
					if(WifiRouterCount <= StartNumber){
						pageIndex = (int)Math.ceil((double)WifiRouterCount/DEFAULTPAGESIZE);
						StartNumber = (pageIndex-1)*DEFAULTPAGESIZE;
					}
					//获取路由器列表和商家下的消息接口
					List<Map<String,Object>> WifiRouter = sqlSession.selectList("RouterBatis.WifiRouterMapper.SelectBussessShopID", BusinessShop.get("BusinesShopID"),new RowBounds(StartNumber, DEFAULTPAGESIZE));
					//List<Map<String,Object>> WifiRouter = sqlSession.selectList("RouterBatis.WifiRouterMapper.SelectBussessShopID", BusinessShop.get("BusinesShopID"));
					List<Map<String,Object>> AppEntrance = sqlSession.selectList("RouterBatis.AppEntranceMapper.SelectBusinessShopID", BusinessShop.get("BusinesShopID"));
					
					//根据商家ID和和广告类型选择对应广告类型
					Map<String,Object> InfoArgu = new HashMap<String,Object>();
					InfoArgu.put("BusinesShopID", BusinessShop.get("BusinesShopID"));
					InfoArgu.put("Type", "1");
					List<Map<String,Object>> AdInfo = sqlSession.selectList("RouterBatis.InfoTemplateMapper.ByBusinesShopIDAndTypeGetTemplate", InfoArgu);
					
					//查询该商家下可查询的所有分享任务
					List<Map<String,Object>> ShareTaskList = sqlSession.selectList("RouterBatis.ShareTaskMapper.SelectBusinessShopID",Operator);
					
					request.setAttribute("ShareTaskList", ShareTaskList);
					//AppEntrance==null||AppEntrance.size()==0?null:AppEntrance
					request.setAttribute("Applist", AppEntrance);
					request.setAttribute("list", WifiRouter);
					request.setAttribute("WifiRouterCount", WifiRouterCount);
					request.setAttribute("PageIndex", pageIndex);
					request.setAttribute("Adlist", AdInfo);
					request.setAttribute("getRandomString", StringToolkit.getRandomString());
			        request.getRequestDispatcher("routerlist.jsp").forward(request, response);
					break;
				default:
					request.setAttribute("Operat","-8");
					request.setAttribute("ErrorMessage","角色选择错误:"+UserType);
					request.setAttribute("ShowBug",properties.getValue("ShowBug"));
					request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
					request.getRequestDispatcher("/AuthHtmlError.jsp").forward(request, response);
					break;
			}
		} catch (Exception e) {	
			request.setAttribute("Operat","-9");
			request.setAttribute("ErrorMessage",e.getMessage().replace("\r\n", ""));
			request.setAttribute("ShowBug",properties.getValue("ShowBug"));
			request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
			request.getRequestDispatcher("/AuthHtmlError.jsp").forward(request, response);
		}finally{
			sqlSession.close();
		}
    }
}
