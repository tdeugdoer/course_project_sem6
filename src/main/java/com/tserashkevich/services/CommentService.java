package com.tserashkevich.services;

import com.tserashkevich.models.Comment;
import com.tserashkevich.models.Item;
import com.tserashkevich.models.User;
import com.tserashkevich.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ItemService itemService;
    private final UserService userService;

    @Transactional(readOnly = true)
    public List<Comment> findCommentByItem(Long id) {
        return commentRepository.findCommentByItem(itemService.findOne(id));
    }

    public void save(Comment comment, String username, Long id) {
        Optional<User> user = userService.findUserByLogin(username);
        Item item = itemService.findOne(id);
        comment.setUser(user.get());
        comment.setItem(item);
        commentRepository.save(comment);
    }

    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}
