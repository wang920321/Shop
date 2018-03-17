package com.itheima.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.itheima.domain.Category;
import com.itheima.service.ProductService;
import com.itheima.utils.JedisPoolUtils;

import redis.clients.jedis.Jedis;

public class CategoryListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//准备分类数据
		ProductService service=new ProductService();
		//先从缓存中查询categoryList 如果有直接使用，如果没有再从数据库中查询并放入缓存
		//1获得jedis对象 连接redis数据库
		
		Jedis jedis = JedisPoolUtils.getJedis();
		String categoryListjson = jedis.get("categoryListjson");
		//判断categoryListjson是否为空
		if(categoryListjson==null){
			System.out.println("缓存没有数据，从数据库读取");
		    List<Category> categoryList =service.findAllCategoryList();
		    Gson gson=new Gson();
		    categoryListjson= gson.toJson(categoryList);
		    jedis.set("categoryListjson", categoryListjson);
		}
		
		response.setContentType("text/html;charset=UTF-8");	
		response.getWriter().write(categoryListjson);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}