package com.xwl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.xwl.entity.OrderInfo;
import com.xwl.mapper.OrderMapper;
import com.xwl.service.IOrderService;
import com.xwl.util.RabbitParamUtils;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

/**
 * @Auther: 薛
 * @Date: 2021/2/19 10:37
 * @Description:
 */
@Service
@AllArgsConstructor
public class OrderServiceImpl implements IOrderService {
    //消息发送API
    private RabbitTemplate rabbitTemplate;
    private OrderMapper orderMapper;
    /**
     * 订单业务处理
     * @param userName
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean placeOrder(String userName) throws RuntimeException{
        //随机生成订单号--这里举例为UUID
        String orderId = UUID.randomUUID().toString();
        //消息发送
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserName(userName);
        orderInfo.setAddress("。。。");
        orderInfo.setOrderCode(orderId);
        //订单保存
        int isSuccess=0;
        try {
            isSuccess= orderMapper.insert(orderInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isSuccess<0){
            throw  new RuntimeException("创建订单失败!1");
        }
        //发送消息到消费者
        sendMessages(orderId);
        return true;
    }

    /**
     * 发送消息
     * @param orderId   订单ID
     */
    private void sendMessages(String orderId){
        JSONObject jsonObect = new JSONObject();
        jsonObect.put("orderId", orderId);
        String msg = jsonObect.toJSONString();
        System.out.println("msg:" + msg);
        //消息创建为json格式 发送
        MessageBuilder.withBody(msg.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setContentEncoding("utf-8")
                .setMessageId(orderId);
        CorrelationData correlationData=new CorrelationData(orderId);
        this.rabbitTemplate.setMandatory(true);
        // TODO 消息发送确认
        this.rabbitTemplate.setConfirmCallback(this);
        //如果下单成功即推送消息,告知监听器订单已插入数据库
        rabbitTemplate.convertAndSend(RabbitParamUtils.ITEM_TOPIC_EXCHANGE,"item.msg",orderId,correlationData);
    }

    //生产消息确认机制 生产者往服务器端发送消息的时候 采用应答机制
    @Override
    @Transactional
    public void confirm(CorrelationData correlationData, boolean ack, String s) {
        String id = correlationData.getId();
        System.out.println("消息ID"+id);
        if (ack){
            //int x=1/0;
            System.out.println("消息发送成功并且消费方成功消费");
        }else {
            sendMessages(id);
            System.out.println("补发消息");
        }
    }
}
