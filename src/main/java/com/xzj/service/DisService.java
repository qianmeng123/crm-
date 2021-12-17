package com.xzj.service;

import com.xzj.domain.DisValue;

import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface DisService {


    Map<String, List<DisValue>> getAll(ServletContextEvent servletContextEvent);
}
