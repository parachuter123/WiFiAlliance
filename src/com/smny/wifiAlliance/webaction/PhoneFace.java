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
import smny.util.DianPingConvert;
import smny.util.ServletRefactorMethod;
import smny.util.SetSystemProperty;
import smny.util.StringToolkit;

/**
 * PACKAGE_NAME： webaction
 * <p/>
 * <广告列表>
 * <p/>
 * 作者：  parachuter
 * 创建时间： 15-01-05.
 */
@WebServlet(name = "PhoneFace", urlPatterns = "/PhoneFace.htm")
public class PhoneFace extends HttpServlet {

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
		try {
			switch(UserType.intValue()){
				case 4:
					Map<String,Object> bs = sqlSession.selectOne("RouterBatis.BusinesShopMapper.SelectBusinesShopID",Operator);
					if (bs == null) {
						request.setAttribute("ErrorMessage","当前商家不存在或已删除");
						request.setAttribute("Operat","2");
						request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
						return;
					}
					String Type = request.getParameter("Type");
					if("createPhoneFace".equals(Type)){
						String Name = request.getParameter("Name");
						String ImgUrl = request.getParameter("ImgUrl");
						String Title = request.getParameter("Title");
						String LineLink = request.getParameter("LineLink");
						String Desc = request.getParameter("Desc");
						String ShareTemplateKey = request.getParameter("ShareTemplateKey");
						String poiId = request.getParameter("poiId");
						String BusinesShopID = (String)bs.get("BusinesShopID");
						Map<String,Object> PhoneFace =  new HashMap<String,Object>();
						if(!StringToolkit.isNullOrEmpty(poiId)){
							try{
								double[] LatitudeAndLongitude = DianPingConvert.DianPingAddressConvert(poiId);
								PhoneFace.put("poiId", poiId);
								PhoneFace.put("latitude", LatitudeAndLongitude[0]);
								PhoneFace.put("longitude",LatitudeAndLongitude[1]);
							}catch(Exception e){
								request.setAttribute("Operat","-5");
								request.setAttribute("ErrorMessage",e.getMessage().replace("\r\n", ""));
								request.setAttribute("ShowBug",properties.getValue("ShowBug"));
								request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
								request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
								return;
							}
						}
						PhoneFace.put("Name", Name);
						PhoneFace.put("ImgUrl", ImgUrl);
						PhoneFace.put("Title", Title);
						PhoneFace.put("LineLink", LineLink);
						PhoneFace.put("Desc", Desc);
						PhoneFace.put("ShareTemplateKey", ShareTemplateKey);
						PhoneFace.put("BusinesShopID", BusinesShopID);
						try {
							sqlSession.insert("RouterBatis.ShareTaskMapper.insertShare",PhoneFace);
							sqlSession.commit();
							request.setAttribute("Operat","1");
							request.setAttribute("ErrorMessage","分享界面创建成功！！");
						} catch (Exception e) {
							request.setAttribute("Operat","-4");
							request.setAttribute("ErrorMessage",e.getMessage().replace("\r\n", ""));
							request.setAttribute("ShowBug",properties.getValue("ShowBug"));
							request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
						} 
						request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
						return;
					}
					if("getPhoneFaceInfo".equals(Type)){
						String ShareID = request.getParameter("ShareID");
						if (StringToolkit.isNullOrEmpty(ShareID)) {
							request.setAttribute("Operat","4");
							request.setAttribute("ErrorMessage","分享ID不能为空");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
						}
						Map<String,Object> PhoneFace = sqlSession.selectOne("RouterBatis.ShareTaskMapper.SelectShareID",ShareID);
						if (PhoneFace == null) {
							request.setAttribute("Operat","5");
							request.setAttribute("ErrorMessage","指定的分享不存在");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
						}
						Map<String,Object> InfoTemplate = sqlSession.selectOne("RouterBatis.InfoTemplateMapper.SelectKey", PhoneFace.get("ShareTemplateKey"));
						if(InfoTemplate == null){
							InfoTemplate = null;
						}
						StringBuilder json = new StringBuilder("{");
						json.append("\"State\":1,\"Message\":\"成功\",");
		                json.append("\"Name\": \"").append(PhoneFace.get("Name")).append("\",");
		                json.append("\"ImgUrl\": \"").append(PhoneFace.get("ImgUrl")).append("\",");
		                json.append("\"Title\": \"").append(PhoneFace.get("Title")).append("\",");
		                json.append("\"LineLink\": \"").append(PhoneFace.get("LineLink")).append("\",");
		                json.append("\"Desc\": \"").append(PhoneFace.get("Desc")).append("\",");
		                json.append("\"ShareTemplateKey\": \"").append(InfoTemplate == null ? "" : (String)InfoTemplate.get("Key")).append("\",");
		                json.append("\"poiId\": \"").append(PhoneFace.get("poiId")).append("\"");
		                json.append("}");
		                response.setContentType("text/json; charset=utf-8"); 
		                response.getWriter().print(json.toString());
					}
					if("updatePhoneFace".equals(Type)){
						String ShareID = request.getParameter("ShareID");
						String Name = request.getParameter("Name");
						String ImgUrl = request.getParameter("ImgUrl");
						String Title = request.getParameter("Title");
						String LineLink = request.getParameter("LineLink");
						String Desc = request.getParameter("Desc");
						String ShareTemplateKey = request.getParameter("ShareTemplateKey");
						String poiId = request.getParameter("poiId");
						Map<String,Object> PhoneFace = sqlSession.selectOne("RouterBatis.ShareTaskMapper.SelectShareID", ShareID);
						if (PhoneFace == null) {
							request.setAttribute("Operat","6");
							request.setAttribute("ErrorMessage","指定的分享不存在");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
						}
						boolean isChange = false;
						if(!StringToolkit.isNullOrEmpty(poiId) && !poiId.equals(PhoneFace.get("poiId"))){
							try{
								double[] LatitudeAndLongitude = DianPingConvert.DianPingAddressConvert(poiId);
								PhoneFace.put("poiId", poiId);
								PhoneFace.put("latitude", LatitudeAndLongitude[0]);
								PhoneFace.put("longitude",LatitudeAndLongitude[1]);
								isChange = true;
							}catch(Exception e){
								request.setAttribute("Operat","-4");
								request.setAttribute("ErrorMessage",e.getMessage().replace("\r\n", ""));
								request.setAttribute("ShowBug",properties.getValue("ShowBug"));
								request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
								request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
								return;
							}
						}
						if (!StringToolkit.isNullOrEmpty(Name) && !Name.equals(PhoneFace.get("Name"))) {
							PhoneFace.put("Name", Name);
							isChange = true;
						}
						if (!StringToolkit.isNullOrEmpty(ImgUrl) && !ImgUrl.equals(PhoneFace.get("ImgUrl"))) {
							PhoneFace.put("ImgUrl", ImgUrl);
							isChange = true;
						}
						if (!StringToolkit.isNullOrEmpty(Title) && !Title.equals(PhoneFace.get("Title"))) {
							PhoneFace.put("Title", Title);
							isChange = true;
						}
						if (!StringToolkit.isNullOrEmpty(LineLink) && !Name.equals(PhoneFace.get("LineLink"))) {
							PhoneFace.put("LineLink", LineLink);
							isChange = true;
						}
						if (!StringToolkit.isNullOrEmpty(Desc) && !Desc.equals(PhoneFace.get("Desc"))) {
							PhoneFace.put("Desc", Desc);
							isChange = true;
						}
						if (!ShareTemplateKey.equals(PhoneFace.get("ShareTemplateKey"))) {
							PhoneFace.put("ShareTemplateKey", ShareTemplateKey==null?"":ShareTemplateKey);
							isChange = true;
						}
						if (!StringToolkit.isNullOrEmpty(poiId) && !poiId.equals(PhoneFace.get("poiId"))) {
							PhoneFace.put("poiId", poiId);
							isChange = true;
						}
						if(isChange){
							try {
								sqlSession.update("RouterBatis.ShareTaskMapper.updateShare",PhoneFace);
								sqlSession.commit();
								request.setAttribute("Operat","1");
								request.setAttribute("ErrorMessage","修改成功");
							}catch(Exception e){
								request.setAttribute("Operat","-4");
								request.setAttribute("ErrorMessage",e.getMessage().replace("\r\n", ""));
								request.setAttribute("ShowBug",properties.getValue("ShowBug"));
								request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
								return;
							}
						}else{
							request.setAttribute("Operat","3");
							request.setAttribute("ErrorMessage","数据无变更!");
						}
						request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
						return;
					}
					if("DelPhoneFace".equals(Type)){
						String ShareID = request.getParameter("ShareID");
						Map<String,Object> ShareTask = sqlSession.selectOne("RouterBatis.ShareTaskMapper.SelectShareID", ShareID);
						if(ShareTask == null){
							request.setAttribute("Operat","4");
							request.setAttribute("ErrorMessage","指定的分享内容不存在");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
						}
						try {
							sqlSession.delete("RouterBatis.ShareTaskMapper.deleteShareID", ShareID);
							sqlSession.commit();
							request.setAttribute("Operat", "1");
							request.setAttribute("ErrorMessage", "删除成功");
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
        SqlSession sqlSession = MyBatisFactory.openSession();
        try {
			switch(UserType.intValue()){				
				//商户
				case 4:
					Map<String,Object> bs = sqlSession.selectOne("RouterBatis.BusinesShopMapper.SelectBusinesShopID",Operator);
					if (bs == null) {
						request.setAttribute("ErrorMessage","当前商家不存在或已删除");
						request.setAttribute("Operat","2");
						request.getRequestDispatcher("/AuthHtmlError.jsp").forward(request, response);
						return;
		            }
					
					Map<String,Object> AgentAndShareArgu = new HashMap<String,Object>();
					AgentAndShareArgu.put("BusinesShopID", bs.get("BusinesShopID"));
					AgentAndShareArgu.put("Type", "5");
					List<Map<String,Object>> PhoneFaceShareTaskList = sqlSession.selectList("RouterBatis.InfoTemplateMapper.ByBusinesShopIDAndTypeGetTemplate", AgentAndShareArgu);
					
					List<Map<String,Object>> ShareTaskList = sqlSession.selectList("RouterBatis.ShareTaskMapper.SelectBusinessShopID",Operator);
					request.setAttribute("ShareTaskList", ShareTaskList);
					request.setAttribute("PhoneFaceShareTaskList", PhoneFaceShareTaskList);
		            request.getRequestDispatcher("PhoneFace.jsp").forward(request, response);
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
