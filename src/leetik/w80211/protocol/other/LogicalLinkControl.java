package leetik.w80211.protocol.other;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class LogicalLinkControl {
    byte DSAP;

    byte SSAP;

    byte controlField;

    byte[] organizationCode;

    short type;

    public LogicalLinkControl(ByteBuffer byteBuffer)
    {
        byteBuffer.order(ByteOrder.BIG_ENDIAN);
        DSAP = byteBuffer.get();
        SSAP = byteBuffer.get();

        controlField = byteBuffer.get();

        organizationCode = new byte[3];
        byteBuffer.get(organizationCode);

        type = byteBuffer.getShort();
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
    }

    public int getType() {
        return ((int)type & 0xFFFF);
    }

    public byte getControlField() {
        return controlField;
    }

    public byte getDSAP() {
        return DSAP;
    }

    public byte getSSAP() {
        return SSAP;
    }

    public byte[] getOrganizationCode() {
        return organizationCode;
    }
}
