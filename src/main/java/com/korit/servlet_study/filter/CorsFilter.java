package com.korit.servlet_study.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.setHeader("Access-Control-Allow-Origin", "*"); // Origin : 출처 대상, 서버 >> 허용 서버 제한
        response.setHeader("Access-Control-Allow-Methods", "*"); // 요청 메서드 허용 제한
        response.setHeader("Access-Control-Allow-Headers", "*"); // 아래의 ContentType 을 허용하여 받음
        response.setHeader("Access-Control-Allow-Credentials", "true"); // 쿠키 허용 제한

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
