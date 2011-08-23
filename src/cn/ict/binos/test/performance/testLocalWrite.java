package cn.ict.binos.test.performance;

import java.util.Random;

import org.apache.hadoop.io.Text;

import cn.ict.binos.io.BinosFileOutputStream;
import cn.ict.binos.transmit.BinosDataClient;
import cn.ict.binos.transmit.BinosURL;

public class testLocalWrite {
	public static void testLocalWriteBytes(BinosURL url, int blockSize, int blockNum) throws Exception{
		BinosFileOutputStream out = (BinosFileOutputStream) BinosDataClient.getOutputStream(url);
		//int i = 0;
		byte[] data = new byte[blockSize];
		new Random().nextBytes(data);
		long delta = System.nanoTime();
		for (int i = 0; i < blockNum; i++) {	
		}
		delta = System.nanoTime() - delta;
		long start = System.nanoTime();
		for(int i = 0; i < blockNum; i++) {
			out.write(data);
		}
		System.out.println(" Write local file used Time:" + (System.nanoTime() - start - delta));
		out.close();
	}
	/**
	 * @param args
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public static void main(String[] args) throws NumberFormatException, Exception {
		// TODO Auto-generated method stub
	
		BinosURL url = new BinosURL(new Text("LOCAL@Binos#write#" + "/tmp/test"));
		testLocalWriteBytes(url, TestUtility.size, TestUtility.readCount);
	}

}
