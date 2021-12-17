package com.xzj.service;

import com.xzj.domain.Activity;
import com.xzj.domain.Clue;
import com.xzj.domain.Tran;

import java.util.List;
import java.util.Map;

public interface ClueService {

    boolean save(Clue clue);

    Map<String, Object> getAllClue(Integer pageNo, Integer pageSize);

      Clue getIdClue(String id);

    List<Activity> clueIDGetActivity(String clueId);

    Map<String, Object> deleteCar(String id);

    List<Activity> modelActivity(String clueId);

    List<Activity> textActivity(String textActivity,String clueId);


    Map<String, Object> addActivityAndClue(String[] ids, String clueId);

    List<Activity> getActivityConventModel(String name);

    boolean conversionActivity(String clueId, String flag, Tran tran, String createBy);
}
