/**  

* Title: AdminService.java  

* Description:  

* Copyright: Copyright (c) 2017 

* Company: www.baidudu.com 

* @author 172219902  

* @date 2018年3月20日  

* @version 1.0  

*/
package com.itheima.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.itheima.dao.AdminDao;
import com.itheima.domain.Category;
import com.itheima.domain.Order;
import com.itheima.domain.Product;

/**  

* Title: AdminService  

* Description:   

* @author 172219902  

* @date 2018年3月20日  

*/
public class AdminService {

	/**  
	
	 * Title: findAllCategory  
	
	 * Description:  
	
	 * @return  
	
	 */ 
	public List<Category> findAllCategory() {
		AdminDao dao=new AdminDao();
		List<Category> categoryList=null;
		try {
			categoryList = dao.findAllCategory();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return categoryList;
	}

	/**  
	
	 * Title: saveProduct  
	
	 * Description:  
	
	 * @param product  
	
	 */ 
	public void saveProduct(Product product) {
		AdminDao dao=new AdminDao();
		try {
			dao.saveProduct(product);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**  
	
	 * Title: findAllOrders  
	
	 * Description:  
	
	 * @return  
	
	 */ 
	public List<Order> findAllOrders() {
		AdminDao dao=new AdminDao();
		List<Order> orderList=null;
		try {
			orderList = dao.findAllOrders();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orderList;
	}

	/**  
	
	 * Title: findOrderInfoByOid  
	
	 * Description:  
	
	 * @param oid
	 * @return  
	
	 */ 
	public List<Map<String, Object>> findOrderInfoByOid(String oid) {
		AdminDao dao=new AdminDao();
		List<Map<String, Object>> mapList=null;
		try {
			mapList = dao.findOrderInfoByOid(oid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapList;
	}

}
