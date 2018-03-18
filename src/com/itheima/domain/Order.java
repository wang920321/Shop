/**  

* Title: Order.java  

* Description:  

* Copyright: Copyright (c) 2017 

* Company: www.baidudu.com 

* @author 172219902  

* @date 2018年3月18日  

* @version 1.0  

*/
package com.itheima.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**  

* Title: Order  

* Description:   

* @author 172219902  

* @date 2018年3月18日  

*/
public class Order {
    private String oid;//该订单的订单号
    private Date ordertime;//下单时间
    private double total;//该订单的总金额
    private int state;//订单的状态   1代表付款  0代码未付款
    private String address;  //收货人信息的地址
    private String name;   //收货人姓名
    private String telephone;//收货人电话号码
    private User user; //该订单属于哪个用户
    
    //该订单中有多少订单项
    List<OrderItem> orderItems=new ArrayList<OrderItem>();
	/**
	 * @return the orderItems
	 */
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	/**
	 * @param orderItems the orderItems to set
	 */
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	/**
	 * @return the oid
	 */
	public String getOid() {
		return oid;
	}
	/**
	 * @param oid the oid to set
	 */
	public void setOid(String oid) {
		this.oid = oid;
	}
	/**
	 * @return the ordertime
	 */
	public Date getOrdertime() {
		return ordertime;
	}
	/**
	 * @param ordertime the ordertime to set
	 */
	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
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
	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}
	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
    
}
