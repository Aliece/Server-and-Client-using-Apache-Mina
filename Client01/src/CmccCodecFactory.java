import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;


/**
 * 对编码器和解码器进行封装，方便调用
 * */
public class CmccCodecFactory implements ProtocolCodecFactory{

	private final CmccEncoder encoder;
	private final CmccDecoder decoder;

	public CmccCodecFactory(){
		this(Charset.defaultCharset());
	}

	public CmccCodecFactory(Charset charset){
		this.encoder = new CmccEncoder(charset);
		this.decoder = new CmccDecoder(charset);
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception{
		return decoder;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception{
		return encoder;
	}
}