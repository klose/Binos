package cn.ict.binos.transmit;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.hadoop.fs.FileSystem;

public class LocalClientChannel<T> extends ClientChannelBase<T> {
	private File file;
	private String path;
	private Class<? extends DataInputStream> inputStreamClass;
	public LocalClientChannel(String path) {
		this.path = path;
		this.file = new File(path);
	}
	public InputStream open() throws IOException{
		if (!this.file.exists()) {
			throw new FileNotFoundException(this.path);
		} 
		return 
	}
	public String readLine(DataInputStream in) {
		
		
			
	}
}
