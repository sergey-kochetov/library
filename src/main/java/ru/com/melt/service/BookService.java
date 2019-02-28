package ru.com.melt.service;

import ru.com.melt.model.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    Book getBookById(Long id);

    boolean deleteBookById(Long id);

    Book updateBookTitleById(Long id, String title);
}
