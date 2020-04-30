package leetik.w80211.protocol.authentication;

import leetik.w80211.protocol.wlan.WlanFramePacket;
import leetik.w80211.protocol.wlan.frame.management.element.EWlanElementID;
import leetik.w80211.protocol.wlan.frame.management.element.IWlanElement;
import leetik.w80211.protocol.wlan.frame.management.element.WlanElementAbstr;
import leetik.w80211.protocol.wlan.frame.management.element.WlanElementIdDecoder;
import leetik.w80211.utils.ByteUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;

public class AuthenticationDecoder {

    private WlanFramePacket wlanDecoder;

    byte version;

    byte type;

    short length;

    byte keyDescriptorType;

    KeyInformation keyInformation;

    short keyLength;

    long replayCounter;

    byte[] wpaKeyNonce;
    byte[] keyIV;
    byte[] wpaKeyRsc;
    byte[] wpaKeyID;
    byte[] wpaKeyMic;

    short wpaKeyDataLength;

    List<IWlanElement> taggedParameter;

    byte[] frame;

    byte messageNumber;

    public AuthenticationDecoder(WlanFramePacket wlanDecoder, ByteBuffer byteBuffer) {
        decode(byteBuffer);

        this.wlanDecoder = wlanDecoder;
    }

    public AuthenticationDecoder(ByteBuffer byteBuffer)
    {
        decode(byteBuffer);
    }

    private void decode(ByteBuffer byteBuffer){
        byteBuffer.order(ByteOrder.BIG_ENDIAN);

        byteBuffer.mark();
        frame = new byte[byteBuffer.remaining()];
        byteBuffer.get(frame);
        byteBuffer.reset();

        version = byteBuffer.get();
        type = byteBuffer.get();
        length = byteBuffer.getShort();

        if (type != 3) return;

        keyDescriptorType = byteBuffer.get();
        keyInformation = new KeyInformation(byteBuffer);

        keyLength = byteBuffer.getShort();

        replayCounter = byteBuffer.getLong();

        wpaKeyNonce = new byte[32];
        byteBuffer.get(wpaKeyNonce);

        keyIV = new byte[16];
        byteBuffer.get(keyIV);

        wpaKeyRsc = new byte[8];
        byteBuffer.get(wpaKeyRsc);
        wpaKeyID = new byte[8];
        byteBuffer.get(wpaKeyID);

        wpaKeyMic = new byte[16];
        byteBuffer.get(wpaKeyMic);

        wpaKeyDataLength = byteBuffer.getShort();

        taggedParameter = WlanElementIdDecoder.decode(byteBuffer);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);


        calculatedNumberMessage();
    }

    public byte getKeyDescriptorType() {
        return keyDescriptorType;
    }

    public byte getType() {
        return type;
    }

    public byte getVersion() {
        return version;
    }

    public byte[] getKeyIV() {
        return keyIV;
    }

    public byte[] getWpaKeyID() {
        return wpaKeyID;
    }

    public byte[] getWpaKeyMic() {
        return wpaKeyMic;
    }

    public byte[] getWpaKeyNonce() {
        return wpaKeyNonce;
    }

    public byte[] getWpaKeyRsc() {
        return wpaKeyRsc;
    }

    public KeyInformation getKeyInformation() {
        return keyInformation;
    }

    public List<IWlanElement> getTaggedParameter() {
        return taggedParameter;
    }

    public long getReplayCounter() {
        return replayCounter;
    }

    public short getKeyLength() {
        return keyLength;
    }

    public short getLength() {
        return length;
    }

    public short getWpaKeyDataLength() {
        return wpaKeyDataLength;
    }


    public WlanElementAbstr getTaggedParameter(EWlanElementID EWlanElementID){
        if (taggedParameter==null) return null;

        for (int i=0; i<taggedParameter.size(); i++)
        {
            if (taggedParameter.get(i).getWlanElementId()== EWlanElementID) return (WlanElementAbstr) taggedParameter.get(i);
        }

        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        AuthenticationDecoder authenticationDecoder = (AuthenticationDecoder) obj;

        if (authenticationDecoder.getMessageNumber()!=this.getMessageNumber()) {
            return false;
        }

        if (Arrays.equals(wpaKeyNonce,authenticationDecoder.wpaKeyNonce)){
            return true;
        }
        else return false;

       // return false;
    }

    public WlanFramePacket getWlanFrameDecoder() {
        return wlanDecoder;
    }

    public byte[] getFrame() {
        return frame;
    }


    private void calculatedNumberMessage() {
        messageNumber = (byte) 0xFF;
        if (!getKeyInformation().isInstall() && getKeyInformation().isKeyAck() && !getKeyInformation().isKetMic() && !getKeyInformation().isSecure())
        {
            messageNumber = 1;
            return;
        } else {
            if (!getKeyInformation().isInstall() && !getKeyInformation().isKeyAck() && getKeyInformation().isKetMic() && !getKeyInformation().isSecure() && !ByteUtils.byteArrayCheckZero(getWpaKeyNonce()))
            {
                messageNumber = 2;
                return;
            } else {
                if (getKeyInformation().isInstall() && getKeyInformation().isKeyAck() && getKeyInformation().isKetMic() && getKeyInformation().isSecure())
                {
                    messageNumber = 3;
                    return;
                } else {
                    if (!getKeyInformation().isInstall() && !getKeyInformation().isKeyAck() && getKeyInformation().isKetMic() && getKeyInformation().isSecure())
                    {
                        messageNumber = 4;
                        return;
                    }
                }
            }
        }
    }

    public byte getMessageNumber() {
        return messageNumber;
    }
}
