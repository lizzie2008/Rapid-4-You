package tech.lancelot.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lancelot
 */
@Data
public class RoleSmallDto implements Serializable {

    private Long id;

    private String name;

    private Integer level;

    private String dataScope;
}
