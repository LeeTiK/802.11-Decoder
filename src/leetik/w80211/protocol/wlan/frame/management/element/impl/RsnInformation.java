package leetik.w80211.protocol.wlan.frame.management.element.impl;

import leetik.w80211.protocol.wlan.frame.management.element.WlanElementAbstr;
import leetik.w80211.protocol.wlan.frame.management.element.WlanElementID;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class RsnInformation extends WlanElementAbstr {

    short version;
    byte groupCipherSuite;
    short pairwiseCipherSuiteCount;
    ArrayList<Byte> pairwiseCipherSuiteArrayList;
    short authenticationSuiteCount;
    ArrayList<Byte> authenticationSuiteArrayList;
    short rsnCapabilities;
    short pmkCount;


    public RsnInformation(byte[] data) {
        super(data);
    }

    public RsnInformation(ByteBuffer byteBuffer, byte[] data)
    {
        super(data);

        version = byteBuffer.getShort();

        if (byteBuffer.get()==0x00 && byteBuffer.get()==0x0F && byteBuffer.get() == 0xAC)
        {
            groupCipherSuite = byteBuffer.get();
        }
        else {
            decodeError = true;
            return;
        }

        pairwiseCipherSuiteCount = byteBuffer.getShort();

        for (int i=0; i<pairwiseCipherSuiteCount; i++)
        {
            if (byteBuffer.get()==0x00 && byteBuffer.get()==0x0F && byteBuffer.get() == 0xAC)
            {
                pairwiseCipherSuiteArrayList.add(byteBuffer.get());
            }
            else {
                decodeError = true;
                return;
            }
        }
    }

    @Override
    public byte getElementId() {
        return 48;
    }

    @Override
    public WlanElementID getWlanElementId() {
        return WlanElementID.RSN_INFORMATION;
    }
}
