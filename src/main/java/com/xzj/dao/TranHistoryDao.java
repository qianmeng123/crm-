package com.xzj.dao;

import com.xzj.domain.TranHistory;

import java.util.List;

public interface TranHistoryDao {

    int save(TranHistory tranHistory);

    List<TranHistory> getTranHistoryList(String tranId);
}
