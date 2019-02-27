package ru.com.melt.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.com.melt.model.Author;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

    @Query("SELECT a.name FROM Author a")
    List<String> findAllAuthorsNames();

    Optional<Author> findAuthorByName(@NonNull String name);

    Optional<Author> findAuthorById(@NonNull Long id);

    int deleteAuthorById(Long id);
}
