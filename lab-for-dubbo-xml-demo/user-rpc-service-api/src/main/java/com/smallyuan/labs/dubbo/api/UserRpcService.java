package com.smallyuan.labs.dubbo.api;

import com.smallyuan.labs.dubbo.dto.UserDTO;

public interface UserRpcService {

    UserDTO get(Integer id);

}
