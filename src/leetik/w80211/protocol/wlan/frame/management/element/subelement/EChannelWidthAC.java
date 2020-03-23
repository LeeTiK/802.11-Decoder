package leetik.w80211.protocol.wlan.frame.management.element.subelement;

public enum EChannelWidthAC {

    WIDTH_20MHZ_OR_40MHZ(0x00),
    WIDTH_80MHZ(0x01),
    WIDTH_160MHZ(0x02),
    WIDTH_80MHZ_AND_80MHZ(0x03),
    NOT_DECODER(0xFF);

    byte value;

    EChannelWidthAC(int value)
    {
        this.value = (byte) value;
    }

    public byte getValue() {
        return value;
    }

    public static EChannelWidthAC getEChannelWidthAC (byte value) {
        for (EChannelWidthAC eChannelWidthAC : EChannelWidthAC.values()) {
            if (eChannelWidthAC.getValue() == value) return eChannelWidthAC;
        }
        return NOT_DECODER;
    }
}
