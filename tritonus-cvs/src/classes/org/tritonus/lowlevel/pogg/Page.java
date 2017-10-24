/*
 *	Page.java
 *
 *	This file is part of Tritonus: http://www.tritonus.org/
 */

/*
 *  Copyright (c) 2000 - 2001 by Matthias Pfisterer
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU Library General Public License as published
 *   by the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Library General Public License for more details.
 *
 *   You should have received a copy of the GNU Library General Public
 *   License along with this program; if not, write to the Free Software
 *   Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

/*
|<---            this code is formatted to fit into 80 columns             --->|
*/

package org.tritonus.lowlevel.pogg;



/** Wrapper for ogg_page.
 */
public class Page
{
	private static final int[] crc_lookup =
	{
		0x00000000, 0x04c11db7, 0x09823b6e, 0x0d4326d9,
		0x130476dc, 0x17c56b6b, 0x1a864db2, 0x1e475005,
		0x2608edb8, 0x22c9f00f, 0x2f8ad6d6, 0x2b4bcb61,
		0x350c9b64, 0x31cd86d3, 0x3c8ea00a, 0x384fbdbd,
		0x4c11db70, 0x48d0c6c7, 0x4593e01e, 0x4152fda9,
		0x5f15adac, 0x5bd4b01b, 0x569796c2, 0x52568b75,
		0x6a1936c8, 0x6ed82b7f, 0x639b0da6, 0x675a1011,
		0x791d4014, 0x7ddc5da3, 0x709f7b7a, 0x745e66cd,
		0x9823b6e0, 0x9ce2ab57, 0x91a18d8e, 0x95609039,
		0x8b27c03c, 0x8fe6dd8b, 0x82a5fb52, 0x8664e6e5,
		0xbe2b5b58, 0xbaea46ef, 0xb7a96036, 0xb3687d81,
		0xad2f2d84, 0xa9ee3033, 0xa4ad16ea, 0xa06c0b5d,
		0xd4326d90, 0xd0f37027, 0xddb056fe, 0xd9714b49,
		0xc7361b4c, 0xc3f706fb, 0xceb42022, 0xca753d95,
		0xf23a8028, 0xf6fb9d9f, 0xfbb8bb46, 0xff79a6f1,
		0xe13ef6f4, 0xe5ffeb43, 0xe8bccd9a, 0xec7dd02d,
		0x34867077, 0x30476dc0, 0x3d044b19, 0x39c556ae,
		0x278206ab, 0x23431b1c, 0x2e003dc5, 0x2ac12072,
		0x128e9dcf, 0x164f8078, 0x1b0ca6a1, 0x1fcdbb16,
		0x018aeb13, 0x054bf6a4, 0x0808d07d, 0x0cc9cdca,
		0x7897ab07, 0x7c56b6b0, 0x71159069, 0x75d48dde,
		0x6b93dddb, 0x6f52c06c, 0x6211e6b5, 0x66d0fb02,
		0x5e9f46bf, 0x5a5e5b08, 0x571d7dd1, 0x53dc6066,
		0x4d9b3063, 0x495a2dd4, 0x44190b0d, 0x40d816ba,
		0xaca5c697, 0xa864db20, 0xa527fdf9, 0xa1e6e04e,
		0xbfa1b04b, 0xbb60adfc, 0xb6238b25, 0xb2e29692,
		0x8aad2b2f, 0x8e6c3698, 0x832f1041, 0x87ee0df6,
		0x99a95df3, 0x9d684044, 0x902b669d, 0x94ea7b2a,
		0xe0b41de7, 0xe4750050, 0xe9362689, 0xedf73b3e,
		0xf3b06b3b, 0xf771768c, 0xfa325055, 0xfef34de2,
		0xc6bcf05f, 0xc27dede8, 0xcf3ecb31, 0xcbffd686,
		0xd5b88683, 0xd1799b34, 0xdc3abded, 0xd8fba05a,
		0x690ce0ee, 0x6dcdfd59, 0x608edb80, 0x644fc637,
		0x7a089632, 0x7ec98b85, 0x738aad5c, 0x774bb0eb,
		0x4f040d56, 0x4bc510e1, 0x46863638, 0x42472b8f,
		0x5c007b8a, 0x58c1663d, 0x558240e4, 0x51435d53,
		0x251d3b9e, 0x21dc2629, 0x2c9f00f0, 0x285e1d47,
		0x36194d42, 0x32d850f5, 0x3f9b762c, 0x3b5a6b9b,
		0x0315d626, 0x07d4cb91, 0x0a97ed48, 0x0e56f0ff,
		0x1011a0fa, 0x14d0bd4d, 0x19939b94, 0x1d528623,
		0xf12f560e, 0xf5ee4bb9, 0xf8ad6d60, 0xfc6c70d7,
		0xe22b20d2, 0xe6ea3d65, 0xeba91bbc, 0xef68060b,
		0xd727bbb6, 0xd3e6a601, 0xdea580d8, 0xda649d6f,
		0xc423cd6a, 0xc0e2d0dd, 0xcda1f604, 0xc960ebb3,
		0xbd3e8d7e, 0xb9ff90c9, 0xb4bcb610, 0xb07daba7,
		0xae3afba2, 0xaafbe615, 0xa7b8c0cc, 0xa379dd7b,
		0x9b3660c6, 0x9ff77d71, 0x92b45ba8, 0x9675461f,
		0x8832161a, 0x8cf30bad, 0x81b02d74, 0x857130c3,
		0x5d8a9099, 0x594b8d2e, 0x5408abf7, 0x50c9b640,
		0x4e8ee645, 0x4a4ffbf2, 0x470cdd2b, 0x43cdc09c,
		0x7b827d21, 0x7f436096, 0x7200464f, 0x76c15bf8,
		0x68860bfd, 0x6c47164a, 0x61043093, 0x65c52d24,
		0x119b4be9, 0x155a565e, 0x18197087, 0x1cd86d30,
		0x029f3d35, 0x065e2082, 0x0b1d065b, 0x0fdc1bec,
		0x3793a651, 0x3352bbe6, 0x3e119d3f, 0x3ad08088,
		0x2497d08d, 0x2056cd3a, 0x2d15ebe3, 0x29d4f654,
		0xc5a92679, 0xc1683bce, 0xcc2b1d17, 0xc8ea00a0,
		0xd6ad50a5, 0xd26c4d12, 0xdf2f6bcb, 0xdbee767c,
		0xe3a1cbc1, 0xe760d676, 0xea23f0af, 0xeee2ed18,
		0xf0a5bd1d, 0xf464a0aa, 0xf9278673, 0xfde69bc4,
		0x89b8fd09, 0x8d79e0be, 0x803ac667, 0x84fbdbd0,
		0x9abc8bd5, 0x9e7d9662, 0x933eb0bb, 0x97ffad0c,
		0xafb010b1, 0xab710d06, 0xa6322bdf, 0xa2f33668,
		0xbcb4666d, 0xb8757bda, 0xb5365d03, 0xb1f740b4
	};


