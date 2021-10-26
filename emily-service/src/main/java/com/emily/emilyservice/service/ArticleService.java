package com.emily.emilyservice.service;

import com.emily.emilyservice.model.es.Article;

import java.util.List;

public interface ArticleService {

    List<Article> findArticleByAuthorsName();

    void save();
}
