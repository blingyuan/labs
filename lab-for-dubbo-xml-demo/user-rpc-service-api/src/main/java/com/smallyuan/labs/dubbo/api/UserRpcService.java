package com.smallyuan.labs.dubbo.api;

import com.smallyuan.labs.dubbo.dto.UserAddDTO;
import com.smallyuan.labs.dubbo.dto.UserDTO;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;

public interface UserRpcService {

    UserDTO get(@NotNull(message="用户编号不能为空") Integer id) throws ConstraintViolationException;

    Integer add(UserAddDTO userAddDTO) throws ConstraintViolationException;

}
