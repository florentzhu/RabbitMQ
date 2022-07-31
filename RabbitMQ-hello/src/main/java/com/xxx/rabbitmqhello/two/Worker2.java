package com.xxx.rabbitmqhello.two;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.xxx.rabbitmqhello.utis.RabbitMQUtils;

/**
 * @author zhuyuxuan
 * 2022/7/2/10:43
 * @description: 这是一个工作线程,相当于消费者1号
 */
public class Worker2 {

    //队列名称
    public static final String QUEUE_NAME = "hello";

    //接收消息
    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMQUtils.getChannel();

        //消息接收
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println(new String("收到的消息 : " + new String(message.getBody())));
        };
        //声明取消消息回调函数
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println("消息消费回调");
        };


        System.out.println("第一个工作线程等待接收消息。。。。。。。。");
        channel.basicConsume(QUEUE_NAME,false,deliverCallback,cancelCallback);
    }
}
