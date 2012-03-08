package cn.ict.binos.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class AppendableObjectOutputStream extends ObjectOutputStream{
	AppendableObjectOutputStream(OutputStream out) throws IOException {
		super(out);
	}

	@Override
	protected void writeStreamHeader() throws IOException {
		// TODO Auto-generated method stub
		
	}
	public static void main(String[] args) throws IOException {
		
//		OutputStream fos = new FileOutputStream("/tmp/abc", true);
		
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		ObjectOutputStream oos = new ObjectOutputStream(baos);
//		//List<Integer> list = new ArrayList<Integer> ();
//		for (int i = 0; i < 1000; i++) {
//			oos.writeObject(i);
//		}
//		baos.flush();
//		fos.write(baos.toByteArray());
//		baos.close();
//		fos.close();
		FileOutputStream fos = new FileOutputStream("/tmp/abc1", true);
		for (int i = 1000; i < 2000; i++) {
			fos.write(i);
		}
		fos.close();
		FileInputStream fis = new FileInputStream("/tmp/abc1");
		int data;
		while (-1 != (data = fis.read())) {
			System.out.println(data);
		}
		
		
//		
//		File file = new File("/tmp/abc");
//		ObjectOutputStream oos1 ;
//		ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
//		FileOutputStream fos1 = new FileOutputStream(file, true);
////		if (file.exists()) {
////			oos1 = new AppendableObjectOutputStream(baos1);
////		}
////		else {
//			oos1 = new ObjectOutputStream(baos1);
//		//}
//		for (int i = 1000; i < 2000; i++) {
//			oos1.writeObject(i);
//		}
//		
//		oos1.flush();
//		baos1.writeTo(fos1);
////		fos1.write(baos1.toByteArray());
//		baos1.close();
//		fos1.close();
//		
//		
//		InputStream is = new FileInputStream("/tmp/abc");
//		ObjectInputStream ois = new ObjectInputStream(is);
//		Integer tmp = null;
//		try {
//			while (null != (tmp = (Integer) ois.readObject())) {
//				System.out.println(tmp);
//			}
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	
//	
	
//	}
	}
}
