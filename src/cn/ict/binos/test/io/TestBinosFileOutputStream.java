package cn.ict.binos.test.io;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import cn.ict.binos.io.BinosFileOutputStream;

public class TestBinosFileOutputStream {
	private File file;
	private BinosFileOutputStream outputStream;
	public TestBinosFileOutputStream(File file) throws IOException {
		this.file = file;
		outputStream = new BinosFileOutputStream(this.file);
	}
	public void testWriteInt() throws IOException {
		int i = 0;
		Random rand = new Random();
		System.out.println(this.outputStream.getPos());
		while (true) {
			outputStream.write(rand.nextInt());
			if (++i > 10000) {
				break;
			}
		}
		outputStream.flush();
		System.out.println(this.outputStream.getPos());
		
	}
	public void testWriteBytes() throws IOException {
		// TODO Auto-generated method stub
		long pos = this.outputStream.getPos();
		byte[] writeBytes = new byte[1024];
		for (int i = 0; i < 1024; i ++) {
			writeBytes[i] = (byte) (i%128);
		}
		long j = 0;
		while (j++ < 10) {
			System.out.println("pos:" + this.outputStream.getPos());
			this.outputStream.write(writeBytes);
		
		}
		this.outputStream.flush();
	}
	public void testClose()  {
		try {
			this.outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean testWriteInt = false;
		boolean testWriteBytes = false;
		
		File file = null ;
		TestBinosFileOutputStream tbfis = null;
		 String usage = "Usage: TestBinosFileOutputStream " +
	      " [-testWriteInt ] [-testWriteBytes ]" + " file";
	    if (args.length == 0) {
	      System.err.println(usage);
	      System.exit(-1);
	    }
		for (int i = 0; i < args.length; ++i) { // parse command line
			if (args[i] == null) {
				continue;
			} else if (args[i].equals("-testWriteInt")) {
		          testWriteInt = true;
	        } else if (args[i].equals("-testWriteBytes")) {
		          testWriteBytes = true;
	        } else {
	        	// file is required parameter
				file = new File(args[i]);
			}
		}
		try {
			tbfis = new TestBinosFileOutputStream(file);
			if (testWriteInt) 
				tbfis.testWriteInt();
			if (testWriteBytes) {
				tbfis.testWriteBytes();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			tbfis.testClose();
		}
	}
}
