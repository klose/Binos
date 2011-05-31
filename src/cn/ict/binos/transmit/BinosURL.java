package cn.ict.binos.transmit;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

/**
 * BinosURL is access interface open for client. It depicts the context of server and client.
 * @author jiangbing May 30, 2011
 *
 */
public class BinosURL implements Writable, Cloneable {
	private Text url ;
	public BinosURL(Text url) {
		this.url = url;
	}
	
	
	public String getServiceType() {
		String[] tmp = this.url.toString().split("@");
		if (tmp[0].length() > 0) {
			return tmp[0];
		} else {
			try {
				throw new Exception(this.url.toString() + " : ERROR format!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				return null;
			}
		}
	}
	/**
	 * 
	 * @return
	 */
	public Method getOperation(ClientChannelBase base) {
		Class cls = Class.forName(ClientChannelBase.class.getName());
		return new Method();
	}
	public Text getUrl() {
		return this.url;
	}
	@Override
	public void readFields(DataInput arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void write(DataOutput arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
}
