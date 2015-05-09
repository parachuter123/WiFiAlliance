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
@WebServlet(name = "BrowseGuide", urlPatterns = "/BrowseGuide.do")
public class BrowseGuide extends HttpServlet{
		//浏览器引导
		protected void service(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
				req.setCharacterEncoding("UTF-8");
        MacBinding.ParamHandle(req,resp);
        String HardSeq = (String)req.getAttribute("HardSeq");
        if (StringToolkit.isNullOrEmpty(HardSeq)){
            resp.setContentType("text/html; charset=UTF-8");
            resp.getWriter().println("<script type='text/javascript'>alert('浏览器引导:HardSeq不能为空！');</script>");
            return;
        }
        
				SqlSession sqlSession = MyBatisFactory.openSession();
				try {
						//本商家
            Map<String,Object> WifiRouter = sqlSession.selectOne("RouterBatis.WifiRouterMapper.SelectHardSeq",HardSeq);
            if(WifiRouter == null){
		            resp.setContentType("text/html; charset=UTF-8");
		            resp.getWriter().println("<script type='text/javascript'>alert('浏览器引导:HardSeq:"+HardSeq+",路由器不存在！');</script>");
            		return;
            }
            req.setAttribute("WifiRouter",WifiRouter);
            String AppEntranceID = (String)WifiRouter.get("AppEntranceID");
            if(!StringToolkit.isNullOrEmpty(AppEntranceID)){
		            Map<String,Object> AppEntrance = sqlSession.selectOne("RouterBatis.AppEntranceMapper.SelectEntranceID",AppEntranceID);
		            if(AppEntrance != null){
				            req.setAttribute("AppEntrance",AppEntrance);
		            }
            }
            String InfoTemplateKey = (String)WifiRouter.get("BrowseGuide");
            if(!StringToolkit.isNullOrEmpty(InfoTemplateKey)){
            		Map<String,Object> InfoTemplate = sqlSession.selectOne("RouterBatis.InfoTemplateMapper.SelectKey",InfoTemplateKey);
            		if(InfoTemplate != null){
            				Integer Model = (Integer)InfoTemplate.get("Model");
            				Integer Type = (Integer)InfoTemplate.get("Type");
            				if(Type==null || Model==null || Type.intValue()!=1 || Model.intValue()<1 || Model.intValue()>3){
												StringBuilder InfoSB = new StringBuilder(300);
												InfoSB.append("alert('浏览器引导:\r\n Key:");
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
												InfoSB.append("\r\n Error:内容模式Model或Type错误')");
												resp.setContentType("text/javascript; charset=UTF-8");
												resp.getWriter().println(InfoSB.toString());
            				}else if(!StringToolkit.isNullOrEmpty((String)InfoTemplate.get("Content"))){
		            				req.setAttribute("InfoTemplate",InfoTemplate);
												switch(Model.intValue()){
													case 1:              //直接内容 1
														req.getRequestDispatcher("/Template/BrowseGuideCustom.jsp").forward(req, resp);
														break;
													case 2:              //内部模板 2
														req.getRequestDispatcher(InfoTemplate.get("Content").toString()).forward(req, resp);
														break;
													case 3:              //外部链接 3
														resp.sendRedirect(InfoTemplate.get("Content").toString());
														break;
												}
            				}else{
												StringBuilder InfoSB = new StringBuilder(300);
												InfoSB.append("alert('浏览器引导:\r\n Key:");
												InfoSB.append(InfoTemplateKey.replace("'","\\'").replace("\r\n","\\r\\n"));
												InfoSB.append("\r\n Name:");
												if(InfoTemplate.get("Name") != null){
														InfoSB.append(InfoTemplate.get("Name").toString().replace("'","\\'").replace("\r\n","\\r\\n"));
												}
												InfoSB.append("\r\n Type:");
												InfoSB.append(Type);
												InfoSB.append("\r\n Model:");
												InfoSB.append(Model);
												InfoSB.append("\r\n Error:模板Key内容为空!');");
												resp.setContentType("text/javascript;charset=UTF-8");
												resp.getWriter().println(InfoSB.toString());
            				}
            		}else{
										StringBuilder InfoSB = new StringBuilder(300);
										InfoSB.append("alert('浏览器引导:\r\n Key:");
										InfoSB.append(InfoTemplateKey.replace("'","\\'").replace("\r\n","\\r\\n"));
										InfoSB.append("\r\n Error:模板Key不存在')");
										resp.setContentType("text/javascript; charset=UTF-8");
										resp.getWriter().println(InfoSB.toString());
            		}
            		return;
            }
				} finally {
						sqlSession.close();
				}
        
        req.getRequestDispatcher("/AuthJs/BrowseGuide.jsp").forward(req, resp);
		}
}
