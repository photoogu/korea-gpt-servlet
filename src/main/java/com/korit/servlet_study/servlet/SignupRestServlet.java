package com.korit.servlet_study.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.korit.servlet_study.dto.ResponseDto;
import com.korit.servlet_study.dto.SignupDto;
import com.korit.servlet_study.service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/api/signup")
public class SignupRestServlet extends HttpServlet {

    private AuthService authService;
    public SignupRestServlet() { authService = AuthService.getInstance(); }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder requestJsonData = new StringBuilder();
        try(BufferedReader bufferedReader = req.getReader()) {
            String line;
            while((line = bufferedReader.readLine()) != null) {
                requestJsonData.append(line);
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        SignupDto signupDto = objectMapper.readValue(requestJsonData.toString(), SignupDto.class);

        ResponseDto<?> responseDto = authService.signup(signupDto);
        String responseJson = objectMapper.writeValueAsString(responseDto);

        resp.setStatus(responseDto.getStatus());
        resp.setContentType("application/json");
        resp.getWriter().println(responseJson);

    }
}
