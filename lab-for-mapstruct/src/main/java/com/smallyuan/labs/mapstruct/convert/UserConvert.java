package com.smallyuan.labs.mapstruct.convert;

import com.smallyuan.labs.mapstruct.bo.UserBO;
import com.smallyuan.labs.mapstruct.dataobject.UserDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    UserBO convert(UserDO userDO);
}
