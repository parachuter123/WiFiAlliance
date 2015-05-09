package com.smny.wifiAlliance.webaction;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.ibatis.session.SqlSession;

import smny.MyBatisFactory;
import smny.util.ServletRefactorMethod;
import smny.util.SetSystemProperty;

@WebServlet(name = "UploadFile", urlPatterns = "/UploadFile.htm")
public class UploadFile extends HttpServlet {

	
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
			case 4:
				String uploadfile = properties.getValue("uploadfile");
				/*D:\apache-tomcat-7.0.57\webapps\WiFiAlliance\/upload\1_smny*/
				String busifile = uploadfile + File.separator + Operator;
				File saveDir = new File(busifile);
				if(!saveDir.exists()){
					saveDir.mkdir();
				}
				String fileName = null;
				String newfilename = null;
				String fileId = null;
				boolean isMultipart = ServletFileUpload.isMultipartContent(request);
				DiskFileItemFactory factory = new DiskFileItemFactory();
		        ServletContext servletContext = this.getServletConfig().getServletContext();  
		        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");  
		        factory.setRepository(repository);  
		        
		        // Create a new file upload handler  
		        ServletFileUpload upload = new ServletFileUpload(factory);  
		        //设置编码  
		        upload.setHeaderEncoding("UTF-8"); 
	            // 设置上传的单个文件的最大字节数为20M  
		        upload.setFileSizeMax(500*1024);  
	            //设置整个表单的最大字节数为20M  
		        upload.setSizeMax(500*1024); 
		        
		        try {
					List<FileItem> itemList = upload.parseRequest(request);
					
					for(FileItem item: itemList){
						//System.out.println(item.getName() + "," + item.getFieldName() + "," + item.getContentType());
						if(item.getContentType()!=null){
							if(item.getContentType().split("/")[0].equals("image")){
								long size = item.getSize();
								if(size>500*1024){
									JSONObject obj = new JSONObject(); 
									obj.put("State",3);
									obj.put("Message","文件不能大于500k，请重新上传！！");
									response.setContentType("text/json;charset=utf-8");
					                response.getWriter().print(obj.toString());
									return;
								}
								fileId = item.getFieldName();
								fileName = item.getName();
								newfilename = Operator + "_" +  java.net.URLEncoder.encode(fileName,"UTF-8") ;
								//将文件保存到指定的路径  
		                        File file = new File(busifile,newfilename);
		                        if(file.exists()) {  
		                        	file.delete();  
		                        }  
		                        file.createNewFile(); 
		                        item.write(file);
							}
						}
					}
				} catch (Exception e) {
					//e.printStackTrace();
					JSONObject obj = new JSONObject(); 
					obj.put("State",-5);
					obj.put("Message",e.getMessage().replace("\r\n", ""));
					obj.put("ShowBug",properties.getValue("ShowBug"));
					obj.put("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
					response.setContentType("text/json;charset=utf-8");
	                response.getWriter().print(obj.toString());
	                return;
				}
		        //返回结果  
                JSONObject obj = new JSONObject();  
                obj.put("newfilename", newfilename);  
                System.out.println("newfilename:" + newfilename);
                obj.put("Operator",Operator);
                obj.put("fileId",fileId);
                obj.put("State",1);
                response.setContentType("text/json;charset=utf-8");
                response.getWriter().print(obj.toString());
                
				break;
			default:
				break;
	        }
		} catch (Exception e) {
			//e.printStackTrace();
			JSONObject obj = new JSONObject(); 
			obj.put("State",-5);
			obj.put("Message",e.getMessage().replace("\r\n", ""));
			obj.put("ShowBug",properties.getValue("ShowBug"));
			obj.put("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
			response.setContentType("text/json;charset=utf-8");
            response.getWriter().print(obj.toString());
		}finally{
			sqlSession.close();
		}
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	

}
