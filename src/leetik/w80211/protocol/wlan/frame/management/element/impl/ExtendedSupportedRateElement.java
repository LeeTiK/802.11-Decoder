package leetik.w80211.protocol.wlan.frame.management.element.impl;

import leetik.w80211.protocol.wlan.frame.management.element.WlanElementAbstr;
import leetik.w80211.protocol.wlan.frame.management.element.WlanElementID;
import leetik.w80211.protocol.wlan.frame.management.element.inter.IExtendedSupportedRateElement;

/**
 * Extended supported data rate for w80211 802.11 element id
 * <ul>
 * <li>element id : 1 Byte</li>
 * <li>length : 1 Byte</li>
 * <li>Extended Supported Rates: 1 - 255 Bytes</li>
 * </ul>
 * 
 * @author Bertrand Martel
 * 
 */
public class ExtendedSupportedRateElement extends WlanElementAbstr implements IExtendedSupportedRateElement {

	public final static int id = 50;

	private byte[] dataRate = null;

	public ExtendedSupportedRateElement(byte[] data) {
			super(data);
			dataRate = data;
	}

	/**
	 * retrieve max rate in Mbps
	 * 
	 * @return
	 */
	public int getMaxRate() {
		if (dataRate.length > 0) {
			System.out.println((dataRate[dataRate.length - 1] & 0x7F) * 500);
			return ((dataRate[dataRate.length - 1] & 0x7F) * 500) / 1000;
		} else {
			return -1;
		}
	}

	@Override
	public byte getElementId() {
		return id;
	}

	@Override
	public WlanElementID getWlanElementId() {
		return WlanElementID.EXTENDED_SUPPORTED_RATE;
	}

	@Override
	public byte[] getDataRate() {
		return dataRate;
	}
}
