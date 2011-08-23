package cn.ict.binos.transmit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import cn.ict.binos.transmit.ClientChannel;
import org.apache.hadoop.io.Text;

import com.transformer.compiler.TransmitType;

public class ServiceType {
	public static enum defaultType {NONE, HDFS, MEMCACHED, MESSAGE, REMOTE, LOCAL};
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
	 * This is used to transform the type of transmit into service type.
	 * for example, TramsmitType is defined in Compiler.  
	 * @param transmitType: the type of transmit.
	 * @param isInput:  
	 * @return
	 */
	public static defaultType transmitTypeToServiceType(String transmitType, boolean isInput) {
		TransmitType type = TransmitType.valueOf(transmitType);
		switch(type) {
			case HDFS: return defaultType.HDFS;
			case HTTP: 
				if (isInput) return defaultType.REMOTE;
				else return defaultType.LOCAL;
			case MESSAGE:
				return defaultType.MESSAGE;
			default:
				return defaultType.NONE;
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
			return definedClass.get(new Text(type));
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
	public static  boolean checkValid(String type)  {
		if (definedClass.containsKey(new Text(type))) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isDefaultService(String type) {
		if (userDefinedTypes.contains(new Text(type))) {
			return false;
		} else {
			return true;
		}
	}
	
	/*public boolean checkVolidate(Text type) {
		
	}*/
}
