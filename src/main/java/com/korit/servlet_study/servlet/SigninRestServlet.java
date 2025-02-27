package com.korit.servlet_study.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.korit.servlet_study.dto.ResponseDto;
import com.korit.servlet_study.dto.SigninDto;
import com.korit.servlet_study.service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/api/signin")
public class SigninRestServlet extends HttpServlet {
    private AuthService authService;

    public SigninRestServlet() {
        authService = AuthService.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        BufferedReader bufferedReader = req.getReader();
//        StringBuilder requestJsonData = new StringBuilder();
//        String line;
//        if ((line = bufferedReader.readLine()) != null) {
//            requestJsonData.append(line);
//        } >> 내가 직접 한것..
        StringBuilder requestJsonData = new StringBuilder();
        try(BufferedReader bufferedReader = req.getReader()) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                requestJsonData.append(line);
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        SigninDto signinDto = objectMapper.readValue(requestJsonData.toString(), SigninDto.class); // JSON -> DTO

        ResponseDto<?> responseDto = authService.signin(signinDto);

        resp.setContentType("application/json");
        resp.setStatus(responseDto.getStatus());
        resp.getWriter().write(objectMapper.writeValueAsString(responseDto)); // DTO -> JSON

    }
}
