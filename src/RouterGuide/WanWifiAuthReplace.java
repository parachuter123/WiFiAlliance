package RouterGuide;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.Map;
import java.net.URLDecoder;


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
@WebServlet(name = "WanWifiAuthReplace", urlPatterns = "/WanWifiAuthReplace.do")
public class WanWifiAuthReplace extends HttpServlet{
		public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				doGet(request,response);
		}
		//路由认证状态通知
		public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				request.setCharacterEncoding("UTF-8");
        String HardSeq = request.getParameter("HardSeq");
				if(StringToolkit.isNullOrEmpty(HardSeq)){
						response.setContentType("text/html; charset=UTF-8");
						response.getWriter().println("<script type='text/javascript'>alert('参数“HardSeq”不能为空！');</script>");
						return;
				}
				String AuthAaddr = request.getParameter("AuthAaddr");
				if(StringToolkit.isNullOrEmpty(AuthAaddr)){
						response.setContentType("text/html; charset=UTF-8");
						response.getWriter().println("<script type='text/javascript'>alert('参数“AuthAaddr”不能为空！');</script>");
						return;
				}
				
				SqlSession sqlSession = MyBatisFactory.openSession();
				try {
						//AuthAaddr = URLDecoder.decode(AuthAaddr,"UTF-8");
						Map<String,Object> Router = sqlSession.selectOne("RouterBatis.WifiRouterMapper.SelectHardSeq",HardSeq);
						if(Router == null){
								response.setContentType("text/html; charset=UTF-8");
								response.getWriter().println("<script type='text/javascript'>alert('RouterBatis.WifiRouterMapper.SelectHardSeq("+HardSeq+")为空！');</script>");
								return;
						}
						Map<String,Object> AppEntrance = sqlSession.selectOne("RouterBatis.AppEntranceMapper.SelectEntranceID",Router.get("AppEntranceID"));
						if(Router == null){
								response.setContentType("text/html; charset=UTF-8");
								response.getWriter().println("<script type='text/javascript'>alert('RouterBatis.AppEntranceMapper.SelectEntranceID("+Router.get("AppEntranceID")+")为空！');</script>");
								return;
						}
            request.setAttribute("HardSeq", HardSeq);
            request.setAttribute("AuthAaddr", AuthAaddr);
            request.setAttribute("OriginNumber", AppEntrance.get("OriginNumber"));
						request.getRequestDispatcher("./WanWifiAuthReplace.jsp").forward(request,response);
				} finally {
						sqlSession.close();
				}
		}
}
