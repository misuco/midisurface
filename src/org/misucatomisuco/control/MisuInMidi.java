/*
 * MisuMidiReceiver.java
 *
 * Copyright (C) 2009-2010 Claudio Zopfi
 * 
 * Licensed under CC Attribution-Noncommercial-Share Alike 3.0 Germany
 * 
 * See the file license.txt which came with this distribution
 * or http://creativecommons.org/licenses/by-nc-sa/3.0/de/deed.en
 * or http://c1audio.com/by-nc-sa/
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * 
 */
package org.misucatomisuco.control;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

public class MisuInMidi implements Receiver {
	
	MisuProg mp;
	int playchord=0;
	String intIn;

	public MisuInMidi(MisuProg mp, String intIn) {
		super();
		this.mp = mp;
		this.intIn=intIn;
	}

	//@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void send(MidiMessage message, long timeStamp) {
		byte[] m=message.getMessage();
		
//		System.out.print("received ts: "+timeStamp+" msg: ");
//		for(int i=0;i<m.length;i++) {
//			System.out.print(String.valueOf(m[i])+" ");
//		}
//		System.out.println(" ");

		// ribbon controller
		if(intIn.contains("e2 [hw:1,0]") || intIn.contains("USB Audio Device")) {
			if(m[0]==-80 && m[1]==1) {
//				mp.cc(m[1],m[2]);
				if(m[2]==0 || m[2]>120) {
					mp.stopChord();
//					System.out.println("cc"+m[2]+" stop to "+playchord);
					playchord=0;
				} else {
					int pn=m[2]*8/127;
					if(pn!=playchord) {
						playchord=pn;
						mp.updChord(playchord);
//						System.out.println("cc"+m[2]+" upd chord to "+playchord);
					}
				}
			}
			
		}
		// control change
		else if(m[0]==-80) {
			mp.cc(m[1],m[2]);
		}
		
		// program change
		if(m[0]==-64) {
			mp.setSong(m[1]);
		}
		
		// korg pad scene 2
		if (m[0] == -112 && m[1] >= 60 && m[1] < 69) {
			mp.updChord(m[1] - 59);
		}
		if (m[0] == -128 && m[1] >= 60 && m[1] < 69) {
			mp.stopChord();
		}
		
		// Akai lpd8 pad prog 1
		if(m[0]==-112 && m[1]>=36 && m[1]<=43) {
			mp.updChord(m[1]-35);
		}
		if(m[0]==-128 && m[1]>=36 && m[1]<69) {
//			mp.stopChord(m[1]-36);
			mp.stopChord();
		}
	}
}
