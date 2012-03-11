import java.io.*;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;


public class ClientHandler implements IoHandler {

	//SLF4J
	//private final static Logger log = LoggerFactory.getLogger(ClientHandler.class);
	
	@Override
	public void exceptionCaught(IoSession arg0, Throwable arg1)
			throws Exception {
		// TODO Auto-generated method stub

	}

		/**
	 * 当收到消息时调用此方法
	 * @param session  当前的连接
	 * @param message  收到的消息，已经解码成为SmsObject对象
	 * */
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		SmsObject sms = (SmsObject) message;
		Integer i = new Integer(255);
		if (i.equals(sms.getReceiver()) &&
			i.equals(sms.getDataType()) &&
			i.equals(sms.getDataReceiver()) &&
			i.equals(sms.getDataSender())) {
			/*
			 * other operate 
			 * */
		} else {
			System.out.println("================================================");
			System.out.println("Data From : " + session.getRemoteAddress());
			System.out.println("Receiver : [" + sms.getReceiver() + "]");
			System.out.println("Data Type : [" + sms.getDataType() + "]");
			System.out.println("Data Receiver : [" + sms.getDataReceiver() + "]");
			System.out.println("Data Sender : [" + sms.getDataSender() + "]");
			System.out.println("Data : [" + sms.getData() + "]");
			System.out.println("================================================");
			/*
			 * other operate 
			 * */
		}
		
		sms = sms.send();
		session.write(sms);
	}

	@Override
	public void messageSent(IoSession session, Object arg1) throws Exception {
		// TODO Auto-generated method stub

		System.out.println("send============>");
	}

	@Override
	public void sessionClosed(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("One Client Disconnect !");
	}

	@Override
	public void sessionCreated(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionIdle(IoSession arg0, IdleStatus arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	
	/**
	 * 当连接建立时调用此方法，进行客户端的注册操作
	 * @param session   当前的连接
	 * */
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Incomming Client: " + session.getRemoteAddress());
		int i = 255;
		SmsObject sms = new SmsObject();
		sms.setReceiver(i);
		sms.setDataType(i);
		sms.setDataReceiver(i);
		sms.setDataSender(i);
		//==========================================
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter the  client data types (separated by spaces): ");
		try {
			String s = in.readLine();
			sms.setData(s);
		}
		catch(IOException E) {
			System.out.println("Input Error!!");
		}
		
		//===================================================================================
		session.write(sms);
	}
	
}
