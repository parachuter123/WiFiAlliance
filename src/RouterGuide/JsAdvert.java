package RouterGuide;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.Map;
import java.util.List;


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
@WebServlet(name = "JsAdvert", urlPatterns = "/JsAdvert.do")
public class JsAdvert extends HttpServlet{
		public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				doGet(request,response);
		}
		//·����֤״̬֪ͨ
		public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				req.setCharacterEncoding("UTF-8");
        MacBinding.ParamHandle(req,resp);
        String Mac = (String)req.getAttribute("Mac");
        String IpAddr = (String)req.getAttribute("IpAddr");
        String HardSeq = (String)req.getAttribute("HardSeq");
        String Referer = (String)req.getAttribute("referer");
				
				//Js���
				if (!StringToolkit.isNullOrEmpty(HardSeq,Referer)){
						StringBuilder InfoSB = new StringBuilder(200);
						InfoSB.append("alert('Js���:\r\n Mac:");
						InfoSB.append(Mac);
						InfoSB.append("\r\n IpAddr:");
						InfoSB.append(IpAddr);
						InfoSB.append("\r\n HardSeq:");
						InfoSB.append(HardSeq);
						InfoSB.append("\r\n Error:��Ҫ����Ϊ��!");
						InfoSB.append("');");
						resp.setContentType("text/javascript; charset=UTF-8");
						resp.getWriter().println(InfoSB.toString());
						return;
				}
				SqlSession sqlSession = MyBatisFactory.openSession();
				try {
						//���̼�
						Map<String,Object> Router = sqlSession.selectOne("RouterBatis.WifiRouterMapper.SelectHardSeq",HardSeq);
						if(Router == null){
								StringBuilder InfoSB = new StringBuilder(200);
								InfoSB.append("alert('Js���:\r\n Mac:");
								InfoSB.append(Mac);
								InfoSB.append("\r\n IpAddr:");
								InfoSB.append(IpAddr);
								InfoSB.append("\r\n HardSeq:");
								InfoSB.append(HardSeq);
								InfoSB.append("\r\n Error:HardSeqָʾ��·�ɲ�����!");
								InfoSB.append("');");
								resp.setContentType("text/javascript; charset=UTF-8");
								resp.getWriter().println(InfoSB.toString());
		            return;
						}
						req.setAttribute("Router",Router);
						Map<String,Object> MasterBusines = sqlSession.selectOne("RouterBatis.BusinesShopMapper.SelectBusinesShopID",Router.get("BusinesShopID"));
						if(MasterBusines == null){
								StringBuilder InfoSB = new StringBuilder(200);
								InfoSB.append("alert('Js���:\r\n Mac:");
								InfoSB.append(Mac);
								InfoSB.append("\r\n IpAddr:");
								InfoSB.append(IpAddr);
								InfoSB.append("\r\n HardSeq:");
								InfoSB.append(HardSeq);
								InfoSB.append("\r\n BusinesShopID:");
								InfoSB.append(Router.get("BusinesShopID"));
								InfoSB.append("\r\n Error:BusinesShopIDָʾ���̼Ҳ�����!");
								InfoSB.append("');");
								resp.setContentType("text/javascript; charset=UTF-8");
								resp.getWriter().println(InfoSB.toString());
		            return;
						}
						req.setAttribute("MasterBusines",MasterBusines);
						Map<String,Object> AppEntrance = sqlSession.selectOne("RouterBatis.AppEntranceMapper.SelectEntranceID",Router.get("AppEntranceID"));
						if(AppEntrance == null){
								StringBuilder InfoSB = new StringBuilder(200);
								InfoSB.append("alert('Js���:\r\n Mac:");
								InfoSB.append(Mac);
								InfoSB.append("\r\n IpAddr:");
								InfoSB.append(IpAddr);
								InfoSB.append("\r\n HardSeq:");
								InfoSB.append(HardSeq);
								InfoSB.append("\r\n AppEntranceID:");
								InfoSB.append(Router.get("AppEntranceID"));
								InfoSB.append("\r\n Error:AppEntranceIDָʾ�Ĺ�ע�ӿڲ�����!");
								InfoSB.append("');");
								resp.setContentType("text/javascript; charset=UTF-8");
								resp.getWriter().println(InfoSB.toString());
		            return;
						}
						req.setAttribute("AppEntrance",AppEntrance);
						Map<String,Object> OnlineTerminal = null;
						Map<String,Object> GuestBusines = null;
						if (!StringToolkit.isNullOrEmpty(Mac)){
								List<Map<String,Object>>  OnlineTerminalList = sqlSession.selectList("RouterBatis.OnlineTerminalMapper.SelectMac",Mac);
								if(OnlineTerminalList!=null && OnlineTerminalList.size()>0){
										String BusinesShopID = (String)MasterBusines.get("BusinesShopID");
										for (Map<String,Object> Online:OnlineTerminalList){
												String TerminalID = (String)Online.get("TerminalID");
												Integer Result = (Integer)OnlineTerminal.get("VerificationResult");
												if(Result!=null && Result.intValue()>=1 && Result.intValue()<=5 && Result.intValue()!=13 && !TerminalID.equals(Online.get("TerminalMac")) && !TerminalID.contains(">"+BusinesShopID+":")){
														OnlineTerminal = Online;
														GuestBusines = sqlSession.selectOne("RouterBatis.BusinesShopMapper.SelectBusinesTerminalID",TerminalID);
														break;
												}
										}
								}
						}
						if(GuestBusines==null || OnlineTerminal==null){
								//���̼ҹ�ע��
								
								
								
								
								
								
								
								
								
								
								
						}else{
								//����
								req.setAttribute("Online",OnlineTerminal);
								req.setAttribute("GuestBusines",GuestBusines);
						}
		        Router.put("Mac",Mac);
		        Router.put("Gateway",IpAddr);
		        Router.put("HardSeq",HardSeq);
		        Router.put("Referer",Referer);
		        //Js���
						Map<String,Object> WebController = sqlSession.selectOne("RouterBatis.WebControllerMapper.SelectHardSeqReferer",Router);
						if(WebController != null){
		            req.setAttribute("WebController",WebController);
						}
				} finally {
						sqlSession.close();
				}
        req.getRequestDispatcher("./AuthJs/JsAdvert.jsp").forward(req,resp);
		}
}
