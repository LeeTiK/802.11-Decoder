package leetik.w80211.protocol.wlan.frame;

import leetik.w80211.protocol.wlan.WlanFramePacket;

/**
 * Basic interface for standard Wlan frame => data/management or control frame
 * 
 * @author Bertrand Martel
 * 
 */
public interface IWlanFrame {
    public WlanFramePacket getWlanDecoder();
}
