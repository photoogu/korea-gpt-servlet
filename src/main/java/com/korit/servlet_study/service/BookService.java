package com.korit.servlet_study.service;

import com.korit.servlet_study.entity.Book;

public class BookService {
    private static BookService bookService;

    private BookService() {

    }

    public static BookService getInstance() {
        if (bookService == null) {
            bookService = new BookService();
        }
        return bookService;
    }

    public Book addBook(Book book) {
        bookDao.saveAuthor
    }

}
