package com.situ2001.hrm.filter;

import com.situ2001.hrm.pojo.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter({"*.action", "*.html", "*.jsp"})
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var req = (HttpServletRequest) servletRequest;
        var res = (HttpServletResponse) servletResponse;
        var path = req.getServletPath();
        if (path.equals("/login.action") || path.equals("/register.action") || path.equals("/jsp/login.html")) {
            filterChain.doFilter(req, res);
        } else {
            var session = req.getSession();
            var user = (User) session.getAttribute("user");
            if (user != null) {
                filterChain.doFilter(req, res);
            } else {
                res.sendRedirect("/jsp/login.html");
            }
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
