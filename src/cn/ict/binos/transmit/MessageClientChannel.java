package cn.ict.binos.transmit;

import org.zeromq.*;

import com.longyi.databus.clientapi.DataBusAPI;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class MessageClientChannel <T> extends ClientChannelBase<byte[]> {
	public static Set<String> supportOps = new HashSet<String>();
	private static Logger LOG = Logger.getLogger(DataBusClientChannel.class
			.getName());
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

	public MessageClientChannel() {

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

/**
 * This API should be compatible with C++ zeroMQ Master-Slave.
 * @author jiangbing Mar 3, 2012
 *
 * @param <T>
 */
//use zeromq to send message.
/*public class MessageClientChannel<T> extends ClientChannelBase<byte[]> {
	
	public static Set<String> supportOps = new HashSet<String> ();
	private static Logger LOG = Logger.getLogger(MessageClientChannel.class.getName());
	private String InProEndpoint;
	private ZContext context = null;
	private ZMQ.Socket SocketToLocalDataServer = null;
	static {
		supportOps.add("get");
		supportOps.add("set");
		supportOps.add("read");
		supportOps.add("write");
	};
	public MessageClientChannel() {
		InProEndpoint="tcp://127.0.0.1:56432";
		context = new ZContext();
		SocketToLocalDataServer = context.createSocket(ZMQ.REQ);
		SocketToLocalDataServer.connect(InProEndpoint);
	}	
	private int StoreMessage(String key,byte[] data)
	{
		ZMsg SendMsg = new ZMsg();
		SendMsg.addLast("INSERT");
		SendMsg.addLast(key);
		SendMsg.addLast(data);
		SendMsg.send(SocketToLocalDataServer);
			
		ZMsg Recvmsg=ZMsg.recvMsg(SocketToLocalDataServer);
		ZFrame FirstFrame=Recvmsg.pop();
		String BackString =new String(FirstFrame.getData());
		if(BackString.equals("OK"))
			return 1;
		else if(BackString.equals("FAILED"))
			return 0;
		else
			return -1;
	}
	public int FreeData(String key)
	{
		ZMsg SendMsg=new ZMsg();
		SendMsg.addLast("FREE");
		SendMsg.addLast(key);
			
		SendMsg.send(SocketToLocalDataServer);
		ZMsg Recvmsg=ZMsg.recvMsg(SocketToLocalDataServer);
		ZFrame FirstFrame=Recvmsg.pop();
		String BackString=new String(FirstFrame.getData());
		if(BackString.equals("OK"))
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}

	private int FreeAllDataByNodeEndpoint(String Endpoint)
	{
		ZMsg SendMsg=new ZMsg();
		SendMsg.addLast("DELETENODE");
		SendMsg.addLast(Endpoint);
			
		SendMsg.send(SocketToLocalDataServer);
		ZMsg Recvmsg=ZMsg.recvMsg(SocketToLocalDataServer);
		ZFrame FirstFrame=Recvmsg.pop();
		String BackString=new String(FirstFrame.getData());
		if(BackString.equals("OK"))
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	private byte[] GetData(String key)
	{
		ZMsg SendMsg=new ZMsg();
		SendMsg.addLast("RECEIVE");
		SendMsg.addLast(key);
			
		SendMsg.send(SocketToLocalDataServer);
		ZMsg Recvmsg=ZMsg.recvMsg(SocketToLocalDataServer);
		ZFrame FirstFrame=Recvmsg.pop();
		String BackString=new String(FirstFrame.getData());
		if(BackString.equals("OK"))
		{
			ZFrame SecondFrame=Recvmsg.pop();
			return SecondFrame.getData();
		}
		else
		{
			byte[] tmp=null;
			return tmp;
		}
	}
	
    @Override
	public byte[] getValue(String key) {
		return GetData(key.toString());
	}
    @Override
	public int putValue(String key, byte[] value) {
		// TODO Auto-generated method stub
    	return StoreMessage(key.toString(), value);
	}	
	//just for testing
	public static void main(String[] args)
	{
		if (args.length < 2) {
			LOG.info("args length error! length:" + args.length);
			System.exit(-1);
		}
		MessageClientChannel _data = new MessageClientChannel();
		if (args[0].equals("put")) {
			if (_data.putValue(args[1], args[2].getBytes()) ==1 ) {
				LOG.info("PUT operation successfully!");
			}
			else {
				LOG.info("PUT operation wrong!");
			}
		}
		else if (args[0].equals("get")) {
			System.out.println(new String(_data.getValue(args[1])));
		}
		else if (args[0].equals("delete")) {
			if (_data.FreeData(args[1]) == 1) {
				LOG.info("DELETE operation successfully!");;
			}else {
				LOG.info("DELETE operation wrong!");
			}
		}	
	}
}*/

