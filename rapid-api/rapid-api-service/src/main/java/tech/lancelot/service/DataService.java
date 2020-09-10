package tech.lancelot.service;

import tech.lancelot.dto.UserDto;

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