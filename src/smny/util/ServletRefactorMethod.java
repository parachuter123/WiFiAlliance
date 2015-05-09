package smny.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ServletRefactorMethod  extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	public static Map<String,Object>start(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		request.setCharacterEncoding("UTF-8");
        Map<String, Object> map = null;
        SetSystemProperty ssp = null;
		try {
			HttpSession session = request.getSession();
			Integer UserType = (Integer)session.getAttribute("UserType");
			String Operator = (String)session.getAttribute("Operator");
			ssp = new SetSystemProperty();
			if(UserType!=1){
				if (UserType==null || StringToolkit.isNullOrEmpty(Operator)) {
					request.setAttribute("Operat","10");
					request.setAttribute("ErrorMessage","µÇÂ½Ê§Ð§");
					request.getRequestDispatcher("/AuthHtmlError.jsp").forward(request, response);
					return null;
				}
			}else{
				if (UserType==null) {
					request.setAttribute("Operat","10");
					request.setAttribute("ErrorMessage","µÇÂ½Ê§Ð§");
					request.getRequestDispatcher("/AuthHtmlError.jsp").forward(request, response);
					return null;
				}
			}
			map = new HashMap<String,Object>();
			map.put("UserType", UserType);
			map.put("Operator", Operator);
			map.put("ssp", ssp);
			
		} catch (Exception e) {
			response.sendRedirect("login.html");
		}
		return map;
	}
}
