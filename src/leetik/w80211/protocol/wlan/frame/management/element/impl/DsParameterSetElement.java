package leetik.w80211.protocol.wlan.frame.management.element.impl;

import leetik.w80211.protocol.wlan.frame.management.element.WlanElementAbstr;
import leetik.w80211.protocol.wlan.frame.management.element.EWlanElementID;
import leetik.w80211.protocol.wlan.frame.management.element.inter.IDsssParameterSetElement;

/**
 * The DSSS Parameter Set element contains information to allow channel number
 * identification for STAs.
 * <ul>
 * <li>element id : 1 byte
 * <li>length : 1 byte
 * <li>current channel : 1 byte
 * </ul>
 * 
 * @author Bertrand Martel
 *
 */
public class DsParameterSetElement extends WlanElementAbstr implements IDsssParameterSetElement {

	public final static int id = 3;

	/**
	 * current channel
	 */
	private int currentChannel = 0x00;

	/**
	 * 
	 * @param length
	 * @param data
	 */
	public DsParameterSetElement(byte[] data) {
		super(data);
		currentChannel = data[0] & 0xFF;

	}

	@Override
	public int getCurrentChannel()
	{
		return currentChannel;
	}

	@Override
	public byte getElementId() {
		return id;
	}

	@Override
	public EWlanElementID getWlanElementId() {
		return EWlanElementID.DS_PARAMETER;
	}

}
