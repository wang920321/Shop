/**  

* Title: CartItem.java  

* Description:  

* Copyright: Copyright (c) 2017 

* Company: www.baidudu.com 

* @author 172219902  

* @date 2018年3月18日  

* @version 1.0  

*/
package com.itheima.vo;

import com.itheima.domain.Product;

/**  

* Title: CartItem  

* Description:   

* @author 172219902  

* @date 2018年3月18日  

*/
public class CartItem {
    private Product product;
    private int buyNum;
    private double subtotal;
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
	 * @return the buyNum
	 */
	public int getBuyNum() {
		return buyNum;
	}
	/**
	 * @param buyNum the buyNum to set
	 */
	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
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
    
}
