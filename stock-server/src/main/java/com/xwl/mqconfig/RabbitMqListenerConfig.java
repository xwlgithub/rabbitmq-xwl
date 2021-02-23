package com.xwl.mqconfig;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.xwl.entity.StokOrder;
import com.xwl.mapper.StokMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @Auther: 薛
 * @Date: 2021/2/19 17:26
 * @Description:
 */
@Component
@SuppressWarnings("all")
public class RabbitMqListenerConfig {
    @Autowired
    private StokMapper stokMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostConstruct
    public void init() {
        this.stokMapper = stokMapper;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 监听队列
     *
     * @param message
     * @param channel
     * @throws Exception
     */
    @RabbitListener(queues = "server_queue")
    @Transactional(rollbackFor = Exception.class)
    public void process(Message message, Channel channel) throws Exception {
        String messageId = message.getMessageProperties().getMessageId();
        String msg = new String(message.getBody(), "UTF-8");
        System.out.println("信息体" + msg + ",消息id:" + messageId);
        System.out.println("信息体" + msg);
//        JSONObject jsonObject = JSONObject.parseObject(msg);
//        String orderId = jsonObject.getString("orderId");
        if (StringUtils.isEmpty(msg)) {
            System.out.println("订单ID为空");
            return;
        }
        //查询订单唯一性,幂等性问题
        String orderCodeByCode = this.stokMapper.getOrderCodeByCode(msg);
        if (!StringUtils.isEmpty(orderCodeByCode)) {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            return;
        }
        //保存库存
        StokOrder stokOrder = new StokOrder();
        stokOrder.setComModity(msg);
        int isSuccess = this.stokMapper.insert(stokOrder);
        if (isSuccess > 0) {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            return;
        }

    }
}
