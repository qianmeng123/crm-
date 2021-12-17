package com.xzj.dao;

import com.xzj.domain.DisValue;

import java.util.List;

public interface DisValueDao {
    List<DisValue> getValue(String code);
}
