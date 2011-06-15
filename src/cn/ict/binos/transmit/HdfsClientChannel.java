package cn.ict.binos.transmit;

import java.io.DataInput;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;

import cn.ict.binos.io.BinosFileInputStream;
import cn.ict.binos.io.BinosFileOutputStream;
/**
 * HdfsClientChannel: provide a channel to access the file in the hdfs
 * @author jiangbing Jun 13, 2011
 *
 * @param <T>
 */
public class HdfsClientChannel<T> extends ClientChannelBase<T> {

	public static Set<String> supportOps = new HashSet<String> ();
	private static Logger LOG = Logger.getLogger(HdfsClientChannel.class.getName());
	private Path path;
	private static String hdfsUri;
	private InputStream in = null;
	private OutputStream out = null;
	static{
		supportOps.add("read");
		supportOps.add("write");
	}
	
	/*
	 *static code block to test the data valid. 
	 */
	static {
		
		try {
			Configuration conf = new Configuration();
			FileSystem fs = FileSystem.get(conf);
			String fsUri = fs.getWorkingDirectory().toString();
			if (fsUri.startsWith("file")) {
				LOG.log(Level.WARNING, "HDFS doesnot operate well!");
				try {
					throw new Exception("HDFS doesnot operate well!");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if (fsUri.startsWith("hdfs")) {
				LOG.log(Level.INFO, "HDFS operate at " + fsUri);
				hdfsUri = fsUri;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	/**
	 * constructor 
	 * @param path: the relative path form parent path {@linkplain hdfsUri} 
	 */
	public HdfsClientChannel(String path) {
		this.path = new Path(hdfsUri,  path);
	}
	/*search ops that BinosURL contains */
	public static boolean searchOps(String ops) {
		return supportOps.contains(ops);
	}
	/**
	 * open an input stream for a file
	 * @return
	 * @throws IOException
	 */
	public InputStream open() throws IOException{
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		if (fs.exists(this.path)) {
			return fs.open(this.path);
		}
		throw new FileNotFoundException(this.path + " doesnot exists.");
	}
	/**
	 * open an output stream for a specified file.
	 * @return
	 */
	public OutputStream create() throws IOException {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		return fs.create(this.path);
	}
}
