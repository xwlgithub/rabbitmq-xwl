package com.xwl.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Auther: 薛
 * @Date: 2021/2/19 10:57
 * @Description:
 */
@Component
@Configuration
public class RabbitParamUtils {
    //@Value("${paramMq.serverExchange}")
    public static String ITEM_TOPIC_EXCHANGE="server_exchange";
    //@Value("${paramMq.serverQueue}")
    public static String ITEM_QUEUE="server_queue";
    //@Value("${paramMq.serverRoutKey}")
    public static String ITEM_ROUTKEY="item.#";
    //@Value("${paramMq.serverDealExchange}")
    public static String DEAL_TOPIC_EXCHANGE="server_deal_exchange";
    //@Value("${paramMq.serverDealQueue}")
    public static String DEAL_QUEUE="server_deal_queue";
    // @Value("${paramMq.serverDealRoutKey}")
    public static String DEAL_ROUTKEY="DelayKey";
}
