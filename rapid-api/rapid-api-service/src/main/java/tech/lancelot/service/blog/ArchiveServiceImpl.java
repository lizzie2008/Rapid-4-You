package tech.lancelot.service.blog;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.lancelot.domain.blog.Archive;
import tech.lancelot.dto.blog.ArchiveDto;
import tech.lancelot.dto.blog.ArchiveQueryCriteria;
import tech.lancelot.mapstruct.blog.ArchiveMapper;
import tech.lancelot.repository.blog.ArchiveRepository;
import tech.lancelot.utils.PageUtil;
import tech.lancelot.utils.QueryHelp;

import java.util.List;

/**
 * @author lancelot
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "archive")
public class ArchiveServiceImpl implements ArchiveService {

    private final ArchiveRepository archiveRepository;
    private final ArchiveMapper archiveMapper;

    @Override
    public Object queryAll(ArchiveQueryCriteria criteria, Pageable pageable) {
        Page<Archive> page = archiveRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteriaQuery, criteriaBuilder, criteria), pageable);
        return PageUtil.toPage(page.map(archiveMapper::toDto));
    }

    @Override
    public List<ArchiveDto> getAll() {
        List<Archive> archives = archiveRepository.findAll();
        return archiveMapper.toDto(archives);
    }
}