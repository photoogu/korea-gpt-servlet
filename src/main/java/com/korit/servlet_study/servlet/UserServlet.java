package com.korit.servlet_study.servlet;

import com.korit.servlet_study.entity.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    public void init(ServletConfig config) throws ServletException {
        List<User> users = new ArrayList<>();
        users.add(new User("aaa", "1111", "aaaaaa", "aaa@gmail.com"));
        users.add(new User("bbb", "1111", "bbbbbb", "bbb@gmail.com"));
        users.add(new User("ccc", "1111", "cccccc", "ccc@gmail.com"));
        users.add(new User("ddd", "1111", "dddddd", "ddd@gmail.com"));
        users.add(new User("eee", "1111", "eeeeee", "eee@gmail.com"));

        config.getServletContext().setAttribute("users", users);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchValue = request.getParameter("searchValue");
        ServletContext servletContext = request.getServletContext();
        List<User> users = (List<User>) servletContext.getAttribute("users");

        if (searchValue != null) {
            if (!searchValue.isBlank()) {
                request.setAttribute("users", users.stream()
                        .filter(user -> user.getUsername().contains(searchValue)
                        || user.getPassword().contains(searchValue)
                        || user.getName().contains(searchValue)
                        || user.getEmail().contains(searchValue))
                        .collect(Collectors.toList()));
            }
        }
        request.getRequestDispatcher("/WEB-INF/user.jsp").forward(request, response);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = User.builder()
                .username(request.getParameter("username"))
                .password(request.getParameter("password"))
                .name(request.getParameter("name"))
                .email(request.getParameter("email"))
                .build();

        ServletContext servletContext = request.getServletContext();
        List<User> users = (List<User>) servletContext.getAttribute("users");

        if(users.stream().filter(u -> u.getUsername().equals(user.getUsername())).collect(Collectors.toList()) != null){
            
        }
    }
}

//history.back(); >> js 에서 전 화면으로 돌아가는 함수