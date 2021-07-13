package com.SpringSecurity.Spring.Security.Controllers;

import com.SpringSecurity.Spring.Security.Model.Article;
import com.SpringSecurity.Spring.Security.Model.Comment;
import com.SpringSecurity.Spring.Security.Model.MyUserDetails;
import com.SpringSecurity.Spring.Security.Repositories.ArticleRepository;
import com.SpringSecurity.Spring.Security.Repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/comment")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @PostMapping("/new/{articleId}")
    public Comment commentArticle(@AuthenticationPrincipal MyUserDetails commenter,
                                  @PathVariable UUID articleId,
                                  @RequestBody Comment comment) {

        comment.setUser(commenter.getUser());
        commentRepository.save(comment);
        Article commentedArticle = articleRepository.getArticleByArticleId(articleId);
        commentedArticle.addNewComment(comment);
        articleRepository.save(commentedArticle);
        return comment;
    }

    @PostMapping("/new/comment/{commentId}")
    public Comment commentOnComment(@AuthenticationPrincipal MyUserDetails commenter,
                                  @PathVariable UUID commentId,
                                  @RequestBody Comment comment) {
        comment.setUser(commenter.getUser());
        Comment commentToBeCommented = commentRepository.getCommentByCommentId(commentId);
        commentToBeCommented.addNewComment(comment);

        commentRepository.save(commentToBeCommented);
        return commentToBeCommented;
    }



}
