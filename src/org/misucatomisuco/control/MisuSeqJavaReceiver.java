package org.misucatomisuco.control;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

public class MisuSeqJavaReceiver implements Receiver {

	CMisuHeartBeat hb;
	long ts;
	long tsys;
	
	public MisuSeqJavaReceiver(CMisuHeartBeat hb) {
		super();
		this.hb=hb;
	}

	public void close() {
		
	}

	public void send(MidiMessage message, long timeStamp) {
//		System.out.println("send "+message.getStatus()+" "+ShortMessage.NOTE_ON);
		if(message.getStatus()==ShortMessage.NOTE_ON) {
			hb.advance();
			long tsysnow=System.currentTimeMillis();
//			System.out.println(timeStamp-ts+" "+(tsysnow-tsys));
			ts=timeStamp;
			tsys=tsysnow;
		}		
	}
}
