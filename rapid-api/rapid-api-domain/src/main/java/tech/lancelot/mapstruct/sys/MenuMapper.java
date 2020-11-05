package tech.lancelot.mapstruct.sys;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import tech.lancelot.domain.sys.Menu;
import tech.lancelot.dto.sys.MenuDto;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapper extends BaseMapper<MenuDto, Menu> {
}