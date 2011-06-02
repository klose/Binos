package cn.ict.binos.io;

import java.io.IOException;
/**
 * BinosSyncable: syn the data.
 * @author jiangbing Jun 2, 2011
 *
 */
public interface BinosSyncable {
	/**
	 * sync from memory to disk.
	 * 
	 * @throws IOException
	 * 				if any error occurs
	 */
	public void sync() throws IOException;

	/**
	 * Flush out the data in client's user buffer. After the return of this
	 * call, new readers will see the data.
	 * 
	 * @throws IOException
	 *             if any error occurs
	 */
	public void hflush() throws IOException;

	/**
	 * Similar to posix fsync, flush out the data in client's user buffer all
	 * the way to the disk device (but the disk may have it in its cache).
	 * 
	 * @throws IOException
	 *             if error occurs
	 */
	public void hsync() throws IOException;
}
