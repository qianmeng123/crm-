package com.xzj.controller;
import com.xzj.MyException.MyException;
import com.xzj.domain.User;
import com.xzj.service.LoginUserService;
import com.xzj.util.MD5Util;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping(value = "/user")
public class MyController {
    @Resource
    private LoginUserService loginUserService;

    @RequestMapping(value = "/loginUser",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> loginUser(String loginAct, String loginPwd, HttpServletRequest request) throws MyException {
        loginPwd= MD5Util.getMD5(loginPwd);
        //获取ip
        String ip=request.getRemoteAddr();
        User user=new User();

        user.setLoginAct(loginAct);
        user.setLoginPwd(loginPwd);
        user.setAllowIps(ip);

        Map<String,Object> map=loginUserService.selectLoginUser(user);
        //如果上面这段代码没异常继续往下走

        User daouser=(User)map.get("user");
        request.getSession().setAttribute("user",daouser);
        System.out.println("id:"+user.getId());
         return map;
}


}