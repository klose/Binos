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
		String ops = url.getServiceOps();
		if (!supportOps.contains(ops)) {
			LOG.log(Level.SEVERE,url.getUrl().toString() + "contains an unrecongnized opearation.");
			throw new Exception(url.getUrl().toString() + "contains an unrecongnized opearation.");
		} 
		if (!ops.equals("read")) {
			LOG.log(Level.SEVERE, url.getUrl() + ": " + ops + " collides with getInputStream().");
			throw new Exception(url.getUrl() + ": " + ops + " collides with getInputStream().");
		}
		LocalClientChannel lcc = new LocalClientChannel(url.getServiceOpsUrl());
		return lcc.open();
	}
	
	public static OutputStream getOutputStream(BinosURL url) throws Exception{
		String ops = url.getServiceOps();
		if (!supportOps.contains(ops)) {
			LOG.log(Level.SEVERE,url.getUrl().toString() + "contains an unrecongnized opearation.");
			throw new Exception(url.getUrl().toString() + "contains an unrecongnized opearation.");
		}  
		if (!ops.equals("write")) {
			LOG.log(Level.SEVERE, url.getUrl() + ": " + ops + " collides with getOutputStream().");
			throw new Exception(url.getUrl() + ": " + ops + " collides with getOutputStream().");
		}
		LocalClientChannel lcc = new LocalClientChannel(url.getServiceOpsUrl());
		return lcc.create();
	}
	public static void main() {
	
	}
}
