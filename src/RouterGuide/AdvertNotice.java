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
 * <Mac绑定>
 * <p/>
 * 作者：  信念
 * 创建时间： 14-10-19.
 */
@WebServlet(name = "AdvertNotice", urlPatterns = "/AdvertNotice.do")
public class AdvertNotice extends HttpServlet{
		public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				doGet(request,response);
		}
		//广告通知
		public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				req.setCharacterEncoding("UTF-8");
        MacBinding.ParamHandle(req,resp);
        String HardSeq = (String)req.getAttribute("HardSeq");
        if (StringToolkit.isNullOrEmpty(HardSeq)){
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().println("<script type='text/javascript'>alert('HardSeq不能为空');</script>");
            return;
        }
        String TerminalID = req.getParameter("TerminalID");
        if (StringToolkit.isNullOrEmpty(TerminalID)){
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().println("<script type='text/javascript'>alert('用户ID不能为空');</script>");
            return;
        }
        String AuthAaddr = req.getParameter("AuthAaddr");
        if (StringToolkit.isNullOrEmpty(AuthAaddr)){
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().println("<script type='text/javascript'>alert('认证地址不能为空');</script>");
            return;
        }
        String InfoTemplateKey = req.getParameter("InfoTemplateKey");
        if (StringToolkit.isNullOrEmpty(InfoTemplateKey)){
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().println("<script type='text/javascript'>alert('广告TemplateKey不能为空');window.location='http://'+decodeURIComponent(\""+AuthAaddr+"\")+'/UserAuthorized.do?Type=Log&HardSeq="+HardSeq+"&TerminalID=decodeURIComponent(\""+TerminalID+"\")'</script>");
            return;
        }
        
				SqlSession sqlSession = MyBatisFactory.openSession();
				try {
        		Map<String,Object> InfoTemplate = sqlSession.selectOne("RouterBatis.InfoTemplateMapper.SelectKey",InfoTemplateKey);
        		if(InfoTemplate != null){
        				Integer Model = (Integer)InfoTemplate.get("Model");
        				Integer Type = (Integer)InfoTemplate.get("Type");
        				if(Type==null || Model==null || Type.intValue()!=4 || Model.intValue()<1 || Model.intValue()>3){
										StringBuilder InfoSB = new StringBuilder(500);
										InfoSB.append("<script type='text/javascript'>alert('广告通知:\r\n Key:");
										InfoSB.append(InfoTemplate.get("Key").toString().replace("'","\\'").replace("\r\n","\\r\\n"));
										
										InfoSB.append("\r\n Name:");
										if(InfoTemplate.get("Name") != null){
												InfoSB.append(InfoTemplate.get("Name").toString().replace("'","\\'").replace("\r\n","\\r\\n"));
										}
										InfoSB.append("\r\n Type:");
										InfoSB.append(Type);
										InfoSB.append("\r\n Model:");
										InfoSB.append(Model);
										InfoSB.append("\r\n Error:内容模式Model或Type错误');window.location='http://'+decodeURIComponent(\"");
										InfoSB.append(AuthAaddr);
										InfoSB.append("\")+'/UserAuthorized.do?Type=Log&HardSeq=");
										InfoSB.append(HardSeq);
										InfoSB.append("&TerminalID=decodeURIComponent(\"");
										InfoSB.append(TerminalID);
										InfoSB.append("\")'</script>");
										resp.setContentType("text/html;charset=UTF-8");
										resp.getWriter().println(InfoSB.toString());
        				}else if(!StringToolkit.isNullOrEmpty((String)InfoTemplate.get("Content"))){
            				req.setAttribute("InfoTemplate",InfoTemplate);
										switch(Model.intValue()){
											case 1:              //直接内容 1
												req.getRequestDispatcher("/Template/AdvertNoticeCustom.jsp").forward(req, resp);
												break;
											case 2:              //内部模板 2
												req.getRequestDispatcher(InfoTemplate.get("Content").toString()).forward(req, resp);
												break;
											case 3:              //外部链接 3
												resp.sendRedirect(InfoTemplate.get("Content").toString());
												break;
										}
        				}else{
										StringBuilder InfoSB = new StringBuilder(500);
										InfoSB.append("<script type='text/javascript'>alert('广告通知:\r\n Key:");
										InfoSB.append(InfoTemplateKey.replace("'","\\'").replace("\r\n","\\r\\n"));
										InfoSB.append("\r\n Type:");
										InfoSB.append(Type);
										InfoSB.append("\r\n Model:");
										InfoSB.append(Model);	
										InfoSB.append("\r\n Error:模板Key内容为空!');window.location='http://'+decodeURIComponent(\"");
										InfoSB.append(AuthAaddr);
										InfoSB.append("\")+'/UserAuthorized.do?Type=Log&HardSeq=");
										InfoSB.append(HardSeq);
										InfoSB.append("&TerminalID=decodeURIComponent(\"");
										InfoSB.append(TerminalID);
										InfoSB.append("\")'</script>");
										resp.setContentType("text/html;charset=UTF-8");
										resp.getWriter().println(InfoSB.toString());
        				}
        		}else{
								StringBuilder InfoSB = new StringBuilder(500);
								InfoSB.append("<script type='text/javascript'>alert('广告通知:\r\n Key:");
								InfoSB.append(InfoTemplateKey.replace("'","\\'").replace("\r\n","\\r\\n"));
								InfoSB.append("\r\n Error:模板Key不存在');window.location='http://'+decodeURIComponent(\"");
								InfoSB.append(AuthAaddr);
								InfoSB.append("\")+'/UserAuthorized.do?Type=Log&HardSeq=");
								InfoSB.append(HardSeq);
								InfoSB.append("&TerminalID=decodeURIComponent(\"");
								InfoSB.append(TerminalID);
								InfoSB.append("\")'</script>");
								resp.setContentType("text/html;charset=UTF-8");
								resp.getWriter().println(InfoSB.toString());
        		}
				} finally {
						sqlSession.close();
				}
		}
}
