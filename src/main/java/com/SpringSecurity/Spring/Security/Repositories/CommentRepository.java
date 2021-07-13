package com.SpringSecurity.Spring.Security.Repositories;

import com.SpringSecurity.Spring.Security.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    Comment getCommentByCommentId(UUID commentId);
}
