package com.xuexiangban.rabbitmq.deadletter;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.xuexiangban.rabbitmq.config.MqConfig;

/**
 * 死信队列的使用
 * @Author: dev_guo
 * @Date: 2023/2/7 18:04
 */
public class Producer {
    public static final String NORMAL_EXCHANGE="normal_exchange";
    public static MqConfig mqConfig;
    public static void main(String[] args) throws Exception {
        // 1: 创建连接工厂
        Channel channel = mqConfig.getChannel();
        //发送10条消息
        for (int i = 0; i < 10; i++) {
            String message="消息"+i;
            //设置当条信息过期时间
            AMQP.BasicProperties build = new AMQP.BasicProperties().builder().expiration("3000").build();
            // 7: 发送消息给中间件rabbitmq-server
            // @params1: 交换机exchange
            // @params2: 队列名称/routing
            // @params3: 属性配置
            // @params4: 发送消息的内容
            channel.basicPublish(NORMAL_EXCHANGE,"normal",build,message.getBytes());
            System.out.println("生产者发送消息："+message);
        }
    }
}