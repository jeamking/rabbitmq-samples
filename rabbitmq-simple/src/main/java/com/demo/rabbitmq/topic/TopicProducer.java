/**
 * TODO
 * 
 */
package com.demo.rabbitmq.topic;

/**
 * 模式匹配消费消息
 */
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class TopicProducer {

	private static final String EXCHANGE_NAME = "topic_exchange";

	public static void main(String[] argv) {
		Connection connection = null;
		Channel channel = null;
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");

			connection = factory.newConnection();
			channel = connection.createChannel();
//			声明一个匹配模式的交换器
			channel.exchangeDeclare(EXCHANGE_NAME, "topic");

			// 待发送的消息
			String[] routingKeys = new String[]{"quick.orange.rabbit", 
												"lazy.orange.elephant", 
												"quick.orange.fox", 
												"lazy.brown.fox", 
												"quick.brown.fox", 
												"quick.orange.male.rabbit", 
												"lazy.orange.male.rabbit"};
//			发送消息
	        for(String routingsKey :routingKeys){
	        	String message = "From "+routingsKey+" routingKey' s message!";
	        	channel.basicPublish(EXCHANGE_NAME, routingsKey, null, message.getBytes());
	        	System.out.println("Topic Send " + routingsKey + ":" + message);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception ignore) {
				}
			}
		}
	}
}