package tech.lancelot.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lancelot
 */
@Getter
@Setter
public class AliOssDto implements Serializable {
    private String type;
    private String name;
    private long size;
    private Date lastModified;
}
