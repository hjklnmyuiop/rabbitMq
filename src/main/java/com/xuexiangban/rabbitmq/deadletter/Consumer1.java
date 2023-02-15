package com.xuexiangban.rabbitmq.deadletter;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.xuexiangban.rabbitmq.config.MqConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * 死信队列
 * @Author: dev_guo
 * @Date: 2023/2/7 18:16
 */
public class Consumer1 {

    //普通交换机
    public static final String NORMAL_EXCHANGE="normal_exchange";
    //死信交换机
    public static final String DEAD_EXCHANGE="dead_exchange";
    //普通队列
    public static final String NORMAL_QUEUE="normal_queue";
    //死信队列
    public static final String DEAD_QUEUE="dead_queue";
    public static MqConfig mqConfig;
    public static void main(String[] args) throws Exception {
        // 1: 创建连接工厂
        Channel channel = mqConfig.getChannel();
        //声明死信交换机
        channel.exchangeDeclare(DEAD_EXCHANGE,"direct");
        //声明死信队列
        channel.queueDeclare(DEAD_QUEUE,false,false,false,null);

        //声明普通对列
        Map<String,Object> argument=new HashMap<>();
        //设置普通队列消息过期后死信队列是谁
        argument.put("x-dead-letter-exchange",DEAD_EXCHANGE);
        //设置死信路由
        argument.put("x-message-ttl", 10000);
        argument.put("x-dead-letter-routing-key","dead");
        channel.queueDeclare(NORMAL_QUEUE,false,false,false,argument);


        //绑定交换机与队列
        channel.queueBind(NORMAL_QUEUE,NORMAL_EXCHANGE,"normal");
        channel.queueBind(DEAD_QUEUE,DEAD_EXCHANGE,"dead");

        DeliverCallback deliverCallback=(delivery, message)->{
            String msg=new String(message.getBody());
            //拒绝消息5
            if (msg.equals("消息5")){
                System.out.println("consumer1拒绝接收消息"+msg);
                //false表示不被放回普通队列
                channel.basicReject(message.getEnvelope().getDeliveryTag(),false);
            }else {
                System.out.println("consumer1接收消息："+new String(message.getBody()));
                //消息应答
                channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
            }
        };

        //参数二关闭自动应答
        channel.basicConsume(NORMAL_QUEUE,false,deliverCallback,consumerTag->{});
    }

}
