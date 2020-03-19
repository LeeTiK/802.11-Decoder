package leetik.w80211.protocol.wlan.frame.data;

import leetik.w80211.protocol.authentication.AuthenticationDecoder;
import leetik.w80211.protocol.other.LogicalLinkControl;
import leetik.w80211.protocol.wlan.WlanFramePacket;
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

	private byte[] parametersCCMP = null;

	LogicalLinkControl logicalLinkControl = null;

	Object dataObject = null;

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

	public QosDataFrame(ByteBuffer byteBuffer, WlanFramePacket wlanDecoder) {
		super(byteBuffer, wlanDecoder);

		qosControl = new byte[2];

		byteBuffer.get(qosControl);

		if ( wlanDecoder.getFrameControl().isWep()) {
			if (byteBuffer.remaining() <= 8) return;

			parametersCCMP = new byte[8];

			byteBuffer.get(parametersCCMP);
		}

		byteBuffer.mark();

		data = new byte[byteBuffer.remaining()];

		if (byteBuffer.remaining()<=0) return;

		byteBuffer.get(data);

		if (! wlanDecoder.getFrameControl().isWep()) {
			byteBuffer.reset();
			logicalLinkControl = new LogicalLinkControl(byteBuffer);
			int k = logicalLinkControl.getType();
			if (logicalLinkControl.getType()==0x888e)
			{
				dataObject= new AuthenticationDecoder(wlanDecoder,byteBuffer);
			}
		}
	}

	@Override
	public byte[] getQosControl() {
		return qosControl;
	}

	@Override
	public byte[] getData() {
		return data;
	}

	public byte[] getParametersCCMP() {
		return parametersCCMP;
	}

	public Object getDataObject() {
		return dataObject;
	}

	public LogicalLinkControl getLogicalLinkControl() {
		return logicalLinkControl;
	}
}
