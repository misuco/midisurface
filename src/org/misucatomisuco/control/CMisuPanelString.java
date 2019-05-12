/*
 * CMisuWindow.java
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

import java.awt.event.MouseEvent;

import org.misucatomisuco.model.MisuSeqGrid;
import org.misucatomisuco.model.MisuSignal;
import org.misucatomisuco.view.MisuPanel;

public class CMisuPanelString implements ICMisuPanel {
	 
	MisuPanel w;			// Gui Object
	IMisuOut mm;			// I/O object
	MisuSeqGrid seqgrid;	// Sequencer grid to store previous positions
	MisuSignal lastSig;		// last sent Signal
	
	long lastevent;			// timestamp of last event
	int onnote;				// current note
	int c=1;				// channel
	int vsens=512;			// speed sensitivity
	int px,py,vx,vy=0;		// position, position-difference
	
	int cv=127;				// current speed value 0..127
	int cv1=127;			// previous speed value 0..127
	
	private int pitch=0;	// current pitch value 
	
	public CMisuPanelString(IMisuOut mm2, int c) {
		super();
		this.c=c;
		this.mm = mm2;
		this.w = null;
		this.onnote=-1;
		lastevent=System.nanoTime();
		
		System.out.println("Created CMisuPanelString");
	}

	public void mouseMoved(MouseEvent e) {
		if (onnote >= 0) {
			mm.noteOff(c, onnote, 0);
			seqgrid.addPoint(e.getWhen(), px, py, new MisuSignal(MisuSignal.NOTE_OFF,onnote,0));
			onnote = -1;
		}
		px=py=0;
	}
	
	public void mouseDragged(MouseEvent e) {
		if(e.getX()>=0 && e.getY()>=0 && e.getX()<=w.getWidth() && e.getY()<=w.getHeight()) {
			// detect direction change
			int vxn=e.getX()-px;
			
			// speed calculation
			int cvn=vsens*Math.abs(vxn)/w.getWbar();
			cvn=cvn>127?127:cvn;
			
//			Method 2: peek in velocity			
//			if(cv1>cv && cvn<cv && cv1-cv>10) {
//				mm.noteOff(c, onnote, 0);
//				mm.noteOn(c, onnote, cvn);
//			}
//			cv1=cv;
			cv=cvn;

//			Method 1: direction change			
//			if(vxn*vx<0) {
//				mm.noteOff(c, onnote, 0);
//				mm.noteOn(c, onnote, cv);
//			}
			
			vy=e.getY()-py;
			vx=vxn;
			w.setVx(cv);
			
			mousePressedMoved(e);
		}
	}

	//@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	//@Override
	public void mouseEntered(MouseEvent e) {
//		cv=127; 
//		mousePressedMoved(e);
		
	}

	//@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	//@Override
	public void mousePressed(MouseEvent e) {
		cv=127; 
		mousePressedMoved(e);
	}
		
	public void mousePressedMoved(MouseEvent e) {
		boolean crossingthemiddle=false;
		boolean nop=true;
		
		if(px%w.getWbar() > w.getWbar()/2 && e.getX()%w.getWbar() <= w.getWbar()/2) {
			crossingthemiddle=true;
		} else if(px%w.getWbar() <= w.getWbar()/2 && e.getX()%w.getWbar() > w.getWbar()/2) {
			crossingthemiddle=true;
		}
		
		px=e.getX();
		py=e.getY();
				
		int newnotei=e.getX() / w.getWbar();
		int newnote = onnote;
		if(newnotei<w.getScale().getLenth()) {
			newnote=w.getScale().getNote(newnotei);
		}
		
		int p = (e.getY() - w.getHeight()/2) * 127 / w.getHeight();
		if(p!=pitch) {
			mm.pitch(c, p);
			seqgrid.addPoint(e.getWhen(), px, py, new MisuSignal(MisuSignal.PITCH,p,0));
			nop=false;
			pitch=p;
		}
		
		// play note when crossingthemiddle
		if(crossingthemiddle) {
			if (onnote >= 0) {
				mm.noteOff(c, onnote, 0);
				seqgrid.addPoint(e.getWhen(), px, py, new MisuSignal(MisuSignal.NOTE_OFF,onnote,0));
				nop=false;
			}
			onnote = newnote;
			mm.noteOn(c, onnote, cv);
			seqgrid.addPoint(e.getWhen(), px, py, new MisuSignal(MisuSignal.NOTE_ON,onnote,cv));
			nop=false;
		}
		
		// play note when changing the field
		if(newnote!=onnote) {
			if (onnote >= 0) {
				mm.noteOff(c, onnote, 0);
				seqgrid.addPoint(e.getWhen(), px, py, new MisuSignal(MisuSignal.NOTE_OFF,onnote,0));
				nop=false;
			}
			onnote = newnote;
			mm.noteOn(c, onnote, cv);
			seqgrid.addPoint(e.getWhen(), px, py, new MisuSignal(MisuSignal.NOTE_ON,onnote,cv));
			nop=false;
		}
		
		if(nop) {
			seqgrid.addPoint(e.getWhen(), px, py, null);
		}
		
		w.setCaption(true);
		w.repaint();
	}

	//@Override
	public void mouseReleased(MouseEvent e) {
		w.setCaption(false);
		mouseMoved(e);
	}

	public void setMisuPanel(MisuPanel p) {
		this.w=p;
	}

	public void setSeqgrid(MisuSeqGrid seqgrid) {
		this.seqgrid = seqgrid;
	}

	public int getPitch() {
		return pitch;
	}

	public int getOnnote() {
		return onnote;
	}
}
