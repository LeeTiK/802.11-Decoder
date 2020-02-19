package leetik.w80211.protocol.wlan.frame.management.element;

public enum WlanElementID {

    SSID(0),
    SUPPORTED_RATES(1),
    DS_PARAMETER(3),
    TIM(5),
    ERP_INFORMATION(42),
    HT_CAPABILITIES(45),
    RSN_INFORMATION(48),
    EXTENDED_SUPPORTED_RATE(50),
    NOT_DECODED((byte)0xFF);

    private byte tagNumber;

    WlanElementID(int number)
    {
        tagNumber = (byte) number;
    }

    public byte getTagNumber() {
        return tagNumber;
    }

    public static WlanElementID getWlanElementID(int tagNumber) {
        for (WlanElementID wlanElementID : WlanElementID.values()) {
            if (wlanElementID.getTagNumber() == tagNumber) return wlanElementID;
        }
        return NOT_DECODED;
    }
}
