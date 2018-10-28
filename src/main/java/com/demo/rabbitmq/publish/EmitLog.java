package com.demo.rabbitmq.publish;

/**
 * Publish/Subscribe 发布订阅模式，生成者发送了5条消息。logs1订阅者会收到5条消息。logs2订阅者也会收到5条消息
 *        > quene > C1
 *  P > X 
 *        > quene > C2
 */

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLog {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

//		分发消息
		for(int i = 0 ; i < 5; i++){
			String message = "Hello World! " + i;
			 channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
		     System.out.println(" [x] Sent '" + message + "'");
		}
        channel.close();
        connection.close();
    }
}
