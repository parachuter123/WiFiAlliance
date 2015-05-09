package RouterGuide;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.Map;


import smny.MyBatisFactory;
import smny.util.StringToolkit;

import org.apache.ibatis.session.SqlSession;  

/**
 * PACKAGE_NAME： RouterGuide
 * <p/>
 * <分享任务>
 * <p/>
 * 作者：  信念
 * 创建时间： 14-10-19.
 */
@WebServlet(name = "ShareTask", urlPatterns = "/ShareTask.do")
public class ShareTask extends HttpServlet{
		public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				doGet(request,response);
		}
		//分享任务
		public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				req.setCharacterEncoding("UTF-8");
        String ShareID = req.getParameter("ShareID");
        if (StringToolkit.isNullOrEmpty(ShareID)){
            resp.setContentType("text/javascript;charset=UTF-8");
            resp.getWriter().println("alert('分享任务ShareID不能为空');");
            return;
        }
        
				SqlSession sqlSession = MyBatisFactory.openSession();
				try {
        		Map<String,Object> shareTask = sqlSession.selectOne("RouterBatis.ShareTaskMapper.SelectShareID",ShareID);
            if(shareTask == null){
								StringBuilder InfoSB = new StringBuilder("alert('分享任务:");
								InfoSB.append("\r\n ShareID:");
								InfoSB.append(ShareID);
								InfoSB.append("\r\n Error:分享任务不存在!');");
								resp.setContentType("text/javascript;charset=UTF-8");
								resp.getWriter().println(InfoSB.toString());
            		return;
            }
            req.setAttribute("ShareTask",shareTask);
        		String InfoTemplateKey = (String)shareTask.get("ShareTemplateKey");
            if(!StringToolkit.isNullOrEmpty(InfoTemplateKey)){
		        		Map<String,Object> InfoTemplate = sqlSession.selectOne("RouterBatis.InfoTemplateMapper.SelectKey",InfoTemplateKey);
		        		if(InfoTemplate != null){
		        				Integer Model = (Integer)InfoTemplate.get("Model");
		        				Integer Type = (Integer)InfoTemplate.get("Type");
		        				if(Type==null || Model==null || Type.intValue()!=5 || Model.intValue()<1 || Model.intValue()>3){
												StringBuilder InfoSB = new StringBuilder("alert('分享任务:");
												InfoSB.append("\r\n ShareID:");
												InfoSB.append(ShareID);
												InfoSB.append("\r\n ShareName:");
												if(shareTask.get("Name") != null){
														InfoSB.append(shareTask.get("Name").toString().replace("'","\\'").replace("\r\n","\\r\\n"));
												}
												InfoSB.append("\r\n ShareImgUrl:");
												if(shareTask.get("ImgUrl") != null){
														InfoSB.append(shareTask.get("ImgUrl").toString().replace("'","\\'").replace("\r\n","\\r\\n"));
												}
												InfoSB.append("\r\n ShareImgWidth:");
												InfoSB.append(shareTask.get("ImgWidth"));
												InfoSB.append("\r\n ShareImgHeight:");
												InfoSB.append(shareTask.get("ImgHeight"));
												InfoSB.append("\r\n ShareLineLink:");
												if(shareTask.get("LineLink") != null){
														InfoSB.append(shareTask.get("LineLink").toString().replace("'","\\'").replace("\r\n","\\r\\n"));
												}
												InfoSB.append("\r\n ShareDesc:");
												if(shareTask.get("Desc") != null){
														InfoSB.append(shareTask.get("Desc").toString().replace("'","\\'").replace("\r\n","\\r\\n"));
												}
												InfoSB.append("\r\n ShareTitle:");
												if(shareTask.get("Title") != null){
														InfoSB.append(shareTask.get("Title").toString().replace("'","\\'").replace("\r\n","\\r\\n"));
												}
												InfoSB.append("\r\n ShareTemplateKey:");
												InfoSB.append(InfoTemplate.get("Key").toString().replace("'","\\'").replace("\r\n","\\r\\n"));
												
												InfoSB.append("\r\n TemplateName:");
												if(InfoTemplate.get("Name") != null){
														InfoSB.append(InfoTemplate.get("Name").toString().replace("'","\\'").replace("\r\n","\\r\\n"));
												}
												InfoSB.append("\r\n TemplateType:");
												InfoSB.append(Type);
												InfoSB.append("\r\n TemplateModel:");
												InfoSB.append(Model);
												InfoSB.append("\r\n Error:内容模式Model或Type错误');");
												resp.setContentType("text/javascript;charset=UTF-8");
												resp.getWriter().println(InfoSB.toString());
		        				}else if(!StringToolkit.isNullOrEmpty((String)InfoTemplate.get("Content"))){
		            				req.setAttribute("InfoTemplate",InfoTemplate);
												switch(Model.intValue()){
													case 1:              //直接内容 1
														req.getRequestDispatcher("/Template/ShareTaskCustom.jsp").forward(req, resp);
														break;
													case 2:              //内部模板 2
														req.getRequestDispatcher(InfoTemplate.get("Content").toString()).forward(req, resp);
														break;
													case 3:              //外部链接 3
														resp.sendRedirect(InfoTemplate.get("Content").toString());
														break;
												}
		        				}else{
												StringBuilder InfoSB = new StringBuilder("alert('分享任务:");
												InfoSB.append("\r\n ShareID:");
												InfoSB.append(ShareID);
												InfoSB.append("\r\n ShareName:");
												if(shareTask.get("Name") != null){
														InfoSB.append(shareTask.get("Name").toString().replace("'","\\'").replace("\r\n","\\r\\n"));
												}
												InfoSB.append("\r\n ShareImgUrl:");
												if(shareTask.get("ImgUrl") != null){
														InfoSB.append(shareTask.get("ImgUrl").toString().replace("'","\\'").replace("\r\n","\\r\\n"));
												}
												InfoSB.append("\r\n ShareImgWidth:");
												InfoSB.append(shareTask.get("ImgWidth"));
												InfoSB.append("\r\n ShareImgHeight:");
												InfoSB.append(shareTask.get("ImgHeight"));
												InfoSB.append("\r\n ShareLineLink:");
												if(shareTask.get("LineLink") != null){
														InfoSB.append(shareTask.get("LineLink").toString().replace("'","\\'").replace("\r\n","\\r\\n"));
												}
												InfoSB.append("\r\n ShareDesc:");
												if(shareTask.get("Desc") != null){
														InfoSB.append(shareTask.get("Desc").toString().replace("'","\\'").replace("\r\n","\\r\\n"));
												}
												InfoSB.append("\r\n ShareTitle:");
												if(shareTask.get("Title") != null){
														InfoSB.append(shareTask.get("Title").toString().replace("'","\\'").replace("\r\n","\\r\\n"));
												}
												InfoSB.append("\r\n ShareTemplateKey:");
												InfoSB.append(InfoTemplate.get("Key").toString().replace("'","\\'").replace("\r\n","\\r\\n"));
												
												InfoSB.append("\r\n TemplateName:");
												if(InfoTemplate.get("Name") != null){
														InfoSB.append(InfoTemplate.get("Name").toString().replace("'","\\'").replace("\r\n","\\r\\n"));
												}
												InfoSB.append("\r\n TemplateType:");
												InfoSB.append(Type);
												InfoSB.append("\r\n TemplateModel:");
												InfoSB.append(Model);
												InfoSB.append("\r\n Error:模板Key内容为空!');");
												resp.setContentType("text/javascript;charset=UTF-8");
												resp.getWriter().println(InfoSB.toString());
		        				}
		        		}else{
										StringBuilder InfoSB = new StringBuilder("alert('分享任务:");
										InfoSB.append("\r\n ShareID:");
										InfoSB.append(ShareID);
										InfoSB.append("\r\n ShareName:");
										if(shareTask.get("Name") != null){
												InfoSB.append(shareTask.get("Name").toString().replace("'","\\'").replace("\r\n","\\r\\n"));
										}
										InfoSB.append("\r\n ShareImgUrl:");
										if(shareTask.get("ImgUrl") != null){
												InfoSB.append(shareTask.get("ImgUrl").toString().replace("'","\\'").replace("\r\n","\\r\\n"));
										}
										InfoSB.append("\r\n ShareImgWidth:");
										InfoSB.append(shareTask.get("ImgWidth"));
										InfoSB.append("\r\n ShareImgHeight:");
										InfoSB.append(shareTask.get("ImgHeight"));
										InfoSB.append("\r\n ShareLineLink:");
										if(shareTask.get("LineLink") != null){
												InfoSB.append(shareTask.get("LineLink").toString().replace("'","\\'").replace("\r\n","\\r\\n"));
										}
										InfoSB.append("\r\n ShareDesc:");
										if(shareTask.get("Desc") != null){
												InfoSB.append(shareTask.get("Desc").toString().replace("'","\\'").replace("\r\n","\\r\\n"));
										}
										InfoSB.append("\r\n ShareTitle:");
										if(shareTask.get("Title") != null){
												InfoSB.append(shareTask.get("Title").toString().replace("'","\\'").replace("\r\n","\\r\\n"));
										}
										InfoSB.append("\r\n ShareTemplateKey:");
										InfoSB.append(InfoTemplate.get("Key").toString().replace("'","\\'").replace("\r\n","\\r\\n"));
										
										InfoSB.append("\r\n Error:模板Key不存在!');");
										resp.setContentType("text/javascript;charset=UTF-8");
										resp.getWriter().println(InfoSB.toString());
		        		}
		        		return;
            }
        		req.getRequestDispatcher("./AuthJs/ShareTask.jsp").forward(req,resp);
				} finally {
						sqlSession.close();
				}
		}
}
