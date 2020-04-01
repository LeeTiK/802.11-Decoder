package leetik.w80211.protocol.authentication;

import java.nio.ByteBuffer;

public class KeyInformation {
    short keyinfo =0x00;

    byte keyDescriptionVersion = 0x00;
    byte keyType =0x00;
    byte keyIndex = 0x00;
    boolean install =false;
    boolean keyAck = false;
    boolean ketMic = false;
    boolean secure = false;
    boolean error = false;
    boolean request = false;
    boolean encryptedKeyData = false;
    boolean smkMessage = false;

    public KeyInformation(ByteBuffer byteBuffer)
    {
        short keyinfo = byteBuffer.getShort();
        decoder(keyinfo);
    }

    public KeyInformation(short keyinfo)
    {
        decoder(keyinfo);
    }

    private void decoder(short keyinfo){
        this.keyinfo = keyinfo;

        keyDescriptionVersion = (byte) (keyinfo & 0x0007);
        keyType = (byte) ((keyinfo & 0x0008) >> 3);
        keyIndex = (byte) ((keyinfo & 0x0030) >> 4);

        if ((keyinfo & 0x0040) !=0)
        {
            install = true;
        }

        if ((keyinfo & 0x0080) !=0)
        {
            keyAck = true;
        }

        if ((keyinfo & 0x0100) !=0)
        {
            ketMic = true;
        }

        if ((keyinfo & 0x0200) !=0)
        {
            secure = true;
        }

        if ((keyinfo & 0x0400) !=0)
        {
            error = true;
        }

        if ((keyinfo & 0x0800) !=0)
        {
            request = true;
        }

        if ((keyinfo & 0x1000) !=0)
        {
            encryptedKeyData = true;
        }

        if ((keyinfo & 0x2000) !=0)
        {
            smkMessage = true;
        }
    }

    public boolean isEncryptedKeyData() {
        return encryptedKeyData;
    }

    public boolean isError() {
        return error;
    }

    public boolean isInstall() {
        return install;
    }

    public boolean isKetMic() {
        return ketMic;
    }

    public boolean isKeyAck() {
        return keyAck;
    }

    public boolean isRequest() {
        return request;
    }

    public boolean isSecure() {
        return secure;
    }

    public boolean isSmkMessage() {
        return smkMessage;
    }

    public byte getKeyDescriptionVersion() {
        return keyDescriptionVersion;
    }

    public byte getKeyIndex() {
        return keyIndex;
    }

    public byte getKeyType() {
        return keyType;
    }

    public short getKeyinfo() {
        return keyinfo;
    }
}
