package com.tserashkevich.services;

import com.tserashkevich.models.Comment;
import com.tserashkevich.models.Item;
import com.tserashkevich.repositories.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private ItemService itemService;
    @Mock
    private UserService userService;
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        commentService = new CommentService(commentRepository, itemService, userService);
    }

    @Test
    void findCommentByItem_ShouldReturnListOfComments() {
        Long itemId = 1L;
        Item item = new Item();
        item.setId(itemId);

        Comment comment1 = new Comment();
        Comment comment2 = new Comment();
        List<Comment> comments = new ArrayList<>();
        comments.add(comment1);
        comments.add(comment2);

        when(itemService.findOne(itemId)).thenReturn(item);
        when(commentRepository.findCommentByItem(item)).thenReturn(comments);

        List<Comment> result = commentService.findCommentByItem(itemId);

        assertEquals(comments, result);
        verify(itemService, times(1)).findOne(itemId);
        verify(commentRepository, times(1)).findCommentByItem(item);
    }

    @Test
    void save_ShouldSaveComment() {
        // Arrange
        Comment comment = new Comment();

        // Act
        commentService.save(comment, "username", 1L);

        // Assert
        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void delete_ShouldDeleteComment() {
        // Arrange
        Long commentId = 1L;

        // Act
        commentService.delete(commentId);

        // Assert
        verify(commentRepository, times(1)).deleteById(commentId);
    }
}

