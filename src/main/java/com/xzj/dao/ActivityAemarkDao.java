package com.xzj.dao;


import com.xzj.domain.ActivityRemark;

import java.util.List;

public interface ActivityAemarkDao {
    int delete(String[] ids);

    List<ActivityRemark> IdActivityAemark(String activityId);

    int deleteIdRemark(String id);

    int insertRemark(ActivityRemark activityRemark);

    ActivityRemark selectIdRemark(String id);

    int updateRemark(ActivityRemark ar);

    ActivityRemark selectEditRemark(String id);
}