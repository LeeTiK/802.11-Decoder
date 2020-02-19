package leetik.w80211.protocol.wlan.frame.management;

import java.nio.ByteBuffer;
import java.util.List;

import leetik.w80211.protocol.wlan.frame.WlanManagementAbstr;
import leetik.w80211.protocol.wlan.frame.management.element.IWlanElement;
import leetik.w80211.protocol.wlan.frame.management.element.WlanElementAbstr;
import leetik.w80211.protocol.wlan.frame.management.element.WlanElementID;
import leetik.w80211.protocol.wlan.frame.management.element.WlanElementIdDecoder;
import leetik.w80211.protocol.wlan.frame.management.inter.IBeaconFrame;
import leetik.w80211.protocol.wlan.frame.management.other.CapabilitiesInformation;

/**
 * Management frame - Beacon frame<br/>
 * <ul>
 * <li>Timestamp : 8 Bytes</li>
 * <li>Beacon interval : 2 Bytes</li>
 * <li>Capability info : 2 Bytes</li>
 * <li>tagged parameter : X Bytes</li>
 * </ul>
 * <p>
 * timestamp,beacon interval,capability info are static information, tagged are
 * dynamic and can be added with respect to their tag element id
 * </p>
 * 
 * @author Bertrand Martel
 * 
 */
public class BeaconFrame extends WlanManagementAbstr implements IBeaconFrame {

	/**
	 * timestamp value for this frame (for sync)
	 */
	private long timestamp = 0;

	/**
	 * This is the time interval between beacon transmissions
	 */
	private int beaconInterval = 0;

	/**
	 * Capability information field spans to 16 bits and contain information
	 * about capability of the device/network.
	 */
	private CapabilitiesInformation capabilityInfo = null;

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
	public BeaconFrame(byte[] frame) {
		super(frame);
		byte[] frameBody = getFrameBody();

		// manage tagged parameter list with element id decoder
		byte[] taggedParameterArray = null;
		if (frameBody.length >= 13) {
			taggedParameterArray = new byte[frameBody.length - 12];
			for (int i = 12; i < frameBody.length; i++) {
				taggedParameterArray[i - 12] = frameBody[i];
			}
		} else {
			taggedParameterArray = new byte[] {};
		}

		WlanElementIdDecoder decoder = new WlanElementIdDecoder();
		taggedParameter = decoder.decode(taggedParameterArray);
	}

	public BeaconFrame(ByteBuffer byteBuffer) {
		super(byteBuffer);
		//byte[] frameBody = getFrameBody();

		timestamp = byteBuffer.getLong();

		beaconInterval = byteBuffer.getShort();

		capabilityInfo = new CapabilitiesInformation(byteBuffer.getShort());

		WlanElementIdDecoder decoder = new WlanElementIdDecoder();
		taggedParameter = decoder.decode(byteBuffer);
	}

	@Override
	public long getTimestamp() {
		return timestamp;
	}
	
	@Override
	public int getBeaconInterval() {
		return beaconInterval;
	}
	
	@Override
	public CapabilitiesInformation getCapabilityInfo() {
		return capabilityInfo;
	}
	
	@Override
	public List<IWlanElement> getTaggedParameter() {
		return taggedParameter;
	}

	public WlanElementAbstr getTaggedParameter(WlanElementID wlanElementID){
		if (taggedParameter==null) return null;

		for (int i=0; i<taggedParameter.size(); i++)
		{
			if (taggedParameter.get(i).getWlanElementId()==wlanElementID) return (WlanElementAbstr) taggedParameter.get(i);
		}

		return null;
	}
}
