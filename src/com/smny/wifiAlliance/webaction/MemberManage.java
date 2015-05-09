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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.ibatis.session.SqlSession;

import smny.MyBatisFactory;
import smny.util.MemberTel;
import smny.util.ServletRefactorMethod;
import smny.util.SetSystemProperty;
/**
 * PACKAGE_NAME： webaction
 * <p/>
 * <会员手机号文件上传>
 * <p/>
 * 作者：  parachuter
 * 创建时间： 14-2-12.
 */
@WebServlet(name = "MemberTel", urlPatterns = "/MemberTel.htm")
public class MemberManage extends HttpServlet{
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html; charset=UTF-8");
    	Map<String,Object> ServletRefactorMethodMap = ServletRefactorMethod.start(request, response);
    	if(ServletRefactorMethodMap==null){
    		return;
    	}
        Integer UserType = (Integer)ServletRefactorMethodMap.get("UserType");
        String Operator = (String)ServletRefactorMethodMap.get("Operator");
        SetSystemProperty properties = (SetSystemProperty) ServletRefactorMethodMap.get("ssp");
		SqlSession sqlSession = MyBatisFactory.openSession();
		
//		String savePath = getServletContext().getRealPath("/upload");
		//存放会员手机号文件路径
		String savePath = properties.getValue("SavePath");
//		System.out.println("保存路径为：" + savePath);
		long FileSpace = Integer.parseInt(properties.getValue("OneFileSpace"));
		File saveDir = new File(savePath);
		if(!saveDir.exists()){
			saveDir.mkdir();
		}
		try{
			switch(UserType.intValue()){
				case 3:
					Map<String,Object> Agent = sqlSession.selectOne("RouterBatis.AgentMapper.SelectAgentID", Operator);
		            if (Agent == null) {
		            	request.setAttribute("Operat","2");
						request.setAttribute("ErrorMessage","指定代理不存在！");
						request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
		                return;
		            }
		            Map<String,Object> map = new HashMap<String,Object>();
		            map.put("BusinesShopID", String.valueOf(Agent.get("AgentID")));
		            
		            // 创建文件上传核心类
		            DiskFileItemFactory factory = new DiskFileItemFactory();  
		            ServletFileUpload sfu = new ServletFileUpload(factory);
		            //设置编码  
		            sfu.setHeaderEncoding("UTF-8"); 
		            // 设置上传的单个文件的最大字节数为20M  
		            sfu.setFileSizeMax(FileSpace);  
		            //设置整个表单的最大字节数为20M  
		            sfu.setSizeMax(FileSpace); 
		            try{
		            	List<FileItem> itemList = sfu.parseRequest(request);
		            	if(itemList==null){
		            		response.getWriter().println("<script type='text/javascript'>alert('请正确选择EXCEL文件！');window.location='/MemberTel.htm';</script>");
							return;
		            	}
//		            	System.out.println(itemList.size());
		            	for (int n=0;n<itemList.size();n++) {
		            		if(itemList.get(n).getFieldName().equals("fileField")){
		            			// 对应表单中的控件的name  
			                    String fieldName = itemList.get(n).getFieldName();
			                    //System.out.println("fieldName:" + fieldName);
			                    // 获得文件大小  
			                    Long size = itemList.get(n).getSize();  
			                    // 获得文件名  
			                    String fileName = itemList.get(n).getName();
			                    String newfile =Operator + "_" +request.getSession().getAttribute("Name") + "_" + String.valueOf(System.currentTimeMillis()) + "_" +  fileName.substring(0, fileName.lastIndexOf(".")) + ".xls";
			                    //System.out.println("文件名："+fileName+"\t大小：" + size + "byte");  
			                    
			                    //将文件保存到指定的路径  
		                        File file = new File(savePath,newfile);  
		                        itemList.get(n).write(file); 
		                        
		                        MemberTel mbt = new MemberTel(savePath,map,newfile);
		                        Thread thread = new Thread(mbt);
		                        thread.start();
		            		}else if(itemList.get(n).getFieldName().equals("ListType")){
		            			String listType =  itemList.get(n).getString();
		            			map.put("ListType", Boolean.parseBoolean(listType));
		            		}else if(itemList.get(n).getFieldName().equals("Remark")){
		            			String Remark = itemList.get(n).getString();
		            			map.put("Remark", Remark);
		            		}else {
		            			continue;
		            		}
		            	}
		            	response.getWriter().println("<script type='text/javascript'>alert('上传成功！！！！');window.location='/MemberTel.htm';</script>");
		            }catch(Exception e){
		            	//e.printStackTrace();
		            	response.getWriter().println("<script type='text/javascript'>alert('请正确选择EXCEL文件！');window.location='/MemberTel.htm';</script>");
		            }
					break;
				default:
					break;
		        }
		}catch(Exception e){
			//e.printStackTrace();
			response.getWriter().println("<script type='text/javascript'>alert('系统异常，请联系管理员！');window.location='/MemberTel.htm';</script>");
		}finally{
			sqlSession.close();
		}
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html; charset=UTF-8");
    	Map<String,Object> ServletRefactorMethodMap = ServletRefactorMethod.start(request, response);
    	if(ServletRefactorMethodMap==null){
    		return;
    	}
        Integer UserType = (Integer)ServletRefactorMethodMap.get("UserType");
        String Operator = (String)ServletRefactorMethodMap.get("Operator");
        SqlSession sqlSession = MyBatisFactory.openSession();
        try{
			switch(UserType.intValue()){
				case 3:
					Map<String,Object> Agent = sqlSession.selectOne("RouterBatis.AgentMapper.SelectAgentID", Operator);
		            if (Agent == null) {
		            	request.setAttribute("Operat","2");
						request.setAttribute("ErrorMessage","指定代理不存在！");
						request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
		                return;
		            }
		            request.getRequestDispatcher("MemberTel.jsp").forward(request, response);
					break;
				default:
					break;
		        }
		}catch(Exception e){
			 response.getWriter().println("<script type='text/javascript'>alert('系统异常，请联系管理员！');window.location='/MemberTel.htm';</script>");
		}finally{
			sqlSession.close();
		}
    }
}
