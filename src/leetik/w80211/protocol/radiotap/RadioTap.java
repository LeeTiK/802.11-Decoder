/**
 * The MIT License (MIT)
 * 
 * Copyright (c) 2015 Bertrand Martel
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package leetik.w80211.protocol.radiotap;

import leetik.w80211.utils.ByteUtils;
import leetik.w80211.utils.RadioTapException;
import leetik.w80211.protocol.radiotap.inter.IRadioTapFrame;
import leetik.w80211.protocol.radiotap.inter.IRadiotapData;
import leetik.w80211.protocol.radiotap.inter.IRadiotapPresentFlags;

import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * Define radio tap header values (total Radio Tap length : 2 Bytes)
 * 
 * 
 * struct ieee80211_radiotap_header {
        u_int8_t        it_version;     // set to 0
        u_int8_t        it_pad;
        u_int16_t       it_len;         // entire length
        u_int32_t[]       it_present;     // fields present
} __attribute__((__packed__));

 * @author Vasetsky Valery
 *
 */
public class RadioTap implements IRadioTapFrame {

	/** major version of the radiotap header is in use */
	private byte headerRevision = 0x00;

	private byte headerPad = 0x00;

	/** * entire length of the radiotap data */
	private int headerLength = 0;

	private IRadiotapPresentFlags flagList = null;
	
	/** * bitmask for values that will be present in radio tap payload */
	private ArrayList<RadioTapPresentFlags> presentFlags = null;

	/** object containing all properties decoded from radio tap payload */
	private ArrayList<IRadiotapData> radioTapData = null;

	//
	private boolean malformedRadioTap = false;

	/**
	 * Parse radio tap headers according to radio tap standard
	 * http://www.radiotap.org/
	 * 
	 * @param byteBuffer
	 */
	public RadioTap(ByteBuffer byteBuffer) throws RadioTapException{
		try
		{
			byteBuffer.mark();

			if (byteBuffer.limit() > 7) {
				headerRevision = byteBuffer.get();

				if (headerRevision !=0 ) {
					malformedRadioTap = true;
					return;
				}

				headerPad = byteBuffer.get();

				headerLength = byteBuffer.getShort();

				//int localHeaderlength = headerLength;

				if (headerLength<byteBuffer.limit())
				{
					radioTapData = new ArrayList<>(1);
					presentFlags = new ArrayList<>(1);

					boolean nextPresentFlags = true;

					while (nextPresentFlags)
					{
						int presentFlag = byteBuffer.getInt();

						presentFlags.add(new RadioTapPresentFlags(presentFlag));

						if (((presentFlag >> 7) & 0x01) == 1)
							nextPresentFlags = true;
						else
							nextPresentFlags = false;
					}

					for (int i=0;i<presentFlags.size(); i++)
					{
						radioTapData.add(presentFlags.get(i).decode(byteBuffer));
					}
				}
				else {
					malformedRadioTap = true;
				}

			} else {
				throw new RadioTapException("An error occured while decoding radio tap frame");
			}
		}
		catch (Exception e )
		{
			e.printStackTrace();
			throw new RadioTapException();
		}
	}

	@Deprecated
	public RadioTap(byte[] frame) throws RadioTapException{
		try
		{
			if (frame.length > 7) {
				headerRevision = frame[0];

				radioTapData = new ArrayList<>();

				headerLength = ByteUtils.convertByteArrayToInt(new byte[] { frame[3], frame[2] });

				byte[] presentFlags = new byte[] { frame[7], frame[6], frame[5], frame[4] };

				int size = 0;

				if (((frame[7] >> 7) & 0x01) == 1) {
					byte[] nextPresentFlags = new byte[] { frame[11], frame[10], frame[9], frame[8] };
					size +=4;
					if (((frame[11] >> 7) & 0x01) == 1) {
						byte[] nextPresentFlags2 = new byte[] { frame[15], frame[14], frame[13], frame[12] };
						size+=4;
					}
				}

				if (frame.length >= headerLength + 8) {
					// fill radio tap header payload according to header length
					byte[] radioTapPayload = new byte[headerLength - 8 -size];

					if (headerLength > 8) {
						for (int i = 8+size; i < headerLength; i++) {
							radioTapPayload[i - 8 -size ] = frame[i];
						}
					}
					RadioTapPresentFlags flags = new RadioTapPresentFlags(ByteUtils.convertByteArrayToInt(presentFlags));
					radioTapData.add(flags.decode(presentFlags, radioTapPayload));
					this.flagList=flags;
				} else {
					throw new RadioTapException("An error occured while decoding radio tap frame");
				}
			} else {
				throw new RadioTapException("An error occured while decoding radio tap frame");
			}
		}
		catch (Exception e )
		{
			e.printStackTrace();
			throw new RadioTapException();
		}
	}


	@Override
	public int getRadiotapVersion() {
		return (headerRevision & 0xFF);
	}

	@Override
	public int getRadioTapDataLength() {
		return (headerLength & 0xFF);
	}

	@Override
	public IRadiotapData getRadioTapData() {
		if (radioTapData==null || radioTapData.size()<1) return null;
		return radioTapData.get(0);
	}

	public ArrayList<IRadiotapData> getRadioTapDataArray() {
		if (radioTapData==null) return null;
		return radioTapData;
	}

	@Override
	public IRadiotapPresentFlags getRadioTapFlagList() {
		return flagList;
	}

	@Override
	public boolean isMalformedRadioTap() {
		return malformedRadioTap;
	}
}
