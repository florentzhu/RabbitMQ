package com.xxx.rabbitmqhello.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;



/**
 * @author zhuyuxuan
 * 2022/6/23/17:52
 */

public class Producer {
    //队列名称
    public static final String QUEUE_NAME = "hello";

    //发消息
    public static void main(String[] args) throws Exception{
        //创建一个连接
        ConnectionFactory factory = new ConnectionFactory();

        //工厂IP,连接RabbitMQ的队列

        factory.setHost("39.108.185.36");
        //factory.setHost("192.168.142.130");
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

        //发消息
        String massage = "helloword";
        /*
        * 第一个参数：发送到哪个交换机,null就是简单默认交换机
        * 第二个参数: 路由的key是哪个,本次是队列名称
        * 第三个参数: 其他参数信息
        * 第四个参数: 发送消息的发送体
        *
        * */
        channel.basicPublish("",QUEUE_NAME,null,massage.getBytes());
        System.out.println("消息发送完毕1");

    }
}
