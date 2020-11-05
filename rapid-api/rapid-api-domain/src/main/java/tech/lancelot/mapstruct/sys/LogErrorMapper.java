package tech.lancelot.mapstruct.sys;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import tech.lancelot.domain.sys.Log;
import tech.lancelot.dto.sys.LogErrorDTO;

/**
 * @author lancelot
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LogErrorMapper extends BaseMapper<LogErrorDTO, Log> {

}
