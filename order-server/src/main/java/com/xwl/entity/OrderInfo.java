package com.xwl.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: 薛
 * @Date: 2021/2/19 10:22
 * @Description:
 */
@TableName("order_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("All")
public class OrderInfo {
    private Long id;
    private String userName;
    private String address;//地址
    private String orderCode;//订单编号
    private Integer comModity;//商品类型
}
