package leetik.w80211.protocol.wlan.frame.management.element.impl;

import leetik.w80211.protocol.wlan.frame.management.element.WlanElementAbstr;
import leetik.w80211.protocol.wlan.frame.management.element.EWlanElementID;
import leetik.w80211.protocol.wlan.frame.management.element.subelement.EChipherType;
import leetik.w80211.protocol.wlan.utils.OtherUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class RsnInformationElement extends WlanElementAbstr {

    short version;
    EChipherType groupCipherSuite;
    short pairwiseCipherSuiteCount;
    ArrayList<EChipherType> pairwiseCipherSuiteArrayList;
    short authenticationSuiteCount;
    ArrayList<EChipherType> authenticationSuiteArrayList;
    short rsnCapabilities;
    short pmkCount;


    @Deprecated
    public RsnInformationElement(byte[] data) {
        super(data);

        ByteBuffer byteBuffer = OtherUtils.createByteBuffer(data.length);

        byteBuffer.put(data);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.flip();

        version = byteBuffer.getShort();

        byte one = byteBuffer.get(), two = byteBuffer.get(), three = byteBuffer.get();

        if (one==0x00 && two==0x0F && three == (byte) 0xAC)
        {
            groupCipherSuite = EChipherType.getEChipherType(byteBuffer.get());
        }
        else {
            decodeError = true;
            return;
        }

        pairwiseCipherSuiteCount = byteBuffer.getShort();
        pairwiseCipherSuiteArrayList = new ArrayList<>(pairwiseCipherSuiteCount);


        for (int i=0; i<pairwiseCipherSuiteCount; i++)
        {
            one = byteBuffer.get(); two = byteBuffer.get(); three = byteBuffer.get();

            if (one==0x00 && two==0x0F && three == (byte)0xAC)
            {
                pairwiseCipherSuiteArrayList.add(EChipherType.getEChipherType(byteBuffer.get()));
            }
            else {
                decodeError = true;
                return;
            }
        }

        authenticationSuiteCount = byteBuffer.getShort();
        authenticationSuiteArrayList = new ArrayList<>(authenticationSuiteCount);

        for (int i=0; i<authenticationSuiteCount; i++)
        {
            one = byteBuffer.get(); two = byteBuffer.get(); three = byteBuffer.get();

            if (one==0x00 && two==0x0F && three == (byte)0xAC)
            {
                /*
                case 0x01:
							ap_cur->security |= AUTH_MGT;
							break;
						case 0x02:
							ap_cur->security |= AUTH_PSK;
							break;
						case 0x06:
						case 0x0d:
							ap_cur->security |= AUTH_CMAC;
							break;
						case 0x08:
							ap_cur->security |= AUTH_SAE;
							break;
						case 0x12:
							ap_cur->security |= AUTH_OWE;
							break;
						default:
							break;
                 */
                authenticationSuiteArrayList.add(EChipherType.getEChipherType(byteBuffer.get()));
            }
            else {
                decodeError = true;
                return;
            }
        }

        rsnCapabilities = byteBuffer.getShort();
    }


    @Override
    public byte getElementId() {
        return 48;
    }

    @Override
    public EWlanElementID getWlanElementId() {
        return EWlanElementID.RSN_INFORMATION;
    }

    public ArrayList<EChipherType> getAuthenticationSuiteArrayList() {
        return authenticationSuiteArrayList;
    }

    public ArrayList<EChipherType> getPairwiseCipherSuiteArrayList() {
        return pairwiseCipherSuiteArrayList;
    }

    public EChipherType getGroupCipherSuite() {
        return groupCipherSuite;
    }
}
