package com.korit.servlet_study.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("*")  // 모든 요청 시에 이 필터를 거쳐감!
public class EncodingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        System.out.println("필터!!");
        HttpServletRequest request = (HttpServletRequest) servletRequest; // 다운 캐스팅! (HttpServletRequest 는 ServletRequest 를 상속받고 있음)

        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");

        filterChain.doFilter(servletRequest, servletResponse); // forward 역할 : doGet 메서드 호출!

//        System.out.println("후처리");
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
