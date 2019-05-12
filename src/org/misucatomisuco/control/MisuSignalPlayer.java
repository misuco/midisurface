package org.misucatomisuco.control;

import org.misucatomisuco.model.MisuSignal;

public class MisuSignalPlayer {
	IMisuOut out;
	
	public MisuSignalPlayer() {
		super();
	}

	public void setOut(IMisuOut out) {
		this.out = out;
	}

	void play(MisuSignal s, int c) {
		if(s!=null) {
			switch(s.type) {
			
			case MisuSignal.NOTE_ON:
				out.noteOn(c, s.v1, s.v2);
				break;
				
			case MisuSignal.NOTE_OFF:
				out.noteOff(c, s.v1, s.v2);
				break;
				
			case MisuSignal.PITCH:
				out.pitch(c, s.v1);
				break;
				
			case MisuSignal.CC:
				out.cc(c, s.v1, s.v2);
				break;
			
			}
		}
	}

}
