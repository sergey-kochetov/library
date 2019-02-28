package ru.com.melt.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.com.melt.model.Comment;
import ru.com.melt.repository.CommentRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceTest {

    @MockBean
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    @Before
    public void init() {
        reset(commentRepository);
    }

    @Test
    public void whenGetAllComments_thenReturnList() {
        // given
        List<Comment> list = Collections.emptyList();
        Long id = 1L;
        // when
        when(commentRepository.findCommentsByBookId(id)).thenReturn(list);
        List<String> allBooks = commentService.getAllComments(id);

        // then
        verify(commentRepository).findCommentsByBookId(id);
    }

    @Test
    public void whenFindAllById_thenReturnList() {
        // given
        List<Comment> list = Collections.emptyList();
        Long id = 1L;
        // when
        when(commentRepository.findAllById(id)).thenReturn(list);
        List<Comment> allBooks = commentService.findAllById(id);

        // then
        verify(commentRepository).findAllById(id);
    }

    @Test
    public void whenDeleteCommentById_thenReturnTrue() {
        // given
        Long id = 1L;
        // when
        when(commentRepository.deleteCommentById(id)).thenReturn(1);
        boolean actual = commentService.deleteCommentById(id);

        // then
        verify(commentRepository).deleteCommentById(id);
        assertTrue(actual);
    }

    @Test
    public void whenDeleteCommentById_thenReturnFalse() {
        // given
        Long id = 1L;
        // when
        when(commentRepository.deleteCommentById(id)).thenReturn(0);
        boolean actual = commentService.deleteCommentById(id);

        // then
        verify(commentRepository).deleteCommentById(id);
        assertFalse(actual);
    }


}