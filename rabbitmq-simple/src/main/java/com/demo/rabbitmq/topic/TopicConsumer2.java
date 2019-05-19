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

public class TopicConsumer2 {

	private static final String EXCHANGE_NAME = "topic_exchange";
	 
	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
//		声明一个匹配模式的交换器
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		String queueName = "topic_queue2";
		channel.queueDeclare(queueName, true, false, false, null);
		// 路由关键字
		String[] routingKeys = new String[]{"*.*.rabbit", "lazy.#"};
//		绑定路由关键字
		for (String routingKey : routingKeys) {
			channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
			System.out.println(TopicConsumer2.class.getSimpleName()+" exchange:"+EXCHANGE_NAME
					+", queue:"+queueName+", BindRoutingKey:" + routingKey);
		}

		System.out.println(TopicConsumer2.class.getSimpleName()+" Waiting for messages. To exit press CTRL+C");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println("Received routingKey:" + envelope.getRoutingKey() + ",message:" + message);
			}
		};
		channel.basicConsume(queueName, true, consumer);
	}
}