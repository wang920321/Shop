package com.itheima.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.vo.PageBean;

public class ProductListByCidServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cid = request.getParameter("cid");
		String currentPageStr = request.getParameter("currentPage");
		  int currentCount=12;
		if(currentPageStr==null){
			currentPageStr="1";
		}
		int currentPage=Integer.parseInt(currentPageStr);
		ProductService service =new ProductService();
		PageBean<Product> pageBean=service.findProductAsPageByCid(currentPage,currentCount,cid);
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("cid",cid);
		
		List<Product> historyProductList=new ArrayList<Product>();
		Cookie[] cookies = request.getCookies();
		if(cookies!=null){
			for (Cookie cookie : cookies) {
				if("pids".equals(cookie.getName())){
					String pids=cookie.getValue();
					String[] split = pids.split("-");
					for (String string : split) {
						Product historyProduct = service.findProductByPid(string);
						historyProductList.add(historyProduct);
					}
				}
			}
		}
		request.setAttribute("historyProductList", historyProductList);
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}