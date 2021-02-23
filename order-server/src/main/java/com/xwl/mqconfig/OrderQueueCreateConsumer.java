package com.xwl.mqconfig;

import com.rabbitmq.client.Channel;
import com.xwl.entity.OrderInfo;
import com.xwl.mapper.OrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @Auther: 薛
 * @Date: 2021/2/20 15:55
 * @Description:
 */
@Component
@Configuration
@AllArgsConstructor
@SuppressWarnings("all")
public class OrderQueueCreateConsumer {
    @Autowired
    private OrderMapper orderMapper;
    @PostConstruct
    public void init(){
        this.orderMapper=orderMapper;
    }

    @RabbitListener(queues = "server_deal_queue")
    public void process(Message message, Channel channel) throws Exception{
        String messageId = message.getMessageProperties().getMessageId();
        String msg = new String(message.getBody(), "UTF-8");
        System.out.println("补单消费者" + msg + ",消息id:" + messageId);
        OrderInfo info = this.orderMapper.findOrderInfoByOrderCode(msg);
        if (info != null) {
            System.out.println("订单已经存在 无需补单  orderId:" + info);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            return;
        }
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setAddress("。。。");
        orderInfo.setOrderCode(msg);
        //订单保存
        int isSuccess=0;
        try {
            isSuccess= orderMapper.insert(orderInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (isSuccess>=0){
                System.out.println("补单添加成功！创建订单号为"+msg);
                channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
                return;
            }
        } catch (Exception e) {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        }

    }

}
