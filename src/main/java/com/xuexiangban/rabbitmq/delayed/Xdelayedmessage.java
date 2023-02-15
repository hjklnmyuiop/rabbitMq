package com.xuexiangban.rabbitmq.delayed;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.xuexiangban.rabbitmq.config.MqConfig;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 延迟队列插件
 * @Author: dev_guo
 * @Date: 2023/2/10 14:39
 */
public class Xdelayedmessage {
    public static MqConfig mqConfig;
    public static void main(String[] args) throws Exception {
        // 延迟的时间
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("x-delay", 1000*6);//30分钟

        AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().headers(headers).build();

// 声明x-delayed-message类型的exchange
        Map<String, Object> argss = new HashMap<String, Object>();
        argss.put("x-delayed-type", "direct");
        // 1: 创建连接工厂
        Channel channel = mqConfig.getChannel();
        channel.exchangeDeclare("delay_exchange", "x-delayed-message", false,false, argss);

// 声明队列
        channel.queueDeclare("delay_queue", false,false,false,null);
// 绑定交换机与队列
        channel.queueBind("delay_queue", "delay_exchange", "delay_key");
        Date date = new Date();
        String format = new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss").format(date);
        String msg="消息延迟"+format;
        System.out.println("msg:"+msg);
        channel.basicPublish("delay_exchange", "delay_key", props,msg.getBytes());
    }
}
