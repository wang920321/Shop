package com.itheima.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.utils.CommonsUtils;
import com.itheima.utils.MailUtils;

public class UserServlet extends BaseServlet {
	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		UserService service =new UserService();
		User user=null;
		try {
			user=service.login(username,password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(user!=null){
			String autoLogin = request.getParameter("autoLogin");
			if("true".equals(autoLogin)){
				//要自动登录
				//创建存储用户名的cookie
				Cookie cookie_username = new Cookie("cookie_username",user.getUsername());
				cookie_username.setMaxAge(10*60);
				//创建存储密码的cookie
				Cookie cookie_password = new Cookie("cookie_password",user.getPassword());
				cookie_password.setMaxAge(10*60);

				response.addCookie(cookie_username);
				response.addCookie(cookie_password);
		    }
			session.setAttribute("user", user);
			
			response.sendRedirect(request.getContextPath());
		}else{
			request.setAttribute("loginError", "用户名或者密码错误");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
	
	public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	public void checkUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//获得用户名
		String username = request.getParameter("username");
		UserService service=new UserService();
		boolean isExist=service.checkUsername(username);
		String json="{\"isExist\":"+isExist+"}";
		response.getWriter().write(json);
	
		
	}
	public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//获得表单数据
		Map<String, String[]> properties = request.getParameterMap();
		User user=new User();
		try {
			//自己制定一个类型转换器（将String转成Date）
			ConvertUtils.register(new Converter() {
				
				@Override
				public Object convert(Class clazz, Object value) {
					//将String转成Date
					SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
					Date parse=null;
					try {
						parse = format.parse(value.toString());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return parse;
				}
			}, Date.class);
			//映射封装
			BeanUtils.populate(user, properties);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//	private String uid;
		user.setUid(CommonsUtils.getUUID());
		// private String telephone;
		user.setTelephone(null);
        //private int state;//是否激活
		user.setState(0);
		//private String code;//激活码
		String activeCode=CommonsUtils.getUUID();
		user.setCode(activeCode);
		//将user传递给service层
		UserService service=new UserService();
		boolean isRegistSeccess=false;
		try {
			isRegistSeccess= service.regist(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(isRegistSeccess){
			//发送激活邮件
			String emailMsg="恭喜你注册成功，清点击下面的链接进行激活账户<a href='http://localhost:8080/Shop/user?method=active&activeCode="+activeCode+"'>"
			            +"http://localhost:8080/Shop/user?method=active&activeCode="+activeCode+"</a>";
			try {
				MailUtils.sendMail(user.getEmail(), emailMsg);
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//跳转到成功页面
			response.sendRedirect(request.getContextPath()+"/registerSuccess.jsp");
		}else{
			//跳转到失败的提示页面
			response.sendRedirect(request.getContextPath()+"/registerFail.jsp");
		}
	}
}