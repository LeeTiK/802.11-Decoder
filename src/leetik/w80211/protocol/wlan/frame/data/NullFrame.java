package leetik.w80211.protocol.wlan.frame.data;

import leetik.w80211.protocol.wlan.WlanFramePacket;
import leetik.w80211.protocol.wlan.frame.WlanDataAbstr;
import leetik.w80211.protocol.wlan.frame.data.inter.INullFrame;

import java.nio.ByteBuffer;

/**
 * Null data or qos data frame decoder
 * 
 * @author Bertrand Martel
 * 
 */
public class NullFrame extends WlanDataAbstr implements INullFrame {

	/**
	 * Decode null frame
	 * 
	 * @param frame
	 *            frame with omitted control frame
	 * @param toDS
	 *            to distribution system indicator
	 * @param fromDS
	 *            from distribution system indicator
	 */
	@Deprecated
	public NullFrame(byte[] frame, boolean toDS, boolean fromDS) {
		super(frame, toDS, fromDS);
	}

	public NullFrame(ByteBuffer byteBuffer, WlanFramePacket wlanFramePacket) {
		super(byteBuffer, wlanFramePacket);
	}
}
