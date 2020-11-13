package tech.lancelot.service.blog;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.lancelot.domain.blog.Article;
import tech.lancelot.domain.blog.Category;
import tech.lancelot.dto.blog.ArticleDto;
import tech.lancelot.dto.blog.ArticleQueryCriteria;
import tech.lancelot.dto.blog.CategoryDto;
import tech.lancelot.dto.blog.CategoryQueryCriteria;
import tech.lancelot.mapstruct.blog.ArticleMapper;
import tech.lancelot.mapstruct.blog.CategoryMapper;
import tech.lancelot.repository.blog.ArticleRepository;
import tech.lancelot.repository.blog.CategoryRepository;
import tech.lancelot.utils.PageUtil;
import tech.lancelot.utils.QueryHelp;

import java.util.List;

/**
 * @author lancelot
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "category")
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Object queryAll(CategoryQueryCriteria criteria, Pageable pageable) {
        Page<Category> page = categoryRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteriaQuery, criteriaBuilder, criteria), pageable);
        return PageUtil.toPage(page.map(categoryMapper::toDto));
    }

    @Override
    public List<CategoryDto> getAll() {
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toDto(categories);
    }
}