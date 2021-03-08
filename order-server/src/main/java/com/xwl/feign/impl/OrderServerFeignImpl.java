package com.xwl.feign.impl;

import com.xwl.feign.OrderServerFeignApi;
import com.xwl.mapper.OrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: 薛
 * @Date: 2021/2/23 15:55
 * @Description:
 */
@AllArgsConstructor
@RestController
@SuppressWarnings("all")
public class OrderServerFeignImpl implements OrderServerFeignApi {
    @Autowired
    private OrderMapper orderMapper;


    @Override
    public Boolean deleOrderInfoByCode(String orderCode) {
        try {
            orderMapper.deleteByOrderCode(orderCode);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
