package leetik.w80211.protocol.wlan.frame.management;

import leetik.w80211.protocol.wlan.frame.WlanManagementAbstr;
import leetik.w80211.protocol.wlan.frame.management.inter.IAuthenticationFrame;
import leetik.w80211.protocol.wlan.utils.ByteUtils;

import java.nio.ByteBuffer;

/**
 * Management frame -Authentication frame<br/>
 * <ul>
 * <li>authentication algorithm :2 Bytes</li>
 * <li>authentication sequential number : 2 Bytes</li>
 * <li>status code : 2 Bytes</li>
 * </ul>
 * <p>
 * contains only fixed parameters
 * </p>
 * 
 * @author Bertrand Martel
 * 
 */
public class AuthenticationFrame extends WlanManagementAbstr implements IAuthenticationFrame {

	/**
	 * authentication algorithm
	 */
	private int authenticationAlgorithmNum = 0;

	/**
	 * authentication sequential number
	 */
	private int authenticationSeqNum = 0;

	/**
	 * authentication status code
	 */
	private int statusCode = 0;

	/**
	 * Parse authentication management frame according to basic management frame
	 * and beacon frame specification
	 * 
	 * @param frame
	 *            frame with omitted control frame
	 */
	@Deprecated
	public AuthenticationFrame(byte[] frame) {
		super(frame);
		byte[] frameBody = getFrameBody();
		authenticationAlgorithmNum = ByteUtils
				.convertByteArrayToInt(new byte[] { frameBody[1], frameBody[0] });
		authenticationSeqNum = ByteUtils
				.convertByteArrayToInt(new byte[] { frameBody[3], frameBody[2] });
		statusCode = ByteUtils.convertByteArrayToInt(new byte[] {
				frameBody[5], frameBody[4] });
	}

	public AuthenticationFrame(ByteBuffer frame) {
		super(frame);
		byte[] frameBody = getFrameBody();
		authenticationAlgorithmNum = ByteUtils
				.convertByteArrayToInt(new byte[] { frameBody[1], frameBody[0] });
		authenticationSeqNum = ByteUtils
				.convertByteArrayToInt(new byte[] { frameBody[3], frameBody[2] });
		statusCode = ByteUtils.convertByteArrayToInt(new byte[] {
				frameBody[5], frameBody[4] });
	}

	@Override
	public int getAuthenticationAlgorithmNum() {
		return authenticationAlgorithmNum;
	}
	
	@Override
	public int getAuthenticationSeqNum() {
		return authenticationSeqNum;
	}
	
	@Override
	public int getStatusCode() {
		return statusCode;
	}

}
