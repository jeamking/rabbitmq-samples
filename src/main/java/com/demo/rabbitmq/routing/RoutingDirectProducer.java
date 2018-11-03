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
 // ·�ɹؼ���
 	private static final String[] routingKeys = new String[]{"info" ,"warning", "error"};
 	
    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
//		����������
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
//		������Ϣ
        for(String routingKey :routingKeys){
        	String message = "log level-" + routingKey;
        	channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
        	System.out.println("send routingKey:" + routingKey + ",message:" + message + "");
        }
        channel.close();
        connection.close();
    }
}