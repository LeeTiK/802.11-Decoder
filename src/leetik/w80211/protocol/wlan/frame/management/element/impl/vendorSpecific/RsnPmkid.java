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

    public boolean isValidPMKID(){
        if (PMKID==null) return false;

        boolean checkBAD = true;
        for (int i=0; i<PMKID.length; i++){
            if (PMKID[i]!=0x00){
                checkBAD = false;
                break;
            }
        }

        if (checkBAD) return false;

        return true;
    }

    @Override
    public String toString(){
        return HexDump.toHexString(PMKID);
    }
}
