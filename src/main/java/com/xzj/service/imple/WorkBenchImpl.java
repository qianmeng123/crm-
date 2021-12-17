package com.xzj.service.imple;

import com.github.pagehelper.PageHelper;
import com.sun.javafx.collections.MappingChange;
import com.xzj.MyException.WorkbenchException;
import com.xzj.dao.ActivityAemarkDao;
import com.xzj.dao.WorkerBenchDao;
import com.xzj.domain.Activity;
import com.xzj.domain.ActivityRemark;
import com.xzj.domain.User;
import com.xzj.service.WorkBench;
import com.xzj.util.DateTimeUtil;
import com.xzj.vo.PageVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
public class WorkBenchImpl implements WorkBench {

    @Resource
    private WorkerBenchDao worker;

    @Resource
    private ActivityAemarkDao activityDao;

    @Override
    public List<User> getName() {
        List<User> user = worker.getName();
        return user;
    }

    @Override
    public Map<String, Object> inset(Activity activity) throws WorkbenchException {
        int num = worker.inset(activity);
        System.out.println("num:" + num);
        Map<String, Object> map = new HashMap();

        if (num <= 0) {
            throw new WorkbenchException("没有查询到数据");
        }


        map.put("success", true);
        return map;

    }

    @Override
    public PageVo<Activity> selectPage(Map<String, Object> map) {
               Activity activity=(Activity)map.get("activity");
         Integer pageNo=(Integer)map.get("pageNo");
         Integer pageSize=(Integer)map.get("pageSize");
        int total=worker.selectall(activity);
        System.out.println("total:"+total);
         //做分页处理
        PageHelper.startPage(pageNo,pageSize);

            List<Activity> activityList=worker.pageList(activity);


          PageVo<Activity> pageVo=new PageVo();
          pageVo.setDataList(activityList);
          pageVo.setTotal(total);
        return pageVo;
    }


    @Transactional
    @Override
    public Map<String, Object> delete(String[] ids) {
        int num1=activityDao.delete(ids);
        int num2=worker.delete(ids);
        Map<String,Object> map=new HashMap<>();
        map.put("success",true);
        return map;
    }

    @Override
    public Map<String, Object> edit(String id) {

         //获取所有者
        List<User> users=worker.getName();

        System.out.println(users);

         //获取信息
         Activity activity=worker.selectAllActivity(id);

         Map<String,Object> map=new HashMap<>();
         map.put("ulist",users);
         map.put("a",activity);
        return map;
    }

    @Override
    public Map<String, Object> updata(Activity activity) {


           int num=worker.update(activity);


        Map<String,Object> map=new HashMap<>();
            if (num>=1){
               map.put("success",true);
            }
        return map;
    }

    @Override
    public Activity detailAndActivity(String id) {
         Activity activity=worker.IdAndActivity(id);

         return activity;
    }

    @Override
    public  List<ActivityRemark> activityIdRemark(String activityId) {

        List<ActivityRemark> activityRemark=activityDao.IdActivityAemark(activityId);

        return activityRemark;
    }

    @Override
    public Map<String, Object> deleteRemark(String id) {
         int num=activityDao.deleteIdRemark(id);
        Map<String,Object> map=new HashMap<>();
          if (num>=1){
              map.put("success",true);
              return map;
          }

        map.put("success",false);
        return map;
    }

    @Override
    public Map<String,Object> saveRemark(ActivityRemark activityRemark) {
        int num=activityDao.insertRemark(activityRemark);

          ActivityRemark EndactivityRemark=activityDao.selectIdRemark(activityRemark.getId());

           Map<String,Object> map=new HashMap<>();
           map.put("success",true);
           map.put("activityRemark",activityRemark);
           return map;
    }

    @Override
    public Map<String, Object> editRemark(ActivityRemark ar) {
              int num=activityDao.updateRemark(ar);

        ActivityRemark activityRemark=activityDao.selectEditRemark(ar.getId());
        Map<String, Object> map=new HashMap<>();
                 if (num>=1){
                     map.put("success",true);
                     map.put("a",activityRemark);
                 }


         return map;
    }
}
