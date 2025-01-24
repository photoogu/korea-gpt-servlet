package com.korit.servlet_study.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.korit.servlet_study.dao.UserDao;
import com.korit.servlet_study.dto.ResponseDto;
import com.korit.servlet_study.entity.User;
import com.korit.servlet_study.security.annotation.JwtValid;
import com.korit.servlet_study.security.jwt.JwtProvider;
import io.jsonwebtoken.Claims;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class AuthenticationFilter implements Filter {

    private JwtProvider jwtProvider;
    private UserDao userDao;

    public AuthenticationFilter() {
        jwtProvider = JwtProvider.getInstance();
        userDao = UserDao.getInstance();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // 다운 캐스팅
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        try {
            if(isJwtTokenValid(request)) { // Jwt 토큰 인증이 필요한가? (ex. 회원가입하는 경우에는 토큰 인증 X)
                // Token 검증
                String bearerToken = request.getHeader("Authorization");
                if(bearerToken == null) {
                    setUnAuthenticatedResponse(response);
                    return;
                }
                // Claim 검증
                Claims claims = jwtProvider.parseToken(bearerToken);
                if(claims == null) {
                    setUnAuthenticatedResponse(response);
                    return;
                }
                // userId 검증 (ex. 회원 탈퇴 후에도 토큰(유효기간: 1년)이 아직 유효한 문제 처리 >> userId 가 DB 에 있는지 검증!)
                int userId = Integer.parseInt(claims.get("userId").toString());
                User foundUser = userDao.findById(userId);
                if(foundUser == null) {
                    setUnAuthenticatedResponse(response);
                    return;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }

    private boolean isJwtTokenValid(HttpServletRequest request) throws ClassNotFoundException{
        String method = request.getMethod(); // 요청 보낸 method 찾기(GET, POST, ...)
        String servletPath = request.getHttpServletMapping().getServletName();
        // 요청 보낸 Servlet 찾기 (* 클래스 명: 패키지의 이름까지 포함)

        Class<?> servletClass = Class.forName(servletPath);
        Method foundMethod = getMappedMethod(servletClass, method);
        return foundMethod != null;
    }

    private Method getMappedMethod(Class<?> clazz, String methodName) { // 인증이 필요한 method 찾기!
        for(Method method : clazz.getDeclaredMethods()) {
            if(method.getName().toLowerCase().endsWith(methodName.toLowerCase()) // method 이름 찾기(lowercase: 소문자로 만듦)
            && method.isAnnotationPresent(JwtValid.class)) { // 해당 Annotation 클래스 포함되어있는가?
                return method;
            }
        }
        return null;
    }

    private void setUnAuthenticatedResponse(HttpServletResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseDto<String> responseDto = ResponseDto.forbidden("검증 할 수 없는 Access Token 입니다.");
        response.setStatus(responseDto.getStatus());
        response.setContentType("application/json");
        response.getWriter().print(objectMapper.writeValueAsString(responseDto));
    }

}
