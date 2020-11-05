package tech.lancelot.mapstruct.sys;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import tech.lancelot.domain.sys.Dict;
import tech.lancelot.dto.sys.DictSmallDto;

/**
 * @author lancelot
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DictSmallMapper extends BaseMapper<DictSmallDto, Dict> {

}
