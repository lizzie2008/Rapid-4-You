package tech.lancelot.service.storage;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import tech.lancelot.domain.blog.Article;
import tech.lancelot.domain.tools.StorageConfig;
import tech.lancelot.dto.AliOssDto;
import tech.lancelot.dto.blog.ArchiveDto;
import tech.lancelot.dto.blog.ArchiveQueryCriteria;
import tech.lancelot.dto.blog.ArticleDto;

import java.io.IOException;
import java.util.List;

/**
 * @author lancelot
 */
public interface AliOssService {

    /**
     * 查询配置
     *
     * @return
     */
    StorageConfig getConfig();

    /**
     * 更新
     *
     * @param config /
     */
    void update(StorageConfig config);

    /**
     * 获取目录
     *
     * @param prefix
     * @return
     */
    List<AliOssDto> getFolders(String prefix);

    /**
     * 上传文件
     * @param file
     * @param prefix
     */
    void upload(MultipartFile file,String prefix);
}
