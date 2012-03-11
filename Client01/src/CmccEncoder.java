import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * 编码器类
 * */
public class CmccEncoder extends ProtocolEncoderAdapter{
	private final Charset charset;
	
	public CmccEncoder(Charset charset){
		this.charset = charset;
	}
	
	/**
	 * 协议编码器
	 * 将{@link SmsObject} 对象编码成字节流通过网络连接发送
	 * @param session  当前的连接
	 * @param message  需要编码的对象
	 * @param out   用来将编码器最终产生的字节流输出到外层
	 * */
	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception{
		SmsObject sms = (SmsObject)message;
		CharsetEncoder ce = charset.newEncoder();
		IoBuffer buffer = IoBuffer.allocate(300).setAutoExpand(true);
		
		byte head = (byte)192;
		byte end = (byte)193;
		int receiver = sms.getReceiver();
		int data_type = sms.getDataType();
		int data_receiver = sms.getDataReceiver();
		int data_sender = sms.getDataSender();
		String data = sms.getData();
		
		buffer.put(head);
		buffer.putInt(receiver);
		buffer.putInt(data_type);
		buffer.putInt(data_receiver);
		buffer.putInt(data_sender);
		//System.out.println(buffer);
		buffer.putString(data, ce);
		//System.out.println(buffer);
		buffer.put(end);
		
		buffer.flip();
		//System.out.println(buffer);
/*		
  		System.out.println("=====================================");
		for (int i = 17; i < 25; i++)
			System.out.println((char)buffer.get(i));
		System.out.println("=====================================");
*/
		session.write(buffer);
	}
}
