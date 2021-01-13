package com.smallyuan.labs.dubbo.service;

import com.smallyuan.labs.dubbo.api.UserRpcService;
import com.smallyuan.labs.dubbo.dto.UserDTO;
import org.springframework.stereotype.Service;


@Service
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
