package com.xzj.service.imple;

import com.xzj.dao.DisTypeDao;
import com.xzj.dao.DisValueDao;
import com.xzj.domain.DisType;
import com.xzj.domain.DisValue;
import com.xzj.service.DisService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


import javax.servlet.ServletContextEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DisServiceImpl implements DisService {


    @Override
    public Map<String, List<DisValue>> getAll(ServletContextEvent servletContextEvent) {
        System.out.println("进入到service");
        WebApplicationContext context= WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());

       DisTypeDao disTypeDao=context.getBean(DisTypeDao.class);
                DisValueDao disValueDao=context.getBean(DisValueDao.class);


               List<DisType> listType=disTypeDao.getType();



             Map<String,List<DisValue>> map=new HashMap<>();

             for (DisType dis:listType) {
             List<DisValue> disTypeList=disValueDao.getValue(dis.getCode());
                map.put(dis.getCode(), disTypeList);
        }

       return map;
}
}
