package com.xuexiangban.rabbitmq.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Author: dev_guo
 * @Date: 2023/1/10 13:47
 */

public  class MqConfig {
    public static   String rabbitmqHost="47.107.65.114";
    public static   String username="dev_guo";
    public static   String password="gqa123";
    public static Channel getChannel(){
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
            connection = connectionFactory.newConnection("生产者");
            // 4: 从连接中获取通道channel
            channel = connection.createChannel();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("getChannel异常...");
        }
        return channel;
    }
}
