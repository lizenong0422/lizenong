package com.whu.web.servlets;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.whu.tools.Validate;


public class ValidateSerialnumServlet extends HttpServlet{

	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("text/html");
		Validate val = new Validate();
		boolean flag = false;
		String serialnum = request.getParameter("serialnum").toString();
		flag = val.validate(serialnum);
		if(flag == true) response.getWriter().write("true");
		else response.getWriter().write("false");
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		this.doGet(request, response);
	}

}
