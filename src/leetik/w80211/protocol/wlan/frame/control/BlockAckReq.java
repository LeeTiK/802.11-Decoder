package leetik.w80211.protocol.wlan.frame.control;

import leetik.w80211.protocol.wlan.WlanFramePacket;
import leetik.w80211.protocol.wlan.frame.IWlanFrame;
import leetik.w80211.protocol.wlan.frame.control.inter.IBlockAckReq;
import leetik.w80211.protocol.wlan.inter.IWlanControlFrame;

import java.nio.ByteBuffer;

public class BlockAckReq implements IWlanFrame, IWlanControlFrame, IBlockAckReq {

    /**
     * duration id value on 2 bytes
     */
    private byte[] durationId = null;

    /**
     * receiver address on 6 Bytes
     */
    private byte[] receiverAddr = null;

    /**
     * transmitter address on 6 Bytes
     */
    private byte[] transmitterAddr = null;

    private short blockAckControl;

    private short blockAckStartingSequenceControl;


    public BlockAckReq(ByteBuffer byteBuffer) {
        if (byteBuffer.remaining() >= 14) {
            int position = byteBuffer.position();

            durationId =  new byte[] { byteBuffer.get(position), byteBuffer.get(position+1) };
            receiverAddr = new byte[] { byteBuffer.get(position+2), byteBuffer.get(position+3), byteBuffer.get(position+4), byteBuffer.get(position+5),
                    byteBuffer.get(position+6), byteBuffer.get(position+7) };
            transmitterAddr =new byte[] { byteBuffer.get(position+8), byteBuffer.get(position+9),byteBuffer.get(position+10),
                    byteBuffer.get(position+11), byteBuffer.get(position+12), byteBuffer.get(position+13) };
            byteBuffer.position(position + 14);

            blockAckControl = byteBuffer.getShort();

            blockAckStartingSequenceControl = byteBuffer.getShort();
        } else {
            System.err.println("error treating Control frame - request to send frame");
        }
    }

    @Override
    public WlanFramePacket getWlanDecoder() {
        return null;
    }

    @Override
    public byte[] getDurationId() {
        return durationId;
    }

    @Override
    public byte[] getTransmitterAddr() {
        return transmitterAddr;
    }

    @Override
    public byte[] getReceiverAddr() {
        return receiverAddr;
    }
}
