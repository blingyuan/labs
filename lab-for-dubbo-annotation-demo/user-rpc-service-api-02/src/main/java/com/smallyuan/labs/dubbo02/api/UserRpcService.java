package com.smallyuan.labs.dubbo02.api;


import com.smallyuan.labs.dubbo02.dto.UserDTO;

public interface UserRpcService {

    UserDTO get(Integer id);

}
