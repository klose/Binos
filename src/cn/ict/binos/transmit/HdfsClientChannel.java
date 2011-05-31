package cn.ict.binos.transmit;

import java.io.DataInput;
import java.io.IOException;

import org.apache.hadoop.io.Text;

public class HdfsClientChannel<T> extends ClientChannelBase<T> {

	@Override
	public T getValue(Text key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T putValue(Text key, T value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T updateValue(Text key, T value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T delete(Text key, T value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T readLine(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T readBuffer(byte[] b, int off, int len, DataInput in)
			throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
