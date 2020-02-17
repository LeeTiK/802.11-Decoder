package leetik.w80211.protocol.wlan.frame.management;

import leetik.w80211.protocol.wlan.frame.WlanManagementAbstr;
import leetik.w80211.protocol.wlan.frame.management.inter.IDeauthenticationFrame;
import leetik.w80211.protocol.wlan.utils.ByteUtils;

import java.nio.ByteBuffer;

/**
 * Management frame - DeAuthentication frame<br/>
 * <ul>
 * <li>reason code :2 Bytes</li>
 * </ul>
 * <p>
 * contains only fixed parameters
 * </p>
 * 
 * @author Bertrand Martel
 * 
 */
public class DeAuthenticationFrame extends WlanManagementAbstr implements IDeauthenticationFrame {

	/**
	 * authentication reason code
	 */
	private int reasonCode = 0;

	/**
	 * Parse DeAuthentication management frame according to basic management
	 * frame and beacon frame specification
	 * 
	 * @param frame
	 *            frame with omitted control frame
	 */
	@Deprecated
	public DeAuthenticationFrame(byte[] frame) {
		super(frame);
		byte[] frameBody = getFrameBody();
		reasonCode = ByteUtils.convertByteArrayToInt(new byte[] {
				frameBody[1], frameBody[0] });
	}

	public DeAuthenticationFrame(ByteBuffer frame) {
		super(frame);
		byte[] frameBody = getFrameBody();
		reasonCode = ByteUtils.convertByteArrayToInt(new byte[] {
				frameBody[1], frameBody[0] });
	}

	@Override
	public int getReasonCode() {
		return reasonCode;
	}
}
