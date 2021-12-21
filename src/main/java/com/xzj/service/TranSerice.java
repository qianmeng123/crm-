package com.xzj.service;


import com.xzj.domain.Tran;
import com.xzj.domain.TranHistory;

import java.util.List;
import java.util.Map;

public interface TranSerice {





    boolean save(Tran tran);

    Tran detail(String id);

    List<TranHistory> getTranHistory(String tranId);

    Map<String, Object> changeStage(Tran tran);

    Map<String, Object> getCountAndMap();
}
