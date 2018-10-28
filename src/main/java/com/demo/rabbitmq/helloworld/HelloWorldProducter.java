/**
 * TODO
 * 
 */
package com.demo.rabbitmq.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
/**
 * ��Ϣ������
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
	 * queue��������
     * durable���Ƿ�־û�
	 * exclusive������������Ϊtrue�Ķ���ֻ�����ڱ��ε������б����ʣ�Ҳ����˵�ڵ�ǰ���Ӵ������ٸ�channel���ʶ�û�й�ϵ��
	 *     ���������һ���µ����������ʣ������queue�ڵ�ǰ���ӱ��Ͽ���ʱ����Զ���ʧ������������Ƿ������˳־û�
     * autoDelete���Ƿ��Զ�ɾ����Ҳ����˵queue�������Լ��������������һ��connection�Ͽ���ʱ��
     * arguments��
	 */    
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    String message = "Hello World1!";
    channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
    System.out.println("P [x] Sent '" + message + "'");

    channel.close();
    connection.close();
  }
}
