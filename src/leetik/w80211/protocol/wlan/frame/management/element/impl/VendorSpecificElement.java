package leetik.w80211.protocol.wlan.frame.management.element.impl;

import leetik.w80211.protocol.wlan.frame.management.element.EWlanElementID;
import leetik.w80211.protocol.wlan.frame.management.element.WlanElementAbstr;
import leetik.w80211.protocol.wlan.frame.management.element.impl.vendorSpecific.EOUI;
import leetik.w80211.protocol.wlan.frame.management.element.impl.vendorSpecific.EVendorSpecificElementType;
import leetik.w80211.protocol.wlan.frame.management.element.impl.vendorSpecific.RsnPmkid;
import leetik.w80211.protocol.wlan.frame.management.element.impl.vendorSpecific.WPS;
import leetik.w80211.protocol.wlan.utils.OtherUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class VendorSpecificElement extends WlanElementAbstr {
    public final static int id = 221;

    byte length;

    EOUI oui;
    EVendorSpecificElementType eVendorSpecificElementType = EVendorSpecificElementType.NOT_DECODER;

    byte vendorSpecificOuiType;

    byte[] vendorSpecificData;

    Object element;

    public VendorSpecificElement(byte[] data){
        super(data);

        length = (byte) data.length;

        ByteBuffer byteBuffer = OtherUtils.createByteBuffer(data.length);

        byteBuffer.put(data);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.flip();

        if (byteBuffer.remaining()>3) {
            byte[] bytes = new byte[3];
            byteBuffer.get(bytes);
            oui = EOUI.getOUI(bytes);
            vendorSpecificOuiType = byteBuffer.get();

            switch (oui) {

                case Ieee802_11:

                    switch (vendorSpecificOuiType) {
                        case 4:
                            eVendorSpecificElementType = EVendorSpecificElementType.RSN_PMKID;
                            element = new RsnPmkid(byteBuffer);
                            break;
                    }

                    break;
                case MicrosoftCorp:

                    switch (vendorSpecificOuiType) {
                        case 4:
                            eVendorSpecificElementType = EVendorSpecificElementType.WPS;
                            element = new WPS(byteBuffer);
                            break;
                    }
                    break;
                case NOT_DECODED:
                    break;
            }
        }
    }

    @Override
    public byte getElementId() {
        return (byte) id;
    }

    @Override
    public EWlanElementID getWlanElementId() {
        return EWlanElementID.VENDOR_SPECIFIC;
    }

    public EOUI getOui() {
        return oui;
    }

    public Object getElement() {
        return element;
    }

    public EVendorSpecificElementType geteVendorSpecificElementType() {
        return eVendorSpecificElementType;
    }
}
