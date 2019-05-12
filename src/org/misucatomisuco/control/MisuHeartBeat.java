package org.misucatomisuco.control;

import org.misucatomisuco.view.MisuFrame;
import org.misucatomisuco.view.MisuPanel;


public class MisuHeartBeat extends Thread implements CMisuHeartBeat {
	
	MisuPanel[] pans=null;
	MisuSeqPlayer[] player;
	MisuFrame frame;
	int heartbeat=50;	// loop delay in ms
	
	public MisuHeartBeat(MisuSeqPlayer[] p, MisuFrame f,MisuPanel[] pans) {
		super();
		player=p;
		frame=f;
		this.pans = pans;
	}
	public MisuHeartBeat(MisuSeqPlayer[] p, MisuFrame f) {
		super();
		player=p;
		frame=f;
	}

 
	public void run() {
//		long t1=System.currentTimeMillis();
		while(true) {
			try {
				advance();
				sleep(heartbeat);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public int getHeartbeat() {
		return heartbeat;
	}
	public void setHeartbeat(int heartbeat) {
		this.heartbeat = heartbeat;
		for(MisuSeqPlayer p:player) {
			p.setHeartbeat(heartbeat);
		}
		if(pans!=null) {
			pans[0].setHeartbeat(heartbeat);
		}
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
}
