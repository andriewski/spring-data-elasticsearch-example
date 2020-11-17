package by.mark.elasticsearchexample.service;

import by.mark.elasticsearchexample.dao.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ArticleService {

    Article createArticle(Article article);

    Optional<Article> findArticleById(String id);

    void deleteArticle(Article article);

    Page<Article> findByAuthorsName(String name, Pageable pageable);

    Page<Article> findByAuthorsNameUsingCustomQuery(String name, Pageable pageable);

    Page<Article> findByFilteredTagQuery(String tag, Pageable pageable);

    Page<Article> findByAuthorsNameAndFilteredTagQuery(String name, String tag, Pageable pageable);
}
