package com.xuexiangban.rabbitmq.CafeManage;

/**
 * 模拟网吧管理
 * @Author: dev_guo
 * @Date: 2023/2/15 15:29
 */
public class InterentCafeManage {
    public static void main(String[] args) {
        //创建网吧
        InternetCafe internetCafe = new InternetCafe();
        //添加上网的人（身份id，姓名，钱数）
        internetCafe.playGame("001","张三",3.00);
        internetCafe.playGame("002","李四",8.00);
        internetCafe.playGame("003","王五",10.00);

        Thread t1 = new Thread(internetCafe);
        t1.start();
    }
}