package com.emily.emilyservice.service.impl;

import com.emily.emilyservice.dao.es.ArticleRepository;
import com.emily.emilyservice.model.es.Article;
import com.emily.emilyservice.model.es.Author;
import com.emily.emilyservice.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public List<Article> findArticleByAuthorsName() {
        Page<Article> articles = articleRepository.
                findArticleByAuthorsName("a", PageRequest.of(0, 10));
        List<Article> articleList = new ArrayList<>();
        articleList = articles.getContent();
        return articleList;
    }

    public void save(){
        Article article1 = new Article("title1");
        article1.setAuthors(Arrays.asList(new Author("a"), new Author("b")));
        articleRepository.save(article1);
    }

}
