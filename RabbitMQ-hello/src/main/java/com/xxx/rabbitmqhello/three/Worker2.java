package com.xxx.rabbitmqhello.three;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.xxx.rabbitmqhello.utis.RabbitMQUtils;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

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
            /*
            *   第一个参数表示消息的标记:是那个消息应答
            *   第二个参数是否批量
            * */
            try {
                Thread.sleep(3000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //手动应答的代码
            channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
        };
        //声明取消消息回调函数
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println("消息消费回调");
        };
        System.out.println("第二个工作线程等待接收消息。。。。。。。。");
        channel.basicConsume(QUEUE_NAME,false,deliverCallback,cancelCallback);
    }
}
