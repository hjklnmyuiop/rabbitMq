package com.xuexiangban.rabbitmq.DelayQueue;

/**
 * @Author: dev_guo
 * @Date: 2023/2/10 17:04
 */
import org.springframework.lang.NonNull;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
public class DelayEntity implements Delayed {

    private static final Long currentTime = System.currentTimeMillis();
    private String str;
    private Long scheduleTime;

    public DelayEntity(String str, Long delayed) {
        this.str = str;
        scheduleTime = System.currentTimeMillis() + (1000) * delayed;
    }

    @Override
    public long getDelay(@NonNull TimeUnit unit) {
        return unit.convert(scheduleTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(@NonNull Delayed o) {
        return (int) (this.scheduleTime - ((DelayEntity) o).scheduleTime);
    }

    public String getStr() {
        return str;
    }

    public Long getScheduleTime() {
        return scheduleTime;
    }

    public String showScheduleTime() {
        return "计划执行时间:" + new Date(this.scheduleTime).toString();
    }
}
