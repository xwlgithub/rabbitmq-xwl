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
    //声明主队列
    @Bean("serverTopicQueue")
    public Queue topicServerQueue() {
        return QueueBuilder.durable(RabbitParamUtils.ITEM_QUEUE).build();
    }
    //主队列绑定交换机以及-路由(采用TOPC通配符)
    @Bean
    public Binding itemQueueExchange(@Qualifier("serverTopicQueue") Queue queue,
                                     @Qualifier("serverTopicExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(RabbitParamUtils.ITEM_ROUTKEY).noargs();
    }
    //声明补单
    @Bean("dealQueue")
    public Queue dealQueue() {
        return QueueBuilder.durable(RabbitParamUtils.DEAL_QUEUE).build();
    }
    //死信队列绑定交换机以及路由key(指定路由键key值)
    @Bean
    public Binding dealQueueExchange(@Qualifier("dealQueue") Queue queue,
                                     @Qualifier("serverTopicExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(RabbitParamUtils.DEAL_ROUTKEY).noargs();
    }
}
