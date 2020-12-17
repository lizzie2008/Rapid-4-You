package tech.lancelot.controller.storage;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.lancelot.annotations.Log;
import tech.lancelot.domain.tools.StorageConfig;
import tech.lancelot.dto.AliOssDto;
import tech.lancelot.service.storage.AliOssService;
import tech.lancelot.utils.FileUtil;
import tech.lancelot.utils.PageUtil;
import tech.lancelot.vo.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lancelot
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "工具：阿里云存储管理")
@RequestMapping("/api/ali-oss")
public class AliOssController {

    private final AliOssService aliOssService;

    @GetMapping(value = "/config")
    public Result queryConfig() {
        return Result.success(aliOssService.getConfig());
    }

    @Log("配置阿里云Oss")
    @ApiOperation("配置阿里云Oss")
    @PutMapping(value = "/config")
    public Result updateConfig(@Validated @RequestBody StorageConfig config) {
        aliOssService.update(config);
        return Result.success();
    }

    @Log("获取OSS目录")
    @ApiOperation("获取OSS目录")
    @GetMapping
    @PreAuthorize("@el.check('ali-oss:list')")
    public Result getFolders(@RequestParam(defaultValue = "") String prefix) {
        return Result.success(aliOssService.getFolders(prefix));
    }

    @Log("上传文件")
    @ApiOperation("上传文件")
    @PostMapping(value = "/upload")
    public Result upload(@RequestParam MultipartFile file, @RequestParam(defaultValue = "") String prefix) {
        aliOssService.upload(file, prefix);
        return Result.success();
    }
}
