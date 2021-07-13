package com.SpringSecurity.Spring.Security.Controllers;

import com.SpringSecurity.Spring.Security.Model.Article;
import com.SpringSecurity.Spring.Security.Model.MyUserDetails;
import com.SpringSecurity.Spring.Security.Repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/article")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @PostMapping("/new")
    public Article postNewArticle(@AuthenticationPrincipal MyUserDetails caller, @RequestBody Article article){
        article.setUser(caller.getUser());
        return articleRepository.save(article);
    }

    @GetMapping("/info/{articleId}")
    public Article getArticleInfo(@PathVariable UUID articleId){
        return articleRepository.getArticleByArticleId(articleId);
    }

}
