package cn.ict.binos.transmit;
import org.zeromq.*;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

//use zeromq to send message.
public class MessageClientChannel<T> extends ClientChannelBase<T> {
	public static Set<String> supportOps = new HashSet<String> ();
	private static Logger LOG = Logger.getLogger(HttpClientChannel.class.getName());
	private String InProEndpoint;
	private ZContext context=null;
	private static ZMQ.Socket SocketToLocalDataServer = null;
	public MessageClientChannel() {
		InProEndpoint="tcp://127.0.0.1:56432";
		context = new ZContext();
		SocketToLocalDataServer = context.createSocket(ZMQ.REQ);
		SocketToLocalDataServer.connect(InProEndpoint);
	}	
	public int StoreMessage(String key,byte[] data)
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
	public int FreeAllDataByNodeEndpoint(String Endpoint)
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
		public byte[] GetData(String key)
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
	
		
	//just for testing
	public static void main(String[] args)
	{
		MessageClientChannel _data=new MessageClientChannel();
		String Data="love you";
		_data.StoreMessage("heihei", Data.getBytes());
		for(int i=0;i<10;i++)
		{
			byte[] rtv=_data.GetData("heihei");
			System.out.println((new String(rtv)));
		}
			
		_data.FreeData("heihei");
			
		byte[] rtv1=_data.GetData("heihei");
		if(rtv1!=null)
			System.out.println((new String(rtv1)));
			
		_data.StoreMessage("heihei", Data.getBytes());
		byte[] rtv2=_data.GetData("heihei");
			//if(rtv1!=null)
		System.out.println((new String(rtv2)));	
	}
}

