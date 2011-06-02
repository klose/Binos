package cn.ict.binos.io;

import java.io.IOException;

public interface BinosPositionedReadable {
	/**
	 * Read upto the specified number of bytes, from a given position within a
	 * file, and return the number of bytes read. This does not change the
	 * current offset of a file, and is thread-safe.
	 */
	public int read(long position, byte[] buffer, int offset, int length)
			throws IOException;

	/**
	 * Read the specified number of bytes, from a given position within a file.
	 * This does not change the current offset of a file, and is thread-safe.
	 */
	public void readFully(long position, byte[] buffer, int offset, int length)
			throws IOException;

	/**
	 * Read number of bytes equalt to the length of the buffer, from a given
	 * position within a file. This does not change the current offset of a
	 * file, and is thread-safe.
	 */
	public void readFully(long position, byte[] buffer) throws IOException;
}
