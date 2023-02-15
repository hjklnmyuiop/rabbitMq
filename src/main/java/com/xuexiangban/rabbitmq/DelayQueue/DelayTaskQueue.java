package com.xuexiangban.rabbitmq.DelayQueue;

import lombok.Data;
import java.util.concurrent.DelayQueue;
/**
 * @Author: dev_guo
 * @Date: 2023/2/15 11:22
 */
@Data
public class DelayTaskQueue {
    private static class Holder{
        static DelayQueue<DelayTask> instance = new DelayQueue(); //单例保证队列唯一
    }
    public static DelayQueue<DelayTask> getInstance() {
        return Holder.instance;
    }
}
