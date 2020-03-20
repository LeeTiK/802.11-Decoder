package leetik.w80211.protocol.wlan.frame.control.inter;

/**
 * Control Frame : request to Send<br/>
 * <ul>
 * <li>duration id : 2 Bytes</li>
 * <li>receiver address : 6 Bytes</li>
 * <li>transmitter address : 6 Bytes</li>
 * </ul>
 * 
 * @author Bertrand Martel
 * 
 */
public interface IBlockAck {

	public byte[] getDurationId();
	
	public byte[] getTransmitterAddr();
	
	public byte[] getReceiverAddr();
}
