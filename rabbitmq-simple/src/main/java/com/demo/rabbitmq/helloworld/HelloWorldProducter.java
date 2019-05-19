/**
 * TODO
 * 
 */
package com.demo.rabbitmq.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
/**
 * 消息生产者
 *
 */
public class HelloWorldProducter {

  private final static String QUEUE_NAME = "helloQueue";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    
	/**
	 * queue：队列名
     * durable：是否持久化
	 * exclusive：设置了排外为true的队列只可以在本次的连接中被访问，也就是说在当前连接创建多少个channel访问都没有关系，
	 *     但是如果是一个新的连接来访问，排外的queue在当前连接被断开的时候会自动消失（清除）无论是否设置了持久化
     * autoDelete：是否自动删除。也就是说queue会清理自己。但是是在最后一个connection断开的时候
     * arguments：
	 */    
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    String message = "Hello World1!";
    channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
    System.out.println("P [x] Sent '" + message + "'");

    channel.close();
    connection.close();
  }
}
