package cn.ict.binos.transmit;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.hadoop.io.Text;

/**
 * TransmitManager manages the type of transmit channel.It checks the
 * status of each service that provides data access, and maintain the 
 * list of service running in the RM.  
 * @author jiangbing May 30, 2011
 *
 */
public class TransmitManager {
	public static Map<BinosURL, ChannelStatus> urlStatus = new ConcurrentHashMap<BinosURL, ChannelStatus>();
	public static void main() {
		
	}
	
}
