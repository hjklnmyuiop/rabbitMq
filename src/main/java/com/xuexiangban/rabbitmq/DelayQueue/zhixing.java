package com.xuexiangban.rabbitmq.DelayQueue;

import java.util.concurrent.DelayQueue;

/**
 *
 * @Author: dev_guo
 * @Date: 2023/2/15 15:21
 */
public class zhixing {
    public static void main(String[] args) {
        DelayTaskProducer.delayTask("119",3l,2);
        DelayTaskConsumer delayTaskConsumer = new DelayTaskConsumer();
        delayTaskConsumer.run();
//        Thread t1 = new Thread(delayTaskConsumer);
//        t1.start();
    }
}
