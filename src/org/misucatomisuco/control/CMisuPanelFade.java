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

import org.misucatomisuco.model.MisuCcMapItem;
import org.misucatomisuco.view.MisuPanel;

public class CMisuPanelFade implements ICMisuPanel {
	static long lastevent;

	MisuPanel w;
	
	int c=1;		//channel
	int vsens=20;	//speed sensitivity
	boolean vertical=false;
	IMisuOut mm;
	int px,py,vx,vy=0;
	
	int[] ccprev;
	MisuCcMapItem[] ccmap;
	
	public CMisuPanelFade(IMisuOut mm2, int c, MisuCcMapItem[] ccmap) {
		super();
		this.c=c;
		this.mm = mm2;
		this.w = null;
		if(ccmap!=null) {
			this.ccmap=ccmap;
			ccprev=new int[ccmap.length];
		}
		lastevent=System.nanoTime();
	}
	
	public CMisuPanelFade(IMisuOut mm2, int c) {
		this(mm2, c, MisuCcvalConf.ccval[c-1]);
	}

	public void mouseMoved(MouseEvent e) {
		px=py=0;
	}
	
	public void mouseDragged(MouseEvent e) {
		
		if(e.getX()>=0 && e.getY()>=0 && e.getX()<=w.getWidth() && e.getY()<=w.getHeight()) {
			mousePressed(e);
		}
	}

	//@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	//@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	//@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	//@Override
	public void mousePressed(MouseEvent e) {
		
		px=e.getX();
		py=e.getY();
		
		int faderNo=e.getX() / w.getWbar();
		
		if(faderNo<ccmap.length ) {
			int ccv = ccmap[faderNo].getMin() + Math.round(e.getY()  * (ccmap[faderNo].getMax()-ccmap[faderNo].getMin()) / w.getHeight());
			if(ccprev[faderNo]!=ccv) {
				if(vertical) {
					w.setCcval(faderNo, py);
				} else {
					w.setCcval(faderNo, px);
				}
				mm.cc(c, ccmap[faderNo].getCc(), ccv);
				ccprev[faderNo]=ccv;
			}
		}
		w.setCaption(true);
		w.repaint();
	}

	//@Override
	public void mouseReleased(MouseEvent e) {
//		w.setCaption(false);
		mouseMoved(e);
	}

//	public void setMisuPanelFade(MisuPanelFade p) {
//		this.w=p;
//	}

//	public void setMisuPanel(MisuPanelString misuPanelString) {
//		// TODO Auto-generated method stub
//		
//	}

//	public void setPix(int i, int j) {
//		// TODO Auto-generated method stub
//		
//	}

	public void setMisuPanel(MisuPanel misuPanel) {
		w=misuPanel;		
	}

	public int getPitch() {
		return 0;
	}

	public int getOnnote() {
		return 0;
	}

	public void setVertical(boolean vertical) {
		this.vertical = vertical;
	}
	
}
