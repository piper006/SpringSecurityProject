package com.SpringSecurity.Spring.Security.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID articleId;

    @Column(nullable = false,name = "title")
    private String tittle;

    @Column(nullable = false,name = "text")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id", referencedColumnName = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Comment> comments;

    public Article() {
    }

    public Article(UUID articleId, String tittle, String text, User user, Set<Comment> comments) {
        this.articleId = articleId;
        this.tittle = tittle;
        this.text = text;
        this.user = user;
        this.comments = comments;
    }


    public UUID getArticleId() {
        return articleId;
    }

    public void setArticleId(UUID articleId) {
        this.articleId = articleId;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void addNewComment(Comment comment){
        this.comments.add(comment);
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

}
