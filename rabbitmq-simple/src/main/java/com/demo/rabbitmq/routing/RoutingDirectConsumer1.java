/**
 * TODO
 * 
 */
package com.demo.rabbitmq.routing;

/**
 *
 */
import com.rabbitmq.client.*;

import java.io.IOException;

public class RoutingDirectConsumer1 {
	// 交换器名称
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
//		获取匿名队列名称
		String queueName = "direct_queue1";
		channel.queueDeclare(queueName, true, false, false, null);
//		根据路由关键字进行多重绑定
		for (String routingKey : routingKeys) {
			channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
			System.out.println(RoutingDirectConsumer1.class.getSimpleName()+" exchange:"+EXCHANGE_NAME+", queue:"+queueName+", BindRoutingKey:" + routingKey);
		}
		System.out.println(RoutingDirectConsumer1.class.getSimpleName()+" Waiting for messages. To exit press CTRL+C");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println("Received routingKey:" + envelope.getRoutingKey() + ",message:" + message + "");
			}
		};
		channel.basicConsume(queueName, true, consumer);
	}
}
