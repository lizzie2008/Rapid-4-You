package tech.lancelot.service.system;

import tech.lancelot.dto.system.UserDto;

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