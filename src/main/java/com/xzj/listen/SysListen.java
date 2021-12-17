package com.xzj.listen;

import com.xzj.domain.DisValue;
import com.xzj.service.DisService;
import com.xzj.service.imple.DisServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;


//检测全局作用域创建和销毁时刻
public class SysListen implements ServletContextListener {


    @Override
    //servletContextEvent 是指监听的对象 现在就是上下文作用域对象
    public void contextInitialized(ServletContextEvent servletContextEvent) {

       DisService disService=new DisServiceImpl();

        System.out.println("全局作用域");

        Map<String, List<DisValue>> map=disService.getAll(servletContextEvent);

             //获取key
               Set<String> set =map.keySet();

        ServletContext appliCation =servletContextEvent.getServletContext();

        for (String key:
             set) {
                                          //通过key取出value
            appliCation.setAttribute(key,map.get(key));

        }


             //解析处理Stage2Possibility文件
             ResourceBundle rb=ResourceBundle.getBundle("conf/Stage2Possibility");

            //获取到所有的key值
               Enumeration<String> keys=rb.getKeys();


               Map<String,String> pMap=new HashMap<>();
               //如果有key，就一直循环，一直遍历完毕
               while (keys.hasMoreElements()){

                   //获取每一个key
                   String key=keys.nextElement();

                   //通过key获取value
                   String value=rb.getString(key);

                   pMap.put(key,value);
               }

                //把封装好的Map放进全局作用域
               appliCation.setAttribute("pMap",pMap);




    }










    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
