package tech.lancelot.service.blog;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.lancelot.domain.blog.Tag;
import tech.lancelot.dto.blog.TagDto;
import tech.lancelot.dto.blog.TagQueryCriteria;
import tech.lancelot.mapstruct.blog.TagMapper;
import tech.lancelot.repository.blog.TagRepository;
import tech.lancelot.utils.PageUtil;
import tech.lancelot.utils.QueryHelp;

import java.util.List;

/**
 * @author lancelot
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "tag")
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public Object queryAll(TagQueryCriteria criteria, Pageable pageable) {
        Page<Tag> page = tagRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(tagMapper::toDto));
    }

    @Override
    public List<TagDto> queryAll(TagQueryCriteria criteria) {
        List<Tag> categories = tagRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder));
        return tagMapper.toDto(categories);
    }
}