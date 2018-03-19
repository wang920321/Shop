/**  

* Title: ProductDao.java  

* Description:  

* Copyright: Copyright (c) 2017 

* Company: www.baidudu.com 

* @author 172219902  

* @date 2018年3月16日  

* @version 1.0  

*/
package com.itheima.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.domain.Category;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.Product;
import com.itheima.utils.DataSourceUtils;
import com.itheima.vo.OrderItemTo;

/**  

* Title: ProductDao  

* Description:   

* @author 172219902  

* @date 2018年3月16日  

*/
public class ProductDao {

	/**  
	
	 * Title: findHotProductList  
	
	 * Description:  
	
	 * @return  
	 * @throws SQLException 
	
	 */ 
	public List<Product> findHotProductList() throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from product where is_hot=? limit ?,? ";
		List<Product> productHotList = runner.query(sql, new BeanListHandler<Product>(Product.class), 1,0,9);
		return productHotList;
	}

	/**  
	
	 * Title: findNewProductList  
	
	 * Description:  
	
	 * @return  
	 * @throws SQLException 
	
	 */ 
	public List<Product> findNewProductList() throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from product order by pdate desc limit ?,?";
		
		return runner.query(sql, new BeanListHandler<Product>(Product.class), 0,9);
	}

	/**  
	
	 * Title: findAllCategoryList  
	
	 * Description:  
	
	 * @return  
	 * @throws SQLException 
	
	 */ 
	public List<Category> findAllCategoryList() throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from category  ";
		List<Category> categoryList = runner.query(sql, new BeanListHandler<Category>(Category.class));
		return categoryList;
	}

	/**  
	
	 * Title: findTotalCount  
	
	 * Description:  
	
	 * @param cid
	 * @return  
	 * @throws SQLException 
	
	 */ 
	public int findTotalCount(String cid) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select count(*) from product where cid=?";
		Long count = (Long) runner.query(sql, new ScalarHandler(), cid);
		return count.intValue();
	}

	/**  
	
	 * Title: findProductAsPageByCid  
	
	 * Description:  
	
	 * @param currentPage
	 * @param currentCount
	 * @param cid
	 * @return  
	 * @throws SQLException 
	
	 */ 
	public List<Product> findProductAsPageByCid(int index, int currentCount, String cid) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from product where cid=? limit ?,?";
		List<Product> productList = runner.query(sql, new BeanListHandler<Product>(Product.class), cid,index,currentCount);
		return productList;
	}

	/**  
	
	 * Title: findProductByPid  
	
	 * Description:  
	
	 * @param pid
	 * @return  
	 * @throws SQLException 
	
	 */ 
	public Product findProductByPid(String pid) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from product where pid=?";
		Product product = runner.query(sql, new BeanHandler<Product>(Product.class), pid);
		return product;
	}

	/**  
	
	 * Title: addOrder  
	
	 * Description:  
	
	 * @param order  
	 * @throws SQLException 
	
	 */ 
	public void addOrder(Order order) throws SQLException {
		QueryRunner runner=new QueryRunner();
		Connection connection = DataSourceUtils.getConnection();
		String sql="insert into orders values(?,?,?,?,?,?,?,?)";
		runner.update(connection, sql, order.getOid(),order.getOrdertime(),order.getTotal(),order.getState(),order.getAddress(),
				order.getName(),order.getTelephone(),order.getUser().getUid());
		
	}

	/**  
	
	 * Title: addOrderItem  
	
	 * Description:  
	
	 * @param order  
	 * @throws SQLException 
	
	 */ 
	public void addOrderItem(Order order) throws SQLException {
		QueryRunner runner=new QueryRunner();
		Connection connection = DataSourceUtils.getConnection();
		String sql="insert into orderitem values(?,?,?,?,?)";
		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			runner.update(connection, sql,orderItem.getItemid(),orderItem.getCount(),orderItem.getSubtotal(),orderItem.getProduct().getPid(),orderItem.getOrder().getOid() );
		}
		
	}

	/**  
	
	 * Title: updateOrderAdrr  
	
	 * Description:  
	
	 * @param order  
	 * @throws SQLException 
	
	 */ 
	public void updateOrderAdrr(Order order) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="update orders set address=? ,name=?, telephone=? where oid=?";
		runner.update(sql, order.getAddress(),order.getName(),order.getTelephone(),order.getOid());
		
	}

	/**  
	
	 * Title: updateOrderStatu  
	
	 * Description:  
	
	 * @param r6_Order  
	 * @throws SQLException 
	
	 */ 
	public void updateOrderState(String r6_Order) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="update orders set state=? where oid=?";
		runner.update(sql, 1,r6_Order);
	}

	/**  
	
	 * Title: findAllOrders  
	
	 * Description:  
	
	 * @param uid
	 * @return  
	 * @throws SQLException 
	
	 */ 
	public List<Order> findAllOrders(String uid) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from orders where uid=? ";
		List<Order> orderList = runner.query(sql, new BeanListHandler<Order>(Order.class), uid);
		return orderList;
		
	}

	/**  
	
	 * Title: findAllOrderItems  
	
	 * Description:  
	
	 * @param oid
	 * @return  
	 * @throws SQLException 
	
	 */ 
	public List<OrderItemTo> findAllOrderItems(String oid) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from orderitem where oid=? ";
		List<OrderItemTo> orderItemTos = runner.query(sql, new BeanListHandler<OrderItemTo>(OrderItemTo.class), oid);
		return orderItemTos;
		
	}

	/**  
	
	 * Title: findProductByItemId  
	
	 * Description:  
	
	 * @return  
	 * @throws SQLException 
	
	 */ 
	public Product findProductByItemId(String pid) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from product where pid=? ";
		Product product = runner.query(sql, new BeanHandler<Product>(Product.class), pid);
		return product;
	}

}
