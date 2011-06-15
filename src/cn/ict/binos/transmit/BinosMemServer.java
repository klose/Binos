package cn.ict.binos.transmit;



import java.io.File;
import java.io.IOException;
import java.util.Vector;

import cn.ict.binos.util.BinosShell.BinosShellCommandExecutor;

/**
 * Provide MemCached server.
 * @author jiangbing Jun 15, 2011
 *
 */
class shutdownExecProcessThread extends Thread {
	BinosShellCommandExecutor bsce;
	public shutdownExecProcessThread(BinosShellCommandExecutor executor) {
		bsce = executor;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		bsce.getProcess().destroy();
	}
}
public class BinosMemServer {
	private final static String userName;
	private final int port;
	private String otherArgs;
	private Vector<String> vargs;
	
	static {
		userName = System.getProperty("user.name");
	}
	
	public BinosMemServer(int port, String otherArgs) {
		this.port = port;
		this.otherArgs = otherArgs;
		this.vargs = new Vector<String>();
	}
	public void start() {
		vargs.add("bash -c");
		vargs.add("./memcached");
		vargs.add("-p "+ this.port);
		vargs.add("-u" + userName);
		vargs.add(otherArgs);
		File dir = new File(System.getProperty("user.dir"), "third_party/memcached-1.4.0");
		BinosShellCommandExecutor shexec = new BinosShellCommandExecutor(vargs.toArray(new String[0]), dir);
		try {
			shexec.execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Runtime.getRuntime().addShutdownHook(new shutdownExecProcessThread(shexec));
	}
}
