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

public class RoutingDirectConsumer2 {
	// ����������
	private static final String EXCHANGE_NAME = "direct_exchange";
	// ·�ɹؼ���
	private static final String[] routingKeys = new String[]{"error"};
	
	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
//		����������
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
//		��ȡ������������
		String queueName = "direct_queue2";
		channel.queueDeclare(queueName, true, false, false, null);
//		����·�ɹؼ��ֽ��ж��ذ�
		for (String routingKey : routingKeys) {
			channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
			System.out.println(RoutingDirectConsumer2.class.getSimpleName()+" exchange:"+EXCHANGE_NAME+", queue:"+queueName+", BindRoutingKey:" + routingKey);
		}
		System.out.println(RoutingDirectConsumer2.class.getSimpleName()+" Waiting for messages. To exit press CTRL+C");

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
