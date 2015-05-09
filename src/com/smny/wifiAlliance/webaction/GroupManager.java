package com.smny.wifiAlliance.webaction;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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

import com.smny.wifiAlliance.entity.logic.FollowersGroupLogic;

/**
 * PACKAGE_NAME： webaction
 * <p/>
 * <分组设置>
 * <p/>
 * 作者：  SatanWang
 * 创建时间： 14-2-12.
 */
@WebServlet(name = "GroupManager", urlPatterns = "/GroupManager.htm")
public class GroupManager extends HttpServlet {


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
				case 4:
					Map<String,Object> bs = sqlSession.selectOne("RouterBatis.BusinesShopMapper.SelectBusinesShopID",Operator);
					if (bs == null) {
						request.setAttribute("Operat","2");
						request.setAttribute("ErrorMessage","当前商家不存在或已删除");
						request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
						return;
					}
					String Type = request.getParameter("Type");
					if (StringToolkit.isNullOrEmpty(Type)) {
						request.setAttribute("Operat","3");
						request.setAttribute("ErrorMessage","分组操作类型不能为空！！");
						request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
						return;
					}
					if("CreateGroup".equals(Type)){
						String GroupName = request.getParameter("GroupName");
						if (StringToolkit.isNullOrEmpty(GroupName)) {
							request.setAttribute("Operat","4");
							request.setAttribute("ErrorMessage","分组名称不能为空！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
		                }
		                String UplinkSpeed = request.getParameter("UplinkSpeed");
		                if (StringToolkit.isNullOrEmpty(UplinkSpeed)) {
							request.setAttribute("Operat","5");
							request.setAttribute("ErrorMessage","上行网速不能为空！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
		                }
		                String DownSpeed = request.getParameter("DownSpeed");
		                if (StringToolkit.isNullOrEmpty(DownSpeed)) {
							request.setAttribute("Operat","6");
							request.setAttribute("ErrorMessage","下行网速不能为空！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
		                }
		                String UplinkFlow = request.getParameter("UplinkFlow");
		                if (StringToolkit.isNullOrEmpty(UplinkFlow)) {
							request.setAttribute("Operat","7");
							request.setAttribute("ErrorMessage","上行流量不能为空！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
		                }
		                String DownFlow = request.getParameter("DownFlow");
		                if (StringToolkit.isNullOrEmpty(DownFlow)) {
							request.setAttribute("Operat","8");
							request.setAttribute("ErrorMessage","下行流量不能为空！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
		                }
		                String Notice = request.getParameter("Notice");
		                String ReasonURL = request.getParameter("ReasonURL");
		                if (StringToolkit.isNullOrEmpty(ReasonURL)) {
							request.setAttribute("Operat","9");
							request.setAttribute("ErrorMessage","通知广告不能为空！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
		                }
		                Map<String,Object> GroupManager = new HashMap<String,Object>();
		                GroupManager.put("BusinessShopID", bs.get("BusinesShopID"));
		                GroupManager.put("GroupName", GroupName);
		                GroupManager.put("UplinkSpeed", UplinkSpeed);
		                GroupManager.put("DownSpeed", DownSpeed);
		                GroupManager.put("UplinkFlow", UplinkFlow);
		                GroupManager.put("DownFlow", DownFlow);
		                GroupManager.put("Notice", Boolean.getBoolean(Notice)?1:0);
		                GroupManager.put("ReasonURL", ReasonURL);
		                try {
		                	sqlSession.insert("RouterBatis.FollowersGroupMapper.insertGroup", GroupManager);
		                	sqlSession.commit();
		                	request.setAttribute("Operat","1");
							request.setAttribute("ErrorMessage","分组创建成功！！");
						} catch (Exception e) {
							request.setAttribute("Operat","-4");
							request.setAttribute("ErrorMessage",e.getMessage().replace("\r\n", ""));
							request.setAttribute("ShowBug",properties.getValue("ShowBug"));
							request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
						}
		                request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
						return;
					}
					if ("UpdateGroupInfo".equals(Type)) {
						
						String GroupID = request.getParameter("GroupID");
						if (StringToolkit.isNullOrEmpty(GroupID)) {
							request.setAttribute("Operat","10");
							request.setAttribute("ErrorMessage","修改错误:指定GroupID不能为空！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
		                }
						Map<String,Object> FollowersGroup = FollowersGroupLogic.getGroup(sqlSession, GroupID);
						if (FollowersGroup == null) {
		                	request.setAttribute("Operat","11");
							request.setAttribute("ErrorMessage","修改错误,指定分组不存在！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
		                }
						String GroupName = request.getParameter("GroupName");
						String UplinkSpeed = request.getParameter("UplinkSpeed");
						String DownSpeed = request.getParameter("DownSpeed");
						String UplinkFlow = request.getParameter("UplinkFlow");
						String DownFlow = request.getParameter("DownFlow");
						String Notice = request.getParameter("Notice");
						String ReasonURL = request.getParameter("ReasonURL");
						boolean isChange = false;
						if (!StringToolkit.isNullOrEmpty(GroupName) && !GroupName.equals(FollowersGroup.get("GroupName"))) {
							FollowersGroup.put("GroupName",GroupName);
		                    isChange = true;
		                }
						if (!StringToolkit.isNullOrEmpty(UplinkSpeed) && !(Integer.parseInt(UplinkSpeed)==(Integer)FollowersGroup.get("UplinkSpeed"))) {
							FollowersGroup.put("UplinkSpeed",Integer.parseInt(UplinkSpeed));
		                    isChange = true;
		                }
						if (!StringToolkit.isNullOrEmpty(DownSpeed) && !(Integer.parseInt(DownSpeed)==(Integer)FollowersGroup.get("DownSpeed"))) {
							FollowersGroup.put("DownSpeed",Integer.parseInt(DownSpeed));
		                    isChange = true;
		                }
						if (!StringToolkit.isNullOrEmpty(UplinkFlow) && !(Integer.parseInt(UplinkFlow)==(Integer)FollowersGroup.get("UplinkFlow"))) {
							FollowersGroup.put("UplinkFlow",Integer.parseInt(UplinkFlow));
		                    isChange = true;
		                }
						if (!StringToolkit.isNullOrEmpty(DownFlow) && !(Integer.parseInt(DownFlow)==(Integer)FollowersGroup.get("DownFlow"))) {
							FollowersGroup.put("DownFlow",Integer.parseInt(DownFlow));
		                    isChange = true;
		                }
						if (!StringToolkit.isNullOrEmpty(Notice) && !((Boolean.valueOf(Notice)?1:0) == (Boolean.parseBoolean(FollowersGroup.get("Notice").toString())?1:0))) {
							FollowersGroup.put("Notice",Boolean.valueOf(Notice));
		                    isChange = true;
		                }
						if (!StringToolkit.isNullOrEmpty(ReasonURL) && !ReasonURL.equals(FollowersGroup.get("ReasonURL"))) {
							FollowersGroup.put("ReasonURL",ReasonURL);
		                    isChange = true;
		                }
						FollowersGroup.put("BusinessShopID",bs.get("BusinesShopID"));
						
						if (isChange) {
	                    	try {
								sqlSession.update("RouterBatis.FollowersGroupMapper.updateGroup",FollowersGroup);
								sqlSession.commit();
								request.setAttribute("Operat","1");
								request.setAttribute("ErrorMessage","分组修改成功");
							} catch (Exception e) {
								request.setAttribute("Operat","-4");
								request.setAttribute("ErrorMessage",e.getMessage().replace("\r\n", ""));
								request.setAttribute("ShowBug",properties.getValue("ShowBug"));
								request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
							}
		                }else{
		                	request.setAttribute("Operat","12");
							request.setAttribute("ErrorMessage","数据无变更!");
		                }
						request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
		                return;
					}
					if ("GetGroupInfo".equals(Type)) {
						String GroupID = request.getParameter("GroupID");
						if (StringToolkit.isNullOrEmpty(GroupID)) {
							request.setAttribute("Operat","13");
							request.setAttribute("ErrorMessage","修改错误:指定GroupID不能为空！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
		                }
						Map<String,Object> FollowersGroup = FollowersGroupLogic.getGroup(sqlSession, GroupID);
						if (FollowersGroup == null) {
		                	request.setAttribute("Operat","14");
							request.setAttribute("ErrorMessage","修改错误,指定分组不存在！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
		                }
						StringBuilder json = new StringBuilder("{");
						json.append("\"State\":1,\"Message\":\"成功\",");
		                json.append("\"GroupName\": \"").append(FollowersGroup.get("GroupName")).append("\",");
		                json.append("\"UplinkSpeed\": \"").append(FollowersGroup.get("UplinkSpeed")).append("\",");
		                json.append("\"DownSpeed\": \"").append(FollowersGroup.get("DownSpeed")).append("\",");
		                json.append("\"UplinkFlow\": \"").append(FollowersGroup.get("UplinkFlow")).append("\",");
		                json.append("\"DownFlow\": \"").append(FollowersGroup.get("DownFlow")).append("\",");
		                json.append("\"Notice\": \"").append(FollowersGroup.get("Notice")).append("\",");
		                json.append("\"ReasonURL\": \"").append(FollowersGroup.get("ReasonURL") == null ? "" : FollowersGroup.get("ReasonURL")).append("\"");
		                json.append("}");
		                response.setContentType("application/json; charset=UTF-8");
		                response.getWriter().print(json.toString());
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
				//商户
				case 4:
					Map<String,Object> BusinessShop = sqlSession.selectOne("RouterBatis.BusinesShopMapper.SelectBusinesShopID",Operator);
					if (BusinessShop == null) {
						request.setAttribute("Operat","9");
						request.setAttribute("ErrorMessage","当前商家不存在或已删除");
						request.getRequestDispatcher("/AuthHtmlError.jsp").forward(request, response);
						return;
					}
					List<Map<String,Object>> Grouplist = FollowersGroupLogic.getFollowersGroup(sqlSession,Operator);
		            request.setAttribute("Grouplist", Grouplist);
		            
		            Map<String,Object> InfoArgu = new HashMap<String,Object>();
		            InfoArgu.put("BusinesShopID", BusinessShop.get("BusinesShopID"));
					InfoArgu.put("Type", "4");
					List<Map<String,Object>> InfoTemplateList = sqlSession.selectList("RouterBatis.InfoTemplateMapper.ByBusinesShopIDAndTypeGetTemplate", InfoArgu);
					
					request.setAttribute("InfoTemplateList", InfoTemplateList);
					request.getRequestDispatcher("/GroupManager.jsp").forward(request, response);
					break;
				default:
					request.setAttribute("Operat","-8");
					request.setAttribute("ErrorMessage","角色选择错误:"+UserType);
					request.setAttribute("ShowBug",properties.getValue("ShowBug"));
					request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
					request.getRequestDispatcher("/AuthHtmlError.jsp").forward(request, response);
					return;
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
