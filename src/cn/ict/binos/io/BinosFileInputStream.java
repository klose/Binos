package cn.ict.binos.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.nio.ByteBuffer;



/*******************************************************
 * BinosFileInputStream is used to read file as a input 
 * stream. It provides seek and skip operation.
 * @author jiangbing
 *******************************************************/
public class BinosFileInputStream extends BinosInputStream {
  private FileInputStream fis;
  private long position;

  public BinosFileInputStream(File f) throws IOException {
    this.fis = new TrackingFileInputStream(f);
  }
  
  public void seek(long pos) throws IOException {
    fis.getChannel().position(pos);
    this.position = pos;
  }
  
  public long getPos() throws IOException {
    return this.position;
  }
  
  public boolean seekToNewSource(long targetPos) throws IOException {
    return false;
  }
  
  /*
   * Just forward to the fis
   */
  public int available() throws IOException { return fis.available(); }
  public void close() throws IOException { fis.close(); }
  public boolean markSupport() { return false; }
  
  public int read() throws IOException {
    try {
      int value = fis.read();
      if (value >= 0) {
        this.position++;
      }
      return value;
    } catch (IOException e) {                 // unexpected exception
      throw new FSError(e);                   // assume native fs error
    }
  }
  
  public int read(byte[] b, int off, int len) throws IOException {
    try {
      int value = fis.read(b, off, len);
      if (value > 0) {
        this.position += value;
      }
      return value;
    } catch (IOException e) {                 // unexpected exception
      throw new FSError(e);                   // assume native fs error
    }
  }
  
  public int read(long position, byte[] b, int off, int len)
    throws IOException {
    ByteBuffer bb = ByteBuffer.wrap(b, off, len);
    try {
      return fis.getChannel().read(bb, position);
    } catch (IOException e) {
      throw new FSError(e);
    }
  }
  public long skip(long n) throws IOException {
    long value = fis.skip(n);
    if (value > 0) {
      this.position += value;
    }
    return value;
  }
   class FSError extends Error {
	  private static final long serialVersionUID = 1L;

	  FSError(Throwable cause) {
	    super(cause);
	  }
	}
}
class TrackingFileInputStream extends FileInputStream {
    public TrackingFileInputStream(File f) throws IOException {
      super(f);
    }
    
    public int read() throws IOException {
      int result = super.read();
      return result;
    }
    
    public int read(byte[] data) throws IOException {
      int result = super.read(data);
      return result;
    }
    
    public int read(byte[] data, int offset, int length) throws IOException {
      int result = super.read(data, offset, length);
      return result;
    }
  }