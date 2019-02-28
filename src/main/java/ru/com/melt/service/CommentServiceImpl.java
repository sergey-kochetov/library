package ru.com.melt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.com.melt.model.Comment;
import ru.com.melt.repository.CommentRepository;

import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<String> getAllComments(Long bookId) {
        return commentRepository.findCommentsByBookId(bookId);
    }

    @Override
    public List<Comment> findAllById(Long id) {
        return commentRepository.findAllById(id);
    }

    @Override
    public boolean deleteCommentById(Long id) {
        int result = commentRepository.deleteCommentById(id);
        return result > 0;
    }
}
