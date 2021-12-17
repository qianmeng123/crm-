package com.xzj.service;

import com.xzj.MyException.MyException;
import com.xzj.domain.User;
import java.util.Map;
public interface LoginUserService {

    Map<String,Object> selectLoginUser(User user) throws MyException;


}
