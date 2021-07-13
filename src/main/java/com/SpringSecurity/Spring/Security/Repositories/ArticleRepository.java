package com.SpringSecurity.Spring.Security.Repositories;

import com.SpringSecurity.Spring.Security.Model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID> {
    Article getArticleByArticleId(UUID articleId);
}
