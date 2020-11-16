package tech.lancelot.service.blog;

import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.lancelot.domain.blog.Article;
import tech.lancelot.domain.blog.ArticleEs;
import tech.lancelot.dto.blog.ArticleDto;
import tech.lancelot.dto.blog.ArticleQueryCriteria;
import tech.lancelot.exceptions.BadRequestException;
import tech.lancelot.mapstruct.blog.ArticleMapper;
import tech.lancelot.repository.blog.ArticleEsRepository;
import tech.lancelot.repository.blog.ArticleRepository;
import tech.lancelot.utils.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lancelot
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "article")
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    private final ArticleEsRepository articleEsRepository;
    private final ElasticsearchTemplate elasticsearchTemplate;
    private final HighLightResultMapper highLightResultMapper;

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

        // 同步elasticsearch
        ArticleEs articleEs = convertToEsEntity(resources);
        articleEsRepository.save(articleEs);
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

        // 同步elasticsearch
        ArticleEs articleEs = convertToEsEntity(article);
        articleEsRepository.save(articleEs);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<String> ids) {
        articleRepository.deleteAllByIdIn(ids);
        // 同步elasticsearch
        articleEsRepository.deleteAllByIdIn(ids);
    }

    /**
     * 全文检索
     * @param searchFields
     * @param keyword
     * @param pageable
     * @return
     */
    @Override
    public Page<ArticleEs> highLightQuery(String[] searchFields, String keyword, Pageable pageable) {

         if(searchFields==null||searchFields.length<=0) {
             throw new BadRequestException("检索的字段不能为空！");
         }
        if(StringUtils.isBlank(keyword)) {
            throw new BadRequestException("检索的关键字不能为空！");
        }

        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
        searchQueryBuilder.withPageable(pageable);
        for (String searchField : searchFields) {
            searchQueryBuilder.withQuery(QueryBuilders.termQuery(searchField, keyword))
                    .withHighlightFields(new HighlightBuilder.Field(searchField)
                            //替换默认高亮标签<em></em>
                            .preTags("<span style=\"color:red\">").postTags("</span>"));

        }
        Page<ArticleEs> tls = elasticsearchTemplate.queryForPage(searchQueryBuilder.build(), ArticleEs.class, highLightResultMapper);
        return tls;
    }

    /**
     * 转换为Elasticsearch实体
     *
     * @param article
     * @return
     */
    private ArticleEs convertToEsEntity(Article article) {
        ArticleEs articleEs = new ArticleEs();
        articleEs.setId(article.getId());
        articleEs.setTitle(article.getTitle());
        articleEs.setSummary(article.getSummary());
        articleEs.setContent(article.getContent());
        articleEs.setCategory(article.getCategory().getName());
        articleEs.setTags(String.join(",", article.getTags().stream().map(x -> x.getName()).collect(Collectors.toList())));
        articleEs.setCreateTime(article.getCreateTime());
        return articleEs;
    }
}