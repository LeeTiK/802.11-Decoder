package leetik.w80211.protocol.wlan.frame.management;

import java.nio.ByteBuffer;
import java.util.List;

import leetik.w80211.protocol.wlan.frame.WlanManagementAbstr;
import leetik.w80211.protocol.wlan.frame.management.element.IWlanElement;
import leetik.w80211.protocol.wlan.frame.management.element.WlanElementIdDecoder;
import leetik.w80211.protocol.wlan.frame.management.inter.IProbeRequestFrame;

/**
 * Probe request management frame decoder<br/>
 * <ul>
 * <li>tagged parameters : X bytes</li>
 * </ul>
 * 
 * @author Bertrand Martel
 * 
 */
public class ProbeRequestFrame extends WlanManagementAbstr implements IProbeRequestFrame {

	private List<IWlanElement> taggedParameter = null;

	@Deprecated
	public ProbeRequestFrame(byte[] frame) {
		super(frame);
		WlanElementIdDecoder decoder = new WlanElementIdDecoder();
		taggedParameter = decoder.decode(getFrameBody());

	}

	public ProbeRequestFrame(ByteBuffer byteBuffer) {
		super(byteBuffer);
		WlanElementIdDecoder decoder = new WlanElementIdDecoder();
		taggedParameter = decoder.decode(byteBuffer);

	}

	@Override
	public List<IWlanElement> getTaggedParameter() {
		return taggedParameter;
	}
}
