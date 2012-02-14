import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * MINA Server
 * @author Lazy
 */

public class Server {
	
	private static int bindPort = 9999;

	/**
	 * @param args
	 */	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		SocketAcceptor acceptor = new NioSocketAcceptor();
		
		//setMinReadBufferSize(), setMaxReadBufferSize()
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		//acceptor.getManagedSessions()
		
		//set MinaServerFilter
		DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
		chain.addLast("codec", new ProtocolCodecFilter(new CmccCodecFactory(Charset.forName("UTF-8"))));
		
		//set MinaServerHandler
		acceptor.setHandler(new ServerHandler());
		
		//bind port		
		try
		{
			acceptor.bind(new InetSocketAddress(bindPort));
			System.out.println("Mina Server is Listing on:= " + bindPort);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
}