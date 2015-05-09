package com.smny.wifiAlliance.webaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
					String businessShopID2 = request.getParameter("businessShop");
					String html2 = this.getBussinessRouterDetail(businessShopID2,sqlSession);
	                response.setContentType("text/html; charset=utf-8");
			        response.getWriter().print(html2);
					break;
				case 3:
					String businessShopID3 = request.getParameter("businessShop");
					String html3 = this.getBussinessRouterDetail(businessShopID3,sqlSession);
	                response.setContentType("text/html; charset=utf-8");
			        response.getWriter().print(html3);
					break;
				case 4:
					Map<String,Object> BusinessShop = sqlSession.selectOne("RouterBatis.BusinesShopMapper.SelectBusinesShopID",Operator);
					if (BusinessShop == null) {
						request.setAttribute("Operat","3");
						request.setAttribute("ErrorMessage","无此商家！");
						request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
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
					timeMap.put("timelength", timelength);
					int TodayPhoneWatch = (Integer)sqlSession.selectOne("RouterBatis.WifiTerminalMapper.SelectTodayPhoneWatch", timeMap);
					
					StringBuilder json = new StringBuilder("{");
			        json.append("\"TodayPhoneWatch\": \"").append(TodayPhoneWatch).append("\"");
			        json.append("}");
	                response.setContentType("text/json; charset=utf-8");
			        response.getWriter().print(json.toString());
					
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
					Map<String,Object> map = this.accountRouter(Operator, sqlSession);
					request.setAttribute("AgentNum", map.get("AgentNum"));
					request.setAttribute("BusinessShopCount", map.get("BusinessShopCount"));
					request.setAttribute("EachBusinesShopList", map.get("EachBusinesShopList"));
					request.setAttribute("RouterCount", map.get("RouterCount"));
					request.setAttribute("OnlineRouterCount", map.get("OnlineRouterCount"));
					request.getRequestDispatcher("index.jsp").forward(request, response);
					break;
				//代理					
				case 3:
					Map<String,Object> map3 = this.accountRouter(Operator, sqlSession);
					request.setAttribute("AgentNum", map3.get("AgentNum"));
					request.setAttribute("BusinessShopCount", map3.get("BusinessShopCount"));
					request.setAttribute("EachBusinesShopList", map3.get("EachBusinesShopList"));
					request.setAttribute("RouterCount", map3.get("RouterCount"));
					request.setAttribute("OnlineRouterCount", map3.get("OnlineRouterCount"));
					request.getRequestDispatcher("index.jsp").forward(request, response);
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
					
					//每个路由器下关注的总数量
					List<String> key = new ArrayList<String>();
					List<Integer> value = new ArrayList<Integer>();
					for(int i=0;i<WifiRouter.size();i++){
						int onWifiRouterCount = (Integer)sqlSession.selectOne("RouterBatis.WifiTerminalMapper.SelectWifiRouterIDCount", WifiRouter.get(i).get("WifiRouterID"));
						key.add((String)WifiRouter.get(i).get("DevName"));
						value.add(onWifiRouterCount);
					}
		            
					request.setAttribute("Routerlist", WifiRouter);
		            request.setAttribute("Applist", AppEntrance);
		            request.setAttribute("PhoneWatchTotal", PhoneWatchTotal);
		            request.setAttribute("TodayPhoneWatch", TodayPhoneWatch);
		            request.setAttribute("key", key);
		            request.setAttribute("value", value);
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
	private  Map<String,Object> accountRouter(String Operator,SqlSession sqlSession){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Map<String,Object> Agent = sqlSession.selectOne("RouterBatis.AgentMapper.SelectAgentID", Operator);
			//该代理下商家总数
			int BusinessShopCount = (Integer)sqlSession.selectOne("RouterBatis.BusinesShopMapper.SelectBusinessShopCountByAgentID", Agent.get("AgentID"));
			
			/**
			 * 代理下每个商家总数量，在线路由器数量的List集合
			 */
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
	private String getBussinessRouterDetail(String businessShopID,SqlSession sqlSession){
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
	/*private Map<String,Object> getAgentRouterDetail(String SuperAgentID,SqlSession sqlSession){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			//根据高级代理查出所有普通代理
			List<Map<String,Object>> normalAgentList = sqlSession.selectList("", SuperAgentID);
			ListIterator<Map<String, Object>> normalAgentListIterator = normalAgentList.listIterator();
			List<Map<String,Object>> EachAgentList = new ArrayList<Map<String,Object>>();
			while(normalAgentListIterator.hasNext()){
				Map<String, Object> agentMap = normalAgentListIterator.next();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return null;
	}*/
}
