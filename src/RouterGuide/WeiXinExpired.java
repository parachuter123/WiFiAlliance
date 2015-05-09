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
@WebServlet(name = "WeiXinExpired", urlPatterns = "/AuthJs/WeiXinExpired.js")
public class WeiXinExpired extends HttpServlet{
		public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				doGet(request,response);
		}
		//��ע���ڴ���
		public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				req.setCharacterEncoding("UTF-8");
        String TerminalID = req.getParameter("TerminalID");
        if (StringToolkit.isNullOrEmpty(TerminalID)){
            resp.setContentType("text/javascript; charset=UTF-8");
            resp.getWriter().println("alert('���ڴ���:TerminalID����Ϊ�գ�')");
            return;
        }
				SqlSession sqlSession = MyBatisFactory.openSession();
				try {
						//���̼�
            Map<String,Object> AppEntrance = sqlSession.selectOne("RouterBatis.AppEntranceMapper.SelectEntranceTerminalID",TerminalID);
            if(AppEntrance == null){
		            resp.setContentType("text/javascript; charset=UTF-8");
		            resp.getWriter().println("alert('���ڴ���:TerminalID:"+TerminalID+",��ע�ӿڲ����ڣ�')");
            		return;
            }
            req.setAttribute("AppEntrance",AppEntrance);
            String InfoTemplateKey = (String)AppEntrance.get("ExpiredHandleJs");
            if(!StringToolkit.isNullOrEmpty(InfoTemplateKey)){
            		Map<String,Object> InfoTemplate = sqlSession.selectOne("RouterBatis.InfoTemplateMapper.SelectKey",InfoTemplateKey);
            		if(InfoTemplate != null){
            				Integer Model = (Integer)InfoTemplate.get("Model");
            				Integer Type = (Integer)InfoTemplate.get("Type");
            				if(Type==null || Model==null || Type.intValue()!=3 || Model.intValue()<1 || Model.intValue()>3){
												StringBuilder InfoSB = new StringBuilder("alert('���ڴ���:");
												InfoSB.append("\r\n OriginNumber:");
												if(AppEntrance.get("OriginNumber") != null){
														InfoSB.append(AppEntrance.get("OriginNumber").toString().replace("'","\\'").replace("\r\n","\\r\\n"));
												}
												InfoSB.append("\r\n Key:");
												InfoSB.append(InfoTemplate.get("Key").toString().replace("'","\\'").replace("\r\n","\\r\\n"));
												InfoSB.append("\r\n Name:");
												if(InfoTemplate.get("Name") != null){
														InfoSB.append(InfoTemplate.get("Name").toString().replace("'","\\'").replace("\r\n","\\r\\n"));
												}
												InfoSB.append("\r\n Type:");
												InfoSB.append(Type);
												InfoSB.append("\r\n Model:");
												InfoSB.append(Model);
												InfoSB.append("\r\n Error:����ģʽModel��Type����')");
												resp.setContentType("text/javascript; charset=UTF-8");
												resp.getWriter().println(InfoSB.toString());
            				}else if(!StringToolkit.isNullOrEmpty((String)InfoTemplate.get("Content"))){
		            				req.setAttribute("InfoTemplate",InfoTemplate);
												switch(Model.intValue()){
													case 1:              //ֱ������ 1
														req.getRequestDispatcher("/Template/WeiXinExpiredCustom.jsp").forward(req, resp);
														break;
													case 2:              //�ڲ�ģ�� 2
														req.getRequestDispatcher(InfoTemplate.get("Content").toString()).forward(req, resp);
														break;
													case 3:              //�ⲿ���� 3
														resp.sendRedirect(InfoTemplate.get("Content").toString());
														break;
												}
            				}else{
            						StringBuilder InfoSB = new StringBuilder("alert('���ڴ���:");
												InfoSB.append("\r\n OriginNumber:");
												if(AppEntrance.get("OriginNumber") != null){
														InfoSB.append(AppEntrance.get("OriginNumber").toString().replace("'","\\'").replace("\r\n","\\r\\n"));
												}
												InfoSB.append("\r\n Key:");
												InfoSB.append(InfoTemplateKey.replace("'","\\'").replace("\r\n","\\r\\n"));
												InfoSB.append("\r\n Name:");
												if(InfoTemplate.get("Name") != null){
														InfoSB.append(InfoTemplate.get("Name").toString().replace("'","\\'").replace("\r\n","\\r\\n"));
												}
												InfoSB.append("\r\n Type:");
												InfoSB.append(Type);
												InfoSB.append("\r\n Model:");
												InfoSB.append(Model);
												InfoSB.append("\r\n Error:ģ��Key����Ϊ��!');");
												resp.setContentType("text/javascript;charset=UTF-8");
												resp.getWriter().println(InfoSB.toString());
            				}
            		}else{
										StringBuilder InfoSB = new StringBuilder("alert(���ڴ���:'");
										InfoSB.append("\r\n OriginNumber:");
										if(AppEntrance.get("OriginNumber") != null){
												InfoSB.append(AppEntrance.get("OriginNumber").toString().replace("'","\\'").replace("\r\n","\\r\\n"));
										}
										InfoSB.append("\r\n Key:");
										InfoSB.append(InfoTemplateKey.replace("'","\\'").replace("\r\n","\\r\\n"));
										InfoSB.append("\r\n Error:ģ��Key������')");
										resp.setContentType("text/javascript; charset=UTF-8");
										resp.getWriter().println(InfoSB.toString());
            		}
            		return;
            }
				} finally {
						sqlSession.close();
				}
        req.getRequestDispatcher("/AuthJs/WeiXinExpired.jsp").forward(req, resp);
		}
}
