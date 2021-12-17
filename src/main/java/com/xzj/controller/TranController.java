package com.xzj.controller;

import com.xzj.dao.CustomerDao;
import com.xzj.domain.User;
import com.xzj.service.CustomerService;
import com.xzj.service.LoginUserService;
import com.xzj.service.TranSerice;
import com.xzj.service.WorkBench;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class TranController {

    @Resource
    private LoginUserService loginUserService;

    @Resource
     private WorkBench workBench;

    @Resource
    private CustomerService customerService;

    //給创建页面复制
    @RequestMapping(value = "/user/tran/add")
    public ModelAndView add(){

        System.out.println("创建页面执行");

                List<User> userList =workBench.getName();



        ModelAndView mv=new ModelAndView();

        mv.addObject("userList",userList);

         mv.setViewName("/workbench/transaction/save.jsp");


         return mv;
    }

     //自动补全
     @RequestMapping("/user/transaction/getCustomerName")
     @ResponseBody
    public List<String>  getCustomerName(String name) {
         System.out.println("自动补全方法执行");

        List<String> list=customerService.getCustomerName(name);

        return list;
    }
}

