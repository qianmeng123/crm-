package com.xzj.ExceptionHandle;

import com.xzj.MyException.MyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class myExceptionHandle {

    @ExceptionHandler(value= MyException.class)
    @ResponseBody
    public Map<String,Object> userException(Exception e){
        Map map=new HashMap();
        map.put("suu",false);
        //异常消息
        map.put("msg",e.getMessage());
        System.out.println("自定义异常");
        return map;
    }

    @ExceptionHandler
    @ResponseBody
    public Map<String,Object> otherException(Exception e){

        Map<String,Object> map=new HashMap();
        map.put("suu",false);

        map.put("msg","出现异常");

        return map;
    }


}
