package com.example.mallmq.config;
/**
* @Description:给rabbit三种类型的交换机策略及延迟队列设置默认名称
* @Author: yl
* @Date: 2019/11/12
*/
public class RabbitExchageConstants {

    //direct：发送到指定的rouingKey的队列
    public static final String DIRECT_EXCHANGE ="direct_exchange";

    //fanout: 发送到绑定这个交换机的所有队列，不需要rouingKey
    public static final String FANOUT_EXCHANGE = "fanout_exchange";

    //topic（广播）： 发送到匹配到这个消息的rouingKey的队列，就是每个队列都有其关心的主题(可以是通配符)
    public static final String TOPIC_EXCHANGE = "topic_exchange";

    //delay 通过插件支持的延时队列
    public static final String DELAY_EXCHANGE = "delay_exchange";

}
