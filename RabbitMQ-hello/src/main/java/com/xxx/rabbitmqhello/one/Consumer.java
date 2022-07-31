package com.xxx.rabbitmqhello.one;

import com.rabbitmq.client.*;

/**
 * @author zhuyuxuan
 * 2022/6/23/18:52
 *
 * 消费者-接收消息
 */
public class Consumer {
    //队列名称
    public static final String QUEUE_NAME = "hello";

    //发消息
    public static void main(String[] args) throws Exception{
        //创建一个连接
        ConnectionFactory factory = new ConnectionFactory();
        //工厂IP,连接RabbitMQ的队列
        factory.setHost("39.108.185.36");
        //factory.setHost("192.168.146.130");
        //用户名
        factory.setUsername("admin");
        factory.setPassword("admin");

        //创建连接
        Connection connection = factory.newConnection();
        //获取信道
        Channel channel = connection.createChannel();
        /*
         * 生成一个队列
         * 第一个参数:队列名称
         * 第二个参数:队列里面的消息是否持久化,默认是存储在内存中
         * 第三个参数:该队列是否只供一个消费者进行消费,是否进行消息的共享,true可以一个消费者消费,false是多个
         * 第四个参数:最后一个消费者断开连接以后,是否自动删除队列
         * 第五个参数:最后一个消费者断开连接以后,是否自动删除队列
         * */

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //声明接收消息回调函数
        DeliverCallback deliverCallback = (consumerTag,message) -> {
            System.out.println(new String(message.getBody()));
        };
        //声明取消消息回调函数
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println("消息消费被中断");
        };

        //接受消息
        /*
         * 第一个参数：消费哪个队列
         * 第二个参数: 消费成功后是否自动应答 true代表自动应答 false代表手动应答
         * 第三个参数: 消费者未成功消费的回调
         * 第四个参数: 消费者取消消费的回调函数
         *
         * */
        System.out.println("第二个工作线程等待接收消息。。。。。。。。。。。");
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
        System.out.println("消息发送完毕");

    }
}