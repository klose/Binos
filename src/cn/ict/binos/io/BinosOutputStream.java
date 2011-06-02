package cn.ict.binos.io;

import java.io.*;

/**
 * Utiltity that wraps the {@link OutputStream}. 
 * @author jiangbing Jun 2, 2011
 *
 */
public class BinosOutputStream extends DataOutputStream implements
		BinosSyncable {
	private OutputStream wrappedStream;
	private static class PositionCache extends FilterOutputStream {
		long position;

		public PositionCache(OutputStream out, long pos) throws IOException {
			super(out);

			position = pos;
		}

		public void write(int b) throws IOException {
			out.write(b);
			position++;
		}

		public void write(byte b[], int off, int len) throws IOException {
			out.write(b, off, len);
			position += len; // update position
		}
		
		public long getPos() throws IOException {
			return position; // return cached position
		}
		

		public void close() throws IOException {
			out.close();
		}
	}

	public BinosOutputStream(OutputStream out) throws IOException {
		this(out, 0);
	}

	public BinosOutputStream(OutputStream out, long startPosition)
			throws IOException {
		super(new PositionCache(out, startPosition));
		wrappedStream = out;
	}

	public long getPos() throws IOException {
		return ((PositionCache) out).getPos();
	}
	
	public void close() throws IOException {
		out.close(); // This invokes PositionCache.close()
	}

	// Returns the underlying output stream. This is used by unit tests.
	public OutputStream getWrappedStream() {
		return wrappedStream;
	}

	@Override
	// Syncable
	@Deprecated
	public void sync() throws IOException {
		if (wrappedStream instanceof BinosSyncable) {
			((BinosSyncable) wrappedStream).sync();
		}
	}

	@Override
	// Syncable
	public void hflush() throws IOException {
		if (wrappedStream instanceof BinosSyncable) {
			((BinosSyncable) wrappedStream).hflush();
		} else {
			wrappedStream.flush();
		}
	}

	@Override
	// Syncable
	public void hsync() throws IOException {
		if (wrappedStream instanceof BinosSyncable) {
			((BinosSyncable) wrappedStream).hsync();
		} else {
			wrappedStream.flush();
		}
	}
}