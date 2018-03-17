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
import com.itheima.domain.Product;
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

}
