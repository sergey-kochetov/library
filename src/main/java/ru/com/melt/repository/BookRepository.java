package ru.com.melt.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.com.melt.model.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    @Query("SELECT b.title FROM Book b")
    List<String> findAllTitles();

    List<Book> findAll();

    @Query("SELECT b FROM Book b JOIN b.authors a WHERE a.name = :name")
    List<Book> findBooksByAuthorName(@Param(value = "name") String authorName);

    List<Book> findBooksByTitle(String title);

    Optional<Book> findBookById(Long id);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Book b SET b.title = :title WHERE b.id = :id")
    int updateBookTitleById(@Param(value = "id") Long id,
                            @Param(value = "title") String title);

    int deleteBookById(Long id);
}
