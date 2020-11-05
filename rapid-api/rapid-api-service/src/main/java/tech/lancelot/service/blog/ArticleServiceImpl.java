package tech.lancelot.service.blog;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.lancelot.domain.blog.Article;
import tech.lancelot.dto.blog.ArticleDto;
import tech.lancelot.dto.blog.BlogQueryCriteria;
import tech.lancelot.mapstruct.blog.ArticleMapper;
import tech.lancelot.repository.blog.BlogRepository;
import tech.lancelot.utils.*;

import java.util.*;

/**
 * @author lancelot
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "blog")
public class ArticleServiceImpl implements ArticleService {

    private final BlogRepository blogRepository;
    private final ArticleMapper articleMapper;

    @Override
    public Object queryAll(BlogQueryCriteria criteria, Pageable pageable) {
        Page<Article> page = blogRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(articleMapper::toDto));
    }

    @Override
    public List<ArticleDto> queryAll(BlogQueryCriteria criteria) {
        List<Article> articles = blogRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder));
        return articleMapper.toDto(articles);
    }
}