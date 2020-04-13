package leetik.w80211.protocol.wlan.frame.management.element.impl.vendorSpecific;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class WPS {

    int version = 0xFF;
    byte state = (byte) 0xFF;
    byte lock = (byte) 0xFF;

    public WPS(ByteBuffer byteBuffer)
    {
        if (byteBuffer.remaining()<4) return;

        byteBuffer.order(ByteOrder.BIG_ENDIAN);

        while (byteBuffer.remaining()>4)
        {
            int subtype = (byteBuffer.getShort());
            int len = (byteBuffer.getShort());

            if (len>byteBuffer.remaining()) {
                break;
            }

            byteBuffer.mark();

            switch (subtype)
            {
                case 0x104a: // WPS Version
                    version = byteBuffer.get();
                    break;
                case 0x1011: // Device Name
                case 0x1012: // Device Password ID
                case 0x1021: // Manufacturer
                case 0x1023: // Model
                case 0x1024: // Model Number
                case 0x103b: // Response Type
                case 0x103c: // RF Bands
                case 0x1041: // Selected Registrar
                case 0x1042: // Serial Number
                    break;
                case 0x1044: // WPS State
                    state = byteBuffer.get();
                    break;
                case 0x1047: // UUID Enrollee
                case 0x1049: // Vendor Extension
                    if (byteBuffer.get() == 0x00 && byteBuffer.get() == (byte)0x37 && byteBuffer.get() == (byte) 0x2A)
                    {
                        byte command = byteBuffer.get();
                        byte lenCommand = byteBuffer.get();
                        version = byteBuffer.get();
                    }
                break;
                case 0x1054: // Primary Device Type
                    break;
                case 0x1057: // AP Setup Locked
                    lock = byteBuffer.get();
                    break;
                case 0x1008: // Config Methods
                case 0x1053: // Selected Registrar Config Methods
                  //  ap_cur->wps.meth = (p[4] << 8) + p[5];
                    break;
                default: // Unknown type-length-value
                    break;
            }

            byteBuffer.reset();
            byteBuffer.position(byteBuffer.position() + len);
        }
    }

    public int getVersion() {
        return version;
    }

    public byte getState() {
        return state;
    }

    public byte getLock() {
        return lock;
    }
}
