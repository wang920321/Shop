package com.itheima.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Category;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;

public class IndexServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//准备热门商品--List<Product>
		ProductService service=new ProductService();
		List<Product> productHotList=service.findHotProductList();
		//准备最新商品List<Product>
		List<Product> productNewList=service.findNewProductList();
		//准备分类数据
		//List<Category> categoryList =service.findAllCategoryList();
		
		
		request.setAttribute("productHotList", productHotList);
		request.setAttribute("productNewList", productNewList);
		//request.setAttribute("categoryList", categoryList);
		
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}