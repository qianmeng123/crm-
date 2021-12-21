package com.xzj.controller;

import com.xzj.service.TranSerice;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
public class ChartController {

    @Resource
    private TranSerice tranSerice;

    @ResponseBody
    @RequestMapping("user/chart/transaction")
    public Map<String,Object> transaction(){

          Map<String,Object> map=tranSerice.getCountAndMap();

            return map;
    }
}
