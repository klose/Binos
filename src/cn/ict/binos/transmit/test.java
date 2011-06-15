package cn.ict.binos.transmit;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.Text;

public class test {
	public static void main(String [] args) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
		BinosURL url = new BinosURL(new Text("LOCAL@Binos#read#/tmp/a.txt"));
		Class<? extends ClientChannelBase> channelCls = ServiceType.findService(url);
		System.out.println(channelCls.toString());
		//channelCls.getConstructor(parameterTypes)
		Method checkOps = channelCls.getMethod("searchOps", java.lang.String.class);
		System.out.println(checkOps.toGenericString());
//		Class cls[] = new Class[1];
//		cls[0] = String.class;
	
		Object valObj = checkOps.invoke(channelCls, "write");
		
		Boolean val = (Boolean) valObj;
		System.out.println(val);
		Constructor cons = channelCls.getConstructor(String.class);
		Object a = cons.newInstance("/tmp/a.txt");
		Method openM = channelCls.getMethod("open");
		InputStream ins = (InputStream) openM.invoke(a);
		while (ins.read() != -1) {
			System.out.println(ins.read());
		}
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		
		System.out.println(fs.getUri());
		System.out.println(fs.getWorkingDirectory());
		
	}
}
