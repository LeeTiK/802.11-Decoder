package leetik.w80211.protocol.wlan.frame.management.element.subelement;

import leetik.w80211.protocol.wlan.frame.management.element.subelement.other.ESecondaryChannelOffset;

public enum EChipherType {


    GROUP_CIPHER(0x00),
    WEP_40(0x01),
    TKIP(0x02),
    RESERVED(0x03),
    CCMP(0x04),
    WEP_104(0x05),
    NOT_DECODER(0xFF);

    byte value;

    EChipherType(int value)
    {
        this.value = (byte) value;
    }

    public byte getValue() {
        return value;
    }

    public static EChipherType getEChipherType (byte value) {
        for (EChipherType eChipherType : EChipherType.values()) {
            if (eChipherType.getValue() == value) return eChipherType;
        }
        return NOT_DECODER;
    }
}
