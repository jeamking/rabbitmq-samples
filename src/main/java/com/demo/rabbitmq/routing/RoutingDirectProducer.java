/**
 * TODO
 * 
 */
package com.demo.rabbitmq.routing;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

/**
 *
 */
public class RoutingDirectProducer {

    private static final String EXCHANGE_NAME = "direct_exchange";
 // 路由关键字
 	private static final String[] routingKeys = new String[]{"info" ,"warning", "error"};
 	
    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
//		声明交换器
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
//		发送消息
        for(String routingKey :routingKeys){
        	String message = "log level-" + routingKey;
        	channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
        	System.out.println("send routingKey:" + routingKey + ",message:" + message + "");
        }
        channel.close();
        connection.close();
    }
}