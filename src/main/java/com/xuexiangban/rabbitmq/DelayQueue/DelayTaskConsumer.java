package com.xuexiangban.rabbitmq.DelayQueue;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Author: dev_guo
 * @Date: 2023/2/15 11:28
 */

@Data
@Slf4j
@Component
public class DelayTaskConsumer implements Runnable {
    /*  @Autowired
          private IProPatMeetingService meetingService;
          @Autowired
          private ParActivityService activityService;*/
    @Override
    public void run() {
        System.out.println("进入获取方法");
        DelayQueue<DelayTask> delayQueue = DelayTaskQueue.getInstance();//获取同一个put进去任务的队列
        new Thread(() -> {
            while (true) {
                try {
                    // 从延迟队列的头部获取已经过期的消息
                    // 如果暂时没有过期消息或者队列为空，则take()方法会被阻塞，直到有过期的消息为止
                    DelayTask delayTask = delayQueue.take();//阻塞
                    switch (delayTask.getType()) {//判断业务类型，执行对应的业务操作
                        case 1:
                            log.info("====================会议消费,{}",delayTask.getType());

                            //ParMeeting meeting = meetingService.getById(delayTask.getId());
                            //meetingService.pushWxMessageAndMail(meeting,true,null);
                            break;
                        case 2:
                            log.info("====================活动报名消费,{}",delayTask.getType());
                            //ParActivity activityApply = activityService.getById(delayTask.getId());
                            //activityService.pushWxAndMailToActivity(activityApply, PartyActivityPushMessageType.apply_activity_type.getCode());
                            break;
                        case 3:
                            log.info("====================活动开始消费,{}",delayTask.getType());
                            //ParActivity activityStart = activityService.getById(delayTask.getId());
                            //activityService.pushWxAndMailToActivity(activityStart,PartyActivityPushMessageType.activity_start_type.getCode());
                            break;
                        default:
                            log.info("====================未找到消费类型,{}",delayTask.getType());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}

