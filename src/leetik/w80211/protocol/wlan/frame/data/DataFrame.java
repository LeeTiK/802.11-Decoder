package leetik.w80211.protocol.wlan.frame.data;

import leetik.w80211.protocol.authentication.AuthenticationDecoder;
import leetik.w80211.protocol.other.LogicalLinkControl;
import leetik.w80211.protocol.wlan.WlanFramePacket;
import leetik.w80211.protocol.wlan.frame.WlanDataAbstr;
import leetik.w80211.protocol.wlan.frame.data.inter.IDataFrame;

import java.nio.ByteBuffer;

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

	private byte[] data;

	private byte[] parametersCCMP;

	LogicalLinkControl logicalLinkControl = null;

	Object dataObject = null;

	@Deprecated
	public DataFrame(byte[] frame, boolean toDS, boolean fromDS) {
		super(frame, toDS, fromDS);
	}

	public DataFrame(ByteBuffer byteBuffer, WlanFramePacket wlanDecoder) {
		super(byteBuffer, wlanDecoder);

		if (wlanDecoder.getFrameControl().isWep()) {
			if (byteBuffer.remaining() <= 8) return;

			parametersCCMP = new byte[8];

			byteBuffer.get(parametersCCMP);
		}

		data = new byte[byteBuffer.remaining()];

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
	public byte[] getData() {
		return data;
	}

	public Object getDataObject() {
		return dataObject;
	}

	public LogicalLinkControl getLogicalLinkControl() {
		return logicalLinkControl;
	}
}
