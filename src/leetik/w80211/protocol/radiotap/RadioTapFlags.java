package leetik.w80211.protocol.radiotap;

import leetik.w80211.protocol.radiotap.inter.IRadiotapFlags;

public class RadioTapFlags implements IRadiotapFlags {

    private byte flags =0x00;

    private boolean cfp = false;
    private boolean preamble= false;
    private boolean wep= false;
    private boolean fragmentation= false;
    private boolean fscAtEnd= false;
    private boolean dataPad= false;
    private boolean badFCS= false;
    private boolean shortGI= false;



    public RadioTapFlags(byte flags)
    {
        this.flags = flags;
        if ((flags & 0x01) != 0) {
            cfp = true;
        }
        if ((flags & 0x02) != 0) {
            preamble = true;
        }
        if ((flags & 0x04) != 0) {
            wep = true;
        }
        if ((flags & 0x08) != 0) {
            fragmentation = true;
        }
        if ((flags & 0x10) != 0) {
            fscAtEnd = true;
        }
        if ((flags & 0x20) != 0) {
            dataPad = true;
        }
        if ((flags & 0x40) != 0) {
            badFCS = true;
        }
        if ((flags & 0x80) != 0) {
            shortGI = true;
        }
    }

    @Override
    public boolean isCFP() {
        return cfp;
    }

    @Override
    public boolean isPreamble() {
        return preamble;
    }

    @Override
    public boolean isWEP() {
        return wep;
    }

    @Override
    public boolean isFragmentation() {
        return fragmentation;
    }

    @Override
    public boolean isFCSatEnd() {
        return fscAtEnd;
    }

    @Override
    public boolean isDataPad() {
        return dataPad;
    }

    @Override
    public boolean isBadFCS() {
        return badFCS;
    }

    @Override
    public boolean isShortGI() {
        return shortGI;
    }
}
