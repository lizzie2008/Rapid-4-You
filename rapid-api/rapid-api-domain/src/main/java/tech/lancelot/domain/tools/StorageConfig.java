package tech.lancelot.domain.tools;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author lancelot
 */
@Data
@Entity
@Table(name = "tool_storage_config")
public class StorageConfig implements Serializable {

    @Id
    @Column(name = "config_id")
    @ApiModelProperty(value = "配置类型id：ali-oss|其他待加入")
    private String id;

    @NotBlank
    @ApiModelProperty(value = "accessKey")
    private String accessKey;

    @NotBlank
    @ApiModelProperty(value = "secretKey")
    private String secretKey;

    @NotBlank
    @ApiModelProperty(value = "存储空间名称作为唯一的 Bucket 识别符")
    private String bucket;

    @NotBlank
    @ApiModelProperty(value = "外链域名，可自定义")
    private String host;

    @ApiModelProperty(value = "备注")
    private String remark;
}
