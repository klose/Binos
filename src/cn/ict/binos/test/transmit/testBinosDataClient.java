package cn.ict.binos.test.transmit;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.io.Text;

import cn.ict.binos.io.BinosFileOutputStream;
import cn.ict.binos.test.io.TestBinosFileOutputStream;
import cn.ict.binos.transmit.BinosDataClient;
import cn.ict.binos.transmit.BinosHttpServer;
import cn.ict.binos.transmit.BinosURL;

public class testBinosDataClient {
	public static void testLocalRead(BinosURL url) throws Exception {
		InputStream in = BinosDataClient.getInputStream(url);
		int data;
		while ((data = in.read()) != -1) {
			System.out.println(data);
		}
		in.close();
	}
	public static void testLocalWrite(BinosURL url) throws Exception{
		BinosFileOutputStream out = (BinosFileOutputStream) BinosDataClient.getOutputStream(url);
		int i = 0;
		Random rand = new Random();
		System.out.println(out.getPos());
		while (true) {
			out.write(rand.nextInt());
			if (++i > 10000) {
				break;
			}
		}
		out.flush();
		System.out.println(out.getPos());
		out.close();
	}
	public static void testHdfsWrite(BinosURL url) throws Exception{
		FSDataOutputStream out = (FSDataOutputStream)BinosDataClient.getOutputStream(url);
		int i = 0;
		Random rand = new Random();
		System.out.println(out.getPos());
		while (true) {
			out.write(rand.nextInt());
			if (++i > 10000) {
				break;
			}
		}
		out.flush();
		System.out.println(out.getPos());
		out.close();
	}
	public static void testHdfsRead(BinosURL url) throws Exception{
		InputStream in = BinosDataClient.getInputStream(url);
		int data;
		while ((data = in.read()) != -1) {
			System.out.println(data);
		}
		in.close();
	}
	public static void testRemoteRead(BinosURL url) throws Exception {
		InputStream in = BinosDataClient.getInputStream(url);
		int data;
		while ((data = in.read()) != -1) {
			System.out.println(data);
		}
		in.close();
	}
	public static void main(String[] args) throws Exception {
		boolean testLocalWrite = false;
		boolean testLocalRead = false;
		boolean testHdfsWrite = false;
		boolean testHdfsRead = false;
		boolean testRemoteRead = false;
		String path = "";
		BinosURL url;
		TestBinosFileOutputStream tbfis = null;
		 String usage = "Usage: TestBinosDataClient " +
	      " [-testLocalWrite ] [-testLocalRead ]" + " path";
	    if (args.length == 0) {
	      System.err.println(usage);
	      System.exit(-1);
	    }
		for (int i = 0; i < args.length; ++i) { // parse command line
			if (args[i] == null) {
				continue;
			} else if (args[i].equals("-testLocalWrite")) {
		          testLocalWrite = true;
	        } else if (args[i].equals("-testLocalRead")) {
		          testLocalRead = true;
	        } else if (args[i].equals("-testHdfsWrite")) {
	        	testHdfsWrite = true;
	        } else if (args[i].equals("-testHdfsRead")) {
	        	testHdfsRead = true;
	        } else if (args[i].equals("-testRemoteRead")) {
	        	testRemoteRead = true;
	        }
	        else {
	        	path = args[i];
	        }
		}
		try {
			if (testLocalWrite) {
				url = new BinosURL(new Text("LOCAL@Binos#write#" + path));
				testLocalWrite(url);
			}
			if (testLocalRead) {
				url = new BinosURL(new Text("LOCAL@Binos#read#" + path));
				testLocalRead(url);
			} 
			if (testHdfsRead) {
				url = new BinosURL(new Text("HDFS@Binos#read#" + path));
				testHdfsRead(url);
			}
			if (testHdfsWrite) {
				url = new BinosURL(new Text("HDFS@Binos#write#" + path));
				testHdfsWrite(url);
			}
			if (testRemoteRead) {
				url = new BinosURL(new Text("REMOTE@Binos#read#" + path));
				testRemoteRead(url);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
	}
}
