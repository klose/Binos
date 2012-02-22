package cn.ict.binos.transmit;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * ClientChannel : defines access api between client and server.
 * @author jiangbing May 30, 2011
 *
 * @param <T> represent the base type of data.
 */
public interface ClientChannel<T> {
	
	public T getValue(String key);
	
	public int putValue(String key, T value);
	
	public T updateValue(String key, T value);
	
	public T delete(String key, T value);
	
	public InputStream open() throws IOException;
	
	public OutputStream create() throws IOException;
	
	
	public void readBuffer(byte b[], int off, int len, DataInput in) throws IOException;
	
	
	public void writeBuffer(byte b[], int off, int len, DataOutput out) throws IOException;
}
