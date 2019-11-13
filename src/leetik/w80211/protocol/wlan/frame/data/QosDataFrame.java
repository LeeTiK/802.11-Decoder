package leetik.w80211.protocol.wlan.frame.data;

import leetik.w80211.protocol.wlan.frame.WlanDataAbstr;
import leetik.w80211.protocol.wlan.frame.data.inter.IQosDataFrame;

/**
 * QOS data frame decoder
 * 
 * @author Bertrand Martel
 * 
 */
public class QosDataFrame extends WlanDataAbstr implements IQosDataFrame {

	/**
	 * qos control 2 Bytes additionnal
	 */
	private byte[] qosControl = null;

	/**
	 * Decode QOS data frame
	 * 
	 * @param frame
	 *            frame with omitted control frame
	 * @param toDS
	 *            to distribution system indicator
	 * @param fromDS
	 *            from distribution system indicator
	 */
	public QosDataFrame(byte[] frame, boolean toDS, boolean fromDS) {
		super(frame, toDS, fromDS);
		qosControl = new byte[] { getFrameBody()[1], getFrameBody()[0] };
	}

	@Override
	public byte[] getQosControl() {
		return qosControl;
	}

}
