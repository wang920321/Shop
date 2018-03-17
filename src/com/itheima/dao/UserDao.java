/**  

* <p>Title: UserDao.java</p>  

* <p>Description: </p>  

* <p>Copyright: Copyright (c) 2017</p>  

* <p>Company: www.baidudu.com</p>  

* @author 172219902  

* @date 2018年3月16日  

* @version 1.0  

*/
package com.itheima.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.domain.User;
import com.itheima.utils.DataSourceUtils;

/**  

* <p>Title: UserDao</p>  

* <p>Description: </p>  

* @author 172219902  

* @date 2018年3月16日  

*/
public class UserDao {

	/**  
	
	 * <p>Title: regist</p>  
	
	 * <p>Description:注册 </p>  
	
	 * @param user
	 * @return  
	 * @throws SQLException 
	
	 */ 
	public int regist(User user) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="insert into user values(?,?,?,?,?,?,?,?,?,?)";
		int row = runner.update(sql, user.getUid(),user.getUsername(),user.getPassword(),
				user.getName(),user.getEmail(),user.getTelephone(),
				user.getBirthday(),user.getSex(),user.getState(),user.getCode());
		return row;
	}

	/**  
	
	 * <p>Title: activation</p>  
	
	 * <p>Description:激活 </p>  
	
	 * @param activeCode
	 * @return  
	 * @throws SQLException 
	
	 */ 
	public int activation(String activeCode) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="update user set state=? where code=?";
		int row = runner.update(sql,1,activeCode);
		return row;
	}

	/**  
	
	 * Title: checkUsername  
	
	 * Description:  
	
	 * @param username
	 * @return  
	 * @throws SQLException 
	
	 */ 
	public Long checkUsername(String username) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select count(*) from user where username=?";
		Long query = (Long) runner.query(sql, new ScalarHandler(), username);
		return query;
	}

}
