package com.korit.servlet_study.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.korit.servlet_study.entity.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/book")
public class BookRestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String author = request.getParameter("author");
        String publisher = request.getParameter("publisher");
        String category = request.getParameter("category");

        Author authorObj = new Author(0, author);
        Publisher publisherObj = new Publisher(0, publisher);
        BookCategory categoryObj = new BookCategory(0, category);

        Book book = Book.builder()
                .bookID(Integer.parseInt(request.getParameter("bookID")))
                .bookName(request.getParameter("bookName"))
                .author(authorObj)
                .publisher(publisherObj)
                .bookCategory(categoryObj)
                .bookImgUrl(request.getParameter("bookImgUrl"))
                .build();

        String jsonUser = objectMapper.writeValueAsString(book);
        System.out.println(jsonUser);

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        response.setContentType("application/json");
        response.getWriter().println(jsonUser);
    }
}
