/*
 *	MidiFileReader.java
 *
 *	This file is part of Tritonus: http://www.tritonus.org/
 */

/*
 *  Copyright (c) 1999 by Matthias Pfisterer
 *
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
 *
 */

/*
|<---            this code is formatted to fit into 80 columns             --->|
*/

package javax.sound.midi.spi;

import java.io.InputStream;
import java.io.IOException;
import java.io.File;

import java.net.URL;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiFileFormat;
import javax.sound.midi.Sequence;




public abstract class MidiFileReader
{
    public abstract MidiFileFormat getMidiFileFormat(InputStream inputStream)
	throws InvalidMidiDataException, IOException;
    public abstract MidiFileFormat getMidiFileFormat(URL url)
	throws InvalidMidiDataException, IOException;
    public abstract MidiFileFormat getMidiFileFormat(File file)
	throws InvalidMidiDataException, IOException;
    public abstract Sequence getSequence(InputStream inputStream)
	throws InvalidMidiDataException, IOException;
    public abstract Sequence getSequence(URL url)
	throws InvalidMidiDataException, IOException;
    public abstract Sequence getSequence(File file)
	throws InvalidMidiDataException, IOException;
}



/*** MidiFileReader.java ***/
