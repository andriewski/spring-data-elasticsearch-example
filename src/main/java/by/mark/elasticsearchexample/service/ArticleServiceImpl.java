package by.mark.elasticsearchexample.service;

import by.mark.elasticsearchexample.dao.model.Article;
import by.mark.elasticsearchexample.dao.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Override
    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public Optional<Article> findArticleById(String id) {
        return articleRepository.findById(id);
    }

    @Override
    public void deleteArticle(Article article) {
        articleRepository.delete(article);
    }

    @Override
    public Page<Article> findByAuthorsName(String name, Pageable pageable) {
        return articleRepository.findByAuthorsName(name, pageable);
    }

    @Override
    public Page<Article> findByAuthorsNameUsingCustomQuery(String name, Pageable pageable) {
        return articleRepository.findByAuthorsNameUsingCustomQuery(name, pageable);
    }

    @Override
    public Page<Article> findByFilteredTagQuery(String tag, Pageable pageable) {
        return articleRepository.findByFilteredTagQuery(tag, pageable);
    }

    @Override
    public Page<Article> findByAuthorsNameAndFilteredTagQuery(String name, String tag, Pageable pageable) {
        return articleRepository.findByAuthorsNameAndFilteredTagQuery(name, tag, pageable);
    }
}
