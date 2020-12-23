package com.smallyuan.labs.sharding;

import com.smallyuan.labs.sharding.dataobject.OrderDO;
import com.smallyuan.labs.sharding.mapper.OrderMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class OrderMapperTest {

    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void testSelectById() {
        OrderDO order = orderMapper.selectById(1);
        System.out.println(order);
    }

    @Test
    public void testSelectListByUserId() {
        List<OrderDO> orders = orderMapper.selectListByUserId(1);
        System.out.println(orders.size());
    }

    @Test
    public void testInsert() {
        OrderDO orderDO = new OrderDO();
        orderDO.setUserId(1);
        orderMapper.insert(orderDO);
    }



}
