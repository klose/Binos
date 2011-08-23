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
	private String ops = null; //the operation eg. read, write, get, put, delete, update... 
	private String opsUrl = null;//the path that operation handles, eg, hdfs://10.5.0.170:26666/user/jiangbing/input/a.txt
	/**
	 * construct a BinosURL.
	 * In the context that task to be executed, path contains two kind of content.
	 * One is operation,the other is service-dependent identifies.
	 * <p>Example:</p>
	 * <p><blockquote><pre>
	 * 	<path type="LOCAL" ops="read">/tmp/input/input1</path>
	 *  <path type="HDFS" ops="write">hdfs://10.5.0.170:26666/user/hadoop/output/output1</path>
	 *  <path type="REMOTE" ops="read">http://10.5.0.171:34212/tmp/remote/input/input2</path>
	 * </pre></blockquote></p>
	 *
	 * @param path: "read#/tmp/input/input1" is the first path, whose format is 'ops'#'path value'. 
	 * @param type: the type of service.
	 */
	public BinosURL(String path, String type) throws Exception{
		if (!ServiceType.checkValid(type)) {
			LOG.log(Level.SEVERE, "ServiceType:" + type + "Not Defined!");
			throw new Exception( "ServiceType:" + type + "Not Defined!");
		} else {
			this.type = type;
			String[] tmp = path.split("#");
			if (tmp.length < 2) {
				LOG.log(Level.SEVERE, "Path " + path + " format error!" );
				throw new Exception("Path " + path + " format error!" );
			} 
			this.ops = tmp[0];
			this.opsUrl = tmp[1];
			this.url = new Text(this.type+"#"+path);
		}
	}
	/**
	 * construct from an intergrate BinosURL.
	 * 
	 * url format : ($ServiceType)@Binos#($operation)#($service dependent url) 
	 * @param url 
	 */
	public BinosURL(Text url) {
		this.url = url;
		parseURL();
	}
	public static String transformBinosURL(String path, String type, String ops) {
		return type + "@Binos#" + ops + "#" + path;
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
	
	/*
	 *  get the path from BinosURL
	 *  For example: 
	 * */
	public static String getPath(BinosURL url) {
		return url.getServiceOpsUrl();
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
