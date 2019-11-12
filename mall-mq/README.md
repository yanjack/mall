rabbitmq整合
version 1.0

1.实现思路

在.yml文件里配置rabbitmq queue,应用启动时根据配置文件,创建队列并绑定exchange和routingKey

同时，需要安装并启用Rabbitmq的rabbitmq_delayed_message_exchange插件 
window
启动插件进入安装rabbit文件下/sbin目录下执行命令：./rabbitmq-plugins enable rabbitmq_delayed_message_exchange


2.使用方法

POM引入

		<dependency>
			     <groupId>com.example</groupId>
                <artifactId>mall-mq</artifactId>
                <version>0.0.1-SNAPSHOT</version>
		</dependency> 
		
yml文件配置：

spring:
    
  rabbitmq:
  
    host: 172.16.8.117
    
    port: 5672
    
    username: guest
    
    password: guest
    
    virtualHost: /
    
rabbitmqconfig:

  #队列

  queues:
    #名称

    - name: A
       #交换机名称,不配置时默为topic_exchange,共四种模式(direct_exchange,topic_exchange,fanout_exchange,delay_exchange)
      exchangeName: fanout_exchange
      #路由关键字,不配置时默为队列名称
      routingKey: itemA
      #是否持久化,不配置时默认true
      durable: true
      #是否为排他队列,不配置时默认false
      exclusive: false
      #是否自动删除,不配置时默认false
      autoDelete: false


    - name: B
       #交换机名称,不配置时默为topic_exchange,共四种模式(direct_exchange,topic_exchange,fanout_exchange,delay_exchange)
      exchangeName: topic_exchange
      #路由关键字,不配置时默为队列名称
      routingKey: itemB
      #是否持久化,不配置时默认true
      durable: true
      #是否为排他队列,不配置时默认false
      exclusive: false
      #是否自动删除,不配置时默认false
      autoDelete: false


    - name: C
       #交换机名称,不配置时默为topic_exchange,共四种模式(direct_exchange,topic_exchange,fanout_exchange,delay_exchange)
      exchangeName: direct_exchange
      #路由关键字,不配置时默为队列名称
      routingKey: itemC
      #是否持久化,不配置时默认true
      durable: true
      #是否为排他队列,不配置时默认false
      exclusive: false
      #是否自动删除,不配置时默认false
      autoDelete: false

    - name: D
       #交换机名称,不配置时默为topic_exchange,共四种模式(direct_exchange,topic_exchange,fanout_exchange,delay_exchange)
      exchangeName: delay_exchange
      #路由关键字,不配置时默为队列名称
      routingKey: D
      #是否持久化,不配置时默认true
      durable: true
      #是否为排他队列,不配置时默认false
      exclusive: false
      #是否自动删除,不配置时默认false
      autoDelete: false


代码实现：

发送：

在需求调用MQ的Service里注入MQService：

    @Resource
    private MQService mqService;
    
发送：

    mqService.send(Exchange名,路由关键字,消息);
    如果是延迟队列请用下面的方法：
    mqService.sendByDelayExchange(路由关键字,消息,延迟时间，单位为秒);
    
接收：

    可以同时接收多个队列消息，队列名称需与配置对应，方法名可以自行定义（^_^ 注意必须加注解）
    @RabbitListener(queues = "A")
    @RabbitHandler
    public void A(String msg) {
        System.out.printf("revice A ok.....");
        log.info("recive ok..." + msg);
    }

    @RabbitListener(queues = "B")
    @RabbitHandler
    public void B(String msg) {
        System.out.printf("revice B ok.....");
        log.info("recive ok..." + msg);
    }

    @RabbitListener(queues = "C")
    @RabbitHandler
    public void C(String msg) {
        System.out.printf("revice C ok.....");
        log.info("recive ok..." + msg);
    }

    @RabbitListener(queues = "D")
    @RabbitHandler
    public void D(String msg) {
        System.out.printf("revice D ok.....");
        log.info("recive ok..." + msg);
    }
    
   