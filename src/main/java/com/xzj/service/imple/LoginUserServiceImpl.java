package com.xzj.service.imple;

import com.xzj.MyException.MyException;
import com.xzj.dao.UserDao;
import com.xzj.domain.User;
import com.xzj.service.LoginUserService;
import com.xzj.util.DateTimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public  class LoginUserServiceImpl implements LoginUserService {

      @Resource
    private UserDao userDao;

      @Override
    public Map<String, Object> selectLoginUser(User conuser) throws MyException {

          User daouser=userDao.selectLoginUser(conuser);

          if (daouser==null){
              throw new MyException("账户或者密码错误");
          }

          //现在时间
          String ndate=DateTimeUtil.getSysTime();
          //到期时间
          String expire=daouser.getExpireTime();

          //到期时间要大于，现在时间，账户才能用
          // expire如果大，返回大于0的数，如果ndate大，返回小于0的数
          if (expire.compareTo(ndate)<0){
              throw new MyException("账户已失效");
          }

          if ("0".equals(daouser.getLockState())){
              throw new MyException("账户已被锁定");
          }

          //数据库ip
          String daoIp=daouser.getAllowIps();
          String conip=conuser.getAllowIps();
          if (!daoIp.contains(conip)){
              throw new MyException("ip地址异常");
          }

          Map<String,Object> map=new HashMap();
          map.put("suu",true);
          map.put("user",daouser);
          map.put("name",daouser.getName());

          return map;
    }
}
