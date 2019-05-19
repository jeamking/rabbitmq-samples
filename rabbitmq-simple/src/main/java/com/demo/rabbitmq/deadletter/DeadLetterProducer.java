package com.demo.rabbitmq.deadletter;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.*;

/**
 * ��������Ϣ���ó�ʱʱ��
 * @author Administrator
 *
 */
public class DeadLetterProducer {
	 public static void main(String[] args) throws Exception { 
		 // 1. ����ConnectionFactory, ���������� 
		 ConnectionFactory factory = new ConnectionFactory(); 
		 factory.setHost("localhost"); 
		 //factory.setPort(5672); 
		 //factory.setVirtualHost("/"); 
		 // 2. ��������
		 Connection connection = factory.newConnection(); 
		 // 3. ����channel
		 Channel channel = connection.createChannel(); 
		 String exchangeName = "dlx_normal_exchange"; 
		 String routingKey = "dlxrouter.jinjian";
		 AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().expiration("5000").build(); 
		 // ������Ϣ
		 String msg = "Hello, test deal letter message"; 
	    /**
	     * @param exchange the exchange to publish the message to
	     * @param routingKey the routing key
	     * @param mandatory true if the 'mandatory' flag is to be set
	     *   ��mandatory��־λ����Ϊtrueʱ�����exchange�����������ͺ���ϢroutingKey�޷��ҵ�һ�����ʵ�queue�洢��Ϣ��
	     *   ��ôbroker�����basic.return��������Ϣ������������;��mandatory����Ϊfalseʱ�������������broker��ֱ�ӽ���Ϣ����;
	     *   ͨ�׵Ľ���mandatory��־����broker������������ٽ���Ϣroute��һ�������У�����ͽ���Ϣreturn��������;
	     * @param props other properties for the message - routing headers etc
	     * @param body the message body   
	     */		 
		 channel.basicPublish(exchangeName, routingKey, true, properties, msg.getBytes()); 
		 // �ر�����
		 channel.close(); 
		 connection.close(); 
	 }
}
