package leetik.w80211.protocol.wlan.frame.control;

import leetik.w80211.protocol.wlan.WlanFramePacket;
import leetik.w80211.protocol.wlan.frame.IWlanFrame;
import leetik.w80211.protocol.wlan.frame.control.inter.IackFrame;
import leetik.w80211.protocol.wlan.inter.IWlanControlFrame;

import java.nio.ByteBuffer;

/**
 * Control frame - ACK <br/>
 * <ul>
 * <li>duration id : 2 Bytes</li>
 * <li>receiver address : 6 Bytes</li>
 * </ul>
 * 
 * @author Bertrand Martel
 * 
 */
public class AckFrame implements IWlanFrame, IWlanControlFrame, IackFrame {

	/**
	 * duration id
	 */
	private byte[] durationId = null;

	/**
	 * receiver addresss
	 */
	private byte[] receiverAddr = null;

	/**
	 * Build control frame ACK
	 * 
	 * @param frame
	 *            w80211 frame with control frame omitted
	 */
	@Deprecated
	public AckFrame(byte[] frame) {
		if (frame.length >= 8) {
			durationId = new byte[] { frame[0], frame[1] };
			receiverAddr = new byte[] { frame[2], frame[3], frame[4], frame[5],
					frame[6], frame[7] };
		} else {
			System.err.println("error treating Control frame - clear to send frame");
		}
	}

	public AckFrame(ByteBuffer byteBuffer) {
		if (byteBuffer.remaining() >= 8) {
			int position = byteBuffer.position();

			durationId =  new byte[] { byteBuffer.get(position), byteBuffer.get(position+1) };
			receiverAddr = new byte[] { byteBuffer.get(position+2), byteBuffer.get(position+3), byteBuffer.get(position+4), byteBuffer.get(position+5),
					byteBuffer.get(position+6), byteBuffer.get(position+7) };

			byteBuffer.position(position + 8);

		} else {
			System.err.println("error treating Control frame - clear to send frame");
		}
	}

	@Override
	public byte[] getDurationId() {
		return durationId;
	}
	@Override
	public byte[] getReceiverAddr() {
		return receiverAddr;
	}


	@Override
	public WlanFramePacket getWlanDecoder() {
		return null;
	}
}
