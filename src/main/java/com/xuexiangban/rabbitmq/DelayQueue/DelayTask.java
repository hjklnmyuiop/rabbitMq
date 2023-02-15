package com.xuexiangban.rabbitmq.DelayQueue;
import lombok.Data;
import lombok.experimental.Accessors;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
/**
 * @Author: dev_guo
 * @Date: 2023/2/15 11:28
 * 延时任务
 * 需要实现 Delayed 接口 重写getDelay和compareTo指定相应的规则
 */
@Data
@Accessors(chain = true)//链式调用注解
public class DelayTask implements Delayed {
    private String id;
    private Long time;
    private Integer type;
    @Override
    public long getDelay(TimeUnit unit) {
        // 计算该任务距离过期还剩多少时间
        long remaining = time - System.currentTimeMillis();
        return unit.convert(remaining, TimeUnit.MILLISECONDS);
    }
    @Override
    public int compareTo(Delayed o) {
        // 比较、排序：对任务的延时大小进行排序，将延时时间最小的任务放到队列头部
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
    }
}
