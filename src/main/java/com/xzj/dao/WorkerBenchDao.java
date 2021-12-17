package com.xzj.dao;


import com.xzj.domain.Activity;
import com.xzj.domain.User;

import java.util.List;

public interface WorkerBenchDao {
    List<User> getName();
    int inset(Activity activity);
    List<Activity> pageList(Activity activity);
    int selectall(Activity activity);
      int delete(String[] ids);
      Activity selectAllActivity(String id);
    int update(Activity activity);
    Activity IdAndActivity(String id);
}
