package com.smny.wifiAlliance.webaction;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.ibatis.session.SqlSession;

import smny.MyBatisFactory;
import smny.util.ServletRefactorMethod;
/**
 * PACKAGE_NAME： webaction
 * <p/>
 * <系统图片上传>
 * <p/>
 * 作者：  parachuter
 * 创建时间： 14-2-12.
 */
@WebServlet(name = "FileUpload", urlPatterns = "/FileUpload")
public class FileUpload extends HttpServlet {

    private ServletFileUpload uploader = null;
    @Override
    public void init() throws ServletException {
        DiskFileItemFactory fileFactory = new DiskFileItemFactory();
        File filesDir = (File) getServletContext().getAttribute("FILES_DIR_FILE");
        fileFactory.setRepository(filesDir);
        this.uploader = new ServletFileUpload(fileFactory);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	if (!ServletFileUpload.isMultipartContent(request)) {
            throw new ServletException("Content type is not multipart/form-data");
        }
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
				case 4:
					Map<String,Object> bs = sqlSession.selectOne("RouterBatis.BusinesShopMapper.SelectBusinesShopID", Operator);
		            if (bs == null) {
		            	request.setAttribute("Operat","2");
						request.setAttribute("ErrorMessage","指定商家不存在！");
						request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
		                return;
		            }

		            PrintWriter out = response.getWriter();
		            
	                List<FileItem> fileItemsList = uploader.parseRequest(request);
	                Iterator<FileItem> fileItemsIterator = fileItemsList.iterator();
	                while (fileItemsIterator.hasNext()) {
	                    FileItem fileItem = fileItemsIterator.next();/*
	                    System.out.println("FieldName=" + fileItem.getFieldName());
	                    System.out.println("FileName=" + fileItem.getName());
	                    System.out.println("ContentType=" + fileItem.getContentType());
	                    System.out.println("Size in bytes=" + fileItem.getSize());*/
	                    //必须为图片格式
	                    if(!fileItem.getContentType().contains("png")
	                            && !fileItem.getContentType().contains("jpeg")
	                            && !fileItem.getContentType().contains("gif")){
	                        out.write("图片格式错误!");
	                        return;
	                    }
	                    //大小不得超过500K
	                    if (fileItem.getSize() > 500000) {
	                        out.write("文件大小不得超过500K!");
	                        return;
	                    }
	                    //System.out.println(request.getServletContext().getContextPath());
	                    File file = new File(this.getServletConfig().getServletContext().getRealPath("/") + "\\StaticPage\\UpLoad\\" +
	                            bs.get("BusinesShopID") + File.separator + fileItem.getName());

	                    if (!file.getParentFile().exists()) {
	                        if (!file.getParentFile().mkdirs()) {
	                            out.write("创建目标文件所在目录失败 :" + file.getAbsolutePath());
	                            return;
	                        }
	                    }
	                    fileItem.write(file);
	                    out.write(this.getServletConfig().getServletContext().getRealPath("/") + "\\StaticPage\\UpLoad\\" + bs.get("BusinesShopID") + "\\" + fileItem.getName());
	                }
					break;
				default:
					break;
		        }
		}catch(Exception e){
			
		}finally{
			sqlSession.close();
		}
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        doPost(request, response);
    }
}