	private byte[]	m_abHeader;
	private int		m_nHeaderLength;
	private byte[]	m_abBody;
	private int		m_nBodyLength;


	public Page()
	{
	}

	/**
	 * Dummy method to make it compatible with native vorbis Page class
	 */
	public void free() {
		// nothing
	}


	/** Obtains the stream structure version.  This method returns the
		stream structure version this page is encoded in. For the
		current ogg version, this is always zero.

		@return the stream structure version
	 */
	public int getVersion()
	{
		return m_abHeader[4] & 0xFF;
	}


	/** Returns if the page is continued.
		This method returns whether this page starts with a packet that is
		continued from the previous page.

		@return true if the page starts with a continued packet.
	 */
	public boolean isContinued()
	{
		return (m_abHeader[5] & 0x01) != 0;
	}



	/** Calls ogg_page_packets().
	 */
/* returns the number of packets that are completed on this page (if
   the leading packet is begun on a previous page, but ends on this
   page, it's counted */

/* NOTE:
If a page consists of a packet begun on a previous page, and a new
packet begun (but not completed) on this page, the return will be:
  ogg_page_packets(page)   ==1, 
  ogg_page_continued(page) !=0

If a page happens to be a single packet that was begun on a
previous page, and spans to the next page (in the case of a three or
more page packet), the return will be: 
  ogg_page_packets(page)   ==0, 
  ogg_page_continued(page) !=0
*/
	public int getPackets()
	{
		int n = m_abHeader[26] & 0xFF;
		int count = 0;
		for (int i = 0; i < n; i++)
			if ((m_abHeader[27 + i] & 0xFF) < 255)
				count++;
		return count;
	}



