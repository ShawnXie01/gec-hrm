package com.situ2001.hrm.filter;


import com.situ2001.hrm.pojo.User;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet({"*.action", ".html"})
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String path = req.getServletPath();
        if("/login.action".equals(path)||"/register.action".equals(path)||"jsp/login.html".equals(path)){
            filterChain.doFilter(req,res);
        }else {
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user_inf");
            if(user!=null){
                filterChain.doFilter(req,res);
            }else {
                res.sendRedirect("/jsp/login.html");
            }
        }
    }
}
