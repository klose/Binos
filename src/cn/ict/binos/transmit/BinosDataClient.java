package cn.ict.binos.transmit;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

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
		String serviceType = url.getServiceType();
		String opsUrl = url.getServiceOpsUrl();
		if (!supportOps.contains(ops)) {
			LOG.log(Level.SEVERE,url.getUrl().toString() + "contains an unrecongnized opearation.");
			throw new Exception(url.getUrl().toString() + "contains an unrecongnized opearation.");
		} 
		if (!ops.equals("read")) {
			LOG.log(Level.SEVERE, url.getUrl() + ": " + ops + " collides with getInputStream().");
			throw new Exception(url.getUrl() + ": " + ops + " collides with getInputStream().");
		}
		/* use the schema of 'if and else' instead of reflection, because the performance
		 * overhead is worse.  
		 */
		if (serviceType.equals("LOCAL")) {
			LocalClientChannel lcc = new LocalClientChannel(opsUrl);
			return lcc.open();
		}
		else if (serviceType.equals("REMOTE")) {
			HttpClientChannel hcc = new HttpClientChannel(opsUrl);
			return hcc.open();
		} 
		else if (serviceType.equals("HDFS")) {
			HdfsClientChannel hscc = new HdfsClientChannel(opsUrl);
			return hscc.open();
		}
		else {
			/* There is probability that user defines a new Data service which contains
			 * the operation 'read', so check it out. 
			 */
			Class<? extends ClientChannelBase> channelCls = ServiceType.findService(url);
			Method checkOps = channelCls.getMethod("searchOps", String.class);
			Object valObj = checkOps.invoke(channelCls, "read");
			Boolean val = (Boolean) valObj;
			if (val.booleanValue() == false) {
				LOG.log(Level.SEVERE, channelCls.toString() + " doesnot support ops: read");
				throw new Exception(channelCls.toString() + " doesnot support ops: read");
			}
			else {
				Constructor cons = channelCls.getConstructor(String.class);
				Object channelClsObj = cons.newInstance(opsUrl);
				Method readMeth = channelCls.getMethod("open");
				return (InputStream)readMeth.invoke(channelClsObj);
			}
		}
	}
	
	public static OutputStream getOutputStream(BinosURL url) throws Exception{
		String ops = url.getServiceOps();
		String serviceType = url.getServiceType();
		String opsUrl = url.getServiceOpsUrl();
		if (!supportOps.contains(ops)) {
			LOG.log(Level.SEVERE,url.getUrl().toString() + "contains an unrecongnized opearation.");
			throw new Exception(url.getUrl().toString() + "contains an unrecongnized opearation.");
		}  
		if (!ops.equals("write")) {
			LOG.log(Level.SEVERE, url.getUrl() + ": " + ops + " collides with getOutputStream().");
			throw new Exception(url.getUrl() + ": " + ops + " collides with getOutputStream().");
		}
		if (serviceType.equals("LOCAL")) {
			LocalClientChannel lcc = new LocalClientChannel(opsUrl);
			return lcc.create();
		} else if (serviceType.equals("HDFS")) {
			HdfsClientChannel hcc = new HdfsClientChannel(opsUrl);
			return hcc.create();
		} else {
			Class<? extends ClientChannelBase> channelCls = ServiceType.findService(url);
			Method checkOps = channelCls.getMethod("searchOps", String.class);
			Object valObj = checkOps.invoke(channelCls, "write");
			Boolean val = (Boolean) valObj;
			if (val.booleanValue() == false) {
				LOG.log(Level.SEVERE, channelCls.toString() + " doesnot support ops: read");
				throw new Exception(channelCls.toString() + " doesnot support ops: read");
			}
			else {
				Constructor cons = channelCls.getConstructor(String.class);
				Object channelClsObj = cons.newInstance(opsUrl);
				Method readMeth = channelCls.getMethod("create");
				return (OutputStream)readMeth.invoke(channelClsObj);
			}
		}
		
	}
	public static void put(BinosURL url, byte[] data) throws Exception {
		String ops = url.getServiceOps();
		String serviceType = url.getServiceType();
		String opsUrl = url.getServiceOpsUrl();
		if (!supportOps.contains(ops)) {
			LOG.log(Level.SEVERE,url.getUrl().toString() + "contains an unrecongnized opearation.");
			throw new Exception(url.getUrl().toString() + "contains an unrecongnized opearation.");
		}
		if (!ops.equals("write")) {
			LOG.log(Level.SEVERE, url.getUrl() + ": " + ops + " collides with put(BinosURL url, byte[] data).");
			throw new Exception(url.getUrl() + ": " + ops + " collides with put(BinosURL url, byte[] data).");
		}
		if (serviceType.equals("MESSAGE")) {
			MessageClientChannel mcc = new MessageClientChannel();
			mcc.putValue(url.getServiceOpsUrl(), data);
		}
	}
	
	public static byte[] get(BinosURL url) throws Exception {
		String ops = url.getServiceOps();
		String serviceType = url.getServiceType();
		String opsUrl = url.getServiceOpsUrl();
		if (!supportOps.contains(ops)) {
			LOG.log(Level.SEVERE,url.getUrl().toString() + "contains an unrecongnized opearation.");
			throw new Exception(url.getUrl().toString() + "contains an unrecongnized opearation.");
		}
		if (!ops.equals("read")) {
			LOG.log(Level.SEVERE, url.getUrl() + ": " + ops + " collides with get(BinosURL url).");
			throw new Exception(url.getUrl() + ": " + ops + " collides with get(BinosURL url).");
		}
		if (serviceType.equals("MESSAGE")) {
			MessageClientChannel mcc = new MessageClientChannel();
			return mcc.getValue(url.getServiceOpsUrl());
		}
		return null;
	}
//	public static void main() {
//	
//	}
}
