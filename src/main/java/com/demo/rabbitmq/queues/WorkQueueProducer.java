/**
 * TODO
 * 
 */
package com.demo.rabbitmq.queues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * Work queues工作队列模式，生产者发送了5条消息。work1收到0,2,4消息，work2收到1，3消息
 *           > C1
 * P > queue 
 *           > C2
 */
public class WorkQueueProducer {

	private static final String WORK_QUEUE_NAME = "work_queue";

	public static void main(String[] argv) throws java.io.IOException, Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(WORK_QUEUE_NAME, true, false, false, null);
//		分发消息
		for(int i = 0 ; i < 5; i++){
			String message = "Hello World! " + i;
			channel.basicPublish("", WORK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");
		}
		channel.close();
		connection.close();
	}
}
