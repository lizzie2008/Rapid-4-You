package tech.lancelot.mapstruct.sys;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import tech.lancelot.domain.sys.Dept;
import tech.lancelot.dto.sys.DeptDto;

/**
 * @author lancelot
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeptMapper extends BaseMapper<DeptDto, Dept> {
}