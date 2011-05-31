package cn.ict.binos.transmit;

import java.io.DataInput;
import java.io.IOException;

import org.apache.hadoop.io.Text;

/**
 * ServerChannel defines the base interface in the server part of channel.
 * @author jiangbing May 30, 2011
 *
 */
public interface ServerChannel<T> {
	
	public void put(Text key, T value);
	
	public T get(Text key);
	
	public T deleteKeyValue(Text key, T value);
	
	public T deleteValue(T value);
	
	public T update(Text key, T value);
	
	public T readLine(DataInput in) throws IOException;
	
	public void readFully(byte b[], int off, int len) throws IOException;
}
