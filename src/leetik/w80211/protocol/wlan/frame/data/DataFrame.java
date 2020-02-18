package leetik.w80211.protocol.wlan.frame.data;

import leetik.w80211.protocol.wlan.frame.WlanDataAbstr;
import leetik.w80211.protocol.wlan.frame.data.inter.IDataFrame;
import leetik.w80211.protocol.wlan.inter.IWlanFrameControl;

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

	@Deprecated
	public DataFrame(byte[] frame, boolean toDS, boolean fromDS) {
		super(frame, toDS, fromDS);
	}

	public DataFrame(ByteBuffer byteBuffer, IWlanFrameControl wlanFrameControl) {
		super(byteBuffer, wlanFrameControl.isToDS(), wlanFrameControl.isFromDS());

		if (wlanFrameControl.isWep()) {
			if (byteBuffer.remaining() <= 8) return;

			parametersCCMP = new byte[8];

			byteBuffer.get(parametersCCMP);
		}

		data = new byte[byteBuffer.remaining()];

		byteBuffer.get(data);

	}

	@Override
	public byte[] getData() {
		return data;
	}
}
