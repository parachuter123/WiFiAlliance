package com.smny.wifiAlliance.webaction;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="AuthJsonError",urlPatterns="/AuthJsonError")
public class AuthJsonError {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		String ErrorMessage = request.getParameter("ErrorMessage");
		String Operat = request.getParameter("Operat");
		request.setAttribute("ErrorMessage", ErrorMessage);
		request.setAttribute("Operat", Operat);
		request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
	}
}
