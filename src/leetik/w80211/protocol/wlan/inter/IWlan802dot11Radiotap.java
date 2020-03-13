package leetik.w80211.protocol.wlan.inter;

import leetik.w80211.protocol.radiotap.inter.IRadioTapFrame;
import leetik.w80211.protocol.wlan.frame.IWlanFrame;

import java.nio.ByteBuffer;

/**
 *
 * Template for Wlan 802.11 frame with preceding radiotap header
 * 
 * @author Bertrand Martel
 *
 */
public interface IWlan802dot11Radiotap {

	public IRadioTapFrame getRadioTap() ;
	
	public IWlanFrameControl getFrameControl();
	
	public IWlanFrame getFrame();

	public ByteBuffer getDataFrame();

}
