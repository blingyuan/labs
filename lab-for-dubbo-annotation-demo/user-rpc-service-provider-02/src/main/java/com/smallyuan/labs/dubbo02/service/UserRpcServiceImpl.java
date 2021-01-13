package com.smallyuan.labs.dubbo02.service;


import com.smallyuan.labs.dubbo02.api.UserRpcService;
import com.smallyuan.labs.dubbo02.dto.UserDTO;
import org.apache.dubbo.config.annotation.Service;


@Service(version = "${dubbo.provider.UserRpcService.version}")
public class UserRpcServiceImpl implements UserRpcService {

    @Override
    public UserDTO get(Integer id) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        userDTO.setName("111");
        userDTO.setGender(1);
        return userDTO;
    }
}
