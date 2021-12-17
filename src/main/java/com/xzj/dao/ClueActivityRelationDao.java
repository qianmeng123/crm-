package com.xzj.dao;


import com.xzj.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationDao {


    List<ClueActivityRelation> getListClueId(String clueId);

    int delete(ClueActivityRelation clueActivityRelation);
}
