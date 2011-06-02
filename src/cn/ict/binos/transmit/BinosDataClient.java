package cn.ict.binos.transmit;

import java.io.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.hadoop.io.Text;

public class BinosDataClient {
	private static Set<String> supportOps = new HashSet<String> ();
	private static Logger LOG = Logger.getLogger(BinosDataClient.class.getName());
	static {
		supportOps.add("read");
		supportOps.add("write");
		supportOps.add("get");
		supportOps.add("put");
		supportOps.add("update");
		supportOps.add("delete");
	}
	public static InputStream getInputStream(BinosURL url) throws Exception {
		//Class <? extends ClientChannelBase> cls = ServiceType.findService(url);
		if (!supportOps.contains(url.getServiceOps())) {
			LOG.log(Level.SEVERE,url.getUrl().toString() + "contains an unrecongnized opearation.");
			throw new Exception(url.getUrl().toString() + "contains an unrecongnized opearation.");
		}
		LocalClientChannel lcc = new LocalClientChannel(url.getServiceOpsUrl());
		//Constructor c = cls.getConstructor()
		return lcc.open();
//		try {
//			
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
		
	}
	
	public OutputStream getOutputStream(BinosURL url) throws Exception{
		Class <? extends ClientChannelBase> cls = ServiceType.findService(url);
		if (!supportOps.contains(url.getServiceOps())) {
			LOG.log(Level.SEVERE,url.getUrl().toString() + "contains an unrecongnized opearation.");
			throw new Exception(url.getUrl().toString() + "contains an unrecongnized opearation.");
		}
		try {
			ClientChannelBase ccb = cls.newInstance();
			return ccb.create();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static void main() {
	
	}
}
