package ru.com.melt.service;

import ru.com.melt.model.Author;

import java.util.List;

public interface AuthorService {

    List<String> getAllAuthorsNames();

    boolean addNewAuthor(Author author);

    boolean deleteAuthorById(Long id);
}
