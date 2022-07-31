package com.xxx.rabbitmqhello.five;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.xxx.rabbitmqhello.utis.RabbitMQUtils;

/**
 * @author zhuyuxuan
 * 2022/7/22/10:31
 * @description:
 *      消息的接收:
 */
public class ReceiveLogs02 {
    //交换机名称
    public static final String ExchangeName = "logs1";
    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMQUtils.getChannel();
        //声明一个交换机
        channel.exchangeDeclare(ExchangeName, "topic");
        //声明一个临时队列
        /*
            队列的名称是随机的 ,消费者断开连接,队列自动删除
         */
        String queue = channel.queueDeclare().getQueue();

        channel.queueBind(queue,ExchangeName,"");
        System.out.println("ReceiveLogs02等待接收消息");

        //消息接收
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println(new String("ReceiveLogs02收到的消息 : " + new String(message.getBody())));
        };

        //声明取消消息回调函数
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println("ReceiveLogs02消息消费回调");
        };

        channel.basicConsume(queue,true,deliverCallback,cancelCallback);
    }
}
