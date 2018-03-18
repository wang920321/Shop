package com.itheima.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.Gson;
import com.itheima.domain.Category;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.Product;
import com.itheima.domain.User;
import com.itheima.service.ProductService;
import com.itheima.utils.CommonsUtils;
import com.itheima.utils.JedisPoolUtils;
import com.itheima.vo.Cart;
import com.itheima.vo.CartItem;
import com.itheima.vo.PageBean;

import redis.clients.jedis.Jedis;

public class ProductServlet extends BaseServlet {
	
	public void confirmOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String[]> properties = request.getParameterMap();
		Order order=new Order();
		try {
			BeanUtils.populate(order, properties);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ProductService service=new ProductService();
		service.updateOrderAdrr(order);
	}
	
	
	public void submitOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		//1判断是否登录
		User user = (User) session.getAttribute("user");
		if(user==null){
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		//目的：封装order
		Order order=new Order();
		order.setOid(CommonsUtils.getUUID());
		order.setOrdertime(new Date());
		Cart cart = (Cart) session.getAttribute("cart");
		order.setTotal(cart.getTotal());
		order.setState(0);
		order.setAddress(null);
		order.setName(null);
		order.setTelephone(null);
		order.setUser(user);
		
		//order.setOrderItems(orderItems);
		Map<String, CartItem> cartItems = cart.getCartItems();
		for(Map.Entry<String, CartItem> entry : cartItems.entrySet()){
			CartItem cartItem = entry.getValue();
			OrderItem orderItem=new OrderItem();
			orderItem.setItemid(CommonsUtils.getUUID());
			orderItem.setCount(cartItem.getBuyNum());
			orderItem.setSubtotal(cartItem.getSubtotal());
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setOrder(order);
			
			order.getOrderItems().add(orderItem);
		}
		
		ProductService service=new ProductService();
		service.submitOrder(order);
		
		session.setAttribute("order", order);
		
		 response.sendRedirect(request.getContextPath()+"/order_info.jsp");
		
	}

	public void clearCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		//删除session中cart
		session.removeAttribute("cart");
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
	}
	public void delCartItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pid = request.getParameter("pid");
		
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		
		if(cart!=null){
			Map<String, CartItem> cartItems = cart.getCartItems();
			//修改总价
			cart.setTotal(cart.getTotal()-cartItems.get(pid).getSubtotal());
			//删除
			cartItems.remove(pid);
			cart.setCartItems(cartItems);
		}
		session.setAttribute("cart", cart);
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
		
	}
	public void addCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		ProductService service=new ProductService();
		String method = request.getParameter("method");
		String pid = request.getParameter("pid");
		int buyNum =Integer.parseInt(request.getParameter("buyNum"));
		//根据pid查询商品信息product
		Product product = service.findProductByPid(pid);
		//获取session中cart
		Cart cart = (Cart) session.getAttribute("cart");
		if(cart==null){
			cart=new Cart();
		}
		
		CartItem cartItem=new CartItem();
		int oldBuyNum=0;
		//判断添加的商品的是否在购物车存在
		if(cart.getCartItems().containsKey(pid)){
			oldBuyNum=cart.getCartItems().get(pid).getBuyNum();
			//添加的件数和存在的件数相加
			buyNum=buyNum+oldBuyNum;
			
		}
		//封装cartItem对象
		cartItem.setProduct(product);
		cartItem.setBuyNum(buyNum);
		double subTotal=buyNum*product.getShop_price();
		cartItem.setSubtotal(subTotal);
		//封装cart对象
		cart.getCartItems().put(pid, cartItem);
		System.out.println(cart.getTotal());
		//由于subTotal是原有的加新添加的总钱，所以需要减去原有的钱
		double total=subTotal+cart.getTotal()-oldBuyNum*product.getShop_price();
		cart.setTotal(total);
		
		session.setAttribute("cart", cart);
		
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
		
	}
	public void categoryList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

	public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

	public void productInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	public void productListByCid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
}