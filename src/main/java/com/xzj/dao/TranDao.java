package com.xzj.dao;

import com.xzj.domain.Tran;

import java.util.List;
import java.util.Map;

public interface TranDao {

    int save(Tran tran);

    Tran getAllId(String id);

    int updateChangeStage(Tran tran);

    int getCount();

    List<Map<String, String>> getMap();
}
