package ru.com.melt.service;

import ru.com.melt.model.Comment;

import java.util.List;

public interface CommentService {

    List<String> getAllComments(Long bookId);

    List<Comment> findAllById(Long id);

    boolean deleteCommentById(Long id);
}
