package cn.ict.binos.transmit;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.Text;

public class BinosDataClient<T> {
	private DataInput in;
	private DataOutput out;
	//private List<BinosURL>  requestURLs = new ArrayList<BinosURL> (); // request the list of url. 
	private BinosURL reqURL;
	public void getData(Text url, T value) {
		reqURL  = new BinosURL(url);
		Class<? extends ClientChannelBase> ccb = ServiceType.findService(reqURL);
		
		
	}
	
	public static void main() {
	
	}
}
