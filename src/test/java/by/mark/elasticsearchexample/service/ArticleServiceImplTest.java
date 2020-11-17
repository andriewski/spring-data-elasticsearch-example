package by.mark.elasticsearchexample.service;

import by.mark.elasticsearchexample.dao.model.Article;
import by.mark.elasticsearchexample.dao.model.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@SpringBootTest
class ArticleServiceImplTest {

    @Autowired
    ArticleService articleService;

    @Test
    void createArticle() {
        //given:
        String id = UUID.randomUUID().toString();

        //when:
        Article article1 = articleService.createArticle(
                getArticle(Article.builder().id(id))
        );
        Article article2 = articleService.createArticle(
                getArticle(Article.builder())
        );

        //then:
        assertEquals(id, article1.getId());
        assertNotNull(article2.getId());
    }

    @Test
    void findArticleById() {
        //given:
        String id = UUID.randomUUID().toString();

        //when:
        Article article1 = articleService.createArticle(
                getArticle(Article.builder().id(id))
        );
        Article article2 = articleService.createArticle(
                getArticle(Article.builder())
        );

        Article foundedArticle1 = articleService.findArticleById(id).get();
        Article foundedArticle2 = articleService.findArticleById(article2.getId()).get();

        //then:
        assertEquals(foundedArticle1, article1);
        assertEquals(foundedArticle2, article2);
    }

    @Test
    void deleteArticle() {
        //when:
        Article article = articleService.createArticle(
                getArticle(Article.builder())
        );

        Article foundedArticle = articleService.findArticleById(article.getId()).get();
        articleService.deleteArticle(article);
        Optional<Article> deletedArticle = articleService.findArticleById(article.getId());

        //then:
        assertNotNull(foundedArticle);
        assertTrue(deletedArticle.isEmpty());
    }

    @Test
    void findByAuthorsName() {
        //given:
        Article article = getArticle(Article.builder());
        article.setAuthors(List.of(new Author(UUID.randomUUID().toString()), new Author(UUID.randomUUID().toString())));

        //when:
        Article createdArticle = articleService.createArticle(article);

        List<Article> foundedArticles1 = articleService.findByAuthorsName(
                createdArticle.getAuthors().get(0).getName(),
                PageRequest.of(0, 2)
        ).getContent();

        List<Article> foundedArticles2 = articleService.findByAuthorsName(
                createdArticle.getAuthors().get(1).getName(),
                PageRequest.of(0, 2)
        ).getContent();

        //then:
        assertEquals(foundedArticles1.size(), 1);
        assertEquals(foundedArticles2.size(), 1);
        assertEquals(foundedArticles1, foundedArticles2);
        assertEquals(foundedArticles1.get(0), createdArticle);
    }

    @Test
    void findByAuthorsNameUsingCustomQuery() {
        //given:
        Article article = getArticle(Article.builder());
        article.setAuthors(List.of(new Author(UUID.randomUUID().toString()), new Author(UUID.randomUUID().toString())));

        //when:
        Article createdArticle = articleService.createArticle(article);

        List<Article> foundedArticles1 = articleService.findByAuthorsNameUsingCustomQuery(
                createdArticle.getAuthors().get(0).getName(),
                PageRequest.of(0, 2)
        ).getContent();

        List<Article> foundedArticles2 = articleService.findByAuthorsNameUsingCustomQuery(
                createdArticle.getAuthors().get(1).getName(),
                PageRequest.of(0, 2)
        ).getContent();

        //then:
        assertEquals(foundedArticles1.size(), 1);
        assertEquals(foundedArticles2.size(), 1);
        assertEquals(foundedArticles1, foundedArticles2);
        assertEquals(foundedArticles1.get(0), createdArticle);
    }

    @Test
    void findByFilteredTagQuery() {
        //given:
        Article article = getArticle(Article.builder());
        article.setAuthors(List.of(new Author(UUID.randomUUID().toString()), new Author(UUID.randomUUID().toString())));
        article.setTags(new String[]{UUID.randomUUID().toString(), UUID.randomUUID().toString()});

        //when:
        Article createdArticle = articleService.createArticle(article);

        List<Article> foundedArticles1 = articleService.findByFilteredTagQuery(
                createdArticle.getTags()[0],
                PageRequest.of(0, 2)
        ).getContent();

        List<Article> foundedArticles2 = articleService.findByFilteredTagQuery(
                createdArticle.getTags()[1],
                PageRequest.of(0, 2)
        ).getContent();

        //then:
        assertEquals(foundedArticles1.size(), 1);
        assertEquals(foundedArticles2.size(), 1);
        assertEquals(foundedArticles1, foundedArticles2);
        assertEquals(foundedArticles1.get(0), createdArticle);
    }

    @Test
    void findByAuthorsNameAndFilteredTagQuery() {
        //given:
        Article article = getArticle(Article.builder());
        article.setAuthors(List.of(new Author(UUID.randomUUID().toString()), new Author(UUID.randomUUID().toString())));
        article.setTags(new String[]{UUID.randomUUID().toString(), UUID.randomUUID().toString()});

        //when:
        Article createdArticle = articleService.createArticle(article);

        List<Article> foundedArticles1 = articleService.findByAuthorsNameAndFilteredTagQuery(
                article.getAuthors().get(0).getName(),
                createdArticle.getTags()[0],
                PageRequest.of(0, 2)
        ).getContent();

        List<Article> foundedArticles2 = articleService.findByAuthorsNameAndFilteredTagQuery(
                article.getAuthors().get(1).getName(),
                createdArticle.getTags()[1],
                PageRequest.of(0, 2)
        ).getContent();

        //then:
        assertEquals(foundedArticles1.size(), 1);
        assertEquals(foundedArticles2.size(), 1);
        assertEquals(foundedArticles1, foundedArticles2);
        assertEquals(foundedArticles1.get(0), createdArticle);
    }

    private Article getArticle(Article.ArticleBuilder builder) {
        return builder
                .title("nasha niva")
                .authors(List.of(new Author("mrk"), new Author("dns")))
                .tags(new String[]{"new", "brand"})
                .build();
    }
}