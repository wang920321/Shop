package com.itheima.web.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Product;
import com.itheima.service.ProductService;

public class ProductInfoServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pid = request.getParameter("pid");
		String currentPage=request.getParameter("currentPage");
		String cid=request.getParameter("cid");
		ProductService service=new ProductService();
		Product product=service.findProductByPid(pid);
		
		
		
		request.setAttribute("product", product);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("cid", cid);
		//获得客户端携带cookie--获得名字是pids的cookie
		String pids=pid;
		Cookie[] cookies = request.getCookies();
		if(cookies!=null){
			for (Cookie cookie : cookies) {
				if("pids".equals(cookie.getName())){
					pids=cookie.getValue();
					String[] split = pids.split("-");
					List<String> asList = Arrays.asList(split);
					LinkedList<String> list=new LinkedList<String>(asList);
					if(list.contains(pid)){
						list.remove(pid);
					}
					list.addFirst(pid);
					StringBuffer sb=new StringBuffer();
					for (int i = 0; i < list.size()&&i<7; i++) {
						sb.append(list.get(i));
						sb.append("-");
					}
					pids=sb.substring(0, sb.length()-1);
				}
			
			}
		}
		Cookie cook_pids=new Cookie("pids",pids);
		response.addCookie(cook_pids);
		//当转发之前 创建cookie存储pid
		request.getRequestDispatcher("/product_info.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}