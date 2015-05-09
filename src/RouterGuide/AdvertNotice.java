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
 * PACKAGE_NAME�� RouterGuide
 * <p/>
 * <Mac��>
 * <p/>
 * ���ߣ�  ����
 * ����ʱ�䣺 14-10-19.
 */
@WebServlet(name = "AdvertNotice", urlPatterns = "/AdvertNotice.do")
public class AdvertNotice extends HttpServlet{
		public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				doGet(request,response);
		}
		//���֪ͨ
		public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				req.setCharacterEncoding("UTF-8");
        MacBinding.ParamHandle(req,resp);
        String HardSeq = (String)req.getAttribute("HardSeq");
        if (StringToolkit.isNullOrEmpty(HardSeq)){
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().println("<script type='text/javascript'>alert('HardSeq����Ϊ��');</script>");
            return;
        }
        String TerminalID = req.getParameter("TerminalID");
        if (StringToolkit.isNullOrEmpty(TerminalID)){
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().println("<script type='text/javascript'>alert('�û�ID����Ϊ��');</script>");
            return;
        }
        String AuthAaddr = req.getParameter("AuthAaddr");
        if (StringToolkit.isNullOrEmpty(AuthAaddr)){
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().println("<script type='text/javascript'>alert('��֤��ַ����Ϊ��');</script>");
            return;
        }
        String InfoTemplateKey = req.getParameter("InfoTemplateKey");
        if (StringToolkit.isNullOrEmpty(InfoTemplateKey)){
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().println("<script type='text/javascript'>alert('���TemplateKey����Ϊ��');window.location='http://'+decodeURIComponent(\""+AuthAaddr+"\")+'/UserAuthorized.do?Type=Log&HardSeq="+HardSeq+"&TerminalID=decodeURIComponent(\""+TerminalID+"\")'</script>");
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
										InfoSB.append("<script type='text/javascript'>alert('���֪ͨ:\r\n Key:");
										InfoSB.append(InfoTemplate.get("Key").toString().replace("'","\\'").replace("\r\n","\\r\\n"));
										
										InfoSB.append("\r\n Name:");
										if(InfoTemplate.get("Name") != null){
												InfoSB.append(InfoTemplate.get("Name").toString().replace("'","\\'").replace("\r\n","\\r\\n"));
										}
										InfoSB.append("\r\n Type:");
										InfoSB.append(Type);
										InfoSB.append("\r\n Model:");
										InfoSB.append(Model);
										InfoSB.append("\r\n Error:����ģʽModel��Type����');window.location='http://'+decodeURIComponent(\"");
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
											case 1:              //ֱ������ 1
												req.getRequestDispatcher("/Template/AdvertNoticeCustom.jsp").forward(req, resp);
												break;
											case 2:              //�ڲ�ģ�� 2
												req.getRequestDispatcher(InfoTemplate.get("Content").toString()).forward(req, resp);
												break;
											case 3:              //�ⲿ���� 3
												resp.sendRedirect(InfoTemplate.get("Content").toString());
												break;
										}
        				}else{
										StringBuilder InfoSB = new StringBuilder(500);
										InfoSB.append("<script type='text/javascript'>alert('���֪ͨ:\r\n Key:");
										InfoSB.append(InfoTemplateKey.replace("'","\\'").replace("\r\n","\\r\\n"));
										InfoSB.append("\r\n Type:");
										InfoSB.append(Type);
										InfoSB.append("\r\n Model:");
										InfoSB.append(Model);	
										InfoSB.append("\r\n Error:ģ��Key����Ϊ��!');window.location='http://'+decodeURIComponent(\"");
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
								InfoSB.append("<script type='text/javascript'>alert('���֪ͨ:\r\n Key:");
								InfoSB.append(InfoTemplateKey.replace("'","\\'").replace("\r\n","\\r\\n"));
								InfoSB.append("\r\n Error:ģ��Key������');window.location='http://'+decodeURIComponent(\"");
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
