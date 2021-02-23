package com.xwl.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: 薛
 * @Date: 2021/2/19 17:30
 * @Description:
 */
@TableName("stok_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StokOrder {
    private Long id;
    private String comModity;//商品类型
    private Integer stockCount;//库存
}
