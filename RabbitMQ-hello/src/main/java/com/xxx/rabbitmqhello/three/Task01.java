package com.xxx.rabbitmqhello.three;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.xxx.rabbitmqhello.utis.RabbitMQUtils;

import java.util.Scanner;

/**
 * @author zhuyuxuan
 * 2022/7/6/11:44
 * @description:
 */
public class Task01 {
    //队列名称
    public static final String QUEUE_NAME = "hello";

    //发消息
    public static void main(String[] args) throws Exception{

        Channel channel = RabbitMQUtils.getChannel();
        /*
         * 生成一个队列
         * 第一个参数:队列名称
         * 第二个参数:队列里面的消息是否持久化,默认是存储在内存中
         * 第三个参数:该队列是否只供一个消费者进行消费,是否进行消息的共享,true可以一个消费者消费,false是多个
         * 第四个参数:最后一个消费者断开连接以后,是否自动删除队列
         * 第五个参数:其他参数
         * */

        //队列持久化
        Boolean durable = true;
        channel.queueDeclare(QUEUE_NAME,durable,false,false,null);

        //开启发布确认
        channel.confirmSelect();

        //发消息
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String next = scanner.next();
            //            MessageProperties.PERSISTENT_TEXT_PLAIN:消息持久化
            channel.basicPublish("",QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,next.getBytes());
            System.out.println("消息发送完毕 : " + next);
        }
        /*
         * 第一个参数：发送到哪个交换机,null就是简单默认交换机
         * 第二个参数: 路由的key是哪个,本次是队列名称
         * 第三个参数: 其他参数信息
         * 第四个参数: 发送消息的发送体
         *
         * */


    }
}
