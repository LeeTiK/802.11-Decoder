package leetik.w80211.protocol.wlan.frame.management.element.subelement.other;

import leetik.w80211.protocol.wlan.frame.management.element.EWlanElementID;

public enum ESecondaryChannelOffset {

    NO_SECONDARY_CHANNEL(0x00),
    ABOVE_SECONDARY_CHANNEL(0x01),
    BELOW_SECONDARY_CHANNEL(0x03),
    NOT_DECODER(0xFF)
    ;

    byte value;

    ESecondaryChannelOffset(int value)
    {
        this.value = (byte) value;
    }

    public byte getValue() {
        return value;
    }

    public static ESecondaryChannelOffset getESecondaryChannelOffset(byte value) {
        for (ESecondaryChannelOffset secondaryChannelOffset : ESecondaryChannelOffset.values()) {
            if (secondaryChannelOffset.getValue() == value) return secondaryChannelOffset;
        }
        return NOT_DECODER;
    }
}
