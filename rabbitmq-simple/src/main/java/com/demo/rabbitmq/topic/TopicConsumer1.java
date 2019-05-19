/**
 * TODO
 * 
 */
package com.demo.rabbitmq.topic;

/**
 *
 */
import com.rabbitmq.client.*;
import java.io.IOException;

public class TopicConsumer1 {

	private static final String EXCHANGE_NAME = "topic_exchange";
	 
	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
//		����һ��ƥ��ģʽ�Ľ�����
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		//String queueName = channel.queueDeclare().getQueue();
		String queueName = "topic_queue1";
		channel.queueDeclare(queueName, true, false, false, null);
		// ·�ɹؼ���
		String[] routingKeys = new String[]{"*.orange.*"};
//		��·�ɹؼ���
		for (String routingKey : routingKeys) {
			channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
			System.out.println(TopicConsumer1.class.getSimpleName()+" exchange:"+EXCHANGE_NAME
					+", queue:"+queueName+", BindRoutingKey:" + routingKey);
		}

		System.out.println(TopicConsumer1.class.getSimpleName()+" Waiting for messages. To exit press CTRL+C");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println("Received " + envelope.getRoutingKey() + ",message:" + message);
			}
		};
		channel.basicConsume(queueName, true, consumer);
	}
}