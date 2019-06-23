package queue;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class MessageConsumer {
	//定义ActivMQ的连接地址 
	private static final String ACTIVEMQ_URL = "tcp://192.168.179.131:61616";

	//定义发送消息的队列名称
	private static final String QUEUE_NAME ="MyMsg";
	public static void main(String[] args) throws JMSException{
		
		//创建连接工厂
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
		
		//创建连接
		Connection createConnection = activeMQConnectionFactory.createConnection();
		
		//打开连接
		createConnection.start();
		/**
		 * 第1个参数 是否使用事务
			第2个参数 消息的确认模式
			•	AUTO_ACKNOWLEDGE = 1    自动确认
			•	CLIENT_ACKNOWLEDGE = 2    客户端手动确认   
			•	DUPS_OK_ACKNOWLEDGE = 3    自动批量确认
			•	SESSION_TRANSACTED = 0    事务提交并确认
		 */
		Session session = createConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//创建队列目标
		Destination destination = session.createQueue(QUEUE_NAME);
		
		//创建消费者
		javax.jms.MessageConsumer consumer = session.createConsumer(destination);
		
		//创建消费者监听
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				TextMessage msg= (TextMessage)message;
				
				try {
					System.out.println("获取消息为："+ msg.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		
		
		
		
	}
	
}
