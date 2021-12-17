package com.xzj.dao;

import com.xzj.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {

    List<ClueRemark> getListClud(String clueId);

    int delete(ClueRemark clueRemark);
}