	/** Returns the "begin of stream" property of the page.  This
		method returns true if the page is the first page of the
		stream.

		@return true if the page is the first in the stream, false
		otherwise
	 */
	public boolean isBos()
	{
		return (m_abHeader[5] & 0x02) != 0;
	}



	/** Returns the "end of stream" property of the page.  This method
		returns true if the page is the last page of the stream.

		@return true if the page is the last in the stream, false
		otherwise
	 */
	public boolean isEos()
	{
		return (m_abHeader[5] & 0x04) != 0;
	}



	/** Obtains the granule position property of the page.  This
		method returns the granule position of the page as stored in
		the page header.

		@return the granule position of the page
	 */
	public long getGranulePos()
	{
		long granulepos = m_abHeader[13]&(0xff);
		granulepos = (granulepos<<8)|(m_abHeader[12] & 0xFF);
		granulepos = (granulepos<<8)|(m_abHeader[11] & 0xFF);
		granulepos = (granulepos<<8)|(m_abHeader[10] & 0xFF);
		granulepos = (granulepos<<8)|(m_abHeader[9] & 0xFF);
		granulepos = (granulepos<<8)|(m_abHeader[8] & 0xFF);
		granulepos = (granulepos<<8)|(m_abHeader[7] & 0xFF);
		granulepos = (granulepos<<8)|(m_abHeader[6] & 0xFF);
		return granulepos;
	}



	/** Obtain the serial number of the stream the page belongs to.
		This method returns the stream serial number stored in the
		page header.

		@return the stream serial number
	 */
	public int getSerialNo()
	{
		return m_abHeader[14] |
			(m_abHeader[15] << 8) |
			(m_abHeader[16] << 16) |
			(m_abHeader[17] << 24);
	}



	/** Calls ogg_page_pageno().
	 */
	public int getPageNo()
	{
		return m_abHeader[18] |
			(m_abHeader[19] << 8) |
			(m_abHeader[20] << 16) |
			(m_abHeader[21] << 24);
	}



	/** Calculate and set the checksum of the page.
	 */
	public void setChecksum()
	{
		int crc_reg = 0;

		/* safety; needed for API behavior, but not framing code */
		m_abHeader[22] = 0;
		m_abHeader[23] = 0;
		m_abHeader[24] = 0;
		m_abHeader[25] = 0;

		for(int i = 0; i < m_nHeaderLength; i++)
			crc_reg = (crc_reg << 8) ^ crc_lookup[((crc_reg >>> 24) & 0xff) ^ (m_abHeader[i] & 0xFF)];
		for(int i = 0; i < m_nBodyLength; i++)
			crc_reg = (crc_reg << 8) ^ crc_lookup[((crc_reg >>> 24) & 0xff) ^ (m_abBody[i] & 0xFF)];
    
		m_abHeader[22] = (byte) (crc_reg & 0xff);
		m_abHeader[23] = (byte) ((crc_reg >> 8) & 0xff);
		m_abHeader[24] = (byte) ((crc_reg >> 16) & 0xff);
		m_abHeader[25] = (byte) ((crc_reg >> 24) & 0xff);
	}



	/** Obtain the header of the page.
	 */
	public byte[] getHeader()
	{
		byte[] abHeader = new byte[m_nHeaderLength];
		System.arraycopy(m_abHeader, 0, abHeader, 0, m_nHeaderLength);
		return abHeader;
	}


	/** Obtain the body of the page.

	
	 */
	public byte[] getBody()
	{
		byte[] abBody = new byte[m_nBodyLength];
		System.arraycopy(m_abBody, 0, abBody, 0, m_nBodyLength);
		return abBody;
	}



	public void setData(byte[] abHeader, int nHeaderOffset,
						int nHeaderLength,
						byte[] abBody, int nBodyOffset,
						int nBodyLength)
	{
		m_abHeader = new byte[nHeaderLength];
		System.arraycopy(abHeader, nHeaderOffset, m_abHeader, 0, nHeaderLength);
		m_nHeaderLength = nHeaderLength;
		m_abBody = new byte[nBodyLength];
		System.arraycopy(abBody, nBodyOffset, m_abBody, 0, nBodyLength);
		m_nBodyLength = nBodyLength;
	}
}





/*** Page.java ***/
