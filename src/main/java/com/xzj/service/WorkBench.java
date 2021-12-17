package com.xzj.service;

import com.xzj.MyException.WorkbenchException;
import com.xzj.domain.Activity;
import com.xzj.domain.ActivityRemark;
import com.xzj.domain.User;
import com.xzj.vo.PageVo;

import java.util.List;
import java.util.Map;

public interface WorkBench {
    List<User> getName();
    Map<String, Object> inset(Activity activity) throws WorkbenchException;
    PageVo<Activity> selectPage(Map<String,Object> map);

    Map<String, Object> delete(String[] ids);

    Map<String, Object> edit(String id);

    Map<String, Object> updata(Activity activity);

    Activity detailAndActivity(String id);

    List<ActivityRemark> activityIdRemark(String activityId);

    Map<String, Object> deleteRemark(String id);

    Map<String,Object> saveRemark(ActivityRemark activityRemark);

    Map<String, Object> editRemark(ActivityRemark ar);
}
