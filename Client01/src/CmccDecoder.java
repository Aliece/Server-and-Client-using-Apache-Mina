import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * 解码器类
 * */
public class CmccDecoder extends CumulativeProtocolDecoder{
	
	private final Charset charset;
	static int matchCount;
	static boolean flag;
	public CmccDecoder(Charset charset){
		this.charset = charset;
		flag = false;
		matchCount = 0;
	}
	
	/**
	 * 协议解码器
	 * 将网络字节流解码成{@link SmsObject}对象
	 * @param session  当前的连接
	 * @param in  当前存储字节流的缓冲区
	 * @param out   用来将编码器最终产生的{@link SmsObject}对象输出到外层
	 * @return <b>true</b>  代表包解析完整，可以调用{@link ServerHandler}类进行处理;<p><b>false</b>  代表包尚未完全接受，继续调用此方法进行接受解析
	 * */
	@Override
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception{
		IoBuffer buffer = IoBuffer.allocate(300).setAutoExpand(true);
		CharsetDecoder cd = charset.newDecoder();
		
		byte head = (byte)192;
		byte end = (byte)193;
		int receiver = -1, data_type = -1, data_receiver = -1, data_sender = -1;
		String data="";

		
		while (in.hasRemaining()){
			byte b = in.get();
			buffer.put(b);
			
			if(b == head){
				buffer.flip();
				buffer.clear();
				matchCount = 0;
				flag = true;
			}else if (b == end){
				if (flag){ 
					buffer.flip();
					receiver = buffer.getInt(0);
					data_type = buffer.getInt(4);
					data_receiver = buffer.getInt(8);
					data_sender = buffer.getInt(12);
					
					buffer.skip(16);
					data = buffer.getString(matchCount-16, cd);
					
					SmsObject smsObject = new SmsObject();
					smsObject.setReceiver(receiver);
					smsObject.setDataType(data_type);
					smsObject.setDataReceiver(data_receiver);
					smsObject.setDataSender(data_sender);
					smsObject.setData(data);
					out.write(smsObject);
					matchCount = 0;
					return true;
				}
			}else{
				matchCount++;
			}
		}
		
		
		return false;
	}
}
