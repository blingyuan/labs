package com.smallyuan.labs.sharding2;

import com.smallyuan.labs.sharding2.dataobject.OrderDO;
import com.smallyuan.labs.sharding2.mapper.OrderMapper;
import org.apache.shardingsphere.api.hint.HintManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class OrderMapperTest {

    @Autowired
    private OrderMapper orderMapper;

    // 测试从库的负载均衡
    @Test
    public void testSelectById() {
        for (int i = 0; i < 2; i++) {
            OrderDO order = orderMapper.selectById(1);
            System.out.println(order);
        }
    }

    // 测试强制访问主库
    @Test
    public void testSelectById02() {
        try(HintManager hintManager = HintManager.getInstance()) {
            // 设置强制访问主库
            hintManager.setMasterRouteOnly();
            // 执行查询
            OrderDO order = orderMapper.selectById(1);
            System.out.println(order);
        }
    }

    @Test
//    @Transactional
    public void testInsert() {
        OrderDO orderDO = new OrderDO();
        orderDO.setUserId(10);
        orderDO.setId(21L);
        orderMapper.insert(orderDO);
        System.out.println(orderMapper.selectById(21));
    }

}
