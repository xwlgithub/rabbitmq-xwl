package com.xwl.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: 薛
 * @Date: 2021/1/30 16:18
 * @Description:
 */
@RestController
@RequestMapping("xwl")
@AllArgsConstructor
public class OrderController {

    /**
     * 举例说明：订单表,库存表
     * 1.根据当前商品信息类型及其它字段查询库存表是否有库存
     *      如果有及创建订单       反之返回死信队列统一返回商品已抢空
     *            <I>
     *                订单已创建之后,更新库存信息,也就是减库存
     *                      <I>
     *                          如果订单创建成功,更新库存信息,(如果库存更新失败,即监听器无法确认消费消息,启动重试机制(新建一个补单队列))
     *                      </I>
     *            </I>
     */

}
