package com.example.mallmq.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@ConfigurationProperties(prefix = "rabbitmqconfig")
public class RabbitConfig {

    List<RabbitQueue> queues = new ArrayList<RabbitQueue>();

    public List<RabbitQueue> getQueues() {
        return queues;
    }

    public void setQueues(List<RabbitQueue> queues) {
        this.queues = queues;
    }
}
