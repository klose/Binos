import java.io.File;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import net.spy.memcached.AddrUtil;

import cn.ict.binos.util.BinosShell.BinosShellCommandExecutor;
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


public class test {
	
	public static void main(String [] args) {
//		System.out.println("Hello world!");
//	
//		System.out.println(System.getProperty("user.dir"));
//		String [] executorArgs = {"bash", "-c", "./memcached", "-p 12345"};
//		File dir = new File(System.getProperty("user.dir"), "third_party/memcached-1.4.0");
//		BinosShellCommandExecutor shexec = new BinosShellCommandExecutor(executorArgs, dir);
//		try {
//			shexec.execute();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Runtime.getRuntime().addShutdownHook(new shutdownExecProcessThread(shexec));
		String path = "10.5.0.170:234";
		System.out.println(path.matches(".*:.*"));
		CopyOnWriteArrayList<String> serverAddrList = new
		CopyOnWriteArrayList<String> ();
		serverAddrList.add("10.5.0.170:234");
		serverAddrList.add("10.5.0.171:223");
		serverAddrList.add("10.5.0.172:324");
		serverAddrList.add("10.5.0.173:654");
		String a = serverAddrList.toString();
		String b = (a.substring(1, a.length() -1));
		AddrUtil.getAddresses(b);
		//System.out.println(serverAddrList);
	}
}
