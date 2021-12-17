package com.xzj.dao;

import com.xzj.domain.User;

public interface UserDao {
    User  selectLoginUser(User user);
}
