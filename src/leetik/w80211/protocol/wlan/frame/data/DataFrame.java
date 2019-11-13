package leetik.w80211.protocol.wlan.frame.data;

import leetik.w80211.protocol.wlan.frame.WlanDataAbstr;
import leetik.w80211.protocol.wlan.frame.data.inter.IDataFrame;

/**
 * Classic Data frame object for protocol w80211 802.11
 * 
 * @author Bertrand Martel
 * 
 */
public class DataFrame extends WlanDataAbstr implements IDataFrame {

	/**
	 * Decode data frame
	 * 
	 * @param frame
	 *            frame with omitted control frame
	 * @param toDS
	 *            to distribution system indicator
	 * @param fromDS
	 *            from distribution system indicator
	 */
	public DataFrame(byte[] frame, boolean toDS, boolean fromDS) {
		super(frame, toDS, fromDS);
	}

}
