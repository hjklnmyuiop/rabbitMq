package com.xuexiangban.rabbitmq.DelayQueue;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.DelayQueue;

/**
 * @Author: dev_guo
 * @Date: 2023/2/15 11:28
 * 在需要使用到延时队列的业务进行投递任务(消息)
 */
@Slf4j
public class DelayTaskProducer {
    /**
     *
     * @param id  业务id
     * @param time 消费时间  单位：毫秒
     * @param type 业务类型
     */
    public static void delayTask(String id,Long time,Integer type){
        DelayQueue<DelayTask> delayQueue = DelayTaskQueue.getInstance();//创建队列 1
        log.info("====================活动获取,{}",delayQueue);
        DelayTask delayTask = new DelayTask();//创建任务
        delayTask.setId(id)
                .setTime(time)
                .setType(type);
        log.info("=============入延时队列,{}",delayTask);
        boolean offer = delayQueue.offer(delayTask);//任务入队
        if(offer){
            log.info("=============入延时队列成功,{}",delayQueue);
        }else{
            log.info("=============入延时队列失败");
        }
    }
    public static void main(String[] args) {
        delayTask("测试",1l,3);
    }
}
