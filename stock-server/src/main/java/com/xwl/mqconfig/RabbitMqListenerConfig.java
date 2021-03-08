package com.xwl.mqconfig;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.xwl.entity.StokOrder;
import com.xwl.mapper.StokMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;

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
//    @Autowired
//    private OrderServerFeignApi orderServerFeignApi;

    @PostConstruct
    public void init() {
        this.stokMapper = stokMapper;
        this.redisTemplate = redisTemplate;
        //this.orderServerFeignApi = orderServerFeignApi;
    }

    /**
     * 监听主队列
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
            System.out.println("订单id" + msg + "为空");
            return;
        }
        //查询订单唯一性,防止重复下单,(幂等性)保证订单ID唯一
        try {
            int x=1/0;
            String orderCodeByCode = this.stokMapper.getOrderCodeByCode(msg);
            //如果订单id在数据库中已存在,即签收当前消息
            if (!StringUtils.isEmpty(orderCodeByCode)) {
                //签收消息
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                //告知生产者,当前消息已被消费者签收,
                this.redisTemplate.opsForValue().set(msg, true);
                return;
            }
            //保存库存
            StokOrder stokOrder = new StokOrder();
            stokOrder.setComModity(msg);
            int isSuccess = this.stokMapper.insert(stokOrder);
            //如果库存成功插入数据,即签收当前消息并告知生产者当前订单消息已被签收
            if (isSuccess > 0) {
                //签收消息
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                //告知生产者,当前消息已被消费者签收,
                this.redisTemplate.opsForValue().set(msg, true);
                return;
            }
        } catch (Exception e) {
            System.out.println("当前消息"+msg+"在消费者消费时失败,放入死信队列待开发人员核对");
        }
        finally {
            /**
             * 当消费成功,即修改缓存中默认订单消费布尔值
             * true:    消费成功
             * false:   消费失败,放至死信队列,待相关人员核实或其他方式成功消费..
             */
            if (!(Boolean) this.redisTemplate.opsForValue().get(msg)) {
               // this.orderServerFeignApi.deleOrderInfoByCode(msg);
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
                return;
            }
        }
    }
    /**
     * 监听死信队列-
     *
     * @param message
     * @param map
     * @param channel
     * @throws InterruptedException
     * @throws IOException
     */
    @RabbitListener(queues = "server_deal_queue")
    public static void sendMiss2(Message message, @Headers Map<String, Object> map, Channel channel) throws InterruptedException, IOException {
        System.out.println("进入死信");
        String msg = new String(message.getBody(), "UTF-8");

        //channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        System.out.println(msg + "消费失败,待核实");
    }
}
