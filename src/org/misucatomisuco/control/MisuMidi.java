/*
 * MisuMidi.java
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

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;
import javax.sound.midi.MidiDevice.Info;

public class MisuMidi implements IMisuOut {
	Transmitter trans;
	Receiver rcvr;
	Synthesizer synth;
	MidiDevice mout;
	MidiDevice minp;
	MisuInMidi mire;
	// int ch; //default channel

	boolean midiOutInit = false;
	boolean midiInInit = false;
	String intIn;
	String intOut;

	public MisuMidi() {
		Info mdi[] = MidiSystem.getMidiDeviceInfo();
		
		for (int i = 0; i < mdi.length; i++) {
			int rcv = 0;
			String[] out={"LoopBe Internal MIDI","Java Sound Synthesizer","Microsoft GS Wavetable Synth"};
//			String out="Out To MIDI Yoke:  1";
			
			try {
				MidiDevice md = MidiSystem.getMidiDevice(mdi[i]);
				System.out.print(mdi[i].getName());
				rcv = md.getMaxReceivers();
			} catch (MidiUnavailableException e) {
				e.printStackTrace();
			}

			for(int j=0;j<out.length;j++) {
				if(mdi[i].getName().contains(out[j]) && rcv==-1) {
					intOut=mdi[i].getName();
					initMidiOut(mdi[i]);
				}
			}
		}
	}

	public MisuMidi(MisuProg p ) {
		Info mdi[] = MidiSystem.getMidiDeviceInfo();
		
		for (int i = 0; i < mdi.length; i++) {
			int trn = 0;
			String[] in={"LPD8 [hw:1,0]","e2 [hw:1,0]","USB Audio Device","nanoPAD"};
			
			try {
				MidiDevice md = MidiSystem.getMidiDevice(mdi[i]);
				System.out.print(mdi[i].getName());
				trn = md.getMaxTransmitters();
			} catch (MidiUnavailableException e) {
				e.printStackTrace();
			}

			for(int j=0;j<in.length;j++) {
				if (mdi[i].getName().contains(in[j]) && trn==-1 ) {
					intIn=mdi[i].getName();
					initMidiIn(mdi[i],p);
				}
			}
		}
	}

	public void noteOn(int c, int f, int v) {
		// ch=c;
		ShortMessage m;
		f = f > 127 ? 127 : f;
		f = f < 0 ? 0 : f;
		m = new ShortMessage();
		try {
			m.setMessage(ShortMessage.NOTE_ON, c, f, v);
		} catch (InvalidMidiDataException e1) {
			e1.printStackTrace();
		}
		rcvr.send(m, -1);
	}

	public void noteOff(int c, int f, int v) {
		ShortMessage m;
		// ch=c;
		f = f > 127 ? 127 : f;
		f = f < 0 ? 0 : f;
		m = new ShortMessage();
		try {
			m.setMessage(ShortMessage.NOTE_OFF, c, f, v);
		} catch (InvalidMidiDataException e1) {
			e1.printStackTrace();
		}
		rcvr.send(m, -1);
	}

	public void pitch(int c, int f) {
		ShortMessage m;
		// ch=c;
		m = new ShortMessage();
		f+=64;
		try {
			m.setMessage(ShortMessage.PITCH_BEND, c, f, f);
		} catch (InvalidMidiDataException e1) {
			e1.printStackTrace();
		}
		rcvr.send(m, -1);
	}

	public void pc(int c, int p) {
		ShortMessage m;
		// ch=c;
		m = new ShortMessage();
		try {
			m.setMessage(ShortMessage.PROGRAM_CHANGE, c, p, 0);
		} catch (InvalidMidiDataException e1) {
			e1.printStackTrace();
		}
		rcvr.send(m, -1);
	}

	public void mod(int c, int p) {
		ShortMessage m;
		// ch=c;
		m = new ShortMessage();
		p = p > 127 ? 127 : p;
		p = p < 0 ? 0 : p;
		try {
			m.setMessage(ShortMessage.CONTROL_CHANGE, c, 1, p);
		} catch (InvalidMidiDataException e1) {
			e1.printStackTrace();
		}
		rcvr.send(m, -1);
	}

	public void cc(int c, int cc, int p) {
		ShortMessage m;
		// ch=c;
		m = new ShortMessage();
		p = p > 127 ? 127 : p;
		p = p < 0 ? 0 : p;
		try {
			m.setMessage(ShortMessage.CONTROL_CHANGE, c, cc, p);
		} catch (InvalidMidiDataException e1) {
			e1.printStackTrace();
		}
		rcvr.send(m, -1);
	}

	public void initMidiOut(Info mo) {
		try {
			if (midiOutInit) {
				// trans.close();
				// rcvr.close();
				mout.close();
			} else {
				midiOutInit = true;
			}
			mout = MidiSystem.getMidiDevice(mo);
			mout.open();
			rcvr = mout.getReceiver();

		} catch (MidiUnavailableException e1) {
			e1.printStackTrace();
		}
		// System.out.println("new midi out");

	}

	public void initMidiIn(Info mi, MisuProg mp) {
		try {
			if (midiInInit) {
				// trans.close();
				// rcvr.close();
				minp.close();
			} else {
				midiInInit = true;
			}
			minp = MidiSystem.getMidiDevice(mi);
			minp.open();
			trans = minp.getTransmitter();
			mire = new MisuInMidi(mp,intIn);
			trans.setReceiver(mire);
		} catch (MidiUnavailableException e1) {
			e1.printStackTrace();
		}
		// System.out.println("new midi out");
	}

	//@Override
	public void scale(int base, int[] scale, int[] chord) {
		// TODO Auto-generated method stub
		
	}
}
