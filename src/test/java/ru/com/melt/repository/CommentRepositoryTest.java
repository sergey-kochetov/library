package ru.com.melt.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ru.com.melt.model.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TestEntityManager manager;

    @Test
    public void whenAddComment_thenOk() {
        // given
        Book book = addBookToDb("author1", "title1", "genre1");
        Customer customer = customerRepository.save(new Customer("username1"));

        Comment comment1 = new Comment(customer, book, "comment text1");
        Comment comment2 = new Comment(customer, book, "comment text2");

        // when
        commentRepository.save(comment1);
        commentRepository.save(comment2);

        List<Comment> comments = commentRepository.findCommentsByBookId(book.getId());

        // then
        assertEquals(2, comments.size());
        assertEquals("comment text1, comment text2",
                comments.stream()
                        .map(Comment::getCommentText)
                        .collect(Collectors.joining(", ")));
    }

    @Test
    public void whenDeleteCommentById_thenSuccess() {
        // given
        Book book = addBookToDb("author1", "title1", "genre1");
        Customer customer = customerRepository.save(new Customer("username1"));
        Comment comment1 = commentRepository.save(new Comment(customer, book, "comment text1"));
        Comment comment2 = commentRepository.save(new Comment(customer, book, "comment text2"));

        // when
        commentRepository.deleteCommentById(comment1.getId());

        List<Comment> comments = commentRepository.findCommentsByBookId(book.getId());

        // then
        assertEquals(1, comments.size());
        assertEquals("comment text2",
                comments.stream()
                        .map(Comment::getCommentText)
                        .collect(Collectors.joining(", ")));
    }

    @Test
    public void whenDeleteCommentById_whenNoComment() {
        // given

        // when
        int i = commentRepository.deleteCommentById(0L);

        // then
        assertEquals(0, i);
    }

    private Author addAuthorToDb(String name) {
        Author author = new Author();
        author.setName(name);

        return manager.persist(author);
    }

    private Book addBookToDb(String authorName, String title, String genreName) {
        Author author = addAuthorToDb(authorName);

        Set<Author> authors = new HashSet<>();
        authors.add(author);

        Genre genre = addGenreToDb(genreName);

        Book book = new Book();
        book.setTitle(title);
        book.setAuthors(authors);
        book.setGenre(genre);

        return manager.persist(book);
    }

    private Genre addGenreToDb(String genreName) {
        Genre genre = new Genre();
        genre.setGenreName(genreName);

        return manager.persist(genre);
    }
}