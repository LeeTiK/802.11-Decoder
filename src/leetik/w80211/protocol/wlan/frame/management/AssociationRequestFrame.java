package leetik.w80211.protocol.wlan.frame.management;

import java.nio.ByteBuffer;
import java.util.List;

import leetik.w80211.protocol.wlan.WlanFrameDecoder;
import leetik.w80211.protocol.wlan.frame.WlanManagementAbstr;
import leetik.w80211.protocol.wlan.frame.management.element.IWlanElement;
import leetik.w80211.protocol.wlan.frame.management.element.WlanElementIdDecoder;
import leetik.w80211.protocol.wlan.frame.management.inter.IAssociationRequestFrame;
import leetik.w80211.utils.ByteUtils;

/**
 * Management frame - Association frame<br/>
 * <ul>
 * <li>Capability info : 2 Bytes</li>
 * <li>listen interval : 2 Bytes</li>
 * <li>tagged parameter : X Bytes</li>
 * </ul>
 * <p>
 * capability info,listen interval are static information, tagged are dynamic
 * and can be added with respect to their tag element id
 * </p>
 * 
 * @author Bertrand Martel
 * 
 */
public class AssociationRequestFrame extends WlanManagementAbstr implements IAssociationRequestFrame {

	/**
	 * Capability information field spans to 16 bits and contain information
	 * about capability of the device/network.
	 */
	private byte[] capabilityInfo = null;

	/**
	 * listen interval for association
	 */
	private byte[] listenInterval = null;

	/**
	 * parameter identified by their tag id. A collection of these id can be
	 * found in WlanElementTagId class
	 */
	private List<IWlanElement> taggedParameter = null;

	/**
	 * Parse beaon management frame according to basic management frame and
	 * beacon frame specification
	 * 
	 * @param frame
	 *            frame with omitted control frame
	 */
	@Deprecated
	public AssociationRequestFrame(byte[] frame) {
		super(frame);
		byte[] frameBody = getFrameBody();

		capabilityInfo = new byte[2];
		for (int i = 0; i < capabilityInfo.length; i++) {
			capabilityInfo[capabilityInfo.length - 1 - i] = frameBody[i];
		}

		listenInterval = new byte[2];
		for (int i = 2; i < 4; i++) {
			listenInterval[4 - 1 - i] = frameBody[i];
		}

		byte[] taggedParameterArray = null;
		if (frameBody.length >= 5) {
			taggedParameterArray = new byte[frameBody.length - 4];
			for (int i = 4; i < frameBody.length; i++) {
				taggedParameterArray[i - 4] = frameBody[i];
			}
		} else {
			taggedParameterArray = new byte[] {};
		}
		WlanElementIdDecoder decoder = new WlanElementIdDecoder();
		taggedParameter = decoder.decode(taggedParameterArray);
	}

	public AssociationRequestFrame(ByteBuffer byteBuffer) {
		super(byteBuffer);

		capabilityInfo = new byte[2];
		byteBuffer.get(capabilityInfo);

		listenInterval = new byte[2];
		byteBuffer.get(listenInterval);

		WlanElementIdDecoder decoder = new WlanElementIdDecoder();
		taggedParameter = decoder.decode(byteBuffer);
	}
	
	@Override
	public byte[] getCapabilityInfo() {
		return capabilityInfo;
	}
	
	@Override
	public List<IWlanElement> getTaggedParameter() {
		return taggedParameter;
	}

	@Override
	public byte[] getListenInterval() {
		return listenInterval;
	}

	@Override
	public WlanFrameDecoder getWlanDecoder() {
		return null;
	}
}
