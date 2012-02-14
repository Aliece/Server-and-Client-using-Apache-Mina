import java.util.ArrayList;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class ServerHandler implements IoHandler {
	
	//SLF4J
	//private final static Logger log = LoggerFactory.getLogger(ServerHandler.class);
	
	private static final CliLocator cli = new CliLocator();
	
	@Override
	public void exceptionCaught(IoSession arg0, Throwable arg1)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		
		SmsObject sms = (SmsObject) message;
		//log.info("The message is [" + sms.getMessage() + "]");

		/*
		 * other operate

		System.out.println("================================================");
		System.out.println("Data From : " + session.getRemoteAddress());
		System.out.println("Receiver : [" + sms.getReceiver() + "]");
		System.out.println("Data Type : [" + sms.getDataType() + "]");
		System.out.println("Data Receiver : [" + sms.getDataReceiver() + "]");
		System.out.println("Data Sender : [" + sms.getDataSender() + "]");
		System.out.println("Data : [" + sms.getData() + "]");
		System.out.println("================================================");
		
		 * */	
		
		//The processing of registration information 
		Integer i = new Integer(255);
		if( i.equals(sms.getReceiver()) &&
			i.equals(sms.getDataType()) &&
			i.equals(sms.getDataReceiver()) &&
			i.equals(sms.getDataSender())) {
			
			cli.addCli(session, sms.getData());
			System.out.println("Client : " + session.getRemoteAddress() + "  DONE");
		} else {
			//Forwarding
			ArrayList<IoSession> tempList = new ArrayList<IoSession>();
			tempList = cli.getCli(sms.getReceiver());
			
			System.out.println("tempting=======>" + session.getRemoteAddress() + "  with receiver : " + sms.getReceiver());
			if(tempList != null) {
				//System.out.println("true");
				for (IoSession session1 : tempList){
					System.out.println("Send =========>" + session1.getRemoteAddress());
					session1.write(sms);
				}
				System.out.println("================================================");
			}
			else System.out.println("forwarding false");
		}
		
		//Trigger the client
		sms.setReceiver(i);
		sms.setDataType(i);
		sms.setDataReceiver(i);
		sms.setDataSender(i);
		sms.setData(" ");
		session.write(sms);

	}

	@Override
	public void messageSent(IoSession arg0, Object arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		
		//delete the timeout address
		System.out.println("Client Closed :" + session.getRemoteAddress());
		cli.delCli(session);
		/*
		 * other operate
		 * */
	}

	@Override
	public void sessionCreated(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionIdle(IoSession arg0, IdleStatus arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Come :" + session.getRemoteAddress());
		/*
		 * other operate
		 * */
	}

}
