package com.smny.wifiAlliance.webaction;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import smny.MyBatisFactory;
import smny.util.StringToolkit;

/**
*       PACKAGE_NAME： webaction
*
*           <用户登录>
*
*           UserType:角色
*           1: 管理员
*           2：高级代理商
*           3：代理商
*           4：商户
*
*       作者：  parachuter
*       创建时间： 14-12-3
 */
@WebServlet(name="login",urlPatterns="/login")
public class login extends HttpServlet {
	private static Logger logger = Logger.getLogger(login.class.getName());
	
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String UserType = request.getParameter("UserType");
        String LoginName = request.getParameter("LoginName");
        String PassWord = request.getParameter("PassWord");
        String SeniorAgentTitle = "WanWiFi";
        String SeniorAgentInstruction = "圣玛尼亚团队致力于改善企业信息化进程。推出高质量的一站式解决方案，将企业软件需求策划、设计开发、软件周边硬件、实施推广、定期售后服务等一系列环节融合到一起。专注企业信息化建设解决方案提供商。";
        String SeniorAgentFooter = "微路由";
        String LogoURL = "http://smny-photo.qiniudn.com/WANWIFI.png";

        if(StringToolkit.isNullOrEmpty(LoginName,PassWord,UserType)){
        	response.getWriter().println("<script>alert('输入有误，请重新输入！！');window.location.href='login.html'</script>");
            return;
        }
        SqlSession  sqlsession = MyBatisFactory.openSession();
        try {
			int userType = Integer.parseInt(UserType);
			switch(userType){
        	//超级管理员登陆
        	case 1:				
        		if("admin".equals(LoginName) && "hnayyg".equals(PassWord)){
                    session.setAttribute("UserType",1);
                    session.setAttribute("Operator",null);
                    session.setAttribute("Name","系统管理员");
                    session.setAttribute("Role","管理员");
                    session.setAttribute("SeniorAgentTitle", SeniorAgentTitle);
                    session.setAttribute("SeniorAgentInstruction", SeniorAgentInstruction);
                    session.setAttribute("SeniorAgentFooter", SeniorAgentFooter);
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                }else{
                	response.getWriter().println("<script>alert('输入有误，请重新输入！！');window.location.href='login.html'</script>");
                	return;
                }
        		break;
        	//代理登陆
        	case 3:
        		Map<String,Object> Agent = sqlsession.selectOne("RouterBatis.AgentMapper.SelectLoginName", LoginName);
                if(Agent == null){
                	response.getWriter().println("<script>alert('输入有误，请重新输入！！');window.location.href='login.html'</script>");
                    return;
                }
                if(PassWord.equals(Agent.get("PassWord"))){
                    Boolean isSeniorAgent = Boolean.valueOf(true);
                    Map<String,Object> ThreeLevelAgent = new HashMap<String,Object>();
                    
                    //高级代理
                	if(Agent.get("SeniorAgent").equals(isSeniorAgent)){
                        session.setAttribute("UserType",2);
                        session.setAttribute("Role","高级代理商");
                        
                        //判断是否为空，如果为空，设为默认值
                        if(StringToolkit.isNullOrEmpty((String)Agent.get("SeniorAgentTitle"))){
                        	session.setAttribute("SeniorAgentTitle", SeniorAgentTitle);
                        }else{
                        	session.setAttribute("SeniorAgentTitle", Agent.get("SeniorAgentTitle"));
                        }if(StringToolkit.isNullOrEmpty((String)Agent.get("SeniorAgentInstruction"))){
                        	session.setAttribute("SeniorAgentInstruction", SeniorAgentInstruction);
                        }else{
                        	session.setAttribute("SeniorAgentInstruction", Agent.get("SeniorAgentInstruction"));
                        }if(StringToolkit.isNullOrEmpty((String)Agent.get("SeniorAgentFooter"))){
                        	session.setAttribute("SeniorAgentFooter", SeniorAgentFooter);
                        }else{
                        	session.setAttribute("SeniorAgentFooter", Agent.get("SeniorAgentFooter"));
                        }if(StringToolkit.isNullOrEmpty((String)Agent.get("LogoUrl"))){
                        	session.setAttribute("LogoURL", LogoURL);
                        }else{
                        	session.setAttribute("LogoURL", Agent.get("LogoUrl"));
                        }
                    }
                	
                	//普通代理
                	else{
                		ThreeLevelAgent = sqlsession.selectOne("RouterBatis.AgentMapper.SelectAgentID", Agent.get("SuperAgentID"));
                        if(ThreeLevelAgent == null){
                        	response.getWriter().println("<script>alert('输入有误，请重新输入！！');window.location.href='login.html'</script>");
                            return;
                        }
                        session.setAttribute("UserType",3);
                        session.setAttribute("Role","代理商");
                        
                        //判断是否为空，如果为空，设为默认值
                        if(StringToolkit.isNullOrEmpty((String)ThreeLevelAgent.get("SeniorAgentTitle"))){
                        	session.setAttribute("SeniorAgentTitle", SeniorAgentTitle);
                        }else{
                        	session.setAttribute("SeniorAgentTitle", ThreeLevelAgent.get("SeniorAgentTitle"));
                        }if(StringToolkit.isNullOrEmpty((String)ThreeLevelAgent.get("SeniorAgentInstruction"))){
                        	session.setAttribute("SeniorAgentInstruction", SeniorAgentInstruction);
                        }else{
                        	session.setAttribute("SeniorAgentInstruction", ThreeLevelAgent.get("SeniorAgentInstruction"));
                        }if(StringToolkit.isNullOrEmpty((String)ThreeLevelAgent.get("SeniorAgentFooter"))){
                        	session.setAttribute("SeniorAgentFooter", SeniorAgentFooter);
                        }else{
                        	session.setAttribute("SeniorAgentFooter", ThreeLevelAgent.get("SeniorAgentFooter"));
                        }if(StringToolkit.isNullOrEmpty((String)ThreeLevelAgent.get("LogoUrl"))){
                        	session.setAttribute("LogoURL", LogoURL);
                        }else{
                        	session.setAttribute("LogoURL", ThreeLevelAgent.get("LogoUrl"));
                        }
                    }
                    session.setAttribute("Operator",(Object)(Agent.get("AgentID").toString()));
                    session.setAttribute("Name",Agent.get("LoginName"));
                    response.sendRedirect("/Dashboard.htm");
                }else{
                	response.getWriter().println("<script>alert('输入有误，请重新输入！！');window.location.href='login.html'</script>");
                }
        		break;
        	//商户登陆
        	case 4:
        		 Map<String,Object> BusinessShop =  sqlsession.selectOne("RouterBatis.BusinesShopMapper.SelectLoginName", LoginName);
                 if(BusinessShop == null){
                	 response.getWriter().println("<script>alert('输入有误，请重新输入！！');window.location.href='login.html'</script>");
                     return;
                 }
                 
                 if(PassWord.equals(BusinessShop.get("PassWord"))){
                	 Map<String,Object> UnKnowLevelAgent = sqlsession.selectOne("RouterBatis.AgentMapper.SelectAgentID", BusinessShop.get("AgentID"));
                     //判断当前是不是高级代理，如果是高级代理，将高级代理的信息放到session；如果是普通代理，则查询高级代理，再将高级代理信息放到session
                     if(UnKnowLevelAgent == null){
                    	 return;
                     }else if(UnKnowLevelAgent != null){
                    	 Boolean isSeniorAgent = Boolean.valueOf(true); 
                    	 
                    	 //高级代理下商家
                    	 if(UnKnowLevelAgent.get("SeniorAgent").equals(isSeniorAgent)){
                    		 //判断是否为空，如果为空，设为默认值
                    		 if(StringToolkit.isNullOrEmpty((String)UnKnowLevelAgent.get("SeniorAgentTitle"))){
                             	session.setAttribute("SeniorAgentTitle", SeniorAgentTitle);
                             }else{
                             	session.setAttribute("SeniorAgentTitle", UnKnowLevelAgent.get("SeniorAgentTitle"));
                             }if(StringToolkit.isNullOrEmpty((String)UnKnowLevelAgent.get("SeniorAgentInstruction"))){
                             	session.setAttribute("SeniorAgentInstruction", SeniorAgentInstruction);
                             }else{
                             	session.setAttribute("SeniorAgentInstruction", UnKnowLevelAgent.get("SeniorAgentInstruction"));
                             }if(StringToolkit.isNullOrEmpty((String)UnKnowLevelAgent.get("SeniorAgentFooter"))){
                             	session.setAttribute("SeniorAgentFooter", SeniorAgentFooter);
                             }else{
                             	session.setAttribute("SeniorAgentFooter", UnKnowLevelAgent.get("SeniorAgentFooter"));
                             }if(StringToolkit.isNullOrEmpty((String)UnKnowLevelAgent.get("LogoUrl"))){
                              	session.setAttribute("LogoURL", LogoURL);
                              }else{
                              	session.setAttribute("LogoURL", UnKnowLevelAgent.get("LogoUrl"));
                              }
                    	 }
                    	 
                    	 //普通代理下商家
                    	 else{
                    		 Map<String,Object> ThreeLevelAgentBusiness = sqlsession.selectOne("RouterBatis.AgentMapper.SelectAgentID", UnKnowLevelAgent.get("SuperAgentID"));
                             if(ThreeLevelAgentBusiness == null){
                            	response.getWriter().println("<script>alert('查找代理不存在!');window.location.href='login.html'</script>");
                             	/*request.setAttribute("ErrorMessage","查找代理不存在!");
             					request.setAttribute("Operat","window.location.replace('/login.html')");
             					request.getRequestDispatcher("/AuthHtmlError.jsp").forward(request, response);*/
                                 return;
                             }
                             //判断是否为空，如果为空，设为默认值
                             if(StringToolkit.isNullOrEmpty((String)ThreeLevelAgentBusiness.get("SeniorAgentTitle"))){
                              	session.setAttribute("SeniorAgentTitle", SeniorAgentTitle);
                              }else{
                              	session.setAttribute("SeniorAgentTitle", ThreeLevelAgentBusiness.get("SeniorAgentTitle"));
                              }if(StringToolkit.isNullOrEmpty((String)ThreeLevelAgentBusiness.get("SeniorAgentInstruction"))){
                              	session.setAttribute("SeniorAgentInstruction", SeniorAgentInstruction);
                              }else{
                              	session.setAttribute("SeniorAgentInstruction", ThreeLevelAgentBusiness.get("SeniorAgentInstruction"));
                              }if(StringToolkit.isNullOrEmpty((String)ThreeLevelAgentBusiness.get("SeniorAgentFooter"))){
                              	session.setAttribute("SeniorAgentFooter", SeniorAgentFooter);
                              }else{
                              	session.setAttribute("SeniorAgentFooter", ThreeLevelAgentBusiness.get("SeniorAgentFooter"));
                              }if(StringToolkit.isNullOrEmpty((String)ThreeLevelAgentBusiness.get("LogoUrl"))){
                                	session.setAttribute("LogoURL", LogoURL);
                                }else{
                                	session.setAttribute("LogoURL", ThreeLevelAgentBusiness.get("LogoUrl"));
                                }
                    	 }
                     }
                     session.setAttribute("UserType",4);
                     session.setAttribute("Operator",BusinessShop.get("BusinesShopID"));
                     session.setAttribute("Name",BusinessShop.get("BusinesName"));
                     if(!StringToolkit.isNullOrEmpty((String)BusinessShop.get("LogoURL"))){
                    	 session.setAttribute("LogoURL", BusinessShop.get("LogoURL"));
                     }
                     session.setAttribute("Role","商户");
                     response.sendRedirect("/Dashboard.htm");
                 }else{
                	 response.getWriter().println("<script>alert('输入有误，请重新输入！！');window.location.href='login.html'</script>");
                 }
        		break;
        	default:
        		response.getWriter().println("<script>alert('用户类型未知错误');window.location.href='login.html'</script>");
        		break;       		
			}
		} catch (Exception e) {
			 response.getWriter().println("<script>alert('系统异常！！！请稍后再试');window.location.href='login.html'</script>");
		}finally{
			sqlsession.close();
		}
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().println("<script>alert('登陆错误，请重新登陆！！！');window.location.href='login.html'</script>");
    }
}
