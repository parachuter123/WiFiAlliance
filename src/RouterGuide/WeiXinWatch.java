package RouterGuide;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
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
@WebServlet(name = "WeiXinWatch", urlPatterns = "/AuthJs/WeiXinWatch.js")
public class WeiXinWatch extends HttpServlet{
		public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				doGet(request,response);
		}
		//·���Ƽ���ע
		public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				req.setCharacterEncoding("UTF-8");
        String OriginNumber = req.getParameter("OriginNumber");
        if (StringToolkit.isNullOrEmpty(OriginNumber)){
            resp.setContentType("text/javascript; charset=UTF-8");
            resp.getWriter().println("alert('�Ƽ���ע:OriginNumber����Ϊ�գ�')");
            return;
        }
				SqlSession sqlSession = MyBatisFactory.openSession();
				try {
						//���̼�
            Map<String,Object> AppEntrance = sqlSession.selectOne("RouterBatis.AppEntranceMapper.SelectOriginNumber",OriginNumber);
            if(AppEntrance == null){
		            resp.setContentType("text/javascript; charset=UTF-8");
		            resp.getWriter().println("alert('�Ƽ���ע:OriginNumber:"+OriginNumber+",��ע�ӿڲ����ڣ�')");
            		return;
            }
						//���̼�
            Map<String,Object> MasterBusines = sqlSession.selectOne("RouterBatis.BusinesShopMapper.SelectBusinesShopID",AppEntrance.get("BusinesShopID"));
						//���̼�
            Map<String,Object> MasterAgent = sqlSession.selectOne("RouterBatis.AgentMapper.SelectAgentID",MasterBusines.get("AgentID"));
            req.setAttribute("MasterAgent",MasterAgent);
            req.setAttribute("AppEntrance",AppEntrance);
            req.setAttribute("MasterBusines",MasterBusines);
            String InfoTemplateKey = (String)AppEntrance.get("WatchHandleJs");
            if(!StringToolkit.isNullOrEmpty(InfoTemplateKey)){
            		Map<String,Object> InfoTemplate = sqlSession.selectOne("RouterBatis.InfoTemplateMapper.SelectKey",InfoTemplateKey);
            		if(InfoTemplate != null){
            				Integer Model = (Integer)InfoTemplate.get("Model");
            				Integer Type = (Integer)InfoTemplate.get("Type");
            				if(Type==null || Model==null || Type.intValue()!=2 || Model.intValue()<1 || Model.intValue()>3){
												StringBuilder InfoSB = new StringBuilder("alert('�Ƽ���ע:");
												InfoSB.append("\r\n OriginNumber:");
												if(AppEntrance.get("OriginNumber") != null){
														InfoSB.append(AppEntrance.get("OriginNumber").toString().replace("'","\\'").replace("\r\n","\\r\\n"));
												}
												InfoSB.append("\r\n Key:");
												if(InfoTemplate.get("Key") != null){
														InfoSB.append(InfoTemplate.get("Key").toString().replace("'","\\'").replace("\r\n","\\r\\n"));
												}
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
														req.getRequestDispatcher("/Template/WeiXinWatchCustom.jsp").forward(req, resp);
														break;
													case 2:              //�ڲ�ģ�� 2
														req.getRequestDispatcher(InfoTemplate.get("Content").toString()).forward(req, resp);
														break;
													case 3:              //�ⲿ���� 3
														resp.sendRedirect(InfoTemplate.get("Content").toString());
														break;
												}
            				}else{
            						StringBuilder InfoSB = new StringBuilder("alert('�Ƽ���ע:");
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
										StringBuilder InfoSB = new StringBuilder("alert('�Ƽ���ע:");
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
            req.getRequestDispatcher("/AuthJs/WeiXinWatch.jsp").forward(req, resp);
				} finally {
						sqlSession.close();
				}
        
		}
}
