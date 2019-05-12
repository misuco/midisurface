/*
 * MisuWindow.java
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
package org.misucatomisuco.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import org.misucatomisuco.control.ICMisuPanel;
import org.misucatomisuco.control.CMisuPanelFade;
import org.misucatomisuco.control.IMisuOut;
import org.misucatomisuco.control.MisuCcvalConf;
import org.misucatomisuco.model.MisuCcMapItem;

public class MisuPanelFade extends MisuPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6078595853715288452L;

	MisuCcMapItem[] ccmap;
	int[] ccval; 			// cc values on bars
	boolean vertical=false;
	
	public MisuPanelFade() {
		super();
		ccmap=MisuCcvalConf.ccval[0];
		ccval = new int[ccmap.length];
	}
	
	public MisuPanelFade(MisuCcMapItem[] cc ) {
		super();
		ccmap=cc;
		ccval = new int[cc.length];
	}
	
	public MisuPanelFade(int ch, IMisuOut mm ) {
		super();
		ccmap=MisuCcvalConf.ccval[ch-1];
		ccval = new int[ccmap.length];
		setControl(new CMisuPanelFade(mm, ch, ccmap));
	}
	
	public void setControl(ICMisuPanel cmw) {
		cmw.setMisuPanel(this);
		control=cmw;
		addMouseMotionListener(cmw);
		addMouseListener(cmw);
	}
	
//	public void setControl(CMisuPanelFadeInternal cmw) {
//		cmw.setMisuPanelFade(this);
//		addMouseMotionListener(cmw);
//		addMouseListener(cmw);
//	}
	
	public void rotate() {
		col.rotate();
	}
	
	public void paint(Graphics g1) {
		int w = getWidth();
		int h = getHeight();
		
		rotate();

		Image offscreen = createImage(w, h);
		Graphics g = offscreen.getGraphics();

		g.setColor(getBackground());
		g.fillRect(0, 0, w, h);

		g.setFont(new Font("Verdana", 10, 10));
		int sl = ccmap.length;
		if (sl > 0) {
			if(vertical) {
				wbar = w / sl;
			} else {
				wbar = h / sl;
			}
			

			for (int i = 0; i < sl; i++) {

				float sat = 0.6f;

				g.setColor(Color.getHSBColor(col.colstart + (i * col.colrange/ sl), 1f, sat));
				if(vertical) {
					g.fillRoundRect(i * wbar, 0, wbar + 15, h, 15, 15);
				} else {
					g.fillRoundRect(0, i * wbar,  w, wbar + 15, 15, 15);
				}
				
				// ????
				g.setColor(Color.WHITE);
				int npos=ccmap[i].getMax()-ccmap[i].getMin();
				int hbar=w/npos;
				
//				// cutoff if more events to display than confortable
//				if(hbar<5) {
//					hbar=5;
//					npos=w/hbar;
//				}
				
				if(vertical) {
					hbar=h/npos;
				}
				
				for(int j=0;j<npos;j++) {
					int yg=j*h/npos;
					int xg=j*w/npos;
					if(vertical) {
						g.drawLine(i*wbar, yg, (i+1)*wbar, yg);
					} else {
						g.drawLine(xg, i*wbar, xg, (i+1)*wbar);
					}
				}

				// paint the controller bar
				if (vertical) {
					g.setColor(Color.getHSBColor(col.colstart + (i * col.colrange/ sl), 1f, 1f));
					g.fill3DRect(i * wbar, ccval[i], wbar, hbar, true);
					g.setColor(Color.BLACK);
					g.drawString(ccmap[i].getName()+" "+ccval[i]/hbar, i*wbar, ccval[i]);
				} else {
//					int xpos=ccval[i];
					int xpos=ccval[i];
					g.setColor(Color.getHSBColor(col.colstart + (i * col.colrange/ sl), 1f, 1f));
					g.fill3DRect(xpos, i * wbar, hbar, wbar, true);
					g.setColor(Color.BLACK);
					g.drawString(ccmap[i].getName()+" "+ccval[i]/hbar, xpos, (int)((i+0.5)*wbar));
				}
			}
		}
		g.dispose();
		g1.drawImage(offscreen, 0, 0, this);
	}

	public void setCaption(boolean caption) {
		this.caption = caption;
	}

	public int getWbar() {
		return wbar;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setOnnote(int n) {
	}

	public void setPitch(int p) {
	}

	public void setVx(int v) {
	}

	public void setCcval(int i, int v) {
		ccval[i] = v;
	}
	public boolean isVertical() {
		return vertical;
	}

	public void setVertical(boolean vertical) {
		this.vertical = vertical;
	}


}