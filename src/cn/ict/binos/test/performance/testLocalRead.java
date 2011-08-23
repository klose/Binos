package cn.ict.binos.test.performance;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class testLocalRead {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		byte [] data = new byte[TestUtility.size];
		FileInputStream fis = new FileInputStream("/tmp/test");
		long start = System.nanoTime();
		for (int i = 0; i < TestUtility.readCount; i++) {
			fis.read(data, 0, TestUtility.size);
		}
		System.out.println("read local file used Time:" + (System.nanoTime() - start));
		fis.close();
	}

}
