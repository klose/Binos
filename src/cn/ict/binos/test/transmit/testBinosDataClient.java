package cn.ict.binos.test.transmit;

import java.io.InputStream;

import org.apache.hadoop.io.Text;

import cn.ict.binos.transmit.BinosDataClient;
import cn.ict.binos.transmit.BinosURL;

public class testBinosDataClient {
	public static void main(String[] args) throws Exception {
		BinosURL url = new BinosURL(new Text("LOCAL@Binos#read#/tmp/write"));
		InputStream in = BinosDataClient.getInputStream(url);
		int data;
		while ((data = in.read()) != -1) {
			System.out.println(data);
		}
		in.close();
	}
}
