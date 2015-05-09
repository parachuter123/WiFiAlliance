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

import net.sf.json.JSONObject;

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
 * 创建时间： 14-12-16.
 */
@WebServlet(name = "AdList", urlPatterns = "/InfoTemplate.htm")
public class InfoTemplate extends HttpServlet {
    
	private static final long serialVersionUID = 1L;

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
						request.setAttribute("Operat","2");
						request.setAttribute("ErrorMessage","当前商家不存在或已删除");
						request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
						return;
					}
					String choose = request.getParameter("Choose");
					if("TemplateAdvancedSet".equals(choose)){
						String TemplateID = request.getParameter("TemplateID");
						String Name = request.getParameter("Name");
						
						Map<String,Object> Template = sqlSession.selectOne("RouterBatis.InteriorTemplateMapper.SelectTemplateID", Integer.parseInt(TemplateID));
						if(Template == null){
							request.setAttribute("ErrorMessage","模板不存在");
							request.setAttribute("Operat","7");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
						}
						//查询当前模板对应的所有的参数
						List<Map<String,Object>> ParaList = sqlSession.selectList("RouterBatis.ParamTemplateMapper.SelectTemplateKey", TemplateID);
						//sqlSession.selectOne("", arg1);
						Iterator<Map<String, Object>> ParaListIterator = ParaList.iterator();
						StringBuilder html = new StringBuilder();
						while(ParaListIterator.hasNext()){
							Map<String,Object> maptemp = ParaListIterator.next();
							Map<String,Object> TemplateParamMap = sqlSession.selectOne("RouterBatis.TemplateParamMapper.SelectTemplateParamByParamID",  Operator + "#" + Name  + ">" + maptemp.get("EleID"));
							if(TemplateParamMap!=null){
								//放置文本信息
								if(Integer.parseInt(TemplateParamMap.get("ParamType").toString())==1){
									html.append("<input type='text' name=" + TemplateParamMap.get("EleID") + " id=" + TemplateParamMap.get("EleID") + " value="+  TemplateParamMap.get("ParamValue") +">").append("<span>" +  TemplateParamMap.get("ParamRemark") +  "</span></br>");
								}
								//放置图片信息
								if(Integer.parseInt(TemplateParamMap.get("ParamType").toString())==2){
									html.append("<img style='width:100px;height:100px;' id='img_" + TemplateParamMap.get("EleID") + "' class='mineimage' src=" + TemplateParamMap.get("ParamValue") + "><input type='file' name=" + TemplateParamMap.get("EleID") + " id=" + TemplateParamMap.get("EleID") + " style='display:none;'>").append("<span>" +  TemplateParamMap.get("ParamRemark") +  "</span></br>");			
								}
								//放置颜色选择器
								if(Integer.parseInt(TemplateParamMap.get("ParamType").toString())==3){
									html.append("<input type='text' name=" + TemplateParamMap.get("EleID") + " id=" + TemplateParamMap.get("EleID") + " class='color' value'#FFFFFF' >").append("<span>" +  TemplateParamMap.get("ParamRemark") +  "</span></br>");
								}
							}else{
								//放置文本信息
								if(Integer.parseInt(maptemp.get("ParamType").toString())==1){
									html.append("<input type='text' name=" + maptemp.get("EleID") + " id=" + maptemp.get("EleID") + " value="+  maptemp.get("ParamValue") +">").append("<span>" +  maptemp.get("ParamRemark") +  "</span></br>");
								}
								//放置图片信息
								if(Integer.parseInt(maptemp.get("ParamType").toString())==2){
									html.append("<img style='width:100px;height:100px;' id='img_" + maptemp.get("EleID") + "' class='mineimage' src=" + maptemp.get("ParamValue") + "><input type='file' name=" + maptemp.get("EleID") + " id=" + maptemp.get("EleID") + " style='display:none;'>").append("<span>" +  maptemp.get("ParamRemark") +  "</span></br>");			
								}
								//放置颜色选择器
								if(Integer.parseInt(maptemp.get("ParamType").toString())==3){
									html.append("<input type='text' name=" + maptemp.get("EleID") + " id=" + maptemp.get("EleID") + " class='color' value'#FFFFFF' >").append("<span>" +  maptemp.get("ParamRemark") +  "</span></br>");
								}
							}
							
						}
						response.setContentType("text/html; charset=utf-8");
						response.getWriter().print(html.toString());
					}
					if("getTemplateList".equals(choose)){
						int TemplateType = Integer.parseInt(request.getParameter("Type"));
						String AgentId = bs.get("AgentID").toString();
						Map<String, Object> map = new HashMap<String,Object>();
						map.put("Type", TemplateType);
						map.put("Owner", AgentId);
						List<Map<String,Object>> TemplateList = sqlSession.selectList("RouterBatis.InteriorTemplateMapper.SelectOwnerAllAndType",map);
						if(TemplateList==null){
							request.setAttribute("Operat","3");
							request.setAttribute("ErrorMessage","模板列表为空！！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
						}
						 ListIterator<Map<String, Object>>  TemplateListIterator = TemplateList.listIterator();
						 StringBuilder html = new StringBuilder();
						 while(TemplateListIterator.hasNext()){
							 Map<String,Object> maptemp = TemplateListIterator.next();
							 html.append("<option value=" + maptemp.get("TemplateID") + ">" + maptemp.get("TemplateName") +"</option>");
						 }
						 response.setContentType("text/html;charset=UTF-8");
						 response.getWriter().print(html.toString());
					}
					if("Content".equals(choose) || "Model".equals(choose) || "Url".equals(choose)){
						String Name = request.getParameter("Name");
						if (StringToolkit.isNullOrEmpty(Name)) {
							request.setAttribute("Operat","3");
							request.setAttribute("ErrorMessage","Name不能为空！！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
						}
						String Type = request.getParameter("Type");
						if (StringToolkit.isNullOrEmpty(Type)) {
							request.setAttribute("Operat","4");
							request.setAttribute("ErrorMessage","Type不能为空！！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
						}
						String Model = request.getParameter("Model");
						if (StringToolkit.isNullOrEmpty(Model)) {
							request.setAttribute("Operat","5");
							request.setAttribute("ErrorMessage","Model不能为空！！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
						}
						String Remark = request.getParameter("Remark");
						if (StringToolkit.isNullOrEmpty(Remark)) {
							request.setAttribute("Operat","6");
							request.setAttribute("ErrorMessage","Remark不能为空！！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
						}
						int type = Integer.parseInt(Type);
						int model = Integer.parseInt(Model);
						Map<String,Object> InfoMap = new HashMap<String,Object>();
						InfoMap.put("BusinesShopID",Operator);
						InfoMap.put("Name",Name);
						InfoMap.put("Type",type);
						InfoMap.put("Remark",Remark);
						InfoMap.put("Model",model);
						
						String Content = request.getParameter("Content");
						
						if("Model".equals(choose)){
							Map<String,Object> InteriorTemplate = sqlSession.selectOne("RouterBatis.InteriorTemplateMapper.SelectTemplateID",Integer.parseInt(Content));
							if(InteriorTemplate == null){
								request.setAttribute("ErrorMessage","模板不存在");
								request.setAttribute("Operat","7");
								request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
								return;
							}
							if(!(InteriorTemplate.get("TemplateID").toString()).equals(Content)){
								request.setAttribute("ErrorMessage","模板类型不符");
								request.setAttribute("Operat","8");
								request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
								return;
							}
							InfoMap.put("TemplateID", Integer.parseInt(InteriorTemplate.get("TemplateID").toString()));
							//存放模板地址
							InfoMap.put("Content", InteriorTemplate.get("TemplatePage"));
						}else if("Url".equals(choose)){
							InfoMap.put("Content", Content);
						}else if("Content".equals(choose)){
							InfoMap.put("Content", Content);
						}
						try {
							sqlSession.insert("RouterBatis.InfoTemplateMapper.insertInfo",InfoMap);
							sqlSession.commit();
							request.setAttribute("Operat","1");
							request.setAttribute("ErrorMessage","新建广告成功！！");
						}catch(NumberFormatException e){
							request.setAttribute("Operat","-3");
							request.setAttribute("ErrorMessage","类型或模式错误,Type:"+type+",Model:"+model);
							request.setAttribute("ShowBug",properties.getValue("ShowBug"));
							request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
						}catch(Exception e){
							request.setAttribute("Operat","-2");
							request.setAttribute("ErrorMessage",e.getMessage().replace("\r\n", ""));
							request.setAttribute("ShowBug",properties.getValue("ShowBug"));
							request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
						}
						request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
			            return;
					}
					//内容修改
					if("updateContent".equals(choose) || "updateModel".equals(choose) || "updateUrl".equals(choose)){
						String InfoTemplatekey = request.getParameter("adkey");
						InfoTemplatekey = java.net.URLDecoder.decode(InfoTemplatekey,"UTF-8");
						if (StringToolkit.isNullOrEmpty(InfoTemplatekey)) {
							request.setAttribute("Operat","9");
							request.setAttribute("ErrorMessage","key不能为空！！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
						}
						Map<String,Object> InfoTemplate = sqlSession.selectOne("RouterBatis.InfoTemplateMapper.SelectKey",InfoTemplatekey);
						InfoTemplate.put("Content", request.getParameter("Content"));
						if("updateModel".equals(choose)){
							String json_param = request.getParameter("Content");
							String json_imgs = request.getParameter("imgsjson");
							JSONObject jsonParam = JSONObject.fromObject( json_param);
							JSONObject jsonImgs = JSONObject.fromObject( json_imgs);
							
							//获得参数模板的所有参数集合
							List<Map<String,Object>> ParamTemplateList = sqlSession.selectList("RouterBatis.ParamTemplateMapper.SelectTemplateKey",InfoTemplate.get("TemplateID").toString());
							Iterator<Map<String,Object>> ParamTemplateListIterator = ParamTemplateList.iterator();
							//type='text'集合
							List<Map<String,Object>> ParamTemplateTypeOneList = new ArrayList<Map<String,Object>>();
							//type='file'集合
							List<Map<String,Object>> ParamTemplateTypeTwoList = new ArrayList<Map<String,Object>>();
							//type='selectbox'集合
							List<Map<String,Object>> ParamTemplateTypeThreeList = new ArrayList<Map<String,Object>>();
							while(ParamTemplateListIterator.hasNext()){
								Map<String,Object> tempmap = ParamTemplateListIterator.next();
								if(Integer.parseInt(tempmap.get("ParamType").toString())==1||Integer.parseInt(tempmap.get("ParamType").toString())==3){
									ParamTemplateTypeOneList.add(tempmap);
								}
								if(Integer.parseInt(tempmap.get("ParamType").toString())==2){
									ParamTemplateTypeTwoList.add(tempmap);
								}
								if(Integer.parseInt(tempmap.get("ParamType").toString())==3){
									ParamTemplateTypeThreeList.add(tempmap);
								}
							}
							//遍历type=text文本
							Iterator<Map<String,Object>> ParamTemplateTypeOneListIterator = ParamTemplateTypeOneList.iterator();
							while(ParamTemplateTypeOneListIterator.hasNext()){
								Map<String,Object> tempmap = new HashMap<String,Object>();
								tempmap = ParamTemplateTypeOneListIterator.next();
								tempmap.put("ParamValue", jsonParam.get(tempmap.get("EleID")));
								tempmap.put("TemplateKey", InfoTemplate.get("Key"));
								try{
									int num = sqlSession.selectOne("RouterBatis.TemplateParamMapper.SelectParamID",InfoTemplate.get("Key") + ">" + tempmap.get("EleID"));
									if(num==0){
										sqlSession.insert("RouterBatis.TemplateParamMapper.insertParam",tempmap);
									}else if(num==1){
										sqlSession.update("RouterBatis.TemplateParamMapper.updateParam",tempmap);
									}else{
										throw new Exception("出现未知错误，模板参数ID查处了两个，这是什么情况？");
									}
									sqlSession.commit();
									request.setAttribute("Operat","1");
									request.setAttribute("ErrorMessage","修改成功！！");
								}catch(Exception e){
									request.setAttribute("Operat","-2");
									request.setAttribute("ErrorMessage",e.getMessage().replace("\r\n", ""));
									request.setAttribute("ShowBug",properties.getValue("ShowBug"));
									request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
								}
							}
							//遍历type=file文本
							Iterator<Map<String,Object>> ParamTemplateTypeTwoListIterator = ParamTemplateTypeTwoList.iterator();
							while(ParamTemplateTypeTwoListIterator.hasNext()){
								Map<String,Object> tempmap = new HashMap<String,Object>();
								tempmap = ParamTemplateTypeTwoListIterator.next();
								tempmap.put("ParamValue", jsonImgs.get("img_" + tempmap.get("EleID")));
								tempmap.put("TemplateKey", InfoTemplate.get("Key"));
								try {
									int num = sqlSession.selectOne("RouterBatis.TemplateParamMapper.SelectParamID",InfoTemplate.get("Key") + ">" + tempmap.get("EleID"));
									if(num==0){
										sqlSession.insert("RouterBatis.TemplateParamMapper.insertParam",tempmap);
									}else if(num==1){
										sqlSession.update("RouterBatis.TemplateParamMapper.updateParam",tempmap);
									}else{
										throw new Exception("出现未知错误，模板参数ID查处了两个，这是什么情况？");
									}
									sqlSession.commit();
									request.setAttribute("Operat","1");
									request.setAttribute("ErrorMessage","修改成功！！");
								} catch (Exception e) {
									request.setAttribute("Operat","-2");
									request.setAttribute("ErrorMessage",e.getMessage().replace("\r\n", ""));
									request.setAttribute("ShowBug",properties.getValue("ShowBug"));
									request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
								}
							}
							
							//获得模板对象
							Map<String,Object> TemplateMap = sqlSession.selectOne("RouterBatis.InteriorTemplateMapper.SelectTemplateID",InfoTemplate.get("TemplateID"));
							if(StringToolkit.isNullOrEmpty(TemplateMap.toString())){
								request.setAttribute("ErrorMessage","模板不存在");
								request.setAttribute("Operat","10");
								request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
								return;
							}
							InfoTemplate.put("Content", TemplateMap.get("TemplatePage"));
							
						}if("updateUrl".equals(choose)){
							InfoTemplate.put("Content", request.getParameter("Content"));
						}
						try {
							sqlSession.update("RouterBatis.InfoTemplateMapper.updateInfo",InfoTemplate);
							sqlSession.commit();
							request.setAttribute("Operat","1");
							request.setAttribute("ErrorMessage","修改成功！！");
						}catch(NumberFormatException e){
							request.setAttribute("Operat","-3");
							request.setAttribute("ErrorMessage","id错误,id:" + InfoTemplatekey);
							request.setAttribute("ShowBug",properties.getValue("ShowBug"));
							request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
						}catch(Exception e){
							request.setAttribute("Operat","-2");
							request.setAttribute("ErrorMessage",e.getMessage().replace("\r\n", ""));
							request.setAttribute("ShowBug",properties.getValue("ShowBug"));
							request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
						}
						request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
						return;
					}
					if("Del".equals(choose)){
						String TemplateKey = request.getParameter("id");
						if (StringToolkit.isNullOrEmpty(TemplateKey)) {
							request.setAttribute("Operat","history.back()");
							request.setAttribute("ErrorMessage","Key不能为空！！");
							request.getRequestDispatcher("/AuthHtmlError.jsp").forward(request, response);
							return;
						}
						try {
							sqlSession.delete("RouterBatis.TemplateParamMapper.deleteTemplateKey", TemplateKey);
							sqlSession.delete("RouterBatis.InfoTemplateMapper.deleteInfoID",TemplateKey);
							sqlSession.commit();
							request.setAttribute("Operat","1");
							request.setAttribute("ErrorMessage","删除成功！！");
						}catch(NumberFormatException e){
							request.setAttribute("Operat","-2");
							request.setAttribute("ErrorMessage","Key错误,id:"+TemplateKey);
							request.setAttribute("ShowBug",properties.getValue("ShowBug"));
							request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
						}catch(Exception e){
							request.setAttribute("Operat","-2");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
						request.setAttribute("Operat","3");
						request.setAttribute("ErrorMessage","当前商家不存在或已删除");
						request.getRequestDispatcher("/AuthHtmlError.jsp").forward(request, response);
						return;
					}
					List<Map<String,Object>> TemplateList = sqlSession.selectList("RouterBatis.InfoTemplateMapper.SelectShopID",Operator);
					request.setAttribute("list", TemplateList);
					request.getRequestDispatcher("/InfoTemplate.jsp").forward(request, response);
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
