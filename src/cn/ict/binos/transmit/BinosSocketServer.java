package cn.ict.binos.transmit;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import cn.ict.binos.test.performance.TestUtility;

public class BinosSocketServer {
	private static int port = -1;
	public static void main(String[] args) throws IOException {

		System.out.println("This is SimpleSocketServer running on port "
				+ args[0]);
		port = Integer.parseInt(args[0]);
		ServerSocket serverSocket = null;
		boolean listening = true;
		boolean isFile = false;
		
		if (args[1].equals("file")) {
			isFile = true;
		}
		
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("Could not listen on port:" + port);
			System.exit(-1);
		}
		

		while (listening) {
			new ThreadedHandler(serverSocket.accept(), isFile).start();
		}

		serverSocket.close();
	}
}
class ThreadedHandler extends Thread {
	private static final int size = TestUtility.size;
	private static final int readCount = TestUtility.readCount;
	private Socket socket = null;
	boolean isFile;
	byte[] data;
	public ThreadedHandler(Socket socket, boolean isFile) {
		super("ThreadedHandler");
		this.socket = socket;
		this.isFile = isFile;
		this.data = new byte[size];
		for (int i = 0; i < size; i++) {
			data[i] = (byte)(i % 255);
		}
	}

	@Override
	public void run() {

		try {
			//PrintWriter out = new PrintWriter(socket.getOutputStream(),
				//	true);
			OutputStream out = socket.getOutputStream();
//			BufferedReader in = new BufferedReader(new InputStreamReader(
//					socket.getInputStream()));
//			
//			in.readLine();
			//out.print(Utility.getPage("SimpleSocketServer", 512));
			if (!isFile) {
				long start = System.nanoTime();
				//System.out.println(System.nanoTime());
				for (int i = 0; i < readCount; i++) {
					out.write(data, 0, size);
					out.flush();
				}
				System.out.println("Memory used Time:" + (System.nanoTime() - start));
			}
			else {
				long start = System.nanoTime();
				FileInputStream ins = new FileInputStream("/tmp/test");
				for (int i = 0; i < readCount; i++) {
					ins.read(data, 0, size);
					out.write(data, 0, size);
					out.flush();
				}
				System.out.println("File used Time:" + (System.nanoTime() - start));
			}
			out.close();
			//in.close();
//			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
