package com.xzj.controller;


import com.sun.javafx.collections.MappingChange;
import com.xzj.domain.*;
import com.xzj.service.ClueService;
import com.xzj.service.WorkBench;
import com.xzj.util.DateTimeUtil;
import com.xzj.util.UUIDUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class ControllerClue {

    @Resource
     private WorkBench workBench;

    @Resource
    private ClueService clueService;

    @ResponseBody
    @RequestMapping(value = "/user/clue")
    public List<User> getClueUser(){

          List<User> listUser=workBench.getName();

           return listUser;
    }

    /*
     * 线索模块模态窗口的添加操作
     * */
       @ResponseBody
    @RequestMapping(value = "/user/clue/save")
    public Map<String, Object> save(Clue clue, HttpServletRequest request){

           String id=UUIDUtil.getUUID();
           String createTime=DateTimeUtil.getSysTime();
           String createBy=((User)request.getSession().getAttribute("user")).getName();

           clue.setId(id);
           clue.setCreateTime(createTime);
           clue.setCreateBy(createBy);

              boolean flag=clueService.save(clue);

              Map<String,Object> map=new HashMap();
              map.put("success",flag);

              return map;


    }

    //分页查询
    @ResponseBody
    @RequestMapping (value = "/user/clue/activityPage")
    public Map<String, Object> activityPage(Integer pageNo,Integer pageSize){

        System.out.println("分页查询方法执行了");

        Map<String, Object> map=clueService.getAllClue(pageNo,pageSize);


              return map;


    }

    //详细信息页
    @RequestMapping (value = "/user/clue/detail")
    public ModelAndView detail(String id){

        ModelAndView mv=new ModelAndView();

            Clue clue=clueService.getIdClue(id);

            mv.addObject("clue",clue);

            mv.setViewName("/workbench/clue/detail.jsp");

              return mv;


    }

    //市场活动关联
    @ResponseBody
    @RequestMapping (value = "/user/clue/clueAndActivity")
    public List<Activity> clueAndActivity(String clueId){

             List<Activity> activityList=clueService.clueIDGetActivity(clueId);

                return activityList;
    }


    @ResponseBody
    @RequestMapping (value = "/user/clue/delete")
    public Map<String,Object> delete(String id){

        Map<String,Object> map=clueService.deleteCar(id);

                return map;
    }


       //给关联市场活动的模态窗口增添数据
    @ResponseBody
    @RequestMapping (value = "/user/clue/modelActivity")
    public List<Activity> modelActivity(String clueId){

        System.out.println("给关联市场活动的模态窗口增添数据");
       List<Activity> list=clueService.modelActivity(clueId);

                return list;
    }

    //模糊查询市场活动
    @ResponseBody
    @RequestMapping (value = "/user/clue/textActivity")
    public List<Activity> textActivity(String textActivity,String clueId){

        System.out.println("模糊查询市场活动");

       List<Activity> list=clueService.textActivity(textActivity,clueId);

                return list;
    }

    //市场活动关联
    @ResponseBody
    @RequestMapping (value = "/user/clue/addActivityAndClue")
    public  Map<String,Object> addActivityAndClue(HttpServletRequest request){
        System.out.println("市场活动关联模态窗口");
           String[] ids=request.getParameterValues("id");
               String clueId=request.getParameter("clueId");

                Map<String,Object> map=clueService.addActivityAndClue(ids,clueId);


                return map;
    }


    //查询市场活动
    @ResponseBody
    @RequestMapping (value = "/user/clue/activityConventModel")
    public List<Activity> activityConventModel(String name){

           List<Activity> list=clueService.getActivityConventModel(name);

           return list;
    }


    //做转换操作
    @RequestMapping (value = "/user/clue/Conversion")
    public String Conversion(HttpServletRequest request,String clueId, String flag,Tran tran){
              System.out.println("做转换操作");
        System.out.println("tran======"+tran);
               String createBy=((User)request.getSession().getAttribute("user")).getName();
                 tran.setId(UUIDUtil.getUUID());
                 tran.setCreateBy(createBy);
                 tran.setCreateTime(DateTimeUtil.getSysTime());


             boolean flag1=clueService.conversionActivity(clueId,flag,tran,createBy);

               if (flag1) {
                   return "/workbench/clue/index.jsp";
               }


               return  "/workbench/clue/convert.jsp";
    }






}
