package com.emily.emilyservice;

import com.emily.emilyservice.dao.es.ArticleRepository;
import com.emily.emilyservice.model.es.Article;
import com.emily.emilyservice.model.es.Author;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.stream.Stream;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EmilyEsTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    public void testEs() {
        Article article1 = new Article("title1");
        article1.setAuthors(Arrays.asList(new Author("a"), new Author("b")));
        articleRepository.save(article1);

        Article article2 = new Article("title2");
        article2.setAuthors(Arrays.asList(new Author("a"), new Author("c")));
        articleRepository.save(article2);

        Article article3 = new Article("title3");
        article3.setAuthors(Arrays.asList(new Author("b"), new Author("c")));
        articleRepository.save(article3);
    }

    @Test
    public void testEsList() {

        Page<Article> articleList = (Page<Article>) articleRepository.findAll();
        Stream<Article> list = articleList.get();
        list.forEach(article -> System.out.println(article.toString() + ","));
        System.out.println(list.toString());
    }

    @Test
    public void queryAuthorName() {
        Page<Article> articles = articleRepository.findArticleByAuthorsName("a", PageRequest.of(0, 10));
        for (Article article : articles) {
            System.out.println(article.toString());
            for (Author author : article.getAuthors()) {
                System.out.println("Author:" + author.toString());
            }
        }
    }

    @Test
    public void update() {
        Page<Article> articles = articleRepository.
                findArticleByTitleIsContaining("title", PageRequest.of(0, 10));
        Article article = articles.getContent().get(0);
        System.out.println("article:" + article);
        Author author = new Author("w");
        article.setAuthors(Arrays.asList(author));
        articleRepository.save(article);
        System.out.println("article save success...");

//        Page<Article> articles1 = articleRepository.
//                findArticleByTitleIsContaining("title",PageRequest.of(0,10));
//        Article article1 = articles1.getContent().get(0);
//        System.out.println("article1:"+article1);


        Page<Article> articleList = (Page<Article>) articleRepository.findAll();
        Stream<Article> list = articleList.get();
        list.forEach(articleAll -> System.out.println(articleAll.toString() + ","));
        System.out.println(list.toString());

    }

    @Test
    public void delete() {
        Page<Article> articles = articleRepository.
                findArticleByTitleIsContaining("title3", PageRequest.of(0, 10));
        Article article = articles.getContent().get(0);
        System.out.println("article:" + article);
        Author author = new Author("w");
        article.setAuthors(Arrays.asList(author));
        articleRepository.delete(article);
        System.out.println("article delete success...");

        Page<Article> articleList = (Page<Article>) articleRepository.findAll();
        Stream<Article> list = articleList.get();
        list.forEach(articleAll -> System.out.println(articleAll.toString() + ","));
        System.out.println(list.toString());
    }


    @Test
    public void testArrayList() throws InterruptedException {
        List<String> list = new ArrayList<>();
        for (int j = 0; j < 100; j++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 100; i++) {
                        list.add(i+"");
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

        Thread.sleep(1000);
        for (int i = 0; i < list.size(); i++) {
            System.out.println("第" + (i + 1) + "个元素为：" + list.get(i));
        }
    }


}
