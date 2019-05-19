package com.demo.rabbitmq.deadletter;

import com.rabbitmq.client.Channel; 
import com.rabbitmq.client.Connection; 
import com.rabbitmq.client.ConnectionFactory; 
import java.util.HashMap; 
import java.util.Map;

/**
 * ���������������Ŷ���
 * @author Administrator
 *
 */
public class DeadLetterConsumer {
	 public static void main(String[] args) throws Exception { 
		 // 1. �������ӹ�������������
		 ConnectionFactory factory = new ConnectionFactory(); 
		 factory.setHost("localhost"); 
		 factory.setPort(5672);
		 factory.setVirtualHost("/"); 
		 // 2. ��������
		 Connection connection = factory.newConnection(); 
		 // 3. ����channel
		 Channel channel = connection.createChannel(); 
		 // 4. �������Ŷ���Exchange��Queue 
		 String dlxExchangeName = "dlx_exchange"; 
		 String dlxExchangeType = "topic";
		 String dlxQueue = "dlx_queue";
		 /**
		  * ��*���Ǻţ�������һ�����ʢ�#�����ţ��������������
		  */
		 String dlxRoutingKey = "#"; 		 
		 channel.exchangeDeclare(dlxExchangeName, dlxExchangeType); 
		 channel.queueDeclare(dlxQueue, true, false, false, null); 
		 channel.queueBind(dlxQueue, dlxExchangeName, dlxRoutingKey); 
		 // 5. ������ͨExchange 
		 String normalExchangeName = "dlx_normal_exchange"; 
		 String normalExchangeType = "topic"; 
		 String normalRoutingKey = "dlxrouter.*"; 
		 channel.exchangeDeclare(normalExchangeName, normalExchangeType, true, false, null); 
		 // 6. ������ͨ����, ָ�����Ŷ���Ϊdlx.exchange�������̨���Կ����ö��е�Features����ΪDLX
		 String normalQueueName = "dlx_normal_queue";
		 Map<String, Object> arguments = new HashMap<String, Object>(); 
		 arguments.put("x-dead-letter-exchange", dlxExchangeName); 
		 channel.queueDeclare(normalQueueName, true, false, false, arguments); 
		 // 7. �󶨶��к�Exchange 
		 channel.queueBind(normalQueueName, normalExchangeName, normalRoutingKey); 
	 }
}
