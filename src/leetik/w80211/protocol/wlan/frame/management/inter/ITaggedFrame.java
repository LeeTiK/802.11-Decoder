package leetik.w80211.protocol.wlan.frame.management.inter;

import leetik.w80211.protocol.wlan.frame.management.element.EWlanElementID;
import leetik.w80211.protocol.wlan.frame.management.element.IWlanElement;
import leetik.w80211.protocol.wlan.frame.management.element.WlanElementAbstr;

import java.util.ArrayList;
import java.util.List;

public interface ITaggedFrame {

    public List<IWlanElement> getTaggedParameter();

    public WlanElementAbstr getTaggedParameter(EWlanElementID EWlanElementID);
}
