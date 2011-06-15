import java.io.File;
import java.io.IOException;

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
		System.out.println("Hello world!");
	//	ProcessBuilder pb = new 
		System.out.println(System.getProperty("user.dir"));
		String [] executorArgs = {"bash", "-c", "./memcached", "-p 12345"};
		File dir = new File(System.getProperty("user.dir"), "third_party/memcached-1.4.0");
		BinosShellCommandExecutor shexec = new BinosShellCommandExecutor(executorArgs, dir);
		try {
			shexec.execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Runtime.getRuntime().addShutdownHook(new shutdownExecProcessThread(shexec));
//		shexec.getProcess().destroy();
	}
}
