package com.emily.emilyweb.controller;

import com.emily.emilyservice.model.es.Article;
import com.emily.emilyservice.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/es")
public class EsController {

    @Autowired
    private ArticleService articleService;

    @ResponseBody
    @RequestMapping("/article")
    public List<Article> getArticleList(){
        return articleService.findArticleByAuthorsName();
    }

    @ResponseBody
    @RequestMapping("/save")
    public void save(){
        articleService.save();
    }
}
