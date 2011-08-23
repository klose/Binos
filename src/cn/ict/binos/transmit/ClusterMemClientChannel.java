package cn.ict.binos.transmit;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;

public class ClusterMemClientChannel {
	private static final Log LOG = LogFactory.getLog(ClusterMemClientChannel.class);
	public static Set<String> supportOps = new HashSet<String> ();
	private static CopyOnWriteArrayList<String> serverAddrList = new
		CopyOnWriteArrayList<String> ();
	private volatile static boolean serverAddrListIsUpdate = false;
	private static ReentrantLock lock  = new ReentrantLock();
	private static Condition processUpdate = lock.newCondition();
	private volatile static int addOpsNum = 0;   
	private String path;
	private static MemcachedClient mc;
	static {
		supportOps.add("get");
		supportOps.add("put");
		supportOps.add("update");
		supportOps.add("delete");
	}
	public static boolean searchOps(String ops) {
		return supportOps.contains(ops);
	}
	
	class UpdateServerAddrThread extends Thread {	
		public void run() {
			lock.lock();
			serverAddrListIsUpdate = false;
			try {
				while (true) {
					while (!serverAddrListIsUpdate) {
						processUpdate.await();
					}
					String a = serverAddrList.toString(); // get [10.5.0.170:24523, 10.5.0.171:23451]
					String b = (a.substring(1, a.length() -1)); // delete the left [ and right ] 
					mc = new MemcachedClient(AddrUtil.getAddresses(b));	
					serverAddrListIsUpdate = false;
				}
			} catch (InterruptedException e) {
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
	}
	
	public synchronized static void addServerAddr(String serverAddr) throws Exception {
		if (!serverAddr.matches(".*:.*")) {
			throw new Exception(serverAddr + " format illegally."); 
		}
		if (!serverAddrList.add(serverAddr)) {
			throw new Exception("Can't add "+ serverAddr + " to server list.");
		}
		serverAddrListIsUpdate = true;
		addOpsNum++;
		if (addOpsNum == 1) {
			serverAddrList.notifyAll();
		}
		else {
			processUpdate.signalAll();
		}	
	}
	public synchronized static boolean deleteServerAddr(String serverAddr) throws Exception {
		if (!serverAddrList.remove(serverAddr)) {
			throw new Exception("Can't remove "+ serverAddr + " to server list.");
		}
		serverAddrListIsUpdate = true;
		processUpdate.signalAll();
		return true;
	}
	public ClusterMemClientChannel() {
		UpdateServerAddrThread usat = new UpdateServerAddrThread();
		usat.start();
		synchronized(serverAddrList) {
			try {
				while (serverAddrList.size() == 0) {
						serverAddrList.wait();
				}
				String a = serverAddrList.toString(); // get [10.5.0.170:24523, 10.5.0.171:23451]
				String b = (a.substring(1, a.length() -1)); // delete the left [ and right ] 
				mc = new MemcachedClient(AddrUtil.getAddresses(b));	
			}catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public Object get(String key) {
		return mc.get(key);
	}
	
	public boolean put(String key, Object value) {
		Future<Boolean> f = mc.set(key, 0, value);
		try {
			return f.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			return false;
		}
	}
	public boolean update(String key, Object value) {
		Future<Boolean> f = mc.set(key, 0, value);
		try {
			return f.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			return false;
		}
	}
	public boolean delete(String key) {
		Future<Boolean> f = mc.delete(key);
		try {
			return f.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return false;
		}
	}
}
