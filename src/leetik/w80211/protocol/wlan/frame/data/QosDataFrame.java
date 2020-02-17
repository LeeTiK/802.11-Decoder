package leetik.w80211.protocol.wlan.frame.data;

import leetik.w80211.protocol.wlan.frame.WlanDataAbstr;
import leetik.w80211.protocol.wlan.frame.data.inter.IQosDataFrame;

import java.nio.ByteBuffer;

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

	private byte[] data;

	private byte[] parametersCCMP;

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
	@Deprecated
	public QosDataFrame(byte[] frame, boolean toDS, boolean fromDS) {
		super(frame, toDS, fromDS);
		qosControl = new byte[] { getFrameBody()[1], getFrameBody()[0] };
	}

	public QosDataFrame(ByteBuffer byteBuffer, boolean toDS, boolean fromDS) {
		super(byteBuffer, toDS, fromDS);

		qosControl = new byte[2];

		byteBuffer.get(qosControl);

		parametersCCMP = new byte[8];

		byteBuffer.get(parametersCCMP);

		data = new byte[byteBuffer.remaining()];

		byteBuffer.get(data);
	}

	@Override
	public byte[] getQosControl() {
		return qosControl;
	}

}
