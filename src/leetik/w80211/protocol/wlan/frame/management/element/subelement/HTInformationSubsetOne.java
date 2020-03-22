package leetik.w80211.protocol.wlan.frame.management.element.subelement;

import leetik.w80211.protocol.wlan.frame.management.element.IWlanSubElement;
import leetik.w80211.protocol.wlan.frame.management.element.subelement.inter.IHtCapabilityInformation;
import leetik.w80211.protocol.wlan.frame.management.element.subelement.other.ESecondaryChannelOffset;

public class HTInformationSubsetOne implements IWlanSubElement {

    ESecondaryChannelOffset eSecondaryChannelOffset = ESecondaryChannelOffset.NOT_DECODER;

    byte supportChannelWidth;
    byte rifs;

    public HTInformationSubsetOne(byte value) {
        eSecondaryChannelOffset = ESecondaryChannelOffset.getESecondaryChannelOffset((byte) (value & 0x03));
        supportChannelWidth = (byte) (value & 0x04);
        rifs = (byte) (value & 0x08);
    }

    public ESecondaryChannelOffset geteSecondaryChannelOffset() {
        return eSecondaryChannelOffset;
    }

    public byte getRifs() {
        return rifs;
    }

    public byte getSupportChannelWidth() {
        return supportChannelWidth;
    }
}
