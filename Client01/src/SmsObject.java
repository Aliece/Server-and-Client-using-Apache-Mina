import java.io.*;

/**
 * 数据包类，消息类
 * 仅作为deamo使用，根据需要要进行重写
 * */
 
public class SmsObject {
	private int receiver;
	private int data_type;
	private int data_receiver;
	private int data_sender;
	private String data;
	
	public int getReceiver(){
		return receiver;
	}
	
	public int getDataType(){
		return data_type;
	}
	
	public int getDataReceiver(){
		return data_receiver;
	}
	
	public int getDataSender(){
		return data_sender;
	}
	
	public String getData(){
		return data;
	}
	
	public void setReceiver(int receiver){
		this.receiver = receiver;
	}
	
	public void setDataType(int data_type){
		this.data_type = data_type;
	}
	
	public void setDataReceiver(int data_receiver){
		this.data_receiver = data_receiver;
	}
	
	public void setDataSender(int data_sender){
		this.data_sender = data_sender;
	}
	
	public void setData(String data){
		this.data = data;
	}
	/**
	*封装的一个消息发送的函数，仅作为调试时使用
	*/
	public SmsObject send(){
		SmsObject sms = new SmsObject();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//=====================================================================================
			//receiver		
			System.out.print("Input Receiver : ");
			try {
				String s = in.readLine();
				sms.setReceiver(Integer.parseInt(s));
			}
			catch(IOException E) {
				System.out.println("Input Error!!");
			}
			
			//data type
			System.out.print("Input Data Type : ");
			try {
				String s = in.readLine();
				sms.setDataType(Integer.parseInt(s));
			}
			catch(IOException E) {
				System.out.println("Input Error!!");
			}
			
			//data receiver
			System.out.print("Input Data receiver : ");
			try {
				String s = in.readLine();
				sms.setDataReceiver(Integer.parseInt(s));
			}
			catch(IOException E) {
				System.out.println("Input Error!!");
			}
			
			//data sender
			System.out.print("Input Data Sender : ");
			try {
				String s = in.readLine();
				sms.setDataSender(Integer.parseInt(s));
			}
			catch(IOException E) {
				System.out.println("Input Error!!");
			}
			
			//data
			System.out.print("Input Data : ");
			try {
				String s = in.readLine();
				sms.setData(s);
			}
			catch(IOException E) {
				System.out.println("Input Error!!");
			}
		//================================================================
			return sms;
	}
	
}
