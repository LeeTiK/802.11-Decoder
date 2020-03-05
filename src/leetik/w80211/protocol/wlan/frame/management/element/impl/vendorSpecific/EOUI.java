package leetik.w80211.protocol.wlan.frame.management.element.impl.vendorSpecific;

public enum EOUI {

    Ieee802_11(new byte[]{0x00,0x0f, (byte) 0xac}, "Ieee 802.11"),
    MicrosoftCorp(new byte[]{0x00,0x50, (byte) 0xf2}, "Microsoft Corp."),
    NOT_DECODED(new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF}, "NOT_DECODED");

    byte[] bytes;
    String name;

    EOUI(byte[] bytes, String name)
    {
        this.bytes = bytes;
        this.name = name;
    }

    public static EOUI getOUI(byte[] bytes) {
        if (bytes==null || bytes.length<3) return NOT_DECODED;
        for (EOUI eoui : EOUI.values()) {
            boolean check = true;
            for (int i=0; i<bytes.length; i++){
                if (eoui.getBytes()[i]!=bytes[i]) {
                    check = false;
                    break;
                }
            }
            if (check) return eoui;
        }
        return NOT_DECODED;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public String getName() {
        return name;
    }
}
