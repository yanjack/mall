package com.example.mallmq;

import com.example.mallmq.config.RabbitConfig;
import com.example.mallmq.config.RabbitExchageConstants;
import com.example.mallmq.config.RabbitQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MQService implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(MQService.class);

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitConfig rabbitConfig;

    //初始化后。应用启动时启动
    public void afterPropertiesSet() throws Exception {
        //exchange默认持久化,交换机不自动删除
        DirectExchange directExchange = new DirectExchange(RabbitExchageConstants.DIRECT_EXCHANGE);
        FanoutExchange fanoutExchange = new FanoutExchange(RabbitExchageConstants.FANOUT_EXCHANGE);

        TopicExchange topicExchange = new TopicExchange(RabbitExchageConstants.TOPIC_EXCHANGE);
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-delayed-type", "direct");
        CustomExchange customExchange = new CustomExchange(RabbitExchageConstants.DELAY_EXCHANGE,"x-delayed-message",true, false,args);

        logger.info("rabbitmq initial configuration start");

        List<RabbitQueue> queues = rabbitConfig.getQueues();
        if(!queues.isEmpty()){
            RabbitAdmin admin = new RabbitAdmin(this.connectionFactory);
            //指定交换机
            admin.declareExchange(directExchange);
            admin.declareExchange(fanoutExchange);
            admin.declareExchange(topicExchange);
            admin.declareExchange(customExchange);
            this.rabbitTemplate = admin.getRabbitTemplate();
            for(RabbitQueue rq :queues){
                Queue queue = new Queue(rq.getName(),rq.getDurable(),rq.getExclusive(),rq.getAutoDelete());
                //指定队列
                admin.declareQueue(queue);
                //将队列与交换机通过路由key进行绑定
                if(RabbitExchageConstants.DIRECT_EXCHANGE.equals(rq.getExchangeName())){
                    admin.declareBinding(BindingBuilder.bind(queue).to(directExchange).with(rq.getRoutingKey()));
                }else if(RabbitExchageConstants.TOPIC_EXCHANGE.equals(rq.getExchangeName())){
                    admin.declareBinding(BindingBuilder.bind(queue).to(topicExchange).with(rq.getRoutingKey()));
                }else if(RabbitExchageConstants.FANOUT_EXCHANGE.equals(rq.getExchangeName())){
                    admin.declareBinding(BindingBuilder.bind(queue).to(fanoutExchange));
                }else if(RabbitExchageConstants.DELAY_EXCHANGE.equals(rq.getExchangeName())){
                    admin.declareBinding(BindingBuilder.bind(queue).to(customExchange).with(rq.getRoutingKey()).noargs());
                }
            }
        }
        logger.info("Rabbitmq initial configuration completed");

    }
    //direct策略发送 发送到指定routingKey的队列
    public void sendByDirectExchange(String routingKey,Object msg){
        this.rabbitTemplate.convertAndSend(RabbitExchageConstants.DIRECT_EXCHANGE,routingKey,msg);
    }
    //fanout策略发送 发送到绑定fanout_exchange这个交换机的所有队列 不需要路由键
    public void sendByFanoutExchange(Object msg){
        this.rabbitTemplate.convertAndSend(RabbitExchageConstants.FANOUT_EXCHANGE,null,msg);
    }
    //topic广播策略发送 发送到匹配到这个消息的rouingKey的队列
    public void sendByTopicExchange(String routingKey,Object msg){
        this.rabbitTemplate.convertAndSend(RabbitExchageConstants.TOPIC_EXCHANGE,routingKey,msg);
    }

    //延时队列
    public void sendByDelayExchange(String routingKey,Object msg,final int times){
        this.rabbitTemplate.convertAndSend(RabbitExchageConstants.DELAY_EXCHANGE, routingKey, msg, new MessagePostProcessor() {
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setHeader("x-delay",times * 1000);
                return message;
            }
        });
    }
    //以指定的exchange,routingKey进行发送
    public void send(String exchange,String routingKey,Object msg){
        this.rabbitTemplate.convertAndSend(exchange,routingKey,msg);
    }
     // 获取发送模板,可自由配置发送
    public RabbitTemplate getRabbitTemplate() {
        return rabbitTemplate;
    }
}
