package cn.ict.binos.transmit;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import com.longyi.databus.clientapi.DataBusAPI;


/**
 * this is used for new version. 
 * 
 * UNDO:
 * key --> file
 * key --> Message List  
 * @author jiangbing Mar 3, 2012
 *
 * @param <T>
 */
public class DataBusClientChannel<T> extends ClientChannelBase<byte[]> {
	public static Set<String> supportOps = new HashSet<String> ();
	private static Logger LOG = Logger.getLogger(DataBusClientChannel.class.getName());
	private String InProEndpoint;
	private ZContext context = null;
	private ZMQ.Socket SocketToLocalDataServer = null;
	private final static DataBusAPI dataBus = new DataBusAPI();
	static {
		supportOps.add("get");
		supportOps.add("set");
		supportOps.add("read");
		supportOps.add("write");
	};
	
	private DataBusClientChannel() {
		
	}
	
	public static String getLocation(String key) {
		return dataBus.getMessageLocation(key);
	}

	@Override
	public byte[] getValue(String key) {
		return dataBus.getMessage(key);
	}

	@Override
	public int putValue(String key, byte[] value) {
		// TODO Auto-generated method stub
		return dataBus.sendMessage(key, value);
	}
	
	public int FreeData(String key) {
		return dataBus.freeMessage(key);
	}
}
