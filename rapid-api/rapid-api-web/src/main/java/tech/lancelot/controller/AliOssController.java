package tech.lancelot.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.lancelot.annotations.Log;
import tech.lancelot.dto.AliOssDto;
import tech.lancelot.utils.FileUtil;
import tech.lancelot.utils.PageUtil;
import tech.lancelot.vo.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lancelot
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "工具：阿里云存储管理")
@RequestMapping("/api/ali-oss")
public class AliOssController {


    @Log("获取OSS目录")
    @ApiOperation("获取OSS目录")
    @GetMapping
    @PreAuthorize("@el.check('ali-oss:list')")
    public Result getFolders(@RequestParam(defaultValue = "") String prefix) {

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4FyQbUcMZrEkWxivopbU";
        String accessKeySecret = "jk1IvVILCwpBAvu7XUOQeKeg6XQqhL";
        String bucketName = "rapid4you";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 构造ListObjectsRequest请求。
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);

        // 设置正斜线（/）为文件夹的分隔符。
        listObjectsRequest.setDelimiter("/");

        // 列出fun目录下的所有文件和文件夹。
        listObjectsRequest.setPrefix(prefix);

        ObjectListing listing = ossClient.listObjects(listObjectsRequest);

        List<AliOssDto> result = new ArrayList<>();
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

//        result.sort(Comparator.comparing(AliOssDto::getSize));

        return Result.success(PageUtil.toPage(result, result.size()));
    }

}
