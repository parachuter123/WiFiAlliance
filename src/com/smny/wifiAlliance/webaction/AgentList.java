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

/**
 * PACKAGE_NAME： webaction
 * <p/>
 * <商家列表>
 * <p/>
 * 作者：  Ronan
 * 创建时间： 14-2-28.
 */
@WebServlet(name = "AgentList", urlPatterns = "/AgentList.htm")
public class AgentList extends HttpServlet {
    //商家设置 修改
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
		try{
			switch(UserType.intValue()){
				case 1:
					if("createAgent".equals(Type)){
						String AgentName = request.getParameter("AgentName");
						String LoginName = request.getParameter("LoginName");
						String WifiShare = request.getParameter("WifiShare");
						String SeniorAgent = request.getParameter("SeniorAgent");
						if (StringToolkit.isNullOrEmpty(AgentName, LoginName)) {
			                response.getWriter().print("创建参数不完整！");
			                return;
			            }
						Map<String,Object> Agent = sqlSession.selectOne("RouterBatis.AgentMapper.SelectLoginName", LoginName);
			            if(Agent!=null){
			            	request.setAttribute("Operat","2");
							request.setAttribute("ErrorMessage","已经存在该代理，请更换登陆名称！！！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
			            }
			            Map<String,Object> AgentMap = new HashMap<String,Object>();
			            AgentMap.put("AgentName", AgentName);
			            AgentMap.put("LoginName", LoginName);
			            AgentMap.put("PassWord", LoginName);
			            AgentMap.put("SeniorAgent", SeniorAgent.equals("true")?1:0);
			            if((SeniorAgent.equals("true")?1:0)==1){
			            	AgentMap.put("SuperAgentID", 0);
			            }else{
			            	AgentMap.put("SuperAgentID", 1);
			            }
			            AgentMap.put("WifiShare", WifiShare.equals("true")?1:0);
			            AgentMap.put("RouterAgentID", 0);
			            try {
							sqlSession.insert("RouterBatis.AgentMapper.insertAgent",AgentMap);
							sqlSession.commit();
			            	request.setAttribute("Operat","1");
							request.setAttribute("ErrorMessage","代理创建成功！！!");
						} catch (Exception e) {
							request.setAttribute("Operat","-2");
							request.setAttribute("ErrorMessage",e.getMessage().replace("\r\n", ""));
							request.setAttribute("ShowBug",properties.getValue("ShowBug"));
							request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
						}
						request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
					}
					break;
				case 2:
					if("createAgent".equals(Type)){
						Map<String,Object> Agent = sqlSession.selectOne("RouterBatis.AgentMapper.SelectAgentID", Integer.parseInt(Operator));
						if(Agent == null){
							request.setAttribute("Operat","3");
							request.setAttribute("ErrorMessage","没有该代理");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
						}
						String AgentName = request.getParameter("AgentName");
						String LoginName = request.getParameter("LoginName");
						String WifiShare = request.getParameter("WifiShare");
						if (StringToolkit.isNullOrEmpty(AgentName, LoginName)) {
			                response.getWriter().print("创建参数不完整！");
			                return;
			            }
			            if(Agent.get("LoginName").equals(LoginName)){
			            	request.setAttribute("Operat","4");
							request.setAttribute("ErrorMessage","已经存在该代理，请更换登陆名称！！！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
			            }
			            Map<String,Object> AgentMap = new HashMap<String,Object>();
			            AgentMap.put("AgentName", AgentName);
			            AgentMap.put("LoginName", LoginName);
			            AgentMap.put("PassWord", LoginName);
			            AgentMap.put("SeniorAgent", 0);
			            AgentMap.put("SuperAgentID", Agent.get("AgentID"));
			            AgentMap.put("WifiShare", Boolean.getBoolean(WifiShare)?0:1);
			            AgentMap.put("RouterAgentID", 0);
			            try {
							sqlSession.insert("RouterBatis.AgentMapper.insertAgent",AgentMap);
							sqlSession.commit();
							request.setAttribute("Operat","1");
							request.setAttribute("ErrorMessage","代理创建成功！！");
						} catch (Exception e) {
							request.setAttribute("Operat","-4");
							request.setAttribute("ErrorMessage",e.getMessage().replace("\r\n", ""));
							request.setAttribute("ShowBug",properties.getValue("ShowBug"));
							request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
						} 
			            request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
			            return;
					}
					if("getAgentInfo".equals(Type)){
						String AgentID = request.getParameter("AgentID");
						Map<String,Object> Agent = sqlSession.selectOne("RouterBatis.AgentMapper.SelectAgentID", Integer.parseInt(AgentID));
						if(Agent == null){
							request.setAttribute("Operat","3");
							request.setAttribute("ErrorMessage","没有该代理");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
						}
						StringBuilder json = new StringBuilder("{");
				        json.append("\"AgentName\": \"").append(Agent.get("AgentName") == null ? "" : Agent.get("AgentName")).append("\",");
				        json.append("\"LoginName\": \"").append(Agent.get("LoginName") == null ? "" : Agent.get("LoginName")).append("\",");
				        json.append("\"WifiShare\": \"").append(Agent.get("WifiShare")).append("\"");
				        json.append("}");
				        response.setContentType("text/json; charset=utf-8");
				        response.getWriter().print(json.toString());
				        return;
					}
					if("changeAgentInfo".equals(Type)){
						String AgentID = request.getParameter("AgentID");
						Map<String,Object> Agent = sqlSession.selectOne("RouterBatis.AgentMapper.SelectAgentID", Integer.parseInt(AgentID));
						if(Agent == null){
							request.setAttribute("Operat","3");
							request.setAttribute("ErrorMessage","没有该代理");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
						}
						String AgentName = request.getParameter("AgentName");
				        String LoginName = request.getParameter("LoginName");
				        String WifiShare = request.getParameter("WifiShare");
				        boolean isChange = false;
				        if (!StringToolkit.isNullOrEmpty(AgentName)) {
				        	Agent.put("AgentName",AgentName);
				            isChange = true;
				        }
				        if (!StringToolkit.isNullOrEmpty(LoginName)) {
				        	Agent.put("LoginName",LoginName);
				            isChange = true;
				        }
				        if (!StringToolkit.isNullOrEmpty(WifiShare)) {
				        	Agent.put("WifiShare",new Boolean(WifiShare));
				            isChange = true;
				        }
						if(isChange){
							try {
								sqlSession.update("RouterBatis.AgentMapper.updateAgent",Agent);
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

    //商家列表
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html; charset=UTF-8");
    	Map<String,Object> ServletRefactorMethodMap = ServletRefactorMethod.start(request, response);
    	if(ServletRefactorMethodMap==null){
    		return;
    	}
        Integer UserType = (Integer)ServletRefactorMethodMap.get("UserType");
        String Operator = (String)ServletRefactorMethodMap.get("Operator");
        SetSystemProperty properties = (SetSystemProperty) ServletRefactorMethodMap.get("ssp");
		SqlSession sqlSession = MyBatisFactory.openSession();
		try{
			switch(UserType.intValue()){
				case 1:
					List<Map<String,Object>> AgentList1= sqlSession.selectList("RouterBatis.AgentMapper.SelectAgentAll");
					if(AgentList1 == null){
						request.setAttribute("Operat","3");
						request.setAttribute("ErrorMessage","没有该代理");
						request.getRequestDispatcher("/AuthHtmlError.jsp").forward(request, response);
						return;
					}
					request.setAttribute("list", AgentList1);
					request.getRequestDispatcher("AgentList.jsp").forward(request, response);
					break;
				case 2:
					List<Map<String,Object>> AgentList2= sqlSession.selectList("RouterBatis.AgentMapper.SelectAgentSuperID", Integer.parseInt(Operator));
					if(AgentList2 == null){
						request.setAttribute("Operat","3");
						request.setAttribute("ErrorMessage","没有该代理");
						request.getRequestDispatcher("/AuthHtmlError.jsp").forward(request, response);
						return;
					}
					request.setAttribute("list", AgentList2);
					request.getRequestDispatcher("AgentList.jsp").forward(request, response);
					break;
				default:
					request.setAttribute("Operat","-8");
					request.setAttribute("ErrorMessage","角色选择错误:"+UserType);
					request.setAttribute("ShowBug",properties.getValue("ShowBug"));
					request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
					request.getRequestDispatcher("/AuthHtmlError.jsp").forward(request, response);
					break;
			}
		}catch(Exception e){
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
