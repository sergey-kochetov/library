package ru.com.melt.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ru.com.melt.model.Author;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext // пересоздает каждый тест
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository repository;

    @Autowired
    private TestEntityManager manager;

    @Test
    public void addNewAuthorTest() {
        // given
        Author author = new Author();
        author.setName("name1");

        // when
        repository.save(author);
        List<String> actual = repository.findAllAuthorsNames();

        // then
        assertEquals(1, actual.size());
        assertTrue(actual.contains("name1"));
    }

    @Test
    public void whenNonAuthors_thenReturnEmptyList() {
        // given

        // when
        List<String> authors = repository.findAllAuthorsNames();

        // then
        assertEquals(0, authors.size());
        assertEquals("[]", authors.toString());
    }

    @Test
    public void whenAddTwoAuthors_thenReturnList() {
        // given
        addAuthor("name1");
        addAuthor("name2");

        // when
        List<String> authors = repository.findAllAuthorsNames();

        // then
        assertEquals(2, authors.size());
        assertEquals("[name1, name2]", authors.toString());
    }

    @Test(expected = NullPointerException.class)
    public void whenAuthorNameIsNull() {
        // given
        addAuthor(null);

        // when
        List<String> authors = repository.findAllAuthorsNames();

        // then
        assertEquals(1, authors.size());
        assertEquals("[null]", authors.toString());
    }

    @Test
    public void whenFindAuthorName_thenOk() {
        // given
        addAuthor("name1");

        // when
        Author author = repository.findAuthorByName("name1").get();

        // then
        assertEquals("name1", author.getName());
    }

    @Test(expected = NoSuchElementException.class)
    public void whenFindAuthorNullName_thenThrowExc() {
        // given
        addAuthor("name1");

        // when
        Author author = repository.findAuthorByName(null).get();

        // then
    }

    @Test
    public void whenDelAuthorIfExistList_thenOk() {
        // given
        addAuthor("name1");
        addAuthor("name2");
        addAuthor("name3");

        // when
        List<String> authorsBefore = repository.findAllAuthorsNames();
        repository.deleteAuthorById(repository.findAuthorByName("name1").get().getId());
        List<String> authorsAfter = repository.findAllAuthorsNames();

        // then
        assertEquals(3, authorsBefore.size());
        assertEquals(2, authorsAfter.size());
        assertEquals("[name2, name3]", authorsAfter.toString());
    }

    @Test
    public void whenDelAllAuthors_thenOk() {
        // given
        addAuthor("name1");
        addAuthor("name2");
        addAuthor("name3");

        // when
        repository.deleteAll();
        List<Author> authors = repository.findAll();

        // then
        assertEquals(0, authors.size());
        assertEquals("[]", authors.toString());
    }


    @Test
    public void whenfindAuthorById_thenOk() {
        // given
        addAuthor("name1");

        // when
        Author authorByName = repository.findAuthorByName("name1").get();
        Author author = repository.findAuthorById(authorByName.getId()).get();

        // then
        assertEquals(authorByName.getId(), author.getId());
        assertEquals("Author(name=name1, books=[])", author.toString());
    }

    @Test(expected = NoSuchElementException.class)
    public void whenfindAuthorById_thenNotOk() {
        // given
        addAuthor("name1");

        // when
        Author author = repository.findAuthorById(2L).get();

        // then
    }

    private Author addAuthor(String name) {
        Author author = new Author();
        author.setName(name);
        return manager.persist(author);
    }

}