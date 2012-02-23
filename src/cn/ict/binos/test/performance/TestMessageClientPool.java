package cn.ict.binos.test.performance;

import java.io.IOException;
import java.io.InputStream;
import org.apache.hadoop.io.Text;
import cn.ict.binos.io.BinosFileInputStream;
import cn.ict.binos.io.BinosFileOutputStream;
import cn.ict.binos.transmit.BinosDataClient;
import cn.ict.binos.transmit.BinosURL;
import cn.ict.binos.transmit.HttpClientChannel;
import cn.ict.binos.transmit.MessageClientChannel;

public class TestMessageClientPool {
	
	public static void writeBytesToMP(String key, int totalNum) {
		MessageClientChannel mcc = new MessageClientChannel();
		byte[] data = new byte[totalNum];
		long start = System.nanoTime();
		mcc.putValue(key, data);
		System.out.println(" Write to message Pool used Time:" + (System.nanoTime() - start) + "ns");
		//mcc.FreeData(key);
	}
	public static void writeBytesToFile(BinosURL url, int totalNum) throws Exception {
		BinosFileOutputStream out = (BinosFileOutputStream) BinosDataClient.getOutputStream(url);
		byte[] data = new byte[totalNum];
		long start = System.nanoTime();
		out.write(data);
		out.close();
		System.out.println(" Write local file used Time:" + (System.nanoTime() - start) + "ns");
		
	}
	public static void readBytesFromMP(String key, int totalNum) {
		MessageClientChannel mcc = new MessageClientChannel();
		byte[] data;
		long start = System.nanoTime();
		data = mcc.getValue(key);
		System.out.println(data.length);
		System.out.println(" Read From message Pool used Time:" + (System.nanoTime() - start) + "ns");
	}
	public static void readBytesFromFile(BinosURL url, int totalNum) throws Exception {
		BinosFileInputStream in = (BinosFileInputStream) BinosDataClient.getInputStream(url);
		long start = System.nanoTime();
		byte[] data = new byte[totalNum];
		in.read(data);
		System.out.println(data.length);
		System.out.println("Read local file used Time:" + (System.nanoTime() - start) + "ns");
	}

	public static void readBytesFromConnection(String httpUrl, int totalNum) throws IOException {
//		"http://127.0.0.1:36661/output?file=/tmp/JLoopClient/1_1_0outputPath0"
		HttpClientChannel hcc = new HttpClientChannel(httpUrl); 		 
		  InputStream in = hcc.open();
		  long start = System.nanoTime();
		  byte[] data = new byte[totalNum];
			in.read(data);
			System.out.println(data.length);
			System.out.println("Read data from remote http used Time:" + (System.nanoTime() - start) + "ns");
	}
	public static void main(String[] args) throws Exception {
		int totalNum = Integer.parseInt(args[0]);
//
		writeBytesToMP("MSG://test-performace-MSG-Pool-write", totalNum);
		BinosURL url = new BinosURL(
				new Text("LOCAL@Binos#write#" + "/tmp/test"));
		writeBytesToFile(url, totalNum);

		readBytesFromMP("MSG://test-performace-MSG-Pool-write", totalNum);
		BinosURL url1 = new BinosURL(new Text("LOCAL@Binos#read#" + "/tmp/test"));
		readBytesFromFile(url1, totalNum);
		MessageClientChannel mcc = new MessageClientChannel();
		
		readBytesFromConnection(args[1], totalNum);
		
		
		mcc.FreeData("MSG://test-performace-MSG-Pool-write");
		
		
	}
}
