package com.xwl.mqconfig;

import com.xwl.util.RabbitParamUtils;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: 薛
 * @Date: 2021/2/19 09:57
 * @Description:
 */
@Component
@Configuration
@SuppressWarnings("all")
public class RabbitmqConfig {
    //声明交换机并加载Spring容器
    @Bean("serverTopicExchange")
    public Exchange topicExchange() {
        return ExchangeBuilder.topicExchange(RabbitParamUtils.ITEM_TOPIC_EXCHANGE).durable(true).build();
    }
    //声明主队列->下辖绑定死信队列
    @Bean("serverTopicQueue")
    public Queue topicServerQueue() {
        //绑定死信队列,用于消息消费时间时存储废弃消息的队列
        Map<String, Object> args = new HashMap<>();
        //声明死信交换机
        args.put("x-dead-letter-exchange", RabbitParamUtils.DEAL_TOPIC_EXCHANGE);
        //声明死信路由键(绑定)->用于队列接收路由指定键
        args.put("x-dead-letter-routing-key", "DelayKey");
        //声明主队列如果发生堵塞或其它-10秒自动消费消息
        args.put("x-message-ttl",10000);
        return QueueBuilder.durable(RabbitParamUtils.ITEM_QUEUE).withArguments(args).build();
    }
    //主队列绑定交换机以及-路由(此处采用TOPC通配符)
    @Bean
    public Binding itemQueueExchange(@Qualifier("serverTopicQueue") Queue queue,
                                     @Qualifier("serverTopicExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(RabbitParamUtils.ITEM_ROUTKEY).noargs();
    }
    //声明死信队列
    @Bean("dealQueue")
    public Queue dealQueue() {
        return QueueBuilder.durable(RabbitParamUtils.DEAL_QUEUE).build();
    }
    //声明死信交换机
    @Bean("serverDealExchange")
    public Exchange dealExchange(){
        return ExchangeBuilder.topicExchange("server_deal_exchange").durable(true).build();
    }
    //死信队列绑定交换机以及路由key(指定路由键key值)
    @Bean
    public Binding dealQueueExchange(@Qualifier("dealQueue") Queue queue,
                                     @Qualifier("serverDealExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(RabbitParamUtils.DEAL_ROUTKEY).noargs();
    }
}
