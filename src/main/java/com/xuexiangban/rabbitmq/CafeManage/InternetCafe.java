package com.xuexiangban.rabbitmq.CafeManage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.DelayQueue;
/**
 * @Author: dev_guo
 * @Date: 2023/2/15 15:30
 * 网吧类
 */
public class InternetCafe implements Runnable{
    //上网钱和时间的比率（1元=多少秒），这里取 1元/10秒
    private static final long MONEY_TIME_RATIO = 1000*10;
    //上网人的队列，使用延时阻塞队列
    private DelayQueue<Netizen> dq = new DelayQueue<>();
    //给时间格式化
    SimpleDateFormat sbf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //上网的方法
    public void playGame(String idNo, String name, double netMoney){
        //截止时间=钱数*时间（1元对应多少时间）+当前时间
        Netizen netizen = new Netizen(idNo,name,new Double(netMoney).longValue()*MONEY_TIME_RATIO+System.currentTimeMillis() );
        System.out.println("欢迎光临，"+name +"，于"+sbf.format(new Date())+"开始上网计费...");
        //把网民加入到队列中
        dq.add(netizen);
    }
    //下机的方法
    public void playOver(Netizen netizen){
        System.out.println(netizen.getNetizenName() + "：您好，"+sbf.format(new Date())+ "上网时间已到，请下机或者续费...");
    }

    /**
     * 覆写线程方法
     */
    @Override
    public void run() {
        //监控每个网民的上网时长
        while (true) {
            try {
                //取出并移除网民，take取出元素并移除，没有元素会阻塞
                Netizen netizen = dq.take();
                //运行下机方法
                playOver(netizen);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



}
