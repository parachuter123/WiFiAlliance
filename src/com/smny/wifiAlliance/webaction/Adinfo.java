package com.smny.wifiAlliance.webaction;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.smny.wifiAlliance.entity.logic.InfoTemplateLogic;

import smny.MyBatisFactory;
import smny.util.SetSystemProperty;
import smny.util.StringToolkit;

/**
 * PACKAGE_NAME： webaction
 * <p/>
 * <广告内容>
 * <p/>
 * 作者：  parachuter	
 * 创建时间： 14-12-17.
 */
@WebServlet(name = "Adinfo", urlPatterns = "/Adinfo")
public class Adinfo extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
    }

    //广告内容详细信息
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int modelid = 0;
        String adkey = request.getParameter("adkey");
        adkey = java.net.URLDecoder.decode(adkey,"UTF-8");
		SetSystemProperty properties = new SetSystemProperty();
        if (StringToolkit.isNullOrEmpty(adkey)) {
        	request.setAttribute("Operat","2");
			request.setAttribute("ErrorMessage","adKey不能为空！！");
			request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
            return;
        }
        SqlSession sqlSession = MyBatisFactory.openSession();
        try {
        	Map<String,Object>  InfoTemplate = InfoTemplateLogic.getInfoTemplateByID(sqlSession,adkey);
        	if (InfoTemplate == null) {
        		request.setAttribute("Operat","3");
    			request.setAttribute("ErrorMessage","广告不能为空！！");
    			request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
                return;
            }
        	modelid = Integer.parseInt(InfoTemplate.get("Model").toString());
        	if(modelid==1){
        		String Content =(String) InfoTemplate.get("Content");
        		if(StringToolkit.isNullOrEmpty(Content)){
        			Content="";
        		}else{
        			Content = Content.replace("\"", "\\\"").replace("\n", "\\n");
        		}
        		StringBuilder json = new StringBuilder();
        		json.append("{\"State\":1,\"Message\":\"成功\",");
        		json.append("\"Content\":\"").append(Content).append("\",");
        		json.append("\"adkey\": \"").append(adkey);
        		json.append("\"}");
                response.setContentType("application/json; charset=UTF-8");
                response.getWriter().print(json.toString());
        		return;
        	}else if(modelid==2){        		
        		Map<String,Object> InteriorTemplate = sqlSession.selectOne("RouterBatis.InteriorTemplateMapper.SelectTemplateID", Integer.parseInt(InfoTemplate.get("Content").toString()));
        		StringBuilder json = new StringBuilder();
        		json.append("{\"State\":1,\"Message\":\"成功\",");
        		json.append("\"templateID\": \"").append(InteriorTemplate.get("TemplateID").toString()).append("\",");
        		json.append("\"adkey\": \"").append(InfoTemplate.get("Key").toString()).append("\"");
        		json.append("}");
                response.setContentType("application/json; charset=UTF-8");
                response.getWriter().print(json.toString());
                return;
        	}else if(modelid==3){
        		StringBuilder json = new StringBuilder();
        		json.append("{\"State\":1,\"Message\":\"成功\",");
        		json.append("\"Content\": \"").append(InfoTemplate.get("Content").toString()).append("\"");
        		json.append("}");
                response.setContentType("application/json; charset=UTF-8");
                response.getWriter().print(json.toString());
    			return;
        	}
		} catch (Exception e) {
			request.setAttribute("Operat","-9");
			request.setAttribute("ErrorMessage",e.getMessage().replace("\r\n", ""));
			request.setAttribute("ShowBug",properties.getValue("ShowBug"));
			request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
			request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
		}finally{
			sqlSession.close();
		}
    }
}
