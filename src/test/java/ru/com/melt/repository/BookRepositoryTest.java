package ru.com.melt.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ru.com.melt.model.Author;
import ru.com.melt.model.Book;
import ru.com.melt.model.Genre;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext
public class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @Autowired
    private TestEntityManager manager;

    @Test
    public void addNewBook_thenOk() {
        // given
        Book book = addBookToDb("author1", "title1", "genre1");

        // when
        Book saveBook = repository.save(book);

        // then
        assertEquals(book, saveBook);
        assertEquals("Book(title=title1, genre=Genre(genreName=genre1), " +
                "authors=[Author(name=author1, books=[])], comments=null)", saveBook.toString());
    }

    @Test(expected = NullPointerException.class)
    public void addNewBookWhenAuthorNull_thenThrowExc() {
        // given
        Book book = addBookToDb(null, "title1", "genre1");

        // when
        Book saveBook = repository.save(book);

        // then
    }

    @Test(expected = NullPointerException.class)
    public void addNewBookWhenTitleNull_thenThrowExc() {
        // given
        Book book = addBookToDb("author1", null, "genre1");

        // when
        Book saveBook = repository.save(book);

        // then
    }

    @Test(expected = NullPointerException.class)
    public void addNewBookWhenGenreNull_thenThrowExc() {
        // given
        Book book = addBookToDb("author1", "title1", null);

        // when
        Book saveBook = repository.save(book);

        // then
    }

    @Test
    public void getAllTitlesWhenTwoBook_thenOk() {
        // given
        Book book1 = addBookToDb("author1", "title1", "genre1");
        Book book2 = addBookToDb("author2", "title2", "genre2");

        // when
        Book saveBook1 = repository.save(book1);
        Book saveBook2 = repository.save(book2);
        List<String> allTitles = repository.findAllTitles();

        // then
        assertEquals(2, allTitles.size());
        assertEquals("[title1, title2]", allTitles.toString());
    }

    @Test
    public void getBookByAuthorWhenTwoBook_thenOk() {
        // given
        Book book1 = addBookToDb("author1", "title1", "genre1");
        Book book2 = addBookToDb("author2", "title2", "genre2");
        Author author = addAuthorToDb("author1");

        // when
        Book saveBook1 = repository.save(book1);
        Book saveBook2 = repository.save(book2);
        List<Book> books = repository.findBooksByAuthorName("author1");

        // then
        assertEquals(1, books.size());
        assertEquals("Author(name=author1, books=[])", author.toString());
        assertEquals(book1, books.get(0));
        assertEquals("Book(title=title1, genre=Genre(genreName=genre1), " +
                        "authors=[Author(name=author1, books=[])], comments=null)",
                books.get(0).toString());
    }

    @Test
    public void getBookByIdWhenTwoBook_thenOk() {
        // given
        Book book1 = addBookToDb("author1", "title1", "genre1");
        Book book2 = addBookToDb("author2", "title2", "genre2");

        // when
        Book saveBook1 = repository.save(book1);
        Book saveBook2 = repository.save(book2);
        Book book = repository.findBookById(book2.getId()).get();

        // then
        assertEquals(book2, book);
        assertEquals("Book(title=title2, genre=Genre(genreName=genre2), " +
                        "authors=[Author(name=author2, books=[])], comments=null)",
                book.toString());
    }

    @Test(expected = NoSuchElementException.class)
    public void getBookByIdWhenTwoBook_thenThrow() {
        // given
        Book book1 = addBookToDb("author1", "title1", "genre1");
        Book book2 = addBookToDb("author2", "title2", "genre2");

        // when
        Book saveBook1 = repository.save(book1);
        Book saveBook2 = repository.save(book2);
        Book book = repository.findBookById(0L).get();

        // then
    }

    @Test
    public void findByTitleWhenTwoBook_thenOk() {
        // given
        Book book1 = addBookToDb("author1", "title1", "genre1");
        Book book2 = addBookToDb("author2", "title1", "genre2");

        // when
        Book saveBook1 = repository.save(book1);
        Book saveBook2 = repository.save(book2);
        List<Book> books = repository.findBooksByTitle("title1");

        // then
        assertEquals(2, books.size());
    }

    @Test
    public void findByTitleWhenNonBook_thenEmptyList() {
        // given

        // when
        List<Book> books = repository.findBooksByTitle("title1");

        // then
        assertEquals(0, books.size());
    }

    @Test
    public void deleteBookByIdWhenBookExist_thenOk() {
        // given
        Book book1 = addBookToDb("author1", "title1", "genre1");
        Book book2 = addBookToDb("author2", "title1", "genre2");

        // when
        Book saveBook1 = repository.save(book1);
        Book saveBook2 = repository.save(book2);
        int i = repository.deleteBookById(book1.getId());
        List<Book> books = repository.findAll();

        // then
        assertEquals(1, i);
        assertEquals(1, books.size());
    }

    @Test
    public void deleteBookByIdWhenIdNoexist_thenOk() {
        // given
        Book book1 = addBookToDb("author1", "title1", "genre1");
        Book book2 = addBookToDb("author2", "title1", "genre2");

        // when
        Book saveBook1 = repository.save(book1);
        Book saveBook2 = repository.save(book2);
        int i = repository.deleteBookById(0L);
        List<Book> books = repository.findAll();

        // then
        assertEquals(0, i);
        assertEquals(2, books.size());
    }

    @Test
    public void deleteAll_thenOk() {
        // given
        Book book1 = addBookToDb("author1", "title1", "genre1");
        Book book2 = addBookToDb("author2", "title1", "genre2");

        // when
        Book saveBook1 = repository.save(book1);
        Book saveBook2 = repository.save(book2);
        repository.deleteAll();
        List<Book> books = repository.findAll();

        // then
        assertEquals(0, books.size());
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