package tech.lancelot.service.storage;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tech.lancelot.domain.tools.StorageConfig;
import tech.lancelot.dto.AliOssDto;
import tech.lancelot.repository.storage.StorageConfigRepository;
import tech.lancelot.utils.FileUtil;
import tech.lancelot.utils.ValidationUtil;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author lancelot
 */
@Service
@RequiredArgsConstructor

public class AliOssServiceImpl implements AliOssService {

    private final StorageConfigRepository storageConfigRepository;

    @Value("${upload.max-size}")
    private Long maxSize;

    @Override
    public StorageConfig getConfig() {
        Optional<StorageConfig> storageConfig = storageConfigRepository.findById("ali-oss");
        if(!storageConfig.isPresent()) {
            throw new RuntimeException("未能匹配[config_id]为ali-oss的相关配置，请在[tool_storage_config]表中进行配置");
        }
        return storageConfig.get();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StorageConfig resources) {
        storageConfigRepository.save(resources);
    }

    @Override
    public List<AliOssDto> getFolders(String prefix) {
        List<AliOssDto> result = null;
        try {
            // 创建OSSClient实例。
            StorageConfig config = getConfig();
            OSS ossClient = new OSSClientBuilder()
                    .build(config.getHost(), config.getAccessKey(), config.getSecretKey());
            // 构造ListObjectsRequest请求。
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest(config.getBucket());

            // 设置正斜线（/）为文件夹的分隔符。
            listObjectsRequest.setDelimiter("/");

            // 列出fun目录下的所有文件和文件夹。
            listObjectsRequest.setPrefix(prefix);
            ObjectListing listing = ossClient.listObjects(listObjectsRequest);
            result = new ArrayList<>();

            // 遍历所有文件夹
            for (String commonPrefix : listing.getCommonPrefixes()) {
                AliOssDto aliOssDto = new AliOssDto();
                aliOssDto.setType("目录");
                aliOssDto.setName(commonPrefix);
                result.add(aliOssDto);
            }
            // 遍历所有文件
            for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
                if (!objectSummary.getKey().equals(prefix)) {
                    AliOssDto aliOssDto = new AliOssDto();
                    String suffix = FileUtil.getExtensionName(objectSummary.getKey());
                    String type = FileUtil.getFileType(suffix);
                    aliOssDto.setType(type);
                    aliOssDto.setName(objectSummary.getKey());
                    aliOssDto.setSize(objectSummary.getSize());
                    aliOssDto.setLastModified(objectSummary.getLastModified());
                    result.add(aliOssDto);
                }
            }
            // 关闭OSSClient。
            ossClient.shutdown();
        } catch (Exception e) {
            throw new RuntimeException("获取OSS目录出错:" + e.getMessage());
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void upload(MultipartFile file, String prefix) {
        try {
            FileUtil.checkSize(maxSize, file.getSize());
            StorageConfig config = getConfig();

            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder()
                    .build(config.getHost(), config.getAccessKey(), config.getSecretKey());

            // <yourObjectName>表示上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
            ossClient.putObject(config.getBucket(), prefix + file.getOriginalFilename(),
                    new ByteArrayInputStream(file.getBytes()));

            // 关闭OSSClient。
            ossClient.shutdown();
        } catch (Exception e) {
            throw new RuntimeException("上传OSS文件出错:" + e.getMessage());
        }
    }
}