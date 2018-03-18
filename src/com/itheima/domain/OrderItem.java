/**  

* Title: OrderItem.java  

* Description:  

* Copyright: Copyright (c) 2017 

* Company: www.baidudu.com 

* @author 172219902  

* @date 2018年3月18日  

* @version 1.0  

*/
package com.itheima.domain;

/**  

* Title: OrderItem  

* Description:   

* @author 172219902  

* @date 2018年3月18日  

*/
public class OrderItem {
    private String itemid;//订单的id
    private int count;//订单项内商品的购买数量
    private double subtotal;//订单项的小计
    private Product product;//订单项内的商品
    private Order order;//该订单项属于哪个订单
	/**
	 * @return the itemid
	 */
	public String getItemid() {
		return itemid;
	}
	/**
	 * @param itemid the itemid to set
	 */
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
	/**
	 * @return the subtotal
	 */
	public double getSubtotal() {
		return subtotal;
	}
	/**
	 * @param subtotal the subtotal to set
	 */
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	/**
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}
	/**
	 * @param product the product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}
	/**
	 * @return the order
	 */
	public Order getOrder() {
		return order;
	}
	/**
	 * @param order the order to set
	 */
	public void setOrder(Order order) {
		this.order = order;
	}
    
}
