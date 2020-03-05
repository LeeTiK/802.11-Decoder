package leetik.w80211.protocol.wlan.frame.management.element.impl.vendorSpecific;

import leetik.w80211.protocol.wlan.utils.HexDump;

import java.nio.ByteBuffer;

public class RsnPmkid {

    byte[] PMKID;

    public RsnPmkid(ByteBuffer byteBuffer)
    {
        if (byteBuffer.remaining()<16) return;

        PMKID = new byte[16];
        byteBuffer.get(PMKID);
    }

    public byte[] getPMKID() {
        return PMKID;
    }

    @Override
    public String toString(){
        return HexDump.toHexString(PMKID);
    }
}
