package leetik.w80211.protocol.wlan.frame.management.element.subelement;


public enum  EAuthenticationType {

    PSK(0x01),
    MGT(0x02),
    CMAC(0x03),
    SAE(0x04),
    OWE(0x05),
    NOT_DECODER(0xFF);

    byte value;

    EAuthenticationType(int type)
    {
        this.value = (byte) type;
    }

    public byte getValue() {
        return value;
    }

    public static EAuthenticationType getEAuthenticationType (byte value) {
        for (EAuthenticationType eAuthenticationType : EAuthenticationType.values()) {
            if (eAuthenticationType.getValue() == value) return eAuthenticationType;
        }
        return NOT_DECODER;
    }
}
