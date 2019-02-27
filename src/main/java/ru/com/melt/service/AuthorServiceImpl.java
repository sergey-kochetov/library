package ru.com.melt.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.com.melt.model.Author;
import ru.com.melt.repository.AuthorRepository;

import java.util.List;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<String> getAllAuthorsNames() {
        return authorRepository.findAllAuthorsNames();
    }

    @Override
    public boolean addNewAuthor(@NonNull Author author) {
        Author result = authorRepository.save(author);
        return result.getId() != null;
    }
}
