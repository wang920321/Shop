package com.itheima.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.service.UserService;

public class ActiveServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获得激活码
		String activeCode = request.getParameter("activeCode");
		UserService service=new UserService();
		boolean isActivation=false;
		try {
			isActivation=service.activation(activeCode);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(isActivation){
			//跳转到
			response.sendRedirect(request.getContextPath()+"/login.jsp");
		}else{
			response.sendRedirect(request.getContextPath()+"/activeFail.jsp");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}