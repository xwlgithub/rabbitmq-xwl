package com.xwl.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

public interface IOrderService  extends RabbitTemplate.ConfirmCallback {
    Boolean placeOrder(String userName);
}
