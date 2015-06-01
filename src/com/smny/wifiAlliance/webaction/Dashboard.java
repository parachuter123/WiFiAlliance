package com.smny.wifiAlliance.webaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.ibatis.session.SqlSession;

import smny.MyBatisFactory;
import smny.util.ServletRefactorMethod;
import smny.util.SetSystemProperty;
import smny.util.StringToolkit;

/**
 * PACKAGE_NAME： webaction
 * <p/>
 * <仪表盘>
 * <p/>
 * 作者：  Ronan
 * 创建时间： 14-12-04.
 */
@WebServlet(name = "Dashboard", urlPatterns = "/Dashboard.htm")
public class Dashboard extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,Object> ServletRefactorMethodMap = ServletRefactorMethod.start(request, response);
    	if(ServletRefactorMethodMap==null){
    		return;
    	}
        Integer UserType = (Integer)ServletRefactorMethodMap.get("UserType");
        String Operator = (String)ServletRefactorMethodMap.get("Operator");
        SetSystemProperty properties = (SetSystemProperty) ServletRefactorMethodMap.get("ssp");
        SqlSession sqlSession = MyBatisFactory.openSession();
        //商户
        try {
			switch(UserType.intValue()){
				case 2:
					String type2 = request.getParameter("type");
					if(type2.equals("getRouterDetail")){
						String businessShopID2 = request.getParameter("businessShop");
						String html2 = this.getEachBussinessRouterDetail(businessShopID2,sqlSession);
		                response.setContentType("text/html; charset=utf-8");
				        response.getWriter().print(html2);
					}else if(type2.equals("overrallStatistics")){
						String days = request.getParameter("timeperiods");
						Map<String,Object> agentTimeperiods = this.agentSystemStation(sqlSession, Integer.parseInt(Operator), Integer.parseInt(days));
						StringBuilder json = new StringBuilder("{");
				        json.append("\"SumWeiXinCount\": \"").append(agentTimeperiods.get("SumWeiXinCount")).append("\",");
				        json.append("\"SumSmsCount\": \"").append(agentTimeperiods.get("SumSmsCount")).append("\",");
				        json.append("\"SumTryCount\": \"").append(agentTimeperiods.get("SumTryCount")).append("\"");
				        json.append("}");
		                response.setContentType("text/json; charset=utf-8");
				        response.getWriter().print(json.toString());
					}
					break;
				case 3:
					String type3 = request.getParameter("type");
					if(type3.equals("getRouterDetail")){
						String businessShopID3 = request.getParameter("businessShop");
						String html3 = this.getEachBussinessRouterDetail(businessShopID3,sqlSession);
		                response.setContentType("text/html; charset=utf-8");
				        response.getWriter().print(html3);
					}else if(type3.equals("overrallStatistics")){
						String days = request.getParameter("timeperiods");
						Map<String,Object> agentTimeperiods = this.agentSystemStation(sqlSession, Integer.parseInt(Operator), Integer.parseInt(days));
						StringBuilder json = new StringBuilder("{");
				        json.append("\"SumWeiXinCount\": \"").append(agentTimeperiods.get("SumWeiXinCount")).append("\",");
				        json.append("\"SumSmsCount\": \"").append(agentTimeperiods.get("SumSmsCount")).append("\",");
				        json.append("\"SumTryCount\": \"").append(agentTimeperiods.get("SumTryCount")).append("\"");
				        json.append("}");
		                response.setContentType("text/json; charset=utf-8");
				        response.getWriter().print(json.toString());
					}
					break;
				case 4:
					Map<String,Object> BusinessShop = sqlSession.selectOne("RouterBatis.BusinesShopMapper.SelectBusinesShopID",Operator);
					if (BusinessShop == null) {
						request.setAttribute("Operat","3");
						request.setAttribute("ErrorMessage","无此商家！");
						request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
						return;
					}
					String type = request.getParameter("type");
					if(type.equals("SMSstatistics")){
						String timeperiods = request.getParameter("timeperiods");
						String timelength = "";
						
						if(StringToolkit.isNullOrEmpty(timeperiods)){
							timelength = "24";
						}else if(timeperiods.equals("week")){
							timelength = "168";
						}else if(timeperiods.equals("month")){
							timelength = "720";
						}else if(timeperiods.equals("season")){
							timelength = "2160";
						}
						Map<String,Object> timeMap = new HashMap<String,Object>();
						timeMap.put("Operator", Operator);
						timeMap.put("timelength", timelength);
						int TodayPhoneWatch = (Integer)sqlSession.selectOne("RouterBatis.WifiTerminalMapper.SelectTodayPhoneWatch", timeMap);
						
						StringBuilder json = new StringBuilder("{");
				        json.append("\"TodayPhoneWatch\": \"").append(TodayPhoneWatch).append("\"");
				        json.append("}");
		                response.setContentType("text/json; charset=utf-8");
				        response.getWriter().print(json.toString());
					}else if(type.equals("overrallStatistics")){
						String timeperiods = request.getParameter("timeperiods");
						String WifiRouterChoose = request.getParameter("WifiRouterChoose");
						System.out.println("timeperiods:" + timeperiods);
						System.out.println("WifiRouterChoose:" + WifiRouterChoose);
						List<Map<String, Object>> RouterAuthRecordList =  this.getRouterAuthRecordList(WifiRouterChoose,timeperiods,sqlSession);
						
						StringBuilder json = new StringBuilder("{");
				        json.append("\"RouterAuthRecordList\": \"").append(JSONArray.fromObject(RouterAuthRecordList)).append("\"");
				        json.append("}");
		                response.setContentType("text/json; charset=utf-8");
				        response.getWriter().print(json.toString());
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
		}catch(Exception e){
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
        SqlSession sqlSession = MyBatisFactory.openSession();
        try {
			switch(UserType.intValue()){
				//高级代理
				case 2:
					/*Map<String,Object> map = this.accountRouter(Operator, sqlSession);
					request.setAttribute("AgentNum", map.get("AgentNum"));
					request.setAttribute("BusinessShopCount", map.get("BusinessShopCount"));
					request.setAttribute("EachBusinesShopList", map.get("EachBusinesShopList"));
					request.setAttribute("RouterCount", map.get("RouterCount"));
					request.setAttribute("OnlineRouterCount", map.get("OnlineRouterCount"));
					request.getRequestDispatcher("index.jsp").forward(request, response);
					break;*/
				//代理					
				case 3:
					//显示代理系统状态内参数
					Map<String,Object> agentResultMap = this.agentSystemStation(sqlSession, Integer.parseInt(Operator),365);
					//每个商家路由器状态
					Map<String,Object> map3 = this.accountRouter(Operator, sqlSession);
					request.setAttribute("AgentNum", map3.get("AgentNum"));
					request.setAttribute("BusinessShopCount", map3.get("BusinessShopCount"));
					request.setAttribute("EachBusinesShopList", map3.get("EachBusinesShopList"));
					request.setAttribute("RouterCount", map3.get("RouterCount"));
					request.setAttribute("OnlineRouterCount", map3.get("OnlineRouterCount"));

		            request.setAttribute("OnlineCount", agentResultMap.get("OnlineCount"));
					request.setAttribute("TotalCount", agentResultMap.get("TotalCount"));
		            
					request.setAttribute("WeiXinCount", agentResultMap.get("WeiXinCount"));
		            request.setAttribute("SmsCount", agentResultMap.get("SmsCount"));
		            request.setAttribute("TryCount", agentResultMap.get("TryCount"));
		            
		            request.setAttribute("SumWeiXinCount", agentResultMap.get("SumWeiXinCount"));
		            request.setAttribute("SumSmsCount", agentResultMap.get("SumSmsCount"));
		            request.setAttribute("SumTryCount", agentResultMap.get("SumTryCount"));
					request.getRequestDispatcher("index.jsp").forward(request, response);
					break;
				case 4:
					Map<String,Object> BusinessShop = sqlSession.selectOne("RouterBatis.BusinesShopMapper.SelectBusinesShopID", Operator);
					if (BusinessShop == null) {
						request.setAttribute("Operat","4");
						request.setAttribute("ErrorMessage","当前商家不存在或已删除");
						request.getRequestDispatcher("/AuthHtmlError.jsp").forward(request, response);
						return;
					}
					String timeperiods = request.getParameter("timeperiods");
					String timelength = "";
					if(StringToolkit.isNullOrEmpty(timeperiods)){
						timelength = "24";
					}else if(timeperiods.equals("week")){
						timelength = "168";
					}else if(timeperiods.equals("month")){
						timelength = "720";
					}else if(timeperiods.equals("season")){
						timelength = "2160";
					}
					Map<String,Object> timeMap = new HashMap<String,Object>();
					timeMap.put("Operator", Operator);
					timeMap.put("timelength", "24");
					
					List<Map<String,Object>> WifiRouter = sqlSession.selectList("RouterBatis.WifiRouterMapper.SelectBussessShopID", (String)BusinessShop.get("BusinesShopID"));
					List<Map<String,Object>> AppEntrance = sqlSession.selectList("RouterBatis.AppEntranceMapper.SelectBusinessShopID", (String)BusinessShop.get("BusinesShopID"));
					
					//通过手机验证码关注总数量
					int PhoneWatchTotal = (Integer)sqlSession.selectOne("RouterBatis.WifiTerminalMapper.SelectPhoneWatch", Operator);
					
					//不同时间段通过手机验证码关注总数量，如：日，周，月，季
					int TodayPhoneWatch = (Integer)sqlSession.selectOne("RouterBatis.WifiTerminalMapper.SelectTodayPhoneWatch", timeMap);
					
					//显示商家系统状态内参数
					Map<String,Object> businessResultMap = this.businessSystemStation(WifiRouter, sqlSession,365,"","7");
					
					//获得每个路由器在线状态
					List<Map<String,Object>> Routerlist = this.getBussinessRouterDetail(Operator,sqlSession);
					
					request.setAttribute("Routerlist", WifiRouter);
		            request.setAttribute("Applist", AppEntrance);
		            request.setAttribute("PhoneWatchTotal", PhoneWatchTotal);
		            request.setAttribute("TodayPhoneWatch", TodayPhoneWatch);
		            request.setAttribute("key", businessResultMap.get("key"));
		            request.setAttribute("value", businessResultMap.get("value"));
		            request.setAttribute("Routerlist", Routerlist);
		            request.setAttribute("TotalCount", businessResultMap.get("TotalCount"));
		            
		            request.setAttribute("WeiXinCount", businessResultMap.get("WeiXinCount"));
		            request.setAttribute("SmsCount", businessResultMap.get("SmsCount"));
		            request.setAttribute("TryCount", businessResultMap.get("TryCount"));
		            
		            /*request.setAttribute("SumWeiXinCount", businessResultMap.get("SumWeiXinCount"));
		            request.setAttribute("SumSmsCount", businessResultMap.get("SumSmsCount"));
		            request.setAttribute("SumTryCount", businessResultMap.get("SumTryCount"));*/
		            request.setAttribute("RouterAuthRecordList", businessResultMap.get("RouterAuthRecordList"));
		            
		            request.setAttribute("OnlineCount", businessResultMap.get("OnlineCount"));
		            request.getRequestDispatcher("index.jsp").forward(request, response);
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
	/**
	 * 代理系统状态统计
	 * @param sqlSession
	 * @param agentID		代理ID
	 * @return
	 */
	private Map<String,Object> agentSystemStation(SqlSession sqlSession,int agentID,int days){
		List<Map<String,Object>> businessShopList = sqlSession.selectList("RouterBatis.BusinesShopMapper.SelectAgentID", agentID);
		Iterator<Map<String, Object>> businessIterator = businessShopList.iterator();
		//接入总人数
		int TotalCount = 0;
		//商家在线总人数
		int OnlineCount = 0;
		//微信认证总人数
		int WeiXinCount = 0;
		//短信认证总人数
		int SmsCount = 0;
		//试用认证总人数
		int TryCount = 0;
		//累计微信认证总人数
		int SumWeiXinCount = 0;
		//累计短信认证总人数
		int SumSmsCount = 0;
		//累计试用认证总人数
		int SumTryCount = 0;
		while(businessIterator.hasNext()){
			Map<String,Object> business = businessIterator.next();
			List<Map<String,Object>> WifiRouter = sqlSession.selectList("RouterBatis.WifiRouterMapper.SelectBussessShopID", (String)business.get("BusinesShopID"));
			Map<String,Object> businessTempMap = this.businessSystemStation(WifiRouter, sqlSession,days,"","");
			TotalCount = TotalCount + (Integer)businessTempMap.get("TotalCount");
			OnlineCount = OnlineCount + (Integer)businessTempMap.get("OnlineCount");
			
			WeiXinCount = WeiXinCount + (Integer)businessTempMap.get("WeiXinCount");
			SmsCount = SmsCount + (Integer)businessTempMap.get("SmsCount");
			TryCount = TryCount + (Integer)businessTempMap.get("TryCount");
			
			SumWeiXinCount = SumWeiXinCount + (Integer)businessTempMap.get("SumWeiXinCount");
			SumSmsCount = SumSmsCount + (Integer)businessTempMap.get("SumSmsCount");
			SumTryCount = SumTryCount + (Integer)businessTempMap.get("SumTryCount");
		}
		Map<String,Object> agentResult = new HashMap<String,Object>();
		agentResult.put("TotalCount", TotalCount);
		agentResult.put("OnlineCount", OnlineCount);
		agentResult.put("WeiXinCount", WeiXinCount);
		agentResult.put("SmsCount", SmsCount);
		agentResult.put("TryCount", TryCount);
		agentResult.put("SumWeiXinCount", SumWeiXinCount);
		agentResult.put("SumSmsCount", SumSmsCount);
		agentResult.put("SumTryCount", SumTryCount);
		return agentResult;
	}
	/**
	 * 商家系统状态统计
	 * @param WifiRouter 
	 * @param sqlSession
	 * @param days 			统计商家一年内
	 * @return
	 */
	private Map<String,Object> businessSystemStation(List<Map<String,Object>> WifiRouterList,SqlSession sqlSession,int days,String WifiRouter,String timeperiods){
		//每个路由器下关注的总数量
		List<String> key = new ArrayList<String>();
		List<Integer> value = new ArrayList<Integer>();
		//接入总人数
		int TotalCount = 0;
		//商家在线总人数
		int OnlineCount = 0;
		//微信认证总人数
		int WeiXinCount = 0;
		//短信认证总人数
		int SmsCount = 0;
		//试用认证总人数
		int TryCount = 0;
		//累计微信认证总人数
		int SumWeiXinCount = 0;
		//累计短信认证总人数
		int SumSmsCount = 0;
		//累计试用认证总人数
		int SumTryCount = 0;
		WifiRouter = WifiRouterList.get(0).get("WifiRouterID").toString();
		Iterator<Map<String, Object>> WifiRouterIterator = WifiRouterList.iterator();
		while(WifiRouterIterator.hasNext()){
			Map<String,Object> WifiRouterMap = WifiRouterIterator.next();
			TotalCount = TotalCount + (WifiRouterMap.get("TotalCount")!=null?(Integer)WifiRouterMap.get("TotalCount"):0);
			WeiXinCount = WeiXinCount + (WifiRouterMap.get("WeiXinCount")!=null?(Integer)WifiRouterMap.get("WeiXinCount"):0);
			SmsCount = SmsCount + (WifiRouterMap.get("SmsCount")!=null?(Integer)WifiRouterMap.get("SmsCount"):0);
			TryCount = TryCount + (WifiRouterMap.get("TryCount")!=null?(Integer)WifiRouterMap.get("TryCount"):0);
			
			OnlineCount = OnlineCount + (Integer)sqlSession.selectOne("RouterBatis.OnlineTerminalMapper.SelectCountByWifiRouterID",WifiRouterMap.get("HardSeq"));
			int onWifiRouterCount = (Integer)sqlSession.selectOne("RouterBatis.WifiTerminalMapper.SelectWifiRouterIDCount", WifiRouterMap.get("WifiRouterID"));
			key.add((String)WifiRouterMap.get("DevName"));
			value.add(onWifiRouterCount);
			
			/*if(tempSum!=null){
				SumWeiXinCount = SumWeiXinCount +(tempSum.get("WeiXinCount")!=null?(Integer)tempSum.get("WeiXinCount"):0);
				SumSmsCount = SumSmsCount + (tempSum.get("SmsCount")!=null?(Integer)tempSum.get("SmsCount"):0);
				SumTryCount = SumTryCount + (tempSum.get("TryCount")!=null?(Integer)tempSum.get("TryCount"):0);
			}*/
		}
		List<Map<String,Object>> RouterAuthRecordList = this.getRouterAuthRecordList(WifiRouter, "7", sqlSession);
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("key", key);
		resultMap.put("value", value);
		resultMap.put("TotalCount", TotalCount);
		
		resultMap.put("WeiXinCount", WeiXinCount);
		resultMap.put("SmsCount", SmsCount);
		resultMap.put("TryCount", TryCount);
		
		/*resultMap.put("SumWeiXinCount", SumWeiXinCount);
		resultMap.put("SumSmsCount", SumSmsCount);
		resultMap.put("SumTryCount", SumTryCount);*/
		resultMap.put("RouterAuthRecordList", JSONArray.fromObject(RouterAuthRecordList));
		
		resultMap.put("OnlineCount", OnlineCount);
		return resultMap;
	}
	private List<Map<String,Object>> getRouterAuthRecordList(String WifiRouter,String DayCount,SqlSession sqlSession){
		Map<String,Object> EachRouterAndPeriods = new HashMap<String,Object>();
		EachRouterAndPeriods.put("WifiRouterID", WifiRouter);
		EachRouterAndPeriods.put("DayCount", DayCount);
		List<Map<String,Object>> RouterAuthRecordList = sqlSession.selectList("RouterBatis.RouterAuthRecordMapper.SelectAuthRecord",EachRouterAndPeriods);
		return RouterAuthRecordList;
	}
	private  Map<String,Object> accountRouter(String Operator,SqlSession sqlSession){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Map<String,Object> Agent = sqlSession.selectOne("RouterBatis.AgentMapper.SelectAgentID", Operator);
			//该代理下商家总数
			int BusinessShopCount = (Integer)sqlSession.selectOne("RouterBatis.BusinesShopMapper.SelectBusinessShopCountByAgentID", Agent.get("AgentID"));
			
			//代理下每个商家总数量，在线路由器数量的List集合
			List<Map<String,Object>> BusinessShopList= sqlSession.selectList("RouterBatis.BusinesShopMapper.SelectAgentID", Agent.get("AgentID"));
			ListIterator<Map<String, Object>> BusinessShopListIterator = BusinessShopList.listIterator();
			List<Map<String,Object>> EachBusinesShopList = new ArrayList<Map<String,Object>>();
			while(BusinessShopListIterator.hasNext()){
				Map<String,Object> BusinessShop = BusinessShopListIterator.next();
				List<Map<String,Object>> WifiRouterList = sqlSession.selectList("RouterBatis.WifiRouterMapper.SelectBussessShopID", BusinessShop.get("BusinesShopID"));
				Map<String,Object> MapTemp = new HashMap<String,Object>();
				int OnlineCount = 0;
				int EachBusinessShopWifiRouterCount = 0;
				ListIterator<Map<String, Object>> WifiRouterListIterator = WifiRouterList.listIterator();
				while(WifiRouterListIterator.hasNext()){
					Map<String,Object> WifiRouter = WifiRouterListIterator.next();
					Map<String,Object> isRouter = sqlSession.selectOne("RouterBatis.AuthenRouterMapper.SelectRorter", WifiRouter.get("HardSeq"));
					if(isRouter!=null){
						OnlineCount ++;
					}
					EachBusinessShopWifiRouterCount++;
				}
				MapTemp.put("OnlineCount", OnlineCount);
				MapTemp.put("EachBusinessShopWifiRouterCount", EachBusinessShopWifiRouterCount);
				MapTemp.put("BusinesName", BusinessShop.get("BusinesName"));
				MapTemp.put("BusinesShopID", BusinessShop.get("BusinesShopID"));
				EachBusinesShopList.add(MapTemp);
			}
			
			//代理商下路由器集合
			List<Map<String,Object>> RouterList = sqlSession.selectList("RouterBatis.WifiRouterMapper.SelectWifiRouterByAgentID",Agent.get("AgentID"));
			ListIterator<Map<String, Object>> RouterListIterator = RouterList.listIterator();
			//在线路由器集合
			List<Map<String,Object>> onlineRouterList = new ArrayList<Map<String,Object>>();
			//该代理下在线路由器
			while(RouterListIterator.hasNext()){
				Map<String,Object> Router = RouterListIterator.next();
				Map<String,Object> isRouter = sqlSession.selectOne("RouterBatis.AuthenRouterMapper.SelectRorter", Router.get("HardSeq"));
				if(isRouter!=null){
					onlineRouterList.add(isRouter);
				}
			}
			//代理商下路由器总数
			int RouterCount = RouterList.size();
			//代理商下在线路由器总数
			int OnlineRouterCount = onlineRouterList.size();
			
			map.put("AgentNum", "代理商ID为【" + Agent.get("AgentID") + "】");
			map.put("BusinessShopCount", BusinessShopCount);
			map.put("EachBusinesShopList", EachBusinesShopList);
			map.put("RouterCount", RouterCount);
			map.put("OnlineRouterCount", OnlineRouterCount);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return map;
	}
	/**
	 * 代理获得各个商家下路由器状态
	 * @param businessShopID
	 * @param sqlSession
	 * @return
	 */
	private String getEachBussinessRouterDetail(String businessShopID,SqlSession sqlSession){
		StringBuilder html = new StringBuilder();
		try {
			List<Map<String,Object>> CurrentWifiRouterList = sqlSession.selectList("RouterBatis.WifiRouterMapper.SelectBussessShopID", businessShopID);
			List<Map<String,Object>> WifiRouterDetailList = new ArrayList<Map<String,Object>>();
			ListIterator<Map<String, Object>> CurrentWifiRouterListIterator = CurrentWifiRouterList.listIterator();
			while(CurrentWifiRouterListIterator.hasNext()){
				Map<String,Object> WifiRouter = CurrentWifiRouterListIterator.next();
				Map<String,Object> isOnlineRouter = sqlSession.selectOne("RouterBatis.AuthenRouterMapper.SelectRorter", WifiRouter.get("HardSeq"));
				Map<String,Object> maptemp = new HashMap<String,Object>();
				maptemp.put("WifiRouterDevName", WifiRouter.get("DevName"));
				if(isOnlineRouter!=null){
					maptemp.put("isOnline", true);
				}else{
					maptemp.put("isOnline", false);
				}
				WifiRouterDetailList.add(maptemp);
			}
			for(Map<String,Object> map:WifiRouterDetailList){
				html.append(map.get("WifiRouterDevName")).append(":").append((Boolean) map.get("isOnline")?"<span class='label label-success'>在线</span>":"<span class='label label-important'>不在线</span>").append("</br>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return html.toString();
	}
	/**
	 * 获得商家下路由器状态
	 * @param businessShopID
	 * @param sqlSession
	 * @return
	 */
	private List<Map<String,Object>> getBussinessRouterDetail(String businessShopID,SqlSession sqlSession){
		List<Map<String,Object>> WifiRouterDetailList = null;
		try {
			List<Map<String,Object>> CurrentWifiRouterList = sqlSession.selectList("RouterBatis.WifiRouterMapper.SelectBussessShopID", businessShopID);
			WifiRouterDetailList = new ArrayList<Map<String,Object>>();
			ListIterator<Map<String, Object>> CurrentWifiRouterListIterator = CurrentWifiRouterList.listIterator();
			while(CurrentWifiRouterListIterator.hasNext()){
				Map<String,Object> WifiRouter = CurrentWifiRouterListIterator.next();
				Map<String,Object> isOnlineRouter = sqlSession.selectOne("RouterBatis.AuthenRouterMapper.SelectRorter", WifiRouter.get("HardSeq"));
				Map<String,Object> maptemp = new HashMap<String,Object>();
				maptemp.put("WifiRouterDevName", WifiRouter.get("DevName"));
				if(isOnlineRouter!=null){
					maptemp.put("isOnline", true);
				}else{
					maptemp.put("isOnline", false);
				}
				WifiRouterDetailList.add(maptemp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return WifiRouterDetailList;
	}
}
