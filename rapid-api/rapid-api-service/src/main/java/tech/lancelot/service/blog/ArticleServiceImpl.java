package tech.lancelot.service.blog;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.lancelot.domain.blog.Article;
import tech.lancelot.domain.system.Dept;
import tech.lancelot.domain.system.User;
import tech.lancelot.dto.blog.ArticleDto;
import tech.lancelot.dto.blog.ArticleQueryCriteria;
import tech.lancelot.exceptions.EntityExistException;
import tech.lancelot.mapstruct.blog.ArticleMapper;
import tech.lancelot.repository.blog.ArticleRepository;
import tech.lancelot.utils.*;

import java.util.*;

/**
 * @author lancelot
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "article")
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    @Override
    public Object queryAll(ArticleQueryCriteria criteria, Pageable pageable) {
        Page<Article> page = articleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteriaQuery, criteriaBuilder, criteria), pageable);
        return PageUtil.toPage(page.map(articleMapper::toDto));
    }

    @Override
    public List<ArticleDto> queryAll(ArticleQueryCriteria criteria) {
        List<Article> articles = articleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteriaQuery, criteriaBuilder, criteria));
        return articleMapper.toDto(articles);
    }

    @Override
    public ArticleDto findById(String id) {
        Article article = articleRepository.findById(id).orElseGet(Article::new);
        ValidationUtil.isNull(article.getId(), "Article", "id", id);
        return articleMapper.toDto(article);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Article resources) {
        articleRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Article resources) {
        Article article = articleRepository.findById(resources.getId()).orElseGet(Article::new);
        ValidationUtil.isNull(article.getId(), "Article", "id", resources.getId());

        article.setBlogType(resources.getBlogType());
        article.setIsSticky(resources.getIsSticky());
        article.setTitle(resources.getTitle());
        article.setImage(resources.getImage());
        article.setSummary(resources.getSummary());
        article.setContent(resources.getContent());

        article.setCategory(resources.getCategory());
        article.setArchive(resources.getArchive());
        article.setTags(resources.getTags());

        articleRepository.save(article);
    }
}