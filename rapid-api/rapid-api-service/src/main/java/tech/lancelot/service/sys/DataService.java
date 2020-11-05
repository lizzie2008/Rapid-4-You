package tech.lancelot.service.sys;

import tech.lancelot.dto.sys.UserDto;

import java.util.List;

/**
 * @author lancelot
 */
public interface DataService {

    /**
     * 获取数据权限
     * @param user /
     * @return /
     */
    List<Long> getDeptIds(UserDto user);
}