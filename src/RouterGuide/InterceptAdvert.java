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
@WebServlet(name = "InterceptAdvert", urlPatterns = "/InterceptAdvert.do")
public class InterceptAdvert extends HttpServlet{
		protected void service(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
				request.setCharacterEncoding("UTF-8");
        
        
        
        
        
        
        
        
        
        
        
        request.getRequestDispatcher("/BrowseGuide.do").forward(request,response);
		}
}
