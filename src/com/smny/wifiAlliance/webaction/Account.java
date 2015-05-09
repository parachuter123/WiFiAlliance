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
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;

import smny.MyBatisFactory;
import smny.util.ServletRefactorMethod;
import smny.util.SetSystemProperty;
import smny.util.StringToolkit;

/**
 * PACKAGE_NAME： webaction
 * <p/>
 * <广告列表>
 * <p/>
 * 作者：  parachuter
 * 创建时间： 14-12-08.
 */
@WebServlet(name = "Account", urlPatterns = "/Account.htm")
public class Account extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//消息接口设置 修改
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html; charset=UTF-8");
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
				case 2:
					Map<String,Object> SeniorAgent = sqlSession.selectOne("RouterBatis.AgentMapper.SelectAgentID", Operator);
					String SeniorAgentName = request.getParameter("AgentName");
					String password21 = request.getParameter("password1");
		            String password22 = request.getParameter("password2");
		            String password23 = request.getParameter("password3");
		            String LogoUrl = request.getParameter("LogoUrl");
		            String SeniorAgentTitle = request.getParameter("SeniorAgentTitle");
		            String SeniorAgentInstruction = request.getParameter("SeniorAgentInstruction");
		            String SeniorAgentFooter = request.getParameter("SeniorAgentFooter");
		            boolean isChange2 = false;
		            if (!StringToolkit.isNullOrEmpty(SeniorAgentName) && !SeniorAgentName.equals(SeniorAgent.get("AgentName"))) {
		            	SeniorAgent.put("AgentName",SeniorAgentName);
		            	isChange2 = true;
		            }
		            if (!StringToolkit.isNullOrEmpty(password21, password22,password23)) {
		                isChange2 = true;
		                if(!password22.equals(password23)){
							response.getWriter().println("<script type='text/javascript'>alert('新设密码不一致，请重新输入！！！');window.location='/Account.htm';</script>");
		                    return;
		                }
		                if (!password21.equals(SeniorAgent.get("PassWord"))) {
		                	response.getWriter().println("<script type='text/javascript'>alert('原密码错误，修改失败');window.location='/Account.htm';</script>");
		                    return;
		                }
		                SeniorAgent.put("PassWord",password22);
		            }
		            if (!LogoUrl.equals(SeniorAgent.get("LogoUrl"))) {
		            	SeniorAgent.put("LogoUrl",LogoUrl);
		            	isChange2 = true;
		            }
		            if (!SeniorAgentTitle.equals(SeniorAgent.get("SeniorAgentTitle"))) {
		            	SeniorAgent.put("SeniorAgentTitle",SeniorAgentTitle);
		            	isChange2 = true;
		            }
		            if (!SeniorAgentInstruction.equals(SeniorAgent.get("SeniorAgentInstruction"))) {
		            	SeniorAgent.put("SeniorAgentInstruction",SeniorAgentInstruction.trim());
		            	isChange2 = true;
		            }
		            if (!SeniorAgentFooter.equals(SeniorAgent.get("SeniorAgentFooter"))) {
		            	SeniorAgent.put("SeniorAgentFooter",SeniorAgentFooter);
		            	isChange2 = true;
		            }
		            if(isChange2){
		            	try {
							sqlSession.update("RouterBatis.AgentMapper.updateAgent", SeniorAgent);
							sqlSession.commit();
							
							HttpSession session = request.getSession();
							String LogoURL = properties.getValue("LogoURL");
							String SeniorAgentTitleDefault = properties.getValue("SeniorAgentTitleDefault");
					        String SeniorAgentInstructionDefault = properties.getValue("SeniorAgentInstructionDefault");
					        String SeniorAgentFooterDefault = properties.getValue("SeniorAgentFooterDefault");
					        
					        //判断是否为空，如果为空，设为默认值
	                        if(StringToolkit.isNullOrEmpty((String)SeniorAgent.get("SeniorAgentTitle"))){
	                        	session.setAttribute("SeniorAgentTitle", SeniorAgentTitleDefault);
	                        }else{
	                        	session.setAttribute("SeniorAgentTitle", SeniorAgent.get("SeniorAgentTitle"));
	                        }if(StringToolkit.isNullOrEmpty((String)SeniorAgent.get("SeniorAgentInstruction"))){
	                        	session.setAttribute("SeniorAgentInstruction", SeniorAgentInstructionDefault);
	                        }else{
	                        	session.setAttribute("SeniorAgentInstruction", SeniorAgent.get("SeniorAgentInstruction"));
	                        }if(StringToolkit.isNullOrEmpty((String)SeniorAgent.get("SeniorAgentFooter"))){
	                        	session.setAttribute("SeniorAgentFooter", SeniorAgentFooterDefault);
	                        }else{
	                        	session.setAttribute("SeniorAgentFooter", SeniorAgent.get("SeniorAgentFooter"));
	                        }if(StringToolkit.isNullOrEmpty((String)SeniorAgent.get("LogoUrl"))){
	                        	session.setAttribute("LogoURL", LogoURL);
	                        }else{
	                        	session.setAttribute("LogoURL", SeniorAgent.get("LogoUrl"));
	                        }
							response.getWriter().println("<script type='text/javascript'>alert('修改成功！');window.location='/Account.htm';</script>");
						} catch (Exception e) {							
							if(properties.getValue("ShowBug") .equals( "true")){
								e.printStackTrace();
								response.getWriter().println(e.getMessage());
							}else{
								response.getWriter().println("<script type='text/javascript'>alert('" +  properties.getValue("SysErrorPointOut") +"');window.location='/Account.htm';</script>");
							}
						}
		            }else{
						response.getWriter().println("<script type='text/javascript'>alert('数据无变更！');window.location='/Account.htm';</script>");
		            }
					break;
				case 3:
					Map<String,Object> Agent = sqlSession.selectOne("RouterBatis.AgentMapper.SelectAgentID", Operator);
					String AgentName = request.getParameter("AgentName");
					String JumpAddr = request.getParameter("JumpAddr");
					String password31 = request.getParameter("password1");
		            String password32 = request.getParameter("password2");
		            String password33 = request.getParameter("password3");
		            boolean isChange3 = false;
		            if (!StringToolkit.isNullOrEmpty(AgentName) && !AgentName.equals(Agent.get("AgentName"))) {
		            	Agent.put("AgentName",AgentName);
		            	isChange3 = true;
		            }
		            if (!JumpAddr.equals(Agent.get("JumpAddr"))) {
		            	Agent.put("JumpAddr",JumpAddr);
		            	isChange3 = true;
		            }
		            if (!StringToolkit.isNullOrEmpty(password31, password32,password33)) {
		                isChange3 = true;
		                if(!password32.equals(password33)){
		                	request.setAttribute("Operat","history.back()");
							request.setAttribute("ErrorMessage","新设密码不一致，请重新输入！！！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
		                    return;
		                }
		                if (!password31.equals(Agent.get("PassWord"))) {
		                	request.setAttribute("Operat","-2");
							request.setAttribute("ErrorMessage","原密码错误，修改失败");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
		                    return;
		                }
		                Agent.put("PassWord",password32);
		            }
		            if(isChange3){
		            	try {
							sqlSession.update("RouterBatis.AgentMapper.updateAgent", Agent);
							sqlSession.commit();
							request.setAttribute("Operat","1");
							request.setAttribute("ErrorMessage","修改成功");
							response.getWriter().println("<script type='text/javascript'>alert('修改成功！');window.location='/Account.htm';</script>");
						} catch (Exception e) {						
							if(properties.getValue("ShowBug") .equals( "true")){
								e.printStackTrace();
								response.getWriter().println(e.getMessage());
							}else{
								response.getWriter().println("<script type='text/javascript'>alert('" +  properties.getValue("SysErrorPointOut") +"');window.location='/Account.htm';</script>");
							}
						}
		            }else{
		            	request.setAttribute("Operat","0");
						request.setAttribute("ErrorMessage","数据无变更！！！");
						response.getWriter().println("<script type='text/javascript'>alert('数据无变更！');window.location='/Account.htm';</script>");
		            }
					break;
				case 4:
					Map<String,Object> BusinessShop = sqlSession.selectOne("RouterBatis.BusinesShopMapper.SelectBusinesShopID", Operator);
					String BusinessName = request.getParameter("BusinessName");
		            String BusinessAddress = request.getParameter("BusinessAddress");
		            String BusinessBoss = request.getParameter("BusinessBoss");
		            String BossMobile = request.getParameter("BossMobile");
		            String BusinessContact = request.getParameter("BusinessContact");
		            String ContactMobile = request.getParameter("ContactMobile");
		            String BusinessPhone = request.getParameter("BusinessPhone");
		            String password1 = request.getParameter("password1");
		            String password2 = request.getParameter("password2");
		            String password3 = request.getParameter("password3");
		            
		            String LogoURL = request.getParameter("LogoURL");
		            String WelcomePictureURL = request.getParameter("WelcomePictureURL");
		            String HomeAddres = request.getParameter("HomeAddres");
		            String AgentGuestGroupID = request.getParameter("AgentGuestGroupID");
		            String ShareGuestGroupID = request.getParameter("ShareGuestGroupID");
		            boolean isChange = false;
		            if (!StringToolkit.isNullOrEmpty(BusinessName) && !BusinessName.equals(BusinessShop.get("BusinesName"))) {
		            	BusinessShop.put("BusinesName",BusinessName);
		                isChange = true;
		            }
		            if (!StringToolkit.isNullOrEmpty(BusinessAddress) && !BusinessAddress.equals(BusinessShop.get("BusinesAddres"))) {
		            	BusinessShop.put("BusinesAddres",BusinessAddress);
		                isChange = true;
		            }
		            if (!StringToolkit.isNullOrEmpty(BusinessBoss) && !BusinessBoss.equals(BusinessShop.get("BusinesBoss"))) {
		            	BusinessShop.put("BusinesBoss",BusinessBoss);
		                isChange = true;
		            }
		            if (!StringToolkit.isNullOrEmpty(BossMobile) && !BossMobile.equals(BusinessShop.get("BossMobile"))) {
		            	BusinessShop.put("BossMobile",BossMobile);
		                isChange = true;
		            }
		            if (!StringToolkit.isNullOrEmpty(BusinessContact) && !BusinessContact.equals(BusinessShop.get("BusinesContact"))) {
		            	BusinessShop.put("BusinesContact",BusinessContact);
		                isChange = true;
		            }
		            if (!StringToolkit.isNullOrEmpty(ContactMobile) && !ContactMobile.equals(BusinessShop.get("ContactMobile"))) {
		            	BusinessShop.put("ContactMobile",ContactMobile);
		                isChange = true;
		            }
		            if (!StringToolkit.isNullOrEmpty(BusinessPhone) && !BusinessPhone.equals(BusinessShop.get("BusinesPhone"))) {
		            	BusinessShop.put("BusinesPhone",BusinessPhone);
		                isChange = true;
		            }

		            if (!StringToolkit.isNullOrEmpty(LogoURL) && !LogoURL.equals(BusinessShop.get("LogoURL"))) {
		            	BusinessShop.put("LogoURL",LogoURL);
		                isChange = true;
		            }
		            if (!StringToolkit.isNullOrEmpty(WelcomePictureURL) && !WelcomePictureURL.equals(BusinessShop.get("WelcomePictureURL"))) {
		            	BusinessShop.put("WelcomePictureURL",WelcomePictureURL);
		                isChange = true;
		            }
		            if (!StringToolkit.isNullOrEmpty(HomeAddres) && !HomeAddres.equals(BusinessShop.get("HomeAddres"))) {
		            	BusinessShop.put("HomeAddres",HomeAddres);
		                isChange = true;
		            }
		            if (!StringToolkit.isNullOrEmpty(password1, password2,password3)) {
		                isChange = true;
		                if(!password2.equals(password3)){
		                	request.setAttribute("Operat","history.back()");
							request.setAttribute("ErrorMessage","新设密码不一致，请重新输入！！！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
		                    return;
		                }
		                if (!password1.equals(BusinessShop.get("PassWord"))) {
		                	request.setAttribute("Operat","-2");
							request.setAttribute("ErrorMessage","原密码错误，修改失败");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
		                    return;
		                }
		                BusinessShop.put("PassWord",password2);
		            }
		            if (!StringToolkit.isNullOrEmpty(AgentGuestGroupID) && !AgentGuestGroupID.equals(BusinessShop.get("AgentGuestGroupID"))) {
		            	BusinessShop.put("AgentGuestGroupID",AgentGuestGroupID);
		                isChange = true;
		            }
		            if (!StringToolkit.isNullOrEmpty(ShareGuestGroupID) && !ShareGuestGroupID.equals(BusinessShop.get("ShareGuestGroupID"))) {
		            	BusinessShop.put("ShareGuestGroupID",ShareGuestGroupID);
		                isChange = true;
		            }
		            if(isChange){
		            	try {
							sqlSession.update("RouterBatis.BusinesShopMapper.updateBusinesShop", BusinessShop);
							sqlSession.commit();
							
							HttpSession session = request.getSession();
							session.setAttribute("UserType", 4);
		                    session.setAttribute("Operator", BusinessShop.get("BusinesShopID"));
		                    session.setAttribute("Name", BusinessShop.get("BusinesName"));
		                    session.setAttribute("Role", "商户");
		                    session.setAttribute("LogoURL", BusinessShop.get("LogoURL"));
		                    
							request.setAttribute("Operat","1");
							request.setAttribute("ErrorMessage","修改成功");
							response.getWriter().println("<script type='text/javascript'>alert('修改成功！');window.location='/Account.htm';</script>");
						} catch (Exception e) {						
							if(properties.getValue("ShowBug") .equals( "true")){
								e.printStackTrace();
								response.getWriter().println(e.getMessage());
							}else{
								response.getWriter().println("<script type='text/javascript'>alert('" +  properties.getValue("SysErrorPointOut") +"');window.location='/Account.htm';</script>");
							}
						}
		            }else{
		            	request.setAttribute("Operat","0");
						request.setAttribute("ErrorMessage","数据无变更！！！");
						response.getWriter().println("<script type='text/javascript'>alert('数据无变更！');window.location='/Account.htm';</script>");
		            }
					break;
				default:
					request.setAttribute("Operat","history.back()");
					request.setAttribute("ErrorMessage","角色选择错误:"+UserType);
					request.getRequestDispatcher("/AuthHtmlError.jsp").forward(request, response);
					break;
			}
		} catch (Exception e) {					
			if(properties.getValue("ShowBug") .equals( "true")){
				e.printStackTrace();
				response.getWriter().println(e.getMessage());
			}else{
				response.getWriter().println("<script type='text/javascript'>alert('" +  properties.getValue("SysErrorPointOut") +"');window.location='/Account.htm';</script>");
			}
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
				case 2:
					Map<String,Object> Agent2 = sqlSession.selectOne("RouterBatis.AgentMapper.SelectAgentID",Operator);
					if (Agent2 == null) {
						response.getWriter().println("<script type='text/javascript'>alert('当前高级代理不存在或已删除');window.location='/login.htm';</script>");
						return;
		            }
					request.setAttribute("Agent", Agent2);
					request.getRequestDispatcher("AgentAccount.jsp").forward(request, response);
					break;
				case 3:
					Map<String,Object> Agent3 = sqlSession.selectOne("RouterBatis.AgentMapper.SelectAgentID",Operator);
					if (Agent3 == null) {
						response.getWriter().println("<script type='text/javascript'>alert('当前代理不存在或已删除');window.location='/login.htm';</script>");
						return;
		            }
					request.setAttribute("Agent", Agent3);
					request.getRequestDispatcher("AgentAccount.jsp").forward(request, response);
					break;
				//商户
				case 4:
					Map<String,Object> bs = sqlSession.selectOne("RouterBatis.BusinesShopMapper.SelectBusinesShopID",Operator);
					if (bs == null) {
						response.getWriter().println("<script type='text/javascript'>alert('当前商家不存在或已删除');window.location='/login.htm';</script>");
						return;
		            }
					Map<String,Object> AgentAndShareArgu = new HashMap<String,Object>();
					AgentAndShareArgu.put("BusinesShopID", bs.get("BusinesShopID"));
					AgentAndShareArgu.put("Type", "4");
					List<Map<String,Object>> AgentAndShareList = sqlSession.selectList("RouterBatis.InfoTemplateMapper.ByBusinesShopIDAndTypeGetTemplate", AgentAndShareArgu);
					
					request.setAttribute("bs", bs);
					request.setAttribute("AgentGuestAndShareGuestList", AgentAndShareList);
		            request.getRequestDispatcher("account.jsp").forward(request, response);
					break;
				default:
					request.setAttribute("Operat","history.back()");
					request.setAttribute("ErrorMessage","角色选择错误:"+UserType);
					request.getRequestDispatcher("/AuthHtmlError.jsp").forward(request, response);
					return;
			}
		} catch (Exception e) {			
			if(properties.getValue("ShowBug") .equals( "true")){
				e.printStackTrace();
				response.getWriter().println(e.getMessage());
			}else{
				response.getWriter().println("<script type='text/javascript'>alert('" +  properties.getValue("SysErrorPointOut") +"');window.location='/login.jsp';</script>");
			}
		}finally{
			sqlSession.close();
		}
    }
}
