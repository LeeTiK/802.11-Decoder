package leetik.w80211.protocol.wlan.frame.management;

import java.nio.ByteBuffer;
import java.util.List;

import leetik.w80211.protocol.wlan.WlanFramePacket;
import leetik.w80211.protocol.wlan.frame.WlanManagementAbstr;
import leetik.w80211.protocol.wlan.frame.management.element.EWlanElementID;
import leetik.w80211.protocol.wlan.frame.management.element.IWlanElement;
import leetik.w80211.protocol.wlan.frame.management.element.WlanElementAbstr;
import leetik.w80211.protocol.wlan.frame.management.element.WlanElementIdDecoder;
import leetik.w80211.protocol.wlan.frame.management.inter.IProbeResponseFrame;

/**
 * Probe response management frame decoder<br/>
 * <ul>
 * <li>timestamp : 8 Bytes</li>
 * <li>beacon interval : 2 Bytes</li>
 * <li>capability information : 2 Bytes</li>
 * <li>tagged parameters : X bytes</li>
 * </ul>
 * 
 * @author Bertrand Martel
 * 
 */
public class ProbeResponseFrame extends WlanManagementAbstr implements IProbeResponseFrame {

	/**
	 * timestamp value for this frame (for sync)
	 */
	private byte[] timestamp = null;

	/**
	 * This is the time interval between beacon transmissions
	 */
	private byte[] beaconInterval = null;

	/**
	 * Capability information field spans to 16 bits and contain information
	 * about capability of the device/network.
	 */
	private byte[] capabilityInfo = null;

	/**
	 * parameter identified by their tag id. A collection of these id can be
	 * found in WlanElementTagId class
	 */
	private List<IWlanElement> taggedParameter = null;

	@Deprecated
	public ProbeResponseFrame(byte[] frame) {
		super(frame);
		byte[] frameBody = getFrameBody();

		timestamp = new byte[8];
		for (int i = 0; i < timestamp.length; i++) {
			timestamp[timestamp.length - 1 - i] = frameBody[i];
		}

		beaconInterval = new byte[2];
		for (int i = 8; i < 10; i++) {
			beaconInterval[10 - i - 1] = frameBody[i];
		}

		capabilityInfo = new byte[2];
		for (int i = 10; i < 12; i++) {
			capabilityInfo[12 - i - 1] = frameBody[i];
		}

		byte[] taggedParameterArray = null;

		if (frameBody.length >= 12) {
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

	public ProbeResponseFrame(ByteBuffer byteBuffer) {
		super(byteBuffer);
		//byte[] frameBody = getFrameBody();

		timestamp = new byte[8];
		byteBuffer.get(timestamp);

		beaconInterval = new byte[2];
		byteBuffer.get(beaconInterval);

		capabilityInfo = new byte[2];
		byteBuffer.get(capabilityInfo);

		WlanElementIdDecoder decoder = new WlanElementIdDecoder();
		taggedParameter = decoder.decode(byteBuffer);
	}

	@Override
	public List<IWlanElement> getTaggedParameter() {
		return taggedParameter;
	}
	@Override
	public byte[] getTimestamp() {
		return timestamp;
	}
	@Override
	public byte[] getBeaconInterval() {
		return beaconInterval;
	}
	@Override
	public byte[] getCapabilityInformation() {
		return capabilityInfo;
	}

	@Override
	public WlanFramePacket getWlanDecoder() {
		return null;
	}

	public WlanElementAbstr getTaggedParameter(EWlanElementID EWlanElementID){
		if (taggedParameter==null) return null;

		for (int i=0; i<taggedParameter.size(); i++)
		{
			if (taggedParameter.get(i).getWlanElementId()== EWlanElementID) return (WlanElementAbstr) taggedParameter.get(i);
		}

		return null;
	}
}
