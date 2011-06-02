package cn.ict.binos.io;

import java.io.*;


/** Stream that permits seeking. */

public interface BinosSeekable {
  /**
   * Seek to the given offset from the start of the file.
   * The next read() will be from that location.  Can't
   * seek past the end of the file.
   */
  void seek(long pos) throws IOException;
  
  /**
   * Return the current offset from the start of the file
   */
  long getPos() throws IOException;

  /**
   * Seeks a different copy of the data.  Returns true if 
   * found a new source, false otherwise.
   */
  boolean seekToNewSource(long targetPos) throws IOException;
}