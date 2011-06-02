package cn.ict.binos.transmit;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;


/**
 * BinosURL is access interface open for client. It depicts the context of server and client.
 * @author jiangbing May 30, 2011
 *
 */
public class BinosURL implements Writable, Cloneable {
	private Logger LOG = Logger.getLogger(BinosURL.class.getName());
	private Text url;
	private String type = null; //the service type
	private String ops = null; //the operation eg. readLine(FileInputStream),getValue...
	private String opsUrl = null;//the path that operation handles, eg, hdfs://10.5.0.170:26666/user/jiangbing/input/a.txt
	public BinosURL(Text url) {
		this.url = url;
		parseURL();
	}
	
	/*split the url to fetch type, ops and opsUrl */
	private void parseURL() {
		String[] tmp = this.url.toString().split("#");
		if (tmp.length < 3) {
			try {
				throw new Exception("BinosURL " + this.url + " ERROR format!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			type = (tmp[0].split("@"))[0];
			if (!ServiceType.checkValid(type)) {
				LOG.log(Level.SEVERE, "Data service " + type + " Not Exists!");
				try {
					throw new Exception( "Data service " + type + " Not Exists!");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			ops = tmp[1];
			opsUrl = tmp[2];
			LOG.log(Level.FINE, "BinosURL type:" + type + " operation:" + ops + " operation url:" + opsUrl);
		}
	}
	
	
	public String getServiceType() {
		if (this.type != null) {
			return this.type;
		} else {
			try {
				throw new Exception("Binos getServiceType" + this.url + " : ERROR format!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				return null;
			}
		}
	}
	public String getServiceOps() {
		if (this.ops != null) {
			return this.ops;
		} else {
			try {
				throw new Exception("Binos getServiceOps" + this.url + " : ERROR format!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				return null;
			}
		}
	}
	public String getServiceOpsUrl() {
		if (this.opsUrl != null) {
			return this.opsUrl;
		} else {
			try {
				throw new Exception("Binos getServiceOpsUrl" + this.url + " : ERROR format!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				return null;
			}
		}
	}
	/**
	 * There are two kind of data access service.The one is default service, 
	 * the other one is defined service.   
	 * @return
	 */
	public boolean isDefaultService() {
		return ServiceType.isDefaultService(this.type);
	}
//	/**
//	 * 
//	 * @return
//	 */
//	public Method getOperation(ClientChannelBase base) {
//		Class cls = Class.forName(ClientChannelBase.class.getName());
//		return new Method();
//	}
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
