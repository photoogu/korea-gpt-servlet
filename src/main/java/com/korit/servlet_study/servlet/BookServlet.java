package com.korit.servlet_study.servlet;

import com.korit.servlet_study.entity.Author;
import com.korit.servlet_study.entity.Book;
import com.korit.servlet_study.entity.BookCategory;
import com.korit.servlet_study.entity.Publisher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/book1")
public class BookServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/book1_study1.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String bookName = req.getParameter("bookName");
        String isbn = req.getParameter("isbn");
        String author = req.getParameter("author");
        String publisher = req.getParameter("publisher");
        String category = req.getParameter("category");
        String imgUrl = req.getParameter("imgUrl");

        Author authorObj = new Author(0, author);
        Publisher publisherObj = new Publisher(0, publisher);
        BookCategory bookCategoryObj = new BookCategory(0, category);
        Book book = Book.builder()
                .bookName(bookName)
                .isbn(isbn)
                .author(authorObj)
                .publisher(publisherObj)
                .category(bookCategoryObj)
                .bookImgUrl(imgUrl)
                .build();
    }
}
