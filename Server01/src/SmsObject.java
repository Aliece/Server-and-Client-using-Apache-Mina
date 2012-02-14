
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
	
}
