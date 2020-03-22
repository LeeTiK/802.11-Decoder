package leetik.w80211.protocol.wlan.frame.management.element;

public enum EWlanElementID {

    SSID(0),
    SUPPORTED_RATES(1),
    DS_PARAMETER(3),
    TIM(5),
    ERP_INFORMATION(42),
    HT_CAPABILITIES(45),
    RSN_INFORMATION(48),

    EXTENDED_SUPPORTED_RATE(50),

    HT_INFORMATION(61),
    VENDOR_SPECIFIC(221),
    NOT_DECODED((byte)0xFF);

    private byte tagNumber;

    EWlanElementID(int number)
    {
        tagNumber = (byte) number;
    }

    public byte getTagNumber() {
        return tagNumber;
    }

    public static EWlanElementID getWlanElementID(int tagNumber) {
        for (EWlanElementID EWlanElementID : EWlanElementID.values()) {
            if (EWlanElementID.getTagNumber() == tagNumber) return EWlanElementID;
        }
        return NOT_DECODED;
    }
}
