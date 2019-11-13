package leetik.w80211.protocol.wlan.frame.management.element.inter;

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
public interface IExtendedSupportedRateElement {

	public byte[] getDataRate();
	
}
