/**  

* <p>Title: CommonsUtils.java</p>  

* <p>Description: </p>  

* <p>Copyright: Copyright (c) 2017</p>  

* <p>Company: www.baidudu.com</p>  

* @author 172219902  

* @date 2018年3月16日  

* @version 1.0  

*/
package com.itheima.utils;

import java.util.UUID;

/**  

* <p>Title: CommonsUtils</p>  

* <p>Description: </p>  

* @author 172219902  

* @date 2018年3月16日  

*/
public class CommonsUtils {
    /**  
    
     * <p>Title: getUUID</p>  
    
     * Description: 生成uuid方法  
    
     * @return  UUID.randomUUID().toString();
    
     */ 
    public static String getUUID(){
    	return UUID.randomUUID().toString();
    }
}
