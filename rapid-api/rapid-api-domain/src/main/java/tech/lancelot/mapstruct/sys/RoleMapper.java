package tech.lancelot.mapstruct.sys;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import tech.lancelot.domain.sys.Role;
import tech.lancelot.dto.sys.RoleDto;

/**
 * @author lancelot
 */
@Mapper(componentModel = "spring", uses = {MenuMapper.class, DeptMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper extends BaseMapper<RoleDto, Role> {

}
