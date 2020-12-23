package com.smallyuan.labs.sharding;

import com.smallyuan.labs.sharding.dataobject.OrderConfigDO;
import com.smallyuan.labs.sharding.mapper.OrderConfigMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OrderConfigMapperTest {

    @Autowired
    private OrderConfigMapper orderConfigMapper;

    @Test
    public void testSelectById() {
        OrderConfigDO orderConfigDO = orderConfigMapper.selectById(1);
        System.out.println(orderConfigDO);
    }

}
