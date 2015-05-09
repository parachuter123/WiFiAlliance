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

import com.smny.wifiAlliance.entity.logic.AppEntranceLogic;
import com.smny.wifiAlliance.entity.logic.BusinessShopLogic;
import com.smny.wifiAlliance.entity.logic.FollowersGroupLogic;

/**
 * PACKAGE_NAME： webaction
 * <p/>
 * <WIFI消息接口>
 * <p/>
 * 作者：  Ronan
 * 创建时间： 14-12-08.
 */
@WebServlet(name = "AppEntranceManager", urlPatterns = "/AppEntranceManager.htm")
public class AppEntranceManager extends HttpServlet {
	private static final long serialVersionUID = 1L;
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
				//商户
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
						request.setAttribute("ErrorMessage","操作错误：未明确操作类型！");
						request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
						return;
		            }
		            //获取APP信息
		            if ("getinfo".equals(Type)) {
		                String appid = request.getParameter("appid");
		                if (StringToolkit.isNullOrEmpty(appid)) {
							request.setAttribute("Operat","4");
							request.setAttribute("ErrorMessage","参数不完整");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
		                }
		                Map<String,Object> AppEntrance = AppEntranceLogic.getAppEntrance(appid);
		                if (AppEntrance == null) {
							request.setAttribute("Operat","4");
							request.setAttribute("ErrorMessage","app不存在");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
		                }
		                StringBuilder json = new StringBuilder();
						json.append("{\"State\":1,\"Message\":\"成功\",");
		                json.append("\"AppID\": \"").append(AppEntrance.get("AppID")).append("\",");
		                json.append("\"AppSecret\": \"").append(AppEntrance.get("AppSecret")).append("\",");
		                json.append("\"AppName\": \"").append(AppEntrance.get("AppName")).append("\",");
		                json.append("\"AppURL\": \"").append(AppEntrance.get("AppURL")).append("\",");
		                json.append("\"AppToken\": \"").append(AppEntrance.get("AppToken")).append("\",");
		                json.append("\"Email\": \"").append(AppEntrance.get("Email")).append("\",");
		                json.append("\"LoginPassword\": \"").append(AppEntrance.get("LoginPassword")).append("\",");
		                json.append("\"SearchAccount\": \"").append(AppEntrance.get("SearchAccount")).append("\",");
		                json.append("\"Authenticate\": \"").append(AppEntrance.get("Authenticate")).append("\",");
		                json.append("\"OriginalAtuoAttention\": \"").append(AppEntrance.get("OriginalAtuoAttention")).append("\",");
		                json.append("\"AppHomePageUrl\": \"").append(AppEntrance.get("AppHomePageUrl")).append("\",");
		                json.append("\"Introduction\": \"").append(AppEntrance.get("Introduction")).append("\",");
		                json.append("\"VerificationPicture\": \"").append(AppEntrance.get("VerificationPicture")).append("\"");
		                json.append("}");
		                response.setContentType("application/json; charset=UTF-8");
		                response.getWriter().print(json.toString());
		                return;
		            }
		            //新建App接口
		            if ("Create".equals(Type)) {
		                String LoginEmail = request.getParameter("LoginEmail");
		                String LoginPassword = request.getParameter("LoginPassword");
//		                String AppName = request.getParameter("AppName");
//		                String App_ID = request.getParameter("App_ID");
		                String App_Secret = request.getParameter("App_Secret");
//		                String Origin_ID = request.getParameter("Origin_ID");
//		                String ThirdURL = request.getParameter("ThirdURL");
//		                String ThirdToken = request.getParameter("ThirdToken");
		                String AppHomePageUrl = request.getParameter("AppHomePageUrl");
		                String AppType = request.getParameter("AppType");
//		                String Introduction = request.getParameter("Introduction");

		                if (StringToolkit.isNullOrEmpty(LoginEmail, LoginPassword, App_Secret,AppType)) {
		                	request.setAttribute("Operat","5");
							request.setAttribute("ErrorMessage","创建参数不完整！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
		                    return;
		                }
		                if (StringToolkit.isNullOrEmpty(AppType)) {
		                    AppType = "1";
		                }
		                if (AppEntranceLogic.getDatasOriginNumber(App_Secret, AppType) != null) {
		                	request.setAttribute("Operat","6");
							request.setAttribute("ErrorMessage","公众账号已存在，请仔细检查！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
		                    return;
		                }
		                Map<String,Object> appEntrance = new HashMap<String,Object>();
		                appEntrance.put("LoginEmail",LoginEmail);
	                    appEntrance.put("LoginPassword",LoginPassword);
//	                    appEntrance.put("AppName",AppName);
	                    appEntrance.put("App_ID","");
	                    appEntrance.put("App_Secret",App_Secret);
//	                    appEntrance.put("Origin_ID",Origin_ID);
//	                    appEntrance.put("ThirdURL",ThirdURL);
//	                    appEntrance.put("ThirdToken",ThirdToken);
	                    appEntrance.put("BusinessShopID",bs.get("BusinessShopID"));
	                    appEntrance.put("AppType",AppType);
	                    appEntrance.put("AppHomePageUrl",AppHomePageUrl);
//	                    appEntrance.put("Introduction",Introduction);
		                try {
		                    sqlSession.insert("RouterBatis.AppEntranceMapper.insertEntrance", appEntrance);
		            		sqlSession.commit();
		                    request.setAttribute("Operat","1");
							request.setAttribute("ErrorMessage","消息接口创建成功！！");
		                } catch (Exception e) {
		        			request.setAttribute("Operat","-5");
		        			request.setAttribute("ErrorMessage",e.getMessage().replace("\r\n", ""));
		        			request.setAttribute("ShowBug",properties.getValue("ShowBug"));
		        			request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
		                }
						request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
						return;
		            }
		            //App修改
		            if ("AppUpdate".equals(Type)) {
		                String App_ID = request.getParameter("App_ID");
		                if (StringToolkit.isNullOrEmpty(App_ID)) {
							request.setAttribute("Operat","7");
							request.setAttribute("ErrorMessage","修改错误:指定APPID不能为空！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
		                }
		                
		                Map<String,Object> appEntrance = AppEntranceLogic.getAppEntrance(App_ID);

		                if (appEntrance == null) {
		                	request.setAttribute("Operat","8");
							request.setAttribute("ErrorMessage","修改错误,指定消息接口不存在！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
		                }
		                
		                String AppSecret = request.getParameter("AppSecret");
						String AppURL = request.getParameter("AppURL");
						String AppToken = request.getParameter("AppToken");
						
						String Email = request.getParameter("Email");
						String LoginPassword = request.getParameter("LoginPassword");
						
						String AppHomePageUrl = request.getParameter("AppHomePageUrl");
						String Introduction = request.getParameter("Introduction");
		                boolean isChange = false;

		                if (!StringToolkit.isNullOrEmpty(AppSecret) && !AppSecret.equals(appEntrance.get("AppSecret"))) {
		                    appEntrance.put("AppSecret",AppSecret);
		                    isChange = true;
		                }
		                if (!StringToolkit.isNullOrEmpty(AppURL) && !AppURL.equals(appEntrance.get("AppURL"))) {
		                    appEntrance.put("AppURL",AppURL);
		                    isChange = true;
		                }
		                if (!StringToolkit.isNullOrEmpty(AppToken) && !AppToken.equals(appEntrance.get("AppToken"))) {
		                    appEntrance.put("AppToken",AppToken);
		                    isChange = true;
		                }
		                if (!StringToolkit.isNullOrEmpty(Email) && !Email.equals(appEntrance.get("Email"))) {
		                    appEntrance.put("Email",Email);
		                    isChange = true;
		                }
		                if (!StringToolkit.isNullOrEmpty(LoginPassword) && !LoginPassword.equals(appEntrance.get("LoginPassword"))) {
		                    appEntrance.put("LoginPassword",LoginPassword);
		                    isChange = true;
		                }
		                if (!StringToolkit.isNullOrEmpty(AppHomePageUrl) && !AppHomePageUrl.equals(appEntrance.get("AppHomePageUrl"))) {
		                    appEntrance.put("AppHomePageUrl",AppHomePageUrl);
		                    isChange = true;
		                }
		                if (!StringToolkit.isNullOrEmpty(Introduction) && !Introduction.equals(appEntrance.get("Introduction"))) {
		                    appEntrance.put("Introduction",Introduction);
		                    isChange = true;
		                }
		                if (isChange) {
	                    	try {
								sqlSession.update("RouterBatis.AppEntranceMapper.updateEntrance",appEntrance);
								sqlSession.commit();
								request.setAttribute("Operat","1");
								request.setAttribute("ErrorMessage","消息接口修改成功");
							} catch (Exception e) {
								request.setAttribute("Operat","-4");
								request.setAttribute("ErrorMessage",e.getMessage().replace("\r\n", ""));
								request.setAttribute("ShowBug",properties.getValue("ShowBug"));
								request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
							}
		                }else{
		                	request.setAttribute("Operat","2");
							request.setAttribute("ErrorMessage","数据无变更!");
		                }
		                request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
		                return;
		            }
		            //设置App用户的默认分组
		            if ("SetDefualtGroup".equals(Type)) {
		                String App_ID = request.getParameter("App_ID");
		                String Group_ID = request.getParameter("Group_ID");

		                if (StringToolkit.isNullOrEmpty(App_ID,Group_ID)) {
							request.setAttribute("Operat","9");
							request.setAttribute("ErrorMessage","设置默认分组参数不完整！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
		                }
		                Map<String,Object> followersGroup = FollowersGroupLogic.getGroup(Group_ID);
		                if (followersGroup == null) {
							request.setAttribute("Operat","10");
							request.setAttribute("ErrorMessage","设置默认分组不存在！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
		                }
		                Map<String,Object> appEntrance = AppEntranceLogic.getAppEntrance(App_ID);
		                if (appEntrance == null) {
							request.setAttribute("Operat","11");
							request.setAttribute("ErrorMessage","修改错误,指定消息接口不存在！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
		                }
		                appEntrance.put("DefaultGroupID",Group_ID);
						try {
							sqlSession.update("RouterBatis.AppEntranceMapper.updateEntrance",appEntrance);
							sqlSession.commit();
							request.setAttribute("Operat","1");
							request.setAttribute("ErrorMessage","默认分组设置成功！！");
						} catch (Exception e) {
							request.setAttribute("Operat","-4");
							request.setAttribute("ErrorMessage",e.getMessage().replace("\r\n", ""));
							request.setAttribute("ShowBug",properties.getValue("ShowBug"));
							request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
						}
						request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
		                return;
		            }
		            //设置关注JS
		            if("SetWatchHandleJs".equals(Type)){
		            	String App_ID = request.getParameter("App_ID");
		                String Template_ID = request.getParameter("Template_ID");
		                if (StringToolkit.isNullOrEmpty(App_ID)) {
							request.setAttribute("Operat","12");
							request.setAttribute("ErrorMessage","设置关注模板参数不能为空！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
		                }
		                Map<String,Object> appEntranceLogic = AppEntranceLogic.getAppEntrance(App_ID);
		                if (appEntranceLogic == null) {
							request.setAttribute("Operat","13");
							request.setAttribute("ErrorMessage","修改错误,指定消息接口不存在！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
		                }
		                appEntranceLogic.put("WatchHandleJs", Template_ID);
		                try {
							sqlSession.update("RouterBatis.AppEntranceMapper.updateEntrance", appEntranceLogic);
							sqlSession.commit();
							request.setAttribute("Operat","1");
							request.setAttribute("ErrorMessage","关注模板设置成功!");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
						} catch (Exception e) {
							request.setAttribute("Operat","-4");
							request.setAttribute("ErrorMessage",e.getMessage().replace("\r\n", ""));
							request.setAttribute("ShowBug",properties.getValue("ShowBug"));
							request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
						}
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
						request.setAttribute("Operat","14");
						request.setAttribute("ErrorMessage","当前商家不存在或已删除");
						request.getRequestDispatcher("/AuthHtmlError.jsp").forward(request, response);
						return;
					}
					Operator = (String)BusinessShop.get("BusinesShopID");
					//获取用户分组
					List<Map<String,Object>> Grouplist = BusinessShopLogic.getFollowersGroup(sqlSession,(Integer)BusinessShop.get("AgentID"),Operator);
					request.setAttribute("Grouplist", Grouplist);
					
					//获取关注模板/取消关注模板
					Map<String,Object> InfoArgu = new HashMap<String,Object>();
					InfoArgu.put("BusinesShopID", Operator);
					InfoArgu.put("Type", "2");
					List<Map<String,Object>> WatchTemplate = sqlSession.selectList("RouterBatis.InfoTemplateMapper.ByBusinesShopIDAndTypeGetTemplate",InfoArgu);
					request.setAttribute("InfoTemplatelist", WatchTemplate);
					//获取消息接口列表
					List<Map<String,Object>> AppEntranceList = AppEntranceLogic.getDatasBusinessShopID(sqlSession,Operator);
					request.setAttribute("list", AppEntranceList);
					request.setAttribute("BusinesShopID", Operator);
					request.getRequestDispatcher("/AppEntranceManager.jsp").forward(request, response);
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
