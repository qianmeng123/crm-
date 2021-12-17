package com.xzj.controller;


import com.sun.javafx.collections.MappingChange;
import com.sun.org.apache.regexp.internal.RE;
import com.xzj.MyException.WorkbenchException;
import com.xzj.domain.Activity;
import com.xzj.domain.ActivityRemark;
import com.xzj.domain.User;
import com.xzj.service.WorkBench;
import com.xzj.util.DateTimeUtil;
import com.xzj.util.UUIDUtil;
import com.xzj.vo.PageVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user/workbench")
public class MyWorkerBenchController {

    @Resource
     private WorkBench workBench;

     @ResponseBody
    @RequestMapping("/getname")
    public List<User> getNmae() {
         List<User> user= workBench.getName();

         return user;
}

    @ResponseBody
    @RequestMapping("/save")
    public Map<String,Object> save(Activity activity, HttpServletRequest request) throws WorkbenchException{
         //id
          String id= UUIDUtil.getUUID();
          //创建时间
          String createTimr= DateTimeUtil.getSysTime();
          //创建人
          String by=((User)request.getSession().getAttribute("user")).getName();
          activity.setId(id);
          activity.setCreateTime(createTimr);
          activity.setCreateBy(by);

          Map<String,Object> map=workBench.inset(activity);
          //如果没有异常接着往下走

         return map;
    }

    @ResponseBody
    @RequestMapping("/pageList")
    public  PageVo<Activity> pageList(Activity activity, Integer pageNo, Integer pageSize) {
        Map<String,Object> map=new HashMap();
         map.put("activity",activity);
         map.put("pageNo",pageNo);
         map.put("pageSize",pageSize);
          PageVo<Activity> pageVo =workBench.selectPage(map);
         return pageVo;
    }

    //删除
    @ResponseBody
    @RequestMapping("/delete")
    public Map<String,Object> delete(HttpServletRequest request) {
        System.out.println("删除操作");

        //获取name值相同的value(这里的value值是id)
            String[] ids =request.getParameterValues("id");

             Map<String,Object> map=workBench.delete(ids);
        return map;
    }

    //给修改的模态窗口赋值
    @ResponseBody
    @RequestMapping("/edit")
    public Map<String,Object> edit(String id) {

         Map<String,Object> map=workBench.edit(id);

         return map;

    }

     //修改市场活动模态窗口的值
    @ResponseBody
    @RequestMapping("/update")
    public Map<String, Object> update(Activity activity,HttpServletRequest request) {

        //获取当前时间，存入到修改时间当中
        activity.setEditTime(DateTimeUtil.getSysTime());
        //修改人
        String editBy=((User)request.getSession().getAttribute("user")).getName();
         activity.setEditBy(editBy);

        Map<String,Object> map=workBench.updata(activity);

        return map;

    }

    @RequestMapping(value = "/det")
    public ModelAndView detail(String id,HttpServletRequest request){
        System.out.println(id);
        Activity activity=workBench.detailAndActivity(id);

        ModelAndView mv=new ModelAndView();

        mv.addObject("activity",activity);
        mv.setViewName("/workbench/activity/detail.jsp");
        return mv;
         }


         @RequestMapping(value = "/remark")
         @ResponseBody
        public List<ActivityRemark> remark(String activityId){

              List<ActivityRemark> ar=workBench.activityIdRemark(activityId);

              return ar;
         }

         @RequestMapping(value="/deleteRemark")
         @ResponseBody
        public Map<String,Object> deleteRemark(String id){
             Map<String,Object> map=workBench.deleteRemark(id);
              return map;
         }

           @RequestMapping(value="/saveRemark")
           @ResponseBody
        public Map<String,Object> saveRemark(String noteContent,String activityId,HttpServletRequest request){
         ActivityRemark ar=new ActivityRemark();
         ar.setId(UUIDUtil.getUUID());
         ar.setNoteContent(noteContent);
         ar.setCreateTime(DateTimeUtil.getSysTime());
         String name=((User)request.getSession().getAttribute("user")).getName();
         ar.setCreateBy(name);
         ar.setEditFlag("0");
         ar.setActivityId(activityId);
               Map<String,Object> map=workBench.saveRemark(ar);
              return map;
         }

         @RequestMapping(value="/editRemark")
         @ResponseBody
        public Map<String,Object> saveRemark(ActivityRemark ar,HttpServletRequest request){
         String name=((User)request.getSession().getAttribute("user")).getName();
         ar.setEditBy(name);
         ar.setEditTime(DateTimeUtil.getSysTime());
         ar.setEditFlag("1");
               Map<String,Object> map=workBench.editRemark(ar);
              return map;
         }

}