/**  

* Title: Cart.java  

* Description:  

* Copyright: Copyright (c) 2017 

* Company: www.baidudu.com 

* @author 172219902  

* @date 2018年3月18日  

* @version 1.0  

*/
package com.itheima.vo;

import java.util.HashMap;
import java.util.Map;

/**  

* Title: Cart  

* Description:   

* @author 172219902  

* @date 2018年3月18日  

*/
public class Cart {
    private Map<String,CartItem> cartItems=new HashMap<String,CartItem>();
    private double total;
	/**
	 * @return the cartItems
	 */
	public Map<String, CartItem> getCartItems() {
		return cartItems;
	}
	/**
	 * @param cartItems the cartItems to set
	 */
	public void setCartItems(Map<String, CartItem> cartItems) {
		this.cartItems = cartItems;
	}
	/**
	 * @return the total
	 */
	public double getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(double total) {
		this.total = total;
	}
    
}
