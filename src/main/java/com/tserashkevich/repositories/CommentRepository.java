package com.tserashkevich.repositories;

import com.tserashkevich.models.Comment;
import com.tserashkevich.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentByItem(Item item);

}
