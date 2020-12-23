package com.smallyuan.labs.sharding.mapper;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.smallyuan.labs.sharding.dataobject.OrderDO;

import java.util.List;

@Repository
public interface OrderMapper {
    OrderDO selectById(@Param("id") Integer id);

    void insert(OrderDO orderDO);

    List<OrderDO> selectListByUserId(@Param("userId") Integer userId);
}
