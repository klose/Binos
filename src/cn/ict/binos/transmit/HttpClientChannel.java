package cn.ict.binos.transmit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import cn.ict.binos.io.BinosFileInputStream;

public class HttpClientChannel<T> extends ClientChannelBase<T> {
	public static Set<String> supportOps = new HashSet<String> ();
	private static Logger LOG = Logger.getLogger(HttpClientChannel.class.getName());
	
	/** Default read timeout (in milliseconds) */
	private final static int DEFAULT_READ_TIMEOUT = 3 * 60 * 1000;
	
	/** Basic/unit connection timeout (in milliseconds) */
	private final static int UNIT_CONNECT_TIMEOUT = 60 * 1000;
	
	/** Number of ms before timing out a copy */
	private static final int DEFAULT_STALLED_COPY_TIMEOUT = 3 * 60 * 1000;
	
	private File file;
	private String path;
	private InputStream in = null;
	private OutputStream out = null;
	static{
		supportOps.add("read");
	}
	public HttpClientChannel(String path) {
		this.path = path;
		
	}
	/*search ops that BinosURL contains */
	public static boolean searchOps(String ops) {
		return supportOps.contains(ops);
	}
	
	  /** 
	   * The connection establishment is attempted multiple times and is given up 
	   * only on the last failure. Instead of connecting with a timeout of 
	   * X, we try connecting with a timeout of x < X but multiple times. 
	   */
	  private void connect(URLConnection connection, int connectionTimeout)
	  throws IOException {
	    int unit = 0;
	    if (connectionTimeout < 0) {
	      throw new IOException("Invalid timeout "
	                            + "[timeout = " + connectionTimeout + " ms]");
	    } else if (connectionTimeout > 0) {
	      unit = Math.min(UNIT_CONNECT_TIMEOUT, connectionTimeout);
	    }
	    // set the connect timeout to the unit-connect-timeout
	    connection.setConnectTimeout(unit);
	    while (true) {
	      try {
	        connection.connect();
	        break;
	      } catch (IOException ioe) {
	        // update the total remaining connect-timeout
	        connectionTimeout -= unit;

	        // throw an exception if we have waited for timeout amount of time
	        // note that the updated value if timeout is used here
	        if (connectionTimeout == 0) {
	          throw ioe;
	        }

	        // reset the connect timeout for the last try
	        if (connectionTimeout < unit) {
	          unit = connectionTimeout;
	          // reset the connect time out for the final connect
	          connection.setConnectTimeout(unit);
	        }
	      }
	    }
	  }
	/**
	 * open an input stream for a file
	 * @return
	 * @throws IOException
	 */
	public InputStream open() throws IOException{
		URL url = new URL(this.path);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setReadTimeout(DEFAULT_READ_TIMEOUT);
		connect(connection, DEFAULT_STALLED_COPY_TIMEOUT);
		return connection.getInputStream();
	}
}
