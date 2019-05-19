package com.demo.rabbitmq.deadletter;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.*;

/**
 * 生产者消息设置超时时间
 * @author Administrator
 *
 */
public class DeadLetterProducer {
	 public static void main(String[] args) throws Exception { 
		 // 1. 创建ConnectionFactory, 并设置属性 
		 ConnectionFactory factory = new ConnectionFactory(); 
		 factory.setHost("localhost"); 
		 //factory.setPort(5672); 
		 //factory.setVirtualHost("/"); 
		 // 2. 创建连接
		 Connection connection = factory.newConnection(); 
		 // 3. 创建channel
		 Channel channel = connection.createChannel(); 
		 String exchangeName = "dlx_normal_exchange"; 
		 String routingKey = "dlxrouter.jinjian";
		 AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().expiration("5000").build(); 
		 // 发送消息
		 String msg = "Hello, test deal letter message"; 
	    /**
	     * @param exchange the exchange to publish the message to
	     * @param routingKey the routing key
	     * @param mandatory true if the 'mandatory' flag is to be set
	     *   当mandatory标志位设置为true时，如果exchange根据自身类型和消息routingKey无法找到一个合适的queue存储消息，
	     *   那么broker会调用basic.return方法将消息返还给生产者;当mandatory设置为false时，出现上述情况broker会直接将消息丢弃;
	     *   通俗的讲，mandatory标志告诉broker代理服务器至少将消息route到一个队列中，否则就将消息return给发送者;
	     * @param props other properties for the message - routing headers etc
	     * @param body the message body   
	     */		 
		 channel.basicPublish(exchangeName, routingKey, true, properties, msg.getBytes()); 
		 // 关闭连接
		 channel.close(); 
		 connection.close(); 
	 }
}
