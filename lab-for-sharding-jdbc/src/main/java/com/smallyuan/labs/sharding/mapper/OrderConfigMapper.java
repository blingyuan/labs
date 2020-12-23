package com.smallyuan.labs.sharding.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.smallyuan.labs.sharding.dataobject.OrderConfigDO;

@Repository
public interface OrderConfigMapper {

    OrderConfigDO selectById(@Param("id") Integer id);

}
