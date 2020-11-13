package tech.lancelot.service.blog;

import org.springframework.data.domain.Pageable;
import tech.lancelot.dto.blog.ArchiveDto;
import tech.lancelot.dto.blog.ArchiveQueryCriteria;
import tech.lancelot.dto.blog.TagDto;
import tech.lancelot.dto.blog.TagQueryCriteria;

import java.util.List;

/**
 * @author lancelot
 */
public interface ArchiveService {

    /**
     * 查询全部
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Object queryAll(ArchiveQueryCriteria criteria, Pageable pageable);

    /**
     * 查询全部不分页
     * @return /
     */
    List<ArchiveDto> getAll();
}
