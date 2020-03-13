package leetik.w80211.protocol.wlan.frame;

import leetik.w80211.protocol.wlan.WlanDecoder;
import leetik.w80211.protocol.wlan.WlanFrameDecoder;

/**
 * Basic interface for standard Wlan frame => data/management or control frame
 * 
 * @author Bertrand Martel
 * 
 */
public interface IWlanFrame {
    public WlanFrameDecoder getWlanDecoder();
}
