package leetik.w80211.protocol.wlan.frame.control;

import leetik.w80211.protocol.wlan.WlanFramePacket;
import leetik.w80211.protocol.wlan.frame.IWlanFrame;
import leetik.w80211.protocol.wlan.frame.control.inter.IPowerSavePollingFrame;
import leetik.w80211.protocol.wlan.inter.IWlanControlFrame;

import java.nio.ByteBuffer;

/**
 * Power saving frame - Control Frame<br/>
 * <ul>
 * <li>Association id : 2 Bytes</li>
 * <li>BSSID : 6 Bytes</li>
 * <li>Transmitter id : 6 Bytes</li>
 * </ul>
 * 
 * @author Bertrand Martel
 * 
 */
public class PowerSavePollingFrame implements IWlanControlFrame, IWlanFrame,
		IPowerSavePollingFrame {
	private byte[] associationId = null;

	private byte[] bssid = null;

	private byte[] transmitterId = null;

	/**
	 * Build Power saving contriol frame with byte array omitting frame control
	 * 
	 * @param frame
	 *            byte array omitting frame control
	 */
	@Deprecated
	public PowerSavePollingFrame(byte[] frame) {

		if (frame.length >= 14) {
			associationId = new byte[] { frame[0], frame[1] };

			bssid = new byte[] { frame[2], frame[3], frame[4], frame[5],
					frame[6], frame[7] };

			transmitterId = new byte[] { frame[8], frame[9], frame[10],
					frame[11], frame[12], frame[13] };
		} else {
			System.err
					.println("error treating Control frame - power saving frame");
		}
	}

	public PowerSavePollingFrame(ByteBuffer byteBuffer) {

		if (byteBuffer.remaining() >= 14) {
			int position = byteBuffer.position();

			associationId = new byte[] { byteBuffer.get(position), byteBuffer.get(position+1) };

			bssid = new byte[] { byteBuffer.get(position+2), byteBuffer.get(position+3), byteBuffer.get(position+4), byteBuffer.get(position+5),
					byteBuffer.get(position+6), byteBuffer.get(position+7) };

			transmitterId = new byte[] { byteBuffer.get(position+8), byteBuffer.get(position+9),byteBuffer.get(position+10),
					byteBuffer.get(position+11), byteBuffer.get(position+12), byteBuffer.get(position+13) };

			byteBuffer.position(position + 14);
		} else {
			System.err
					.println("error treating Control frame - power saving frame");
		}
	}

	@Override
	public byte[] getAssociationId() {
		return associationId;
	}

	@Override
	public byte[] getBssid() {
		return bssid;
	}

	@Override
	public byte[] getTransmitterId() {
		return transmitterId;
	}

	@Override
	public WlanFramePacket getWlanDecoder() {
		return null;
	}
}
