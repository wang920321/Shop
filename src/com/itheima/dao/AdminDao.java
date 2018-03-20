/**  

* Title: AdminDao.java  

* Description:  

* Copyright: Copyright (c) 2017 

* Company: www.baidudu.com 

* @author 172219902  

* @date 2018年3月20日  

* @version 1.0  

*/
package com.itheima.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.itheima.domain.Category;
import com.itheima.domain.Order;
import com.itheima.domain.Product;
import com.itheima.utils.DataSourceUtils;

/**  

* Title: AdminDao  

* Description:   

* @author 172219902  

* @date 2018年3月20日  

*/
public class AdminDao {

	/**  
	
	 * Title: findAllCategory  
	
	 * Description:  
	
	 * @return  
	 * @throws SQLException 
	
	 */ 
	public List<Category> findAllCategory() throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from category";
		List<Category> categoryList = runner.query(sql, new BeanListHandler<Category>(Category.class));
		return categoryList;
	}

	/**  
	
	 * Title: saveProduct  
	
	 * Description:  
	
	 * @param product  
	 * @throws SQLException 
	
	 */ 
	public void saveProduct(Product product) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="insert into product values(?,?,?,?,?,?,?,?,?,?)";
		runner.update(sql, product.getPid(),product.getPname(),product.getMarket_price(),product.getShop_price(),
				product.getPimage(),product.getPdate(),product.getIs_hot(),product.getPdesc(),
				product.getPflag(),product.getCategory().getCid());
	}

	/**  
	
	 * Title: findAllOrders  
	
	 * Description:  
	
	 * @return  
	 * @throws SQLException 
	
	 */ 
	public List<Order> findAllOrders() throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from orders";
		List<Order> orderList = runner.query(sql, new BeanListHandler<Order>(Order.class));
		return orderList;
	}

	/**  
	
	 * Title: findOrderInfoByOid  
	
	 * Description:  
	
	 * @return  
	 * @throws SQLException 
	
	 */ 
	public List<Map<String, Object>> findOrderInfoByOid(String oid) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="SELECT p.pimage,p.pname,p.shop_price,i.count,i.subtotal "
				+ "FROM orderitem i,product p "
				+ "WHERE i.pid=p.pid AND i.oid=?";
		List<Map<String, Object>> mapList = runner.query(sql, new MapListHandler(), oid);
		return mapList;
	}

}
