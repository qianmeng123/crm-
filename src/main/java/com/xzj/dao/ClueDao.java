package com.xzj.dao;


import com.xzj.domain.Activity;
import com.xzj.domain.Clue;
import com.xzj.domain.ClueActivityRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClueDao {


    int save(Clue clue);

    List<Clue> getAllClue();

    int getCount();

    Clue getIdClue(String id);

    List<Activity> getActivityList(String clueId);

    int deleteCar(String id);

    List<Activity> modelActivityClueId(String clueId);

    List<Activity> getActivityListText(@Param("textActivity") String textActivity,@Param("clueId") String clueId);

    int addActivityAndClueRemark(ClueActivityRelation car);

    List<Activity> getActivityConventModel(String name);

    Clue getById(String clueId);

    int delete(String clueId);
}
