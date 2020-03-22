package leetik.w80211.protocol.wlan.frame.management.element.impl;

import leetik.w80211.protocol.wlan.frame.management.element.EWlanElementID;
import leetik.w80211.protocol.wlan.frame.management.element.WlanElementAbstr;
import leetik.w80211.protocol.wlan.frame.management.element.subelement.HTInformationSubsetOne;

import java.nio.ByteBuffer;

public class HTInformationElement extends WlanElementAbstr {

    byte channel;

    HTInformationSubsetOne mHTInformationSubsetOne;
    // NOT DECODER
    short hTInformationSubsetTwo;
    // NOT DECODER
    short hTInformationSubsetThree;

    @Deprecated
    public HTInformationElement(byte[] data) {
        super(data);
    }

    public HTInformationElement(ByteBuffer byteBuffer, byte[] data)
    {
        super(data);

        channel = byteBuffer.get();

        mHTInformationSubsetOne = new HTInformationSubsetOne(byteBuffer.get());

        hTInformationSubsetTwo = byteBuffer.getShort();

        hTInformationSubsetThree = byteBuffer.getShort();
    }


    @Override
    public byte getElementId() {
        return EWlanElementID.HT_INFORMATION.getTagNumber();
    }

    @Override
    public EWlanElementID getWlanElementId() {
        return EWlanElementID.HT_INFORMATION;
    }

    public int getChannel() {
        return channel & 0xFF;
    }

    public HTInformationSubsetOne getHTInformationSubsetOne() {
        return mHTInformationSubsetOne;
    }
}
