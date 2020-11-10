package tech.lancelot.dto.system;

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
