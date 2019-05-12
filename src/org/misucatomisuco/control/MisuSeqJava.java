package org.misucatomisuco.control;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import org.misucatomisuco.view.MisuFrame;
import org.misucatomisuco.view.MisuPanel;

public class MisuSeqJava implements CMisuHeartBeat {
	Sequencer s;
	Sequence sequence;
	Track t;
	Receiver out;
	
	MisuPanel[] pans=null;
	MisuSeqPlayer[] player;
	MisuFrame frame;
	
	public MisuSeqJava(MisuSeqPlayer[] p, MisuFrame f,MisuPanel[] pans) {
		super();
		
		this.pans=pans;
		this.player=p;
		this.frame=f;
		
		try {
			s=MidiSystem.getSequencer();
		} catch (MidiUnavailableException e1) {
			e1.printStackTrace();
		}
		try {
			s.getTransmitter().setReceiver(new MisuSeqJavaReceiver(this));
		} catch (MidiUnavailableException e1) {
			e1.printStackTrace();
		}
		
        try {
			sequence = new Sequence(Sequence.PPQ, 640);
		} catch (InvalidMidiDataException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			s.setSequence(sequence);
		} catch (InvalidMidiDataException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		t=sequence.createTrack();
		for(long i=0;i<160;i++) {
			ShortMessage shm=new ShortMessage();
			try {
				shm.setMessage(ShortMessage.NOTE_ON, 0, 65, 60 );
			} catch (InvalidMidiDataException e) {
				e.printStackTrace();
			}
			MidiEvent meve=new MidiEvent(shm,i*4);
			t.add(meve);
			
			shm=new ShortMessage();
			try {
				shm.setMessage(ShortMessage.NOTE_OFF, 0, 65, 0 );
			} catch (InvalidMidiDataException e) {
				e.printStackTrace();
			}
			MidiEvent meve2=new MidiEvent(shm,i*4+2);
			t.add(meve2);
		}
		
		s.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
		s.setLoopEndPoint(t.ticks());
		s.setMasterSyncMode(Sequencer.SyncMode.INTERNAL_CLOCK);
		s.setTempoInBPM(180);
		
		System.out.println(s.getLoopStartPoint());
		System.out.println(s.getLoopEndPoint());
		System.out.println(s.getTempoInBPM());
		System.out.println(s.getMasterSyncMode());
		try {
			s.open();
			s.start();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
	}

	public Receiver getOut() {
		return out;
	}

	public void setOut(Receiver out) {
		this.out = out;
		s.stop();
		try {
			s.getTransmitter().setReceiver(out);
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
		s.start();
	}
	
	public void start() {
		s.start();
	}

	public void advance() {
		if(pans!=null) {
			for(MisuSeqPlayer p:player) {
				p.setResolution(pans[0].getHeight());
			}
		}
		for(MisuSeqPlayer p:player) {
			p.advance();
		}
		frame.repaint();
	}

	public int getHeartbeat() {
		return (int) s.getTempoInBPM();
	}

	public void setHeartbeat(int heartbeat) {
		s.setTempoInBPM(heartbeat);
	}	

}
