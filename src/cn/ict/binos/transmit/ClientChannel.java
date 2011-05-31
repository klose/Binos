package cn.ict.binos.transmit;
import java.io.DataInput;
import java.io.IOException;
import org.apache.hadoop.io.Text;
/**
 * ClientChannel : defines access api between client and server.
 * @author jiangbing May 30, 2011
 *
 * @param <T> represent the base type of data.
 */
public interface ClientChannel<T> {
	
	public T getValue(Text key);
	
	public T putValue(Text key, T value);
	
	public T updateValue(Text key, T value);
	
	public T delete(Text key, T value);
	
	public T readLine(DataInput in) throws IOException;
	
	public T readBuffer(byte b[], int off, int len, DataInput in) throws IOException;

}
