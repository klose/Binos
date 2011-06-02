package cn.ict.binos.test.io;

import java.io.File;
import java.io.IOException;

import cn.ict.binos.io.BinosFileInputStream;

/**
 * Test BinosFileInputStream 
 * @author jiangbing Jun 1, 2011
 *
 */
public class TestBinosFileInputStream {
	private File file;
	private BinosFileInputStream inputStream;
	public TestBinosFileInputStream(File file) throws IOException {
		this.file = file;
		inputStream = new BinosFileInputStream(this.file);
	}
	public void testReadInt() throws IOException {
		this.inputStream.seek(0);
		int tmp = 0;
		while ((tmp = inputStream.read()) != -1) {
			System.out.println(tmp);
		}
	}
	public void testReadBytes() throws IOException {
		// TODO Auto-generated method stub
		this.inputStream.seek(0);
		long pos = this.inputStream.getPos();
		byte[] readBytes = new byte[1024];
		while (this.inputStream.read(readBytes, 0, 1024) != -1) {
			System.out.println(String.valueOf(readBytes));
			pos = this.inputStream.getPos();
			System.out.println(pos);
		}
	}
	public void testClose()  {
		try {
			this.inputStream.close();
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
		boolean testReadInt = false;
		boolean testReadBytes = false;
		File file = null ;
		TestBinosFileInputStream tbfis = null;
		 String usage = "Usage: TestBinosFileInputStream " +
	      "[-testReadInt ] [-testReadBytes ]" + " file";
	    if (args.length == 0) {
	      System.err.println(usage);
	      System.exit(-1);
	    }
		for (int i = 0; i < args.length; ++i) { // parse command line
			if (args[i] == null) {
				continue;
			} else if (args[i].equals("-testReadInt")) {
		          testReadInt = true;
	        } else if (args[i].equals("-testReadBytes")) {
		          testReadBytes = true;
	        } else {
				// file is required parameter
				file = new File(args[i]);
			}
		}
		try {
			tbfis = new TestBinosFileInputStream(file);
			if (testReadInt) 
				tbfis.testReadInt();
			if (testReadBytes) {
				tbfis.testReadBytes();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			tbfis.testClose();
		}
	}
	

}
