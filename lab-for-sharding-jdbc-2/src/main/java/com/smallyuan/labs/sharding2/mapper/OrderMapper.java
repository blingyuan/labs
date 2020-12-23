package com.smallyuan.labs.sharding2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smallyuan.labs.sharding2.dataobject.OrderDO;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper extends BaseMapper<OrderDO> {

}