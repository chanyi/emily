package com.emily.emilyservice.dao.es;

import com.emily.emilyservice.model.es.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends ElasticsearchRepository<Article,String> {

    //根据作者名称搜索
    Page<Article> findArticleByAuthorsName(String name, Pageable pageable);

    //搜索title字段
    Page<Article> findArticleByTitleIsContaining(String word,Pageable pageable);

    //数据写入
//    void save(Article article);


}
