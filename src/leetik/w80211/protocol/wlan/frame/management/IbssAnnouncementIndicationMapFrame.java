package leetik.w80211.protocol.wlan.frame.management;

import leetik.w80211.protocol.wlan.frame.WlanManagementAbstr;
import leetik.w80211.protocol.wlan.frame.management.inter.IibssAnnoucementIndicationMapFrame;

/**
 * Management frame for IBSS Annoucement indication map
 * 
 * @author Bertrand Martel
 * 
 */
public class IbssAnnouncementIndicationMapFrame extends WlanManagementAbstr implements IibssAnnoucementIndicationMapFrame {

	/**
	 * Parse IBSS announcement indication map
	 * 
	 * @param frame
	 *            frame with omitted control frame
	 */
	public IbssAnnouncementIndicationMapFrame(byte[] frame) {
		super(frame);
	}
}
