package com.xxx.rabbitmqhello.four;

import com.rabbitmq.client.Channel;
import com.xxx.rabbitmqhello.utis.RabbitMQUtils;
import org.apache.commons.io.filefilter.FalseFileFilter;

import java.nio.charset.StandardCharsets;
import java.util.UUID;


/**
 * @author zhuyuxuan
 * 2022/7/20/10:50
 * @description:
 *     验证发布确认模式
 *      1、单个确认
 *      2、批量确认
 *      1、异步批量确认
 */
public class ConfirmMessage {
    //批量发消息个数
    public static final int MESSAGE_COUNT = 1000;

    public static void main(String[] args) throws Exception{
        //1、单个确认
        //publishMessageIndividlly();
        //2、批量确认
        publishMessageBatch();
        //3、异步批量确认
    }

    //1、单个确认
    public static void publishMessageIndividlly() throws Exception{

        Channel channel = RabbitMQUtils.getChannel();
        //队列的说明
        String QueueName = UUID.randomUUID().toString();
        channel.queueDeclare(QueueName,true, false,false,null);
        //开启发布确认
        channel.confirmSelect();
        //记录时间
        long begin = System.currentTimeMillis();

        //批量发消息,单个确认
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = i + " ";
            channel.basicPublish("",QueueName, null,message.getBytes(StandardCharsets.UTF_8));
            //单个消息的确认
            boolean flag = channel.waitForConfirms();
            if(flag){
                System.out.println("消息确认成功: " + i);
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("确认一次需要的时间 : " +( end - begin));


    }

    //1、批量确认
    public static void publishMessageBatch() throws Exception{

        Channel channel = RabbitMQUtils.getChannel();
        //队列的说明
        String QueueName = UUID.randomUUID().toString();
        channel.queueDeclare(QueueName,true, false,false,null);
        //开启发布确认
        channel.confirmSelect();
        //记录时间
        long begin = System.currentTimeMillis();

        //批量确认消息的大小
        int batchSize = MESSAGE_COUNT / 10;

        //批量发消息,批量确认
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = i + " ";
            channel.basicPublish("",QueueName, null,message.getBytes(StandardCharsets.UTF_8));
            //判断达到100条是,批量确认一次
            boolean flag = false;
            if(i % (MESSAGE_COUNT / 10) == 0    ){
                flag = channel.waitForConfirms();
                System.out.println("消息确认成功: " + i);
            }


        }

        long end = System.currentTimeMillis();
        System.out.println("确认一次需要的时间 : " +( end - begin));


    }


}
