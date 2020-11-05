package tech.lancelot.dto.sys;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lancelot
 */
@Data
public class DeptSmallDto implements Serializable {

    private Long id;

    private String name;
}
