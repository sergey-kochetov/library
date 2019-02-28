package ru.com.melt.service;

import lombok.NonNull;
import ru.com.melt.model.Book;

import java.util.List;

public interface LibraryService {

    List<Book> getBooksByAuthorsName(@NonNull String name);

    Book addNewBook(@NonNull Book book);

    boolean addComment(@NonNull Long bookId, @NonNull String username, @NonNull String comment);

    void deleteAll();
}
