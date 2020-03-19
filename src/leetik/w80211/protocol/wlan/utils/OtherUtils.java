package leetik.w80211.protocol.wlan.utils;

import leetik.w80211.protocol.wlan.WlanPacket;

import java.nio.ByteBuffer;

public class OtherUtils{

    public static ByteBuffer createByteBuffer (int size){
        if (WlanPacket.DIRECT_BYTE_BUFFER)
        {
             return ByteBuffer.allocateDirect(size);
        }
        else {
            return ByteBuffer.allocate(size);
        }
    }
}
