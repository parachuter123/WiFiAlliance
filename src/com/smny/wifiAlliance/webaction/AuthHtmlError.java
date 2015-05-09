package com.smny.wifiAlliance.webaction;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet(name="AuthHtmlError",urlPatterns="/AuthHtmlError.htm")
public class AuthHtmlError extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		String ErrorMessage = request.getParameter("ErrorMessage");
		String Operat = request.getParameter("Operat");
		String ShowBug = request.getParameter("ShowBug");
		request.setAttribute("ErrorMessage", ErrorMessage);
		request.setAttribute("Operat", Operat);
		request.setAttribute("ShowBug", ShowBug);
		if(ShowBug.equals("true")){
			response.setContentType("text/html");
			request.getRequestDispatcher("/AuthHtmlError.jsp").forward(request, response);
		}else{
			response.setContentType("text/json");
			request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
	}

}
