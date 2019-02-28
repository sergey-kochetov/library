package ru.com.melt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.com.melt.model.Book;
import ru.com.melt.repository.BookRepository;

import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findBookById(id)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public boolean deleteBookById(Long id) {
        int result = bookRepository.deleteBookById(id);
        return result > 0;
    }
}
