package com.smny.wifiAlliance.webaction;

import java.io.File;
import java.io.IOException;
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

import RouterGuide.WeiXin.WxWebPage;
import smny.MyBatisFactory;
import smny.util.ServletRefactorMethod;
import smny.util.SetSystemProperty;
import smny.util.StringToolkit;

/**
 * PACKAGE_NAME： webaction
 * <p/>
 * <商家列表>
 * <p/>
 * 作者：  Parachuter
 * 创建时间： 15-1-16.
 */
@WebServlet(name = "ShopList", urlPatterns = "/ShopList.htm")
public class ShopList extends HttpServlet {
	/***********************************************测试代码**********************************************/
	private static Logger logger = LogManager.getLogger(ShopList.class.getName());
	
	/***********************************************测试代码**********************************************/
	//默认页码
    private static final int DEFAULTPAGEINDEX = 1;
    //默认页大小
    private static final int DEFAULTPAGESIZE = 8;
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
					break;
				case 3:
				case 2:
					/***********************************************和case 3一摸一样，将来重构**********************************************/
					if("createBusinessShop".equals(Type)){
						Map<String,Object> Agent = sqlSession.selectOne("RouterBatis.AgentMapper.SelectAgentID", Integer.parseInt(Operator));
						if(Agent == null){
							request.setAttribute("Operat","2");
							request.setAttribute("ErrorMessage","没有该代理");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
						}
						String AgentID = Agent.get("AgentID").toString();
						String BusinessName = request.getParameter("BusinessName");
			            String LoginName = request.getParameter("LoginName");
			            String BusinessAddress = request.getParameter("BusinessAddress");
			            String BusinessBoss = request.getParameter("BusinessBoss");
			            String BossMobile = request.getParameter("BossMobile");
			            String BusinessContact = request.getParameter("BusinessContact");
			            String ContactMobile = request.getParameter("ContactMobile");
			            String BusinessFax = request.getParameter("BusinessFax");
			            String Industry = request.getParameter("Industry");
			            String BusinessNature = request.getParameter("BusinessNature");
			            String ProCity = request.getParameter("ProCity");
			            String WifiShare = request.getParameter("WifiShare");
			            //判断根目录下是否存在HeadPortrait文件夹
			            File HeadPortrait = new File(new File(ShopList.class.getResource("ShopList.class").toURI()).getParentFile().getParentFile().getParentFile().getParentFile().getParentFile().getParentFile().getParentFile(),"HeadPortrait");
			            if(!HeadPortrait.exists()){
			            		HeadPortrait.mkdirs();
			            }
			            //创建商家image文件
			            File CurrentBussinessShopFile = new File(HeadPortrait + File.separator + AgentID + "_" + LoginName);
			            if(!CurrentBussinessShopFile.exists()){
			            	CurrentBussinessShopFile.mkdirs();
			            }
			            
			            if (StringToolkit.isNullOrEmpty(BusinessName, LoginName, BusinessAddress, BusinessBoss,
			                    BossMobile, BusinessContact, ContactMobile, WifiShare)) {
			                response.getWriter().print("创建参数不完整！");
			                return;
			            }
			            if(StringToolkit.isNullOrEmpty(Industry)){
			                Industry = "0";
			            }
			            if(StringToolkit.isNullOrEmpty(BusinessNature)){
			                BusinessNature = "0";
			            }
			            if(StringToolkit.isNullOrEmpty(ProCity)){
			                ProCity = "0";
			            }
			            if(sqlSession.selectOne("RouterBatis.BusinesShopMapper.SelectLoginName", LoginName)!=null){
			            	request.setAttribute("Operat","3");
							request.setAttribute("ErrorMessage","已经存在该商家，请更换登陆名称！！！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
			            }
			            Map<String,Object> BusinessShop = new HashMap<String,Object>();
			            BusinessShop.put("AgentID", Integer.parseInt(AgentID));
			            BusinessShop.put("BusinesName", BusinessName);
			            BusinessShop.put("LoginName", LoginName);
			            BusinessShop.put("PassWord", LoginName);
			            BusinessShop.put("BusinesAddres", BusinessAddress);
			            BusinessShop.put("BusinesBoss", BusinessBoss);
			            BusinessShop.put("BossMobile", BossMobile);
			            BusinessShop.put("BusinesContact", BusinessContact);
			            BusinessShop.put("ContactMobile", ContactMobile);
			            BusinessShop.put("BusinesFax", BusinessFax);
			            BusinessShop.put("IndustryID", Industry);
			            BusinessShop.put("NatureID", BusinessNature);
			            BusinessShop.put("ProCityID", ProCity);
			            BusinessShop.put("WifiShare", WifiShare.equals("true")?1:0);
			            //为商家自动创建手机浏览器模板、电脑模板、微信关注模板
			            Map<String,Object> InfoIphoneMap = this.createInfoMap(Operator,LoginName,Integer.parseInt(properties.getValue("InfoIphoneType")),"手机浏览器模板",
			            		"手机浏览器模板",properties.getValue("InfoIphoneModel"),properties.getValue("InfoIphoneTemplateID"),(String)getInfoMap(sqlSession,Integer.parseInt(properties.getValue("InfoIphoneTemplateID"))).get("TemplatePage"));
			            Map<String,Object> InfoPcMap = this.createInfoMap(Operator,LoginName,Integer.parseInt(properties.getValue("InfoPcType")),"电脑浏览器模板",
			            		"电脑浏览器模板",properties.getValue("InfoPcModel"),properties.getValue("InfoPcTemplateID"),(String)getInfoMap(sqlSession,Integer.parseInt(properties.getValue("InfoPcTemplateID"))).get("TemplatePage"));
			            Map<String,Object> InfoWatchMap = this.createInfoMap(Operator,LoginName,Integer.parseInt(properties.getValue("InfoWatchType")),"关注模板",
			            		"关注模板",properties.getValue("InfoWatchModel"),properties.getValue("InfoWatchTemplateID"),(String)getInfoMap(sqlSession,Integer.parseInt(properties.getValue("InfoWatchTemplateID"))).get("TemplatePage"));
			            try {
			            	sqlSession.insert("RouterBatis.InfoTemplateMapper.insertInfo",InfoIphoneMap);
			            	sqlSession.insert("RouterBatis.InfoTemplateMapper.insertInfo",InfoPcMap);
			            	sqlSession.insert("RouterBatis.InfoTemplateMapper.insertInfo",InfoWatchMap);
							sqlSession.insert("RouterBatis.BusinesShopMapper.insertBusinesShop",BusinessShop);
							sqlSession.commit();
							request.setAttribute("Operat","1");
							request.setAttribute("ErrorMessage","商家创建成功！！");
						} catch (Exception e) {
							request.setAttribute("Operat","-2");
							request.setAttribute("ErrorMessage",e.getMessage().replace("\r\n", ""));
							request.setAttribute("ShowBug",properties.getValue("ShowBug"));
							request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
						} 
						request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
						return;
					}
					if("getShopInfo".equals(Type)){
						String BusinessShopID = request.getParameter("ShopID");
						if (StringToolkit.isNullOrEmpty(BusinessShopID)) {
							request.setAttribute("Operat","5");
							request.setAttribute("ErrorMessage","商家ID不能为空");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
						}
						Map<String,Object> BusinessShop = sqlSession.selectOne("RouterBatis.BusinesShopMapper.SelectBusinesShopID",BusinessShopID);
						if (BusinessShop == null) {
							request.setAttribute("Operat","6");
							request.setAttribute("ErrorMessage","指定的商家不存在");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
						}
						StringBuilder json = new StringBuilder("{");
				        json.append("\"BusinessName\": \"").append(BusinessShop.get("BusinesName") == null ? "" : BusinessShop.get("BusinesName")).append("\",");
				        json.append("\"LoginName\": \"").append(BusinessShop.get("LoginName") == null ? "" : BusinessShop.get("LoginName")).append("\",");
				        json.append("\"BusinessAddress\": \"").append(BusinessShop.get("BusinesAddres") == null ? "" : BusinessShop.get("BusinesAddres")).append("\",");
				        json.append("\"BusinessBoss\": \"").append(BusinessShop.get("BusinesBoss") == null ? "" : BusinessShop.get("BusinesBoss")).append("\",");
				        json.append("\"BossMobile\": \"").append(BusinessShop.get("BossMobile") == null ? "" : BusinessShop.get("BossMobile")).append("\",");
				        json.append("\"BusinessContact\": \"").append(BusinessShop.get("BusinesContact") == null ? "" : BusinessShop.get("BusinesContact")).append("\",");
				        json.append("\"ContactMobile\": \"").append(BusinessShop.get("ContactMobile") == null ? "" : BusinessShop.get("ContactMobile")).append("\",");
				        json.append("\"BusinessFax\": \"").append(BusinessShop.get("BusinesFax") == null ? "" : BusinessShop.get("BusinesFax")).append("\",");
				        json.append("\"Industry\": \"").append(BusinessShop.get("IndustryID")).append("\",");
				        json.append("\"BusinessNature\": \"").append(BusinessShop.get("NatureID")).append("\",");
				        json.append("\"ProCity\": \"").append(BusinessShop.get("ProCityID")).append("\",");
				        json.append("\"WifiShare\": \"").append(BusinessShop.get("WifiShare")).append("\"");
				        json.append("}");
		                response.setContentType("text/json; charset=utf-8");
				        response.getWriter().print(json.toString());
					}
					if("updateBusinessShop".equals(Type)){
						String ShopID = request.getParameter("ShopID");
						Map<String,Object> BusinessShop = sqlSession.selectOne("RouterBatis.BusinesShopMapper.SelectBusinesShopID",ShopID);
						if (BusinessShop == null) {
							request.setAttribute("Operat","6");
							request.setAttribute("ErrorMessage","指定的商家不存在");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
						}
						String BusinessName = request.getParameter("BusinessName");
				        String LoginName = request.getParameter("LoginName");
				        String BusinessAddress = request.getParameter("BusinessAddress");
				        String BusinessBoss = request.getParameter("BusinessBoss");
				        String BossMobile = request.getParameter("BossMobile");
				        String BusinessContact = request.getParameter("BusinessContact");
				        String ContactMobile = request.getParameter("ContactMobile");
				        String BusinessFax = request.getParameter("BusinessFax");
				        String Industry = request.getParameter("Industry");
				        String BusinessNature = request.getParameter("BusinessNature");
				        String ProCity = request.getParameter("ProCity");
				        String WifiShare = request.getParameter("WifiShare");
				        
				        boolean isChange = false;
				        if (!StringToolkit.isNullOrEmpty(BusinessName)) {
				        	BusinessShop.put("BusinesName",BusinessName);
				            isChange = true;
				        }
				        if (!StringToolkit.isNullOrEmpty(LoginName)) {
				        	BusinessShop.put("LoginName",LoginName);
				            isChange = true;
				        }
				        if (!StringToolkit.isNullOrEmpty(BusinessAddress)) {
				        	BusinessShop.put("BusinesAddres",BusinessAddress);
				            isChange = true;
				        }
				        if (!StringToolkit.isNullOrEmpty(BusinessBoss)) {
				        	BusinessShop.put("BusinesBoss",BusinessBoss);
				            isChange = true;
				        }
				        if (!StringToolkit.isNullOrEmpty(BossMobile)) {
				        	BusinessShop.put("BossMobile",BossMobile);
				            isChange = true;
				        }
				        if (!StringToolkit.isNullOrEmpty(BusinessContact)) {
				        	BusinessShop.put("BusinesContact",BusinessContact);
				            isChange = true;
				        }
				        if (!StringToolkit.isNullOrEmpty(ContactMobile)) {
				        	BusinessShop.put("ContactMobile",ContactMobile);
				            isChange = true;
				        }
				        if (!StringToolkit.isNullOrEmpty(BusinessFax)) {
				        	BusinessShop.put("BusinesFax",BusinessFax);
				            isChange = true;
				        }
				        if (!StringToolkit.isNullOrEmpty(Industry)) {
				        	BusinessShop.put("IndustryID",Integer.parseInt(Industry));
				            isChange = true;
				        }
				        if (!StringToolkit.isNullOrEmpty(BusinessNature)) {
				        	BusinessShop.put("NatureID",Integer.parseInt(BusinessNature));
				            isChange = true;
				        }
				        if (!StringToolkit.isNullOrEmpty(ProCity)) {
				        	BusinessShop.put("ProCityID",Integer.parseInt(ProCity));
				            isChange = true;
				        }
				        if (!StringToolkit.isNullOrEmpty(WifiShare)) {
				        	BusinessShop.put("WifiShare",new Boolean(WifiShare));
				            isChange = true;
				        }
						if(isChange){
							try {
								sqlSession.update("RouterBatis.BusinesShopMapper.updateBusinesShop",BusinessShop);
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
        String PageIndex = request.getParameter("PageIndex");
        int pageIndex = DEFAULTPAGEINDEX;
        if (!StringToolkit.isNullOrEmpty(PageIndex)) {
        	pageIndex = Integer.parseInt(PageIndex);
        }
		SqlSession sqlSession = MyBatisFactory.openSession();
		try{
			switch(UserType.intValue()){
				case 1:
					break;
				case 2:
					/*int ShopListCount2 = (Integer)sqlSession.selectOne("RouterBatis.BusinesShopMapper.SelectBusinessShopCountByAgentID", Integer.parseInt(Operator));
					int StartNumber2 = (pageIndex-1)*DEFAULTPAGESIZE;
					if(ShopListCount2 <= StartNumber2){
						pageIndex = (int)Math.ceil((double)ShopListCount2/DEFAULTPAGESIZE);
						StartNumber2 = (pageIndex-1)*DEFAULTPAGESIZE;
					}
					List<Map<String,Object>> ShopList2 = sqlSession.selectList("RouterBatis.BusinesShopMapper.SelectAgentID", Integer.parseInt(Operator),new RowBounds(StartNumber2, DEFAULTPAGESIZE));
					request.setAttribute("ShopListCount", ShopListCount2);
					request.setAttribute("list", ShopList2);
					request.getRequestDispatcher("ShopList.jsp").forward(request, response);
					break;*/
				case 3:
					int ShopListCount3 = (Integer)sqlSession.selectOne("RouterBatis.BusinesShopMapper.SelectBusinessShopCountByAgentID", Integer.parseInt(Operator));
					int StartNumber3 = (pageIndex-1)*DEFAULTPAGESIZE;
					if(ShopListCount3 <= StartNumber3){
						pageIndex = (int)Math.ceil((double)ShopListCount3/DEFAULTPAGESIZE);
						StartNumber3 = (pageIndex-1)*DEFAULTPAGESIZE;
					}
					List<Map<String,Object>> ShopList3 = sqlSession.selectList("RouterBatis.BusinesShopMapper.SelectAgentID", Integer.parseInt(Operator),new RowBounds(StartNumber3, DEFAULTPAGESIZE));
					request.setAttribute("ShopListCount", ShopListCount3);
					request.setAttribute("PageIndex", pageIndex);
					request.setAttribute("list", ShopList3);
					request.getRequestDispatcher("ShopList.jsp").forward(request, response);
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
    /**
     * 创建一个广告模板
     * @param Operator			代理ID
     * @param LoginName		登陆名称
     * @param Type					广告类型
     * @param Name				广告名称
     * @param Remark			广告备注
     * @param Model				模板类型
     * @param TemplateID		模板ID
     * @param Content			模板内容
     * @return				
     */
    private Map<String,Object> createInfoMap(String Operator,String LoginName,int Type,String Name,String Remark,String Model,String TemplateID,String Content){
    	Map<String,Object> InfoMap = new HashMap<String,Object>();
    	InfoMap.put("BusinesShopID",Operator + "_" + LoginName);
    	InfoMap.put("Name",Name);
    	InfoMap.put("Type",Type);
    	InfoMap.put("Remark",Remark);
    	InfoMap.put("Model",Model);
    	InfoMap.put("TemplateID", TemplateID);
    	InfoMap.put("Content", Content);
        return InfoMap;
    }
    private Map<String,Object> getInfoMap(SqlSession sqlSession,int content){
    	Map<String,Object> InteriorTemplate = sqlSession.selectOne("RouterBatis.InteriorTemplateMapper.SelectTemplateID",content);
    	return InteriorTemplate;
    }
}
