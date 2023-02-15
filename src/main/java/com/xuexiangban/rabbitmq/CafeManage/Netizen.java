package com.xuexiangban.rabbitmq.CafeManage;
import java.math.BigDecimal;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
/**
 * @Author: dev_guo
 * @Date: 2023/2/15 15:30
 * 网名实体类
 */
public class Netizen implements Delayed {
    //网民的身份证
    private String idNo;
    //网民的姓名
    private String netizenName;
    //上网时剩余金额
    private BigDecimal netMoney;
    //上网剩余时间
    private long playTime;

    //有参构造函数
    public Netizen(String idNo, String netizenName, long playTime) {
        this.idNo = idNo;
        this.netizenName = netizenName;
        this.playTime = playTime;
    }

    /**
     * 此方法是用来判断你的元素的延迟时间是到期
     * 获取能上网的时长
     */
    @Override
    public long getDelay(TimeUnit unit) {
        //时长=上网截止的时间-当前时间
        return this.playTime - System.currentTimeMillis();
    }

    /**
     *  比较优先级，时间最短的优先
     *  此方法是根据延迟时间从小到大来排列到队列中的元素
     * @param o
     * @return
     */
    @Override
    public int compareTo(Delayed o) {
        Netizen n = (Netizen) o;
        return this.getDelay(TimeUnit.SECONDS)-o.getDelay(TimeUnit.SECONDS)>0?1:0;
    }

    //网民姓名的get方法
    public String getNetizenName() {
        return netizenName;
    }
}