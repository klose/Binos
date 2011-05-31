package cn.ict.binos.transmit;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

/**
 * ChannelStatus represents the status of each Channel.
 * @author jiangbing May 30, 2011
 *
 */
public class ChannelStatus implements Writable, Cloneable{
	
	public static enum State {RUNNING, SUCCEEDED, FAILED,  KILLED, 
         COMMIT_PENDING, FAILED_UNCLEAN, KILLED_UNCLEAN};
   
    //public static Text channelType = new Text (); //the type of the channel.
	
    private Text channelType;  // the type of the channel
    private volatile State channelState; // the state of the channel 
    public ChannelStatus(Text channelType, State initialState) {
    	
    }
    
	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		
	}
	 
}
	