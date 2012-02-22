package cn.ict.binos.transmit;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



public abstract class ClientChannelBase<T> implements ClientChannel<T> {

	
	public  static boolean searchOps(String ops){
		return false;
	}
	
	@Override
	public T getValue(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int putValue(String key, T value) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public T updateValue(String key, T value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T delete(String key, T value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream open() throws IOException {
		// TODO Auto-generated method stub
		return  null;
	}
	
	@Override
	public OutputStream create() throws IOException {
		return null;
	}
	
	@Override
	public void readBuffer(byte[] b, int off, int len, DataInput in)
			throws IOException {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public void writeBuffer(byte[] b, int off, int len, DataOutput out)
			throws IOException {
		// TODO Auto-generated method stub
		
	}
	
}
