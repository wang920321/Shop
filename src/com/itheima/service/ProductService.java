/**  

* Title: ProductService.java  

* Description:  

* Copyright: Copyright (c) 2017 

* Company: www.baidudu.com 

* @author 172219902  

* @date 2018年3月16日  

* @version 1.0  

*/
package com.itheima.service;

import java.sql.SQLException;
import java.util.List;

import com.itheima.dao.ProductDao;
import com.itheima.domain.Category;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.Product;
import com.itheima.utils.DataSourceUtils;
import com.itheima.vo.OrderItemTo;
import com.itheima.vo.PageBean;

/**  

* Title: ProductService  

* Description:   

* @author 172219902  

* @date 2018年3月16日  

*/
public class ProductService {

	/**  
	
	 * Title: findHotProductList  
	
	 * Description:  
	
	 * @return  
	
	 */ 
	public List<Product> findHotProductList() {
		ProductDao dao=new ProductDao();
		List<Product> productHotList=null;
		try {
			productHotList = dao.findHotProductList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productHotList;
	}

	/**  
	
	 * Title: findNewProductList  
	
	 * Description:  
	
	 * @return  
	
	 */ 
	public List<Product> findNewProductList() {
		ProductDao dao=new ProductDao();
		List<Product> productHotList=null;
		try {
			productHotList = dao.findNewProductList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productHotList;
	}

	/**  
	
	 * Title: findAllCategoryList  
	
	 * Description:  
	
	 * @return  
	
	 */ 
	public List<Category> findAllCategoryList() {
		ProductDao dao=new ProductDao();
		List<Category> categoryList=null;
		try {
			categoryList = dao.findAllCategoryList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return categoryList;
	}

	/**  
	
	 * Title: findProductByCid  
	
	 * Description:  
	
	 * @param cid
	 * @return  
	
	 */ 
	public PageBean<Product> findProductAsPageByCid(int currentPage,int currentCount,String cid) {
		PageBean<Product> pageBean=new PageBean<Product>();
		pageBean.setCurrentPage(currentPage);
		pageBean.setCurrentCount(currentCount);
		
		ProductDao dao=new ProductDao();
		int totalCount=0;
		try {
			totalCount=dao.findTotalCount(cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pageBean.setTotalCount(totalCount);
		int totalPage=(int) Math.ceil(1.0*totalCount/currentCount);
		pageBean.setTotalPage(totalPage);
		int index=(currentPage-1)*currentCount;
		List<Product> productList=null;
		try {
			productList=dao.findProductAsPageByCid(index,currentCount,cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pageBean.setProductList(productList);
		
		return pageBean;
	}

	/**  
	
	 * Title: findProductByPid  
	
	 * Description:  
	
	 * @param pid
	 * @return  
	
	 */ 
	public Product findProductByPid(String pid) {
		ProductDao dao=new ProductDao();
		Product product=null;
		try {
			product = dao.findProductByPid(pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return product;
	}

	/**  
	
	 * Title: submitOrder  
	
	 * Description:  
	
	 * @param order  
	
	 */ 
	public void submitOrder(Order order) {
		ProductDao dao=new ProductDao();
		try {
			DataSourceUtils.startTransaction();
			dao.addOrder(order);
			dao.addOrderItem(order);
		} catch (SQLException e) {
			try {
				DataSourceUtils.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try {
				DataSourceUtils.commitAndRelease();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	/**  
	
	 * Title: updateOrderAdrr  
	
	 * Description:  
	
	 * @param order  
	
	 */ 
	public void updateOrderAdrr(Order order) {
		ProductDao dao=new ProductDao();
		try {
			dao.updateOrderAdrr(order);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**  
	
	 * Title: updateOrderStatu  
	
	 * Description:  
	
	 * @param r6_Order  
	
	 */ 
	public void updateOrderState(String r6_Order) {
		ProductDao dao=new ProductDao();
		try {
			dao.updateOrderState(r6_Order);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**  
	
	 * Title: findAllOrders  
	
	 * Description:  
	
	 * @param uid
	 * @return  
	
	 */ 
	public List<Order> findAllOrders(String uid) {
		ProductDao dao=new ProductDao();
		List<Order> orderList=null;
		try {
			orderList = dao.findAllOrders(uid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orderList;
	}

	/**  
	
	 * Title: findAllOrderItems  
	
	 * Description:  
	
	 * @param oid
	 * @return  
	
	 */ 
	public List<OrderItemTo> findAllOrderItems(String oid) {
		ProductDao dao=new ProductDao();
		List<OrderItemTo> orderItemTos=null;
		try {
			orderItemTos = dao.findAllOrderItems(oid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orderItemTos;
	}

	/**  
	
	 * Title: findProductByItemId  
	
	 * Description:  
	
	 * @param itemid
	 * @return  
	
	 */ 
	public Product findProductByItemId(String pid) {
		ProductDao dao=new ProductDao();
		Product prodcut=null;
		try {
			prodcut = dao.findProductByItemId(pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prodcut;
	}

}
