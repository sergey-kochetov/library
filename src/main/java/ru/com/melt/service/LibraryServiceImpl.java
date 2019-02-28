package ru.com.melt.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.com.melt.model.*;
import ru.com.melt.repository.*;

import java.util.*;

@Service
@Transactional
public class LibraryServiceImpl implements LibraryService {


    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public LibraryServiceImpl(AuthorRepository authorRepository,
                              BookRepository bookRepository,
                              GenreRepository genreRepository,
                              CommentRepository commentRepository,
                              CustomerRepository customerRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.commentRepository = commentRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getBooksByAuthorsName(String name) {
        Optional<Author> author = authorRepository.findAuthorByName(name);
        if (author.isPresent()) {
            return bookRepository.findBooksByAuthorName(author.get().getName());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public Book addNewBook(@NonNull Book book) {
        book.setGenre(getProvenGenre(book));

        book.setAuthors(getProvenAuthors(book));

        return bookRepository.save(book);
    }

    @Override
    public boolean addComment(@NonNull Long bookId, @NonNull String username, @NonNull String comment) {
        Optional<Book> bookOptional = bookRepository.findBookById(bookId);
        if (!bookOptional.isPresent()) {
            return false;
        }
        Book book = bookOptional.get();

        Customer customer = customerRepository.findCustomerByUsername(username)
                .orElseGet(() -> customerRepository.save(new Customer(username)));
        Comment com = commentRepository.save(new Comment(customer, book, comment));

        book.getComments().add(com);

        bookRepository.save(book);

        return true;
    }

    @Override
    public void deleteAll() {
        genreRepository.deleteAll();
        authorRepository.deleteAll();
        bookRepository.deleteAll();
        commentRepository.deleteAll();
        customerRepository.deleteAll();
    }

    private Genre getProvenGenre(@NonNull Book book) {
        return genreRepository
                    .findGenreByGenreName(book.getGenre().getGenreName())
                    .orElse(genreRepository.save(book.getGenre()));
    }

    private Set<Author> getProvenAuthors(@NonNull Book book) {
        Set<Author> result = new HashSet<>();
        book.getAuthors().stream()
                .map(this::getAuthor)
                .forEach(author -> {
                    author.getBooks().add(book);
                    result.add(author);
                });
        return result;
    }

    private Author getAuthor(@NonNull Author author) {
        return authorRepository
                .findAuthorByName(author.getName())
                .orElseGet(() -> authorRepository.save(author));
    }
}
