package com.xzj.filter;

import com.xzj.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EncodingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;

        User user=(User)request.getSession().getAttribute("user");

          String path=request.getServletPath();
        System.out.println(path);
        if ("/login.jsp".equals(path)||"/user/loginUser".equals(path)
                ||"/index.jsp".equals(path)){

           filterChain.doFilter(servletRequest,servletResponse);
        }else {
            if (user!=null){
                filterChain.doFilter(servletRequest,servletResponse);
            }
            else{
                response.sendRedirect(request.getContextPath()+"/login.jsp");
            }

        }


    }



    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
