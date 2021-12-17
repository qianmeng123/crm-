package com.xzj.ExceptionHandle;

import com.xzj.MyException.WorkbenchException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class workbencherExceptionHandle {

    @ExceptionHandler(value= WorkbenchException.class)
    @ResponseBody
    public Map<String,Object> userException(Exception e){
        Map map=new HashMap();
        map.put("success",false);
        //异常消息
        map.put("msg",e.getMessage());
        return map;
    }

    @ExceptionHandler
    @ResponseBody
    public Map<String,Object> otherException(Exception e){

        Map<String,Object> map=new HashMap();
        map.put("suu",false);

        map.put("msg","出现不知名异常");

        return map;
    }


}
