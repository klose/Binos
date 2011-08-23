package cn.ict.binos.test.performance;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class testReadRemote {
	private static final int size = TestUtility.size;
	private static final int readCount = TestUtility.readCount; 
	public static void main(String [] args) throws NumberFormatException, UnknownHostException, IOException {
		Socket socket  = new Socket(args[0], Integer.parseInt(args[1]));
		InputStream in = socket.getInputStream();
		byte[] data = new byte[size];
		long delta = System.nanoTime();
		for (int i = 0; i < readCount; i++) {
			
		}
		delta = System.nanoTime() - delta;
		long start = System.nanoTime();
		for (int i = 0; i < readCount; i++) {
			in.read(data, 0, size);
		}
		System.out.println("used Time:" + (System.nanoTime() - start - delta));
		
		in.close();
		//socket.close();
	}
}
