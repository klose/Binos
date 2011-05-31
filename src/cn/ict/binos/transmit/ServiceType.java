package cn.ict.binos.transmit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import cn.ict.binos.transmit.ClientChannel;
import org.apache.hadoop.io.Text;

public class ServiceType {
	public static enum defaultType {HDFS, MEMCACHED, MESSAGE, REMOTE, LOCAL};
	private static Set<Text> userDefinedTypes = new HashSet<Text> ();
	private static Map<Text, Class<? extends ClientChannelBase>> definedClass = 
		new HashMap<Text, Class<? extends ClientChannelBase>> ();
	static {
		definedClass.put(new Text("HDFS"), HdfsClientChannel.class);
		definedClass.put(new Text("MEMCACHED"), MemClientChannel.class);
		definedClass.put(new Text("MESSAGE"), MessageClientChannel.class);
		definedClass.put(new Text("REMOTE"), HttpClientChannel.class);
		definedClass.put(new Text("LOCAL"), LocalClientChannel.class);
	}
	//private static Set<Text> openServiceTypes = new HashSet<Text> (); //define the state of service type open.
	
	/**
	 * add the user defined service. Defined class that extends {@link ClientChannelBase} is bound to a type.  
	 * @param type
	 */
	public static void addServiceType(Text type, Class sClass) throws Exception{
		if (sClass.getSuperclass() != ClientChannelBase.class) {
			throw new Exception(sClass.toString() + " is not ClientChannelBase child class."); 
		} else {
			userDefinedTypes.add(type);
			definedClass.put(type, sClass);
		}
		
	}
	/**
	 * list the service types in the manager.
	 * @return all the data service types that manager contains.
	 */
	public String[] listServiceType() {
		//String value = "";
		List<String> ret = new ArrayList<String> ();
		defaultType [] types = defaultType.values();
		for (defaultType tmp: types) {
			ret.add(tmp.toString());
		}
		if (userDefinedTypes.size() > 0) {
			for (Text definedType: userDefinedTypes) {
				ret.add(definedType.toString());
			}
		}
		return ret.toArray(new String[0]);
	}
	/**
	 * search whether the specified service exists.
	 * @param type
	 * @return
	 */
	public static  Class<? extends ClientChannelBase> findService(BinosURL url) {
		String type = url.getServiceType();
		if (checkValid(type)) {
			return definedClass.get(type);
		} else {
			return null;
		}
	}
	/**
	 * check whether the type exists in {@link defaultType} or {@link userDefinedTypes}.
	 * return true if exists, and false if not exists.
	 * @param type
	 * @return
	 * @throws Exception 
	 */
	private static  boolean checkValid(String type)  {
		if (definedClass.containsKey(new Text(type))) {
			return true;
		} else {
			return false;
		}
	}
	
	/*public boolean checkVolidate(Text type) {
		
	}*/
}
