package com.xzj.service.imple;

import com.xzj.dao.CustomerDao;
import com.xzj.service.CustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
 public class CustomerServiceImpl implements CustomerService {

    @Resource
   private CustomerDao customerDao;

    @Override
    public List<String> getCustomerName(String name) {

              List<String> list=customerDao.getNameList(name);


        return list;
    }
}
