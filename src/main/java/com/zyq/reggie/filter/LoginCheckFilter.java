package com.zyq.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.zyq.reggie.common.BaseContext;
import com.zyq.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    //路径匹配器
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        1.获取本次请求的uri
        String uri = request.getRequestURI();
//        定义不需要拦截的uri
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/login",
                "/user/code",
                "/user/haha"
        };
//        2.本次请求是否需要处理
        Boolean check = check(urls, uri);
//        3.如果不需要处理则直接放行

        if (check){
            filterChain.doFilter(request, response);
            return;
        }
//        4.判断登陆状态，如果已经登录则放行
        Long empid = (Long) request.getSession().getAttribute("employee");
        if (empid !=null){
            BaseContext.setCurrentId(empid);
            filterChain.doFilter(request, response);
            return;
        }
        Long uid = (Long) request.getSession().getAttribute("user");
        if (uid !=null){
            BaseContext.setCurrentId(uid);
            filterChain.doFilter(request, response);
            return;
        }
//        5.如果没有登录，返回未登录结果，通过输出流方式向客户端响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    private Boolean check(String[] urls, String requestURI){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match)
                return true;
        }
        return false;
    }
}
