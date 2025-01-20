package com.korit.servlet_study.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.korit.servlet_study.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/user")
public class UserRestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = User.builder()
                .username("test")
                .password("1234")
                .name("테스트")
                .email("test@gmail.com")
                .build();

        String jsonUser = objectMapper.writeValueAsString(user); // 자바 객체인 User 를 JSON 문자열로 바꾸어준다
        System.out.println(jsonUser); // 확인

        response.setHeader("Access-Control-Allow-Origin", "*"); // Origin : 출처 대상, 서버 >> 허용 서버 제한
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS"); // 요청 메서드 허용 제한
        response.setHeader("Access-Control-Allow-Headers", "Content-Type"); // 아래의 ContentType 을 허용하여 받음
        response.setHeader("Access-Control-Allow-Credentials", "true"); // 쿠키 허용 제한

        response.setContentType("application/json");
        response.getWriter().println(jsonUser);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
