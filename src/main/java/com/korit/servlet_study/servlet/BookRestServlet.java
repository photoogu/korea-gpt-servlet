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
        Author author = new Author(1, "테스트저자");
        Publisher publisher = new Publisher(10, "테스트출판사");
        BookCategory bookCategory = new BookCategory(100, "테스트카테고리");

        Book book = Book.builder()
                .bookID(200)
                .bookName("테스트도서명")
                .isbn("abcd1234")
                .bookImgUrl("http://test.com/1234")
                .authorID(author.getAuthorId())
                .publisherID(publisher.getPublisherId())
                .categoryID(bookCategory.getCategoryId())
                .author(author)
                .publisher(publisher)
                .bookCategory(bookCategory)
                .build();
//        아래는 DB랑 연결 한 경우,
//        String author = request.getParameter("author");
//        String publisher = request.getParameter("publisher");
//        String category = request.getParameter("category");
//
//        Author authorObj = new Author(0, author);
//        Publisher publisherObj = new Publisher(0, publisher);
//        BookCategory categoryObj = new BookCategory(0, category);
//
//        Book book = Book.builder()
//                .bookID(Integer.parseInt(request.getParameter("bookID")))
//                .bookName(request.getParameter("bookName"))
//                .author(authorObj)
//                .publisher(publisherObj)
//                .bookCategory(categoryObj)
//                .bookImgUrl(request.getParameter("bookImgUrl"))
//                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonUser = objectMapper.writeValueAsString(book);

        response.setContentType("application/json");
        response.getWriter().println(jsonUser);
    }
}
