package cn.ict.binos.transmit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;


import cn.ict.binos.io.BinosFileInputStream;
import cn.ict.binos.io.BinosFileOutputStream;

/**
 * LocalClientChannel:provide a channel to access local file.
 * @author jiangbing March 30, 2011
 *
 * @param <T>
 */
public class LocalClientChannel<T> extends ClientChannelBase<T> {
	public static Set<String> supportOps = new HashSet<String> ();
	private static Logger LOG = Logger.getLogger(LocalClientChannel.class.getName());
	private File file;
	private String path;
	private InputStream in = null;
	private OutputStream out = null;
	static{
		supportOps.add("open");
		supportOps.add("create");
	}
	public LocalClientChannel(String path) {
		this.path = path;
		this.file = new File(path);
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
		if (!this.file.exists()) {
			throw new FileNotFoundException(this.path);
		} else {
			in = new BinosFileInputStream(this.file);
			return in;
		}	
	}
	/**
	 * open an output stream for a specified file.
	 * @return
	 */
	public OutputStream create() throws IOException {
		if (!this.file.exists()) {
			if (this.file.createNewFile()) {
				LOG.log(Level.INFO, "LocalClientChannel create a new file " + file.toString());
			} else {
				LOG.log(Level.SEVERE, "LocalClientChannel cannot create a new file " + file.toString());
				throw new IOException("LocalClientChannel cannot create a new file " + file.toString());
			}
		}
		out = new BinosFileOutputStream(this.file);
		return out;
	}
}
