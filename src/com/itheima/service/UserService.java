/**  

* <p>Title: UserService.java</p>  

* <p>Description: </p>  

* <p>Copyright: Copyright (c) 2017</p>  

* <p>Company: www.baidudu.com</p>  

* @author 172219902  

* @date 2018年3月16日  

* @version 1.0  

*/
package com.itheima.service;

import java.sql.SQLException;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;

/**  

* <p>Title: UserService</p>  

* <p>Description: </p>  

* @author 172219902  

* @date 2018年3月16日  

*/
public class UserService {

	/**  
	
	 * <p>Title: regist</p>  
	
	 * <p>Description: 注册</p>  
	
	 * @param user
	 * @return  
	 * @throws SQLException 
	
	 */ 
	public boolean regist(User user) throws SQLException {
		UserDao dao =new UserDao();
		int row=dao.regist(user);
		if(row>0){
			return true;
		}
		return false;
	}

	/**  
	
	 * <p>Title: activation</p>  
	
	 * <p>Description: 激活</p>  
	
	 * @param activeCode
	 * @return  
	 * @throws SQLException 
	
	 */ 
	public boolean activation(String activeCode) throws SQLException {
		UserDao dao=new UserDao();
		return dao.activation(activeCode)>0?true:false;
	}

	/**  
	
	 * Title: checkUsername  
	
	 * Description: 校验用户名是否存在 
	
	 * @param username
	 * @return  
	
	 */ 
	public boolean checkUsername(String username) {
		UserDao dao=new UserDao();
		Long count=0L;
		try {
			count = dao.checkUsername(username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count>0?true:false;
	}

	/**  
	
	 * Title: login  
	
	 * Description:  
	
	 * @param username
	 * @param password
	 * @return  
	 * @throws SQLException 
	
	 */ 
	public User login(String username, String password) throws SQLException {
		UserDao dao=new UserDao();
		
		return dao.login(username,password);
	}
    
}
