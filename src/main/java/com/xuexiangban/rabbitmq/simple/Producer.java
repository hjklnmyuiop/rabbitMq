package com.xuexiangban.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.xuexiangban.rabbitmq.config.MqConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: dev_guo
 * @Date: 2023/1/10 11:01
 */
public class Producer {

    public static void main(String[] args) {
        // 1: 创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 2: 设置连接属性
        connectionFactory.setHost(MqConfig.rabbitmqHost);
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername(MqConfig.username);
        connectionFactory.setPassword(MqConfig.password);
        Connection connection = null;
        Channel channel = null;
        try {
            // 3: 从连接工厂中获取连接
            connection = connectionFactory.newConnection("生产者");
            // 4: 从连接中获取通道channel
            channel = connection.createChannel();

            Map<String,Object> map=new HashMap();
            map.put("x-message-ttl", 10000);//message在该队列queue的存活时间最大为10秒
            map.put("x-dead-letter-exchange", "dead_exchange");//x-dead-letter-exchange参数

            map.put("x-dead-letter-routing-key","dead");//x-dead-letter-routing-key

            // 5: 申明队列queue存储消息
            /*
             *  如果队列不存在，则会创建
             *  Rabbitmq不允许创建两个相同的队列名称，否则会报错。
             *
             *  @params1： queue 队列的名称
             *  @params2： durable 队列是否持久化
             *  @params3： exclusive 是否排他，即是否私有的，如果为true,会对当前队列加锁，其他的通道不能访问，并且连接自动关闭
             *  @params4： autoDelete 是否自动删除，当最后一个消费者断开连接之后是否自动删除消息。
             *  @params5： arguments 可以设置队列附加参数，设置队列的有效期，消息的最大长度，队列的消息生命周期等等。
             * */
//            channel.queueDeclare("direct5", false, false, false, map);
            // 6： 准备发送消息的内容
//            String message = "dev_guo，你好！！！";
            String  exchangeName = "rabbit_direct";
            String routingKey1 = "direct5";
            // 7: 发送消息给中间件rabbitmq-server
            // @params1: 交换机exchange
            // @params2: 队列名称/routing
            // @params3: 属性配置
            // @params4: 发送消息的内容
            for (int i = 0; i < 1000; i++) {
                String message="dev_guo，你好！！！"+i;
                channel.basicPublish(exchangeName, routingKey1, null, message.getBytes());
            }
            System.out.println("消息发送成功!");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("发送消息出现异常...");
        } finally {
            // 7: 释放连接关闭通道
            if (channel != null && channel.isOpen()) {
                try {
                    channel.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
