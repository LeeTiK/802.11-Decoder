package leetik.w80211.protocol.wlan.frame;


import java.nio.ByteBuffer;

/**
 * Basic management frame<br/>
 * <ul>
 * <li>duration id : 2 Bytes</li>
 * <li>destination addr : 6 Bytes</li>
 * <li>source addr : 6 Bytes</li>
 * <li>BSSID : 6 Bytes</li>
 * <li>sequence control : 2 Bytes</li>
 * <li>frame body : 0-2312 Bytes</li>
 * </ul>
 * 
 * @author Bertrand Martel
 * 
 */
public abstract class WlanManagementAbstr implements IWlanManagementFrame,
		IWlanFrame {

	/**
	 * duration id value
	 */
	private byte[] durationId = null;

	/**
	 * destination address
	 */
	private byte[] destinationAddr = null;

	/**
	 * source address
	 */
	private byte[] sourceAddr = null;

	/**
	 * BSSID value
	 */
	private byte[] bssid = null;

	/**
	 * sequence control byte array value
	 */
	private byte[] sequenceControl = null;

	/**
	 * frame body content
	 */
	private byte[] frameBody = null;

	private byte[] fcs = null;
	
	/**
	 * Build basic management frame from w80211 frame
	 * 
	 * @param frame
	 *            w80211 frame with control frame omitted
	 */
	@Deprecated
	public WlanManagementAbstr(byte[] frame) {
		if (frame.length >= 26) {
			durationId = new byte[] { frame[0], frame[1] };
			destinationAddr = new byte[] { frame[2], frame[3], frame[4],
					frame[5], frame[6], frame[7] };
			sourceAddr = new byte[] { frame[8], frame[9], frame[10], frame[11],
					frame[12], frame[13] };
			bssid = new byte[] { frame[14], frame[15], frame[16], frame[17],
					frame[18], frame[19] };
			sequenceControl = new byte[] { frame[20], frame[21] };

			if (frame.length == 26) {
				frameBody = new byte[] {};
				fcs=new byte[]{ frame[22], frame[23],  frame[24], frame[25]};
			} else {
				frameBody = new byte[frame.length - 22];
				for (int i = 22; i < frame.length-4; i++) {
					frameBody[i - 22] = frame[i];
				}
				fcs=new byte[]{ frame[frame.length-4], frame[frame.length-3],  frame[frame.length-2], frame[frame.length-1]};
			}
		} else {
			System.err.println("error treating Management frame");
		}
	}

	public WlanManagementAbstr(ByteBuffer byteBuffer) {
		if (byteBuffer.remaining() >= 22) {
			int position = byteBuffer.position();

			durationId = new byte[] { byteBuffer.get(position), byteBuffer.get(position+1) };
			destinationAddr = new byte[] { byteBuffer.get(position+2), byteBuffer.get(position + 3), byteBuffer.get(position +4),
			byteBuffer.get(position + 5), byteBuffer.get(position+6), byteBuffer.get(position+7) };
			sourceAddr = new byte[] { byteBuffer.get(position+8), byteBuffer.get(position+9), byteBuffer.get(position+10), byteBuffer.get(position+11),
					byteBuffer.get(position+12), byteBuffer.get(position+13) };
			bssid = new byte[] { byteBuffer.get(position+14), byteBuffer.get(position+15), byteBuffer.get(position+16), byteBuffer.get(position+17),
					byteBuffer.get(position+18), byteBuffer.get(position+19) };
			sequenceControl = new byte[] { byteBuffer.get(position+20), byteBuffer.get(position+21) };

			if (byteBuffer.remaining() == 22) {
				frameBody = new byte[] {};
			} else {
				byteBuffer.position(position+22);
				frameBody = new byte[byteBuffer.remaining()];
				byteBuffer.mark();
				byteBuffer.get(frameBody);
				byteBuffer.reset();

				//fcs=new byte[]{ frame[frame.length-4], frame[frame.length-3],  frame[frame.length-2], frame[frame.length-1]};
			}
		} else {
			System.err.println("error treating Management frame");
		}
	}

	public void readFCS(ByteBuffer byteBuffer)
	{
		if (byteBuffer.remaining()==4)
		{
			fcs = new byte[4];
			byteBuffer.get(fcs);
		}
		else{
			fcs = new byte[]{};
		}
	}

	@Override
	public byte[] getFcs()
	{
		return fcs;
	}

	@Override
	public boolean isFcs(){
		return fcs.length!=0;
	}
	
	@Override
	public byte[] getDurationId() {
		return durationId;
	}

	@Override
	public byte[] getDestinationAddr() {
		return destinationAddr;
	}

	@Override
	public byte[] getSourceAddr() {
		return sourceAddr;
	}

	@Override
	public byte[] getBSSID() {
		return bssid;
	}

	@Override
	public byte[] getSequenceControl() {
		return sequenceControl;
	}

	@Override
	public byte[] getFrameBody() {
		return frameBody;
	}

}
