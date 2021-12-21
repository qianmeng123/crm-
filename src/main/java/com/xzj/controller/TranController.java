package com.xzj.controller;

import com.sun.javafx.collections.MappingChange;
import com.xzj.dao.CustomerDao;
import com.xzj.dao.TranDao;
import com.xzj.domain.Tran;
import com.xzj.domain.TranHistory;
import com.xzj.domain.User;
import com.xzj.service.CustomerService;
import com.xzj.service.LoginUserService;
import com.xzj.service.TranSerice;
import com.xzj.service.WorkBench;
import com.xzj.util.DateTimeUtil;
import com.xzj.util.UUIDUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class TranController {

    @Resource
    private LoginUserService loginUserService;

    @Resource
    private WorkBench workBench;

    @Resource
    private CustomerService customerService;

    @Resource
    private TranSerice tranSerice;

    //給创建页面复制
    @RequestMapping(value = "/user/tran/add")
    public ModelAndView add() {

        System.out.println("创建页面执行");

        List<User> userList = workBench.getName();


        ModelAndView mv = new ModelAndView();

        mv.addObject("userList", userList);

        mv.setViewName("/workbench/transaction/save.jsp");


        return mv;
    }

    //自动补全
    @RequestMapping("/user/transaction/getCustomerName")
    @ResponseBody
    public List<String> getCustomerName(String name) {
        System.out.println("自动补全方法执行");

        List<String> list = customerService.getCustomerName(name);

        return list;
    }

    @RequestMapping("/user/transaction/save")
    public String getCustomerName(HttpServletRequest request, Tran tran) {
        System.out.println("创建保存");

        String createBy = ((User) request.getSession().getAttribute("user")).getName();

        tran.setId(UUIDUtil.getUUID());
        tran.setCreateTime(DateTimeUtil.getSysTime());
        tran.setCreateBy(createBy);

        boolean flag = tranSerice.save(tran);
        if (flag) {
            return "/workbench/transaction/index.jsp";
        }


        return "/workbench/transaction/save.jsp";
    }

    @RequestMapping("/user/transaction/detail")
    public ModelAndView detail(HttpServletRequest request,String id) {

        Tran tran = tranSerice.detail(id);

        //查询全局作用域里阶段对应的可能性
         String stage=tran.getStage();

        String possibility=((Map<String,String>)request.getServletContext().getAttribute("pMap")).get(stage);
        tran.setPossibility(possibility);

        ModelAndView mv = new ModelAndView();
        mv.addObject("tran", tran);
        mv.setViewName("/workbench/transaction/detail.jsp");
        return mv;
    }

    @RequestMapping("/user/transaction/tbodyTranHistroy")
    @ResponseBody
    public List<TranHistory> tbodyTranHistroy(HttpServletRequest request,String tranId) {

            List<TranHistory> tranHistoryList=tranSerice.getTranHistory(tranId);

             Map<String,String> mapStage=(Map<String,String>)request.getServletContext().getAttribute("pMap");

        for (TranHistory tranHistory:
             tranHistoryList) {
              String stage=tranHistory.getStage();
                 String possibility=mapStage.get(stage);
                 tranHistory.setPossibility(possibility);
        }

             return tranHistoryList;
    }

    @RequestMapping("/user/transaction/changeStage")
    @ResponseBody
    public Map<String,Object> changeStage(HttpServletRequest request,Tran tran) {

                      //获取创建人
                 String name=((User)request.getSession().getAttribute("user")).getName();

                 tran.setEditBy(name);

           //修改时间
        tran.setEditTime(DateTimeUtil.getSysTime());

                 Map<String,Object> map=tranSerice.changeStage(tran);


                       String stage=tran.getStage();

                 Map<String,String> pMap=(Map<String,String>)request.getServletContext().getAttribute("pMap");

                            String possibility=pMap.get(stage);

                            tran.setPossibility(possibility);
              map.put("tran",tran);
             return map;
    }

}

