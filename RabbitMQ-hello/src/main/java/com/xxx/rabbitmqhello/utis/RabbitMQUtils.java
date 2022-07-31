package com.xxx.rabbitmqhello.utis;

import com.rabbitmq.client.*;

/**
 * @author zhuyuxuan
 * 2022/7/2/10:32
 */
public class RabbitMQUtils {
    //得到一个连接的 channel
    public static Channel getChannel() throws Exception{
//创建一个连接工厂
        //创建一个连接
        ConnectionFactory factory = new ConnectionFactory();
        //工厂IP,连接RabbitMQ的队列
        //factory.setHost("39.108.185.36");
        factory.setHost("39.108.185.36");
        //用户名
        factory.setUsername("admin");
        factory.setPassword("admin");
        //创建连接
        Connection connection = factory.newConnection();
        //获取信道
        Channel channel = connection.createChannel();
        return channel;
    }
}
