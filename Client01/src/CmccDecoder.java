import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;


public class CmccDecoder extends CumulativeProtocolDecoder{
	
	private final Charset charset;

	public CmccDecoder(Charset charset){
		this.charset = charset;
	}
	
	@Override
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception{
		IoBuffer buffer = IoBuffer.allocate(300).setAutoExpand(true);
		CharsetDecoder cd = charset.newDecoder();
		
		byte head = (byte)192;
		byte end = (byte)193;
		int receiver = -1, data_type = -1, data_receiver = -1, data_sender = -1;
		String data="";
		int matchCount = 0;
		boolean flag = false;
		
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
					//System.out.println(buffer);
					receiver = buffer.getInt(0);
					data_type = buffer.getInt(4);
					data_receiver = buffer.getInt(8);
					data_sender = buffer.getInt(12);
/*					
					System.out.println(buffer);
					System.out.println(matchCount);
*/					
					buffer.skip(16);
					data = buffer.getString(matchCount-16, cd);
/*					
			  		System.out.println("=====================================");
					for (int i = 16; i < 24; i++)
						System.out.println((char)buffer.get(i));
					System.out.println("=====================================");
					System.out.println(buffer);
*/					
					buffer.clear();
					matchCount = 0;
					break;
				}
			}else{
				matchCount++;
			}
		}
		
		SmsObject smsObject = new SmsObject();
		smsObject.setReceiver(receiver);
		smsObject.setDataType(data_type);
		smsObject.setDataReceiver(data_receiver);
		smsObject.setDataSender(data_sender);
		smsObject.setData(data);
		out.write(smsObject);
		return false;
	}
}
