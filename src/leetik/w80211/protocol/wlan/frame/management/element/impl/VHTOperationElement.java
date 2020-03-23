package leetik.w80211.protocol.wlan.frame.management.element.impl;

import leetik.w80211.protocol.wlan.frame.management.element.EWlanElementID;
import leetik.w80211.protocol.wlan.frame.management.element.WlanElementAbstr;
import leetik.w80211.protocol.wlan.frame.management.element.subelement.EChannelWidthAC;
import leetik.w80211.protocol.wlan.utils.OtherUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class VHTOperationElement extends WlanElementAbstr {

    byte length;

    EChannelWidthAC mEChannelWidthAC = EChannelWidthAC.NOT_DECODER;

    byte channelCenterSegmentZero= (byte) 0xFF;
    byte channelCenterSegmentOne = (byte) 0xFF;

    public VHTOperationElement(byte[] data) {
        super(data);

        length = (byte) data.length;

        ByteBuffer byteBuffer = OtherUtils.createByteBuffer(data.length);

        byteBuffer.put(data);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.flip();

        mEChannelWidthAC = EChannelWidthAC.getEChannelWidthAC(byteBuffer.get());

        channelCenterSegmentZero = byteBuffer.get();

        channelCenterSegmentOne = byteBuffer.get();
    }

    private void decoder(){
    }

    @Override
    public byte getElementId() {
        return EWlanElementID.VHT_OPERATION.getTagNumber();
    }

    @Override
    public EWlanElementID getWlanElementId() {
        return EWlanElementID.VHT_OPERATION;
    }

    public byte getChannelCenterSegmentOne() {
        return channelCenterSegmentOne;
    }

    public byte getChannelCenterSegmentZero() {
        return channelCenterSegmentZero;
    }

    public EChannelWidthAC getEChannelWidthAC() {
        return mEChannelWidthAC;
    }
}
