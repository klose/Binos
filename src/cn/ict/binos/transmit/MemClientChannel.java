package cn.ict.binos.transmit;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
public class MemClientChannel<T> extends ClientChannelBase<T> {
	private static final Log LOG = LogFactory.getLog(MemClientChannel.class);
	public static Set<String> supportOps = new HashSet<String> ();
	private String path;
	private static MemcachedClient mc;
	static {
		supportOps.add("get");
		supportOps.add("put");
		supportOps.add("update");
		supportOps.add("delete");
	}
	
	/*search ops that BinosURL contains */
	public static boolean searchOps(String ops) {
		return supportOps.contains(ops);
	}
	
	public MemClientChannel(String path) throws Exception {
		if (!path.matches(".*:.*")) {
			throw new Exception("serverAddr:" + path + " format illegally."); 
		}
		mc = new MemcachedClient(AddrUtil.getAddresses(path));
		LOG.info("MemcachedClient initialize at serverAddr:" + path);
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
