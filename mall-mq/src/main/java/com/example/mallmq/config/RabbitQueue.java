package com.example.mallmq.config;

import org.apache.commons.lang3.StringUtils;

/**
* @Description:接收生产者在配置文件配置的队列、路由、交换机等信息bean
* @Author: yl
* @Date: 2019/11/12
*/
public class RabbitQueue {
    //队列名称
    private String name;
    //是否持久化,默认true
    private String durable;
    //是否为排它队列,默认false
    private String exclusive;
    //是否自动删除,默认false
    private String autoDelete;
    //交换机名称,已定义三种分别为directExchange,topicExchange,fanoutExchange,默认为topicExchange
    private String exchangeName;
    //路由key,默认为队列名称
    private String routingKey;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getDurable() {
        return org.apache.commons.lang3.StringUtils.isEmpty(durable) ? true : Boolean.valueOf(durable);
    }

    public void setDurable(String durable) {
        this.durable = durable;
    }

    public boolean getExclusive() {
        return org.apache.commons.lang3.StringUtils.isEmpty(exclusive) ? false : Boolean.valueOf(exclusive);
    }

    public void setExclusive(String exclusive) {
        this.exclusive = exclusive;
    }

    public boolean getAutoDelete() {
        return org.apache.commons.lang3.StringUtils.isEmpty(autoDelete) ? false : Boolean.valueOf(autoDelete);
    }

    public void setAutoDelete(String autoDelete) {
        this.autoDelete = autoDelete;
    }

    public String getExchangeName() {
        return org.apache.commons.lang3.StringUtils.isEmpty(exchangeName) ? RabbitExchageConstants.TOPIC_EXCHANGE : exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getRoutingKey() {
        return StringUtils.isEmpty(routingKey) ? this.name : routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }
}
