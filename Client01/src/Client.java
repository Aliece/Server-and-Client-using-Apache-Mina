import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/**
 * Client
 * @author Lazy
 *
 */

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		//Create TCP/IP connector
		SocketConnector connector = new NioSocketConnector();
		
		//set Filter
		DefaultIoFilterChainBuilder chain = connector.getFilterChain();
		chain.addLast("codec", new ProtocolCodecFilter(new CmccCodecFactory(Charset.forName("UTF-8"))));
		
		//set ClientHandler
		connector.setHandler(new ClientHandler());
		
		//Wait for the connection attempt to be finished
		try
		{
			ConnectFuture cf = connector.connect(new InetSocketAddress("localhost", 9999));
			cf.awaitUninterruptibly();
			cf.getSession().getCloseFuture().awaitUninterruptibly();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		connector.dispose();
	}

}