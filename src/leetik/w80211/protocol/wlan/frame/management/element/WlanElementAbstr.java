package leetik.w80211.protocol.wlan.frame.management.element;

/**
 * Common implementation of a generic element in w80211 protocol 802.11
 * 
 * @author Bertrand Martel
 * 
 */
public abstract class WlanElementAbstr implements IWlanElement {

	private byte[] data = null;

	protected boolean decodeError;

	public WlanElementAbstr(byte[] data) {
		this.data = data;
		decodeError = false;
	}

	@Override
	public byte[] getData() {
		return data;
	}

	public boolean isDecodeError() {
		return decodeError;
	}
}
