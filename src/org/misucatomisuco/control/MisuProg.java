package org.misucatomisuco.control;

import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileOutputStream;

import org.misucatomisuco.model.MisuInitSongs;
import org.misucatomisuco.model.MisuScale;
import org.misucatomisuco.model.MisuSong;
import org.misucatomisuco.view.MisuPanel;
import org.misucatomisuco.view.MisuPanelString;

public class MisuProg {
	MisuOutMulti mm;
	MisuMidi midiIn;
	MisuInOSC oscIn;
	
	MisuSong[] songs;
	MisuPanel[] pans;
	
	CMisuHeartBeat heartbeat; 	// heartbeat
	int tHb=50;			// ms

	int nchord = 8;
	int[] onnote = new int[nchord];
	int[] progCh = { 1 };
	
	int channel=3;
	int channel2=2;
//	HashMap<Integer,Integer[]> onchord=new HashMap<Integer,Integer[]>();

	int cp = 0; // current program
	int cr = 0; // current riff
	int np = 10; // number of programs
	
	int rcvTrans=12;
	int chordTrans=12;

	public MisuProg() {
		super();
	}
	public MisuProg(MisuPanelString[] w) {
		super();
		pans = w;
		setup();
	}
	void setup() {
		mm=new MisuOutMulti();
		
		midiIn=new MisuMidi(this);
				
		MisuInitSongs mi=new MisuInitSongs();
		songs=mi.get();
		
		pans[0].setTitle(songs[cp].getName());
		chord2panel();
		
//		hb=new MisuHeartBeat(pans);
//		hb.start();
		
	}
	
	
	public void setProgCh(int[] progCh) {
		this.progCh = progCh;
	}
	
	public void setHeartbeat(CMisuHeartBeat h) {
		heartbeat=h;
		heartbeat.start();
	}

	public IMisuOut getIO() {
		return mm;
	}

	public void cc(int cc, int v) {
//		System.out.println("cc"+v);
//		int c = v * (songs[cp].getST().length - 1) / 127;
//		updChord(c);
		mm.cc(channel, cc, v);
	}

	public void pc(int p) {
		doPC(p);
	}
	
	public void rcvChord(Integer base, Integer[] scale, Integer[] chord) {
		int[] sc=new int[scale.length];
		for(int i=0;i<sc.length;i++) {
			sc[i]=scale[i];
		}
		int[] ch=new int[chord.length];
		for(int i=0;i<ch.length;i++) {
			ch[i]=chord[i];
		}
		rcvChord(base, sc, ch);
	}
	
	public void rcvChord(int base, int[] scale, int[] chord) {
		cr=0;
		MisuScale s=new MisuScale(base,scale,chord);
		songs[cp].setSTi(s,cr);
		chord2panel();
	}

	synchronized public void updChord(int c) {
		cr=c;
		MisuScale s=songs[cp].getSTi(cr);
		mm.scale(s.getBase(), s.getScale(), s.getChord());
		stopChord();
		chord2panel();
		playChord();
	}

	private synchronized void playChord() {
//		int[] chord=pans[0].getScale().getChord();
//		
//		onnote[0]=pans[0].getScale().getBase()+chordTrans;
//		mm.noteOn(channel2, onnote[0], 127);
//		for (int i = 1; i < onnote.length ; i++) {
//			if(i<=chord.length) {
//				if (i < pans[0].getScale().getLenth()) {
//					onnote[i] = pans[0].getScale().getBase()+chord[i-1]+chordTrans;
//					mm.noteOn(channel2, onnote[i], 127);
//				}
//			} else {
//				onnote[i]=-1;
//			}
//		}
	}

	synchronized void stopChord(int c) {
		if(c==cr) {
			stopChord();
		}
	}
	
	synchronized void stopChord() {
		for (int i = 0; i < nchord; i++) {
			if (onnote[i] >= 0) {
				mm.noteOff(channel2, onnote[i], 0);
				onnote[i] = -1;
			}
		}
	}

	public void chord2panel() {
		for (int i = 0; i < pans.length; i++) {
			if(pans[i] instanceof MisuPanelString) {
				MisuPanelString p=(MisuPanelString)pans[i];
				p.setScale(songs[cp].getSTi(cr));
				pans[i].repaint();
			}
		}
	}

	public void setBase(int b) {
		stopChord();
		songs[cp].getSTi(cr).setBase(b);
		playChord();
		repaintAll();
	}

	public void setRange(int r) {
		songs[cp].getSTi(cr).setRange(r);
		repaintAll();
	}

	public void setScale(int s) {
		stopChord();
		songs[cp].getSTi(cr).setScaleN(s);
		playChord();
		repaintAll();
	}

	void repaintAll() {
		for (int i = 0; i < pans.length; i++) {
			pans[i].repaint();
		}
	}

	public void inc() {
		if (cp < np - 1) {
			cp++;
			stopChord();
			chord2panel();
			pans[0].setTitle(songs[cp].getName());
			doPC(songs[cp].getP());
		}
	}

	public void dec() {
		if (cp > 0) {
			cp--;
			stopChord();
			chord2panel();
			pans[0].setTitle(songs[cp].getName());
			doPC(songs[cp].getP());
		}
	}

	public void setProg(int p) {
		songs[cp].setP(p);
		doPC(songs[cp].getP());
	}
	
	public void setSong(int s) {
		if(s<songs.length) {
			cp=s;
			stopChord();
			chord2panel();
			pans[0].setTitle(songs[cp].getName());
			doPC(songs[cp].getP());
		}
	}
	
	private void doPC(int p) {
		for (int i = 0; i < this.progCh.length; i++) {
			mm.pc(progCh[i], p);
		}
	}

	public void saveSongs() {
		try {
			File old=new File("./songs.xml");
			File newf=new File("./songs.xml.bck");
			old.renameTo(newf);
			
			FileOutputStream os = new FileOutputStream("./songs.xml");
			XMLEncoder encoder = new XMLEncoder(os);
//			for (int i = 0; i < np; i++) {
//				encoder.writeObject(S[i]);
//			}
			encoder.writeObject(songs);
			encoder.close();
			os.close();
			System.out.println("songs.xml written");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public MisuPanel[] getPans() {
		return pans;
	}
	public void setPans(MisuPanel[] pans) {
		this.pans = pans;
		System.out.println("MisuProg set pans");
		setup();
	}
	
	public MisuOutMulti getMm() {
		return mm;
	}
	public void setMm(MisuOutMulti mm) {
		this.mm.setIos(mm.getIos());
	}
	public int gettHb() {
		return tHb;
	}
	public void settHb(int tHb) {
		this.tHb = tHb;
		heartbeat.setHeartbeat(tHb);
	}
}