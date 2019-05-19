package com.demo.rabbitmq.deadletter;

import com.rabbitmq.client.Channel; 
import com.rabbitmq.client.Connection; 
import com.rabbitmq.client.ConnectionFactory; 
import java.util.HashMap; 
import java.util.Map;

/**
 * 消费者中设置死信队列
 * @author Administrator
 *
 */
public class DeadLetterConsumer {
	 public static void main(String[] args) throws Exception { 
		 // 1. 创建连接工厂并设置属性
		 ConnectionFactory factory = new ConnectionFactory(); 
		 factory.setHost("localhost"); 
		 factory.setPort(5672);
		 factory.setVirtualHost("/"); 
		 // 2. 创建连接
		 Connection connection = factory.newConnection(); 
		 // 3. 创建channel
		 Channel channel = connection.createChannel(); 
		 // 4. 声明死信队列Exchange和Queue 
		 String dlxExchangeName = "dlx_exchange"; 
		 String dlxExchangeType = "topic";
		 String dlxQueue = "dlx_queue";
		 /**
		  * ①*（星号）仅代表一个单词②#（井号）代表任意个单词
		  */
		 String dlxRoutingKey = "#"; 		 
		 channel.exchangeDeclare(dlxExchangeName, dlxExchangeType); 
		 channel.queueDeclare(dlxQueue, true, false, false, null); 
		 channel.queueBind(dlxQueue, dlxExchangeName, dlxRoutingKey); 
		 // 5. 声明普通Exchange 
		 String normalExchangeName = "dlx_normal_exchange"; 
		 String normalExchangeType = "topic"; 
		 String normalRoutingKey = "dlxrouter.*"; 
		 channel.exchangeDeclare(normalExchangeName, normalExchangeType, true, false, null); 
		 // 6. 声明普通队列, 指定死信队列为dlx.exchange，管理后台可以看到该队列的Features属性为DLX
		 String normalQueueName = "dlx_normal_queue";
		 Map<String, Object> arguments = new HashMap<String, Object>(); 
		 arguments.put("x-dead-letter-exchange", dlxExchangeName); 
		 channel.queueDeclare(normalQueueName, true, false, false, arguments); 
		 // 7. 绑定队列和Exchange 
		 channel.queueBind(normalQueueName, normalExchangeName, normalRoutingKey); 
	 }
}
