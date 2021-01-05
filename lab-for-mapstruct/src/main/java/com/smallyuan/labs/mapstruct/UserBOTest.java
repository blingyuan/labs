package com.smallyuan.labs.mapstruct;

import com.smallyuan.labs.mapstruct.bo.UserBO;
import com.smallyuan.labs.mapstruct.convert.UserConvert;
import com.smallyuan.labs.mapstruct.dataobject.UserDO;

public class UserBOTest {

    public static void main(String[] args) {
        UserDO userDO = new UserDO();
        userDO.setId(1);
        userDO.setUsername("smallyuan");
        userDO.setPassword("secret");

        UserBO userBO = UserConvert.INSTANCE.convert(userDO);
        System.out.println(userBO.getId());
        System.out.println(userBO.getUsername());
        System.out.println(userBO.getPassword());
        System.out.println(userBO.getTest());
    }
}
