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

import org.misucatomisuco.control.CMisuPanelString;
import org.misucatomisuco.control.ICMisuPanel;
import org.misucatomisuco.control.IMisuOut;
import org.misucatomisuco.model.MisuScale;
import org.misucatomisuco.model.MisuSeqGrid;
import org.misucatomisuco.model.MisuSignal;

public class MisuPanelString extends MisuPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7538192620076976939L;

	/**
	 * 
	 */
	MisuScale scale;

	// private int onnote;
	// private int pitch;
	private int vx;
	private int nglow = 30; // number of glowbacks...

	public MisuPanelString() {
		super();
		setup();
	}

	public MisuPanelString(int ch, IMisuOut mm) {
		super();
		setup();
		setControl(new CMisuPanelString(mm, ch));
	}

	public void setControl(ICMisuPanel cmw) {
		System.out.println("MisuPanelString::setControl()");
		control = cmw;
		control.setMisuPanel(this);
		addMouseMotionListener(control);
		addMouseListener(control);
	}

	private void setup() {
		// this.setScale(new MisuScale(40, 2, 0));

		// seqgrid=new MisuSeqGrid(2500);

		// for (int i = 0; i < seqgrid.len; i++) {
		// seqgrid.tx[i] = -1;
		// seqgrid.ty[i] = -1;
		// }
		// for (int i = 0; i < seqgrid.rep1; i++) {
		// seqgrid.tn[i] = 4;
		// }

	}

	public void rotate() {
		col.rotate();
	}

	public void paint(Graphics g1) {

		w = getWidth();
		h = getHeight();

		seqgrid.len = h;
		long systime = System.currentTimeMillis();

		rotate();

		Image offscreen = createImage(w, h);
		Graphics g = offscreen.getGraphics();

		g.setColor(getBackground());
		g.fillRect(0, 0, w, h);

		g.setFont(new Font("Verdana", 10, 10));
		int sl = 0;
		if (scale != null) {
			sl = scale.getLenth();
		}
		if (sl > 0) {
			wbar = w / sl;
			// int t = 0;
			// Color ct = Color.WHITE;
			// int x1=seqgrid.tx[seqgrid.pointer];
			float satmin = 0.2f;
			float colstart = -1;
			col.colstart = -1;
			
			for (int i = 0; i < scale.getLenth(); i++) {
				float sat = satmin;
				
				col.colrange=col.colrangemin;
				col.colstart=colstart;

				for (int j = 0; j < nglow; j++) {
					int k = seqgrid.pointer - j;
					if (k < 0) {
						k += seqgrid.len;
					}
					if (col.colstart == -1 && seqgrid.ts[k] != null
							&& seqgrid.ts[k].type == MisuSignal.NOTE_ON) {
						colstart=(float) seqgrid.ts[k].v1 / 127f;
						col.colstart = colstart;
					}
					if (seqgrid.tx[k] >= i * wbar
							&& seqgrid.tx[k] <= i * wbar + wbar) {
						// sat = (float)(nglow-j)/(float)nglow;

						sat = 1f - (float) (systime - seqgrid.t[k]) / 1000;
						if (sat < satmin) {
							sat = satmin;
						}
						// t = scale.getNote(i);
						// ct = Color.getHSBColor((colstart + (float) (scale
						// .getNote(i) % 12) / 11)
						// * colrange, 1f, sat);
					}
				}

				// if (x1 >= i * wbar && x1 <= i * wbar + wbar) {
				// sat = 1f;
				// // t = scale.getNote(i);
				// // ct = Color.getHSBColor((colstart + (float) (scale
				// // .getNote(i) % 12) / 11)
				// // * colrange, 1f, sat);
				// }
				
				
				
				g.setColor(col.getColorF((float) (scale.getNote(i) % 12) / 11,
						sat));
				g.fillRoundRect(i * wbar, 0, wbar + 15, h, 15, 15);

				col.colrange=1;
				col.colstart=0;
				
				// middle strings
				// g.setColor(Color.getHSBColor(col.collogo, 1, 1));
				g.setColor(col.getColorF((float) (scale.getNote(i) % 12) / 11,
						1));
				g.drawLine((int) ((i + 0.5) * wbar), 0,
						(int) ((i + 0.5) * wbar), h);
				g.setColor(col.getColorF((float) (scale.getNote(i) % 12) / 11,
						0.5f));
				g.drawLine((int) ((i + 0.5) * wbar) - 1, 0,
						(int) ((i + 0.5) * wbar) - 1, h);
				g.drawLine((int) ((i + 0.5) * wbar) + 1, 0,
						(int) ((i + 0.5) * wbar) + 1, h);

			}

			// int s=h/2;
			// // draw the pitch scala
			// g.setColor(Color.WHITE);
			//
			// float p=h/8;
			// for(float i=0;i<32;i+=0.25) {
			// int h2=(int)(Math.sqrt(i)*p);
			// g.drawLine(0, s+h2, w, s+h2);
			// g.drawLine(0, s-h2, w, s-h2);
			// }

			// draw the waves
			// g.setColor(ct);
			// tn[rep1c]=Math.pow(2, (double)(128-t)/12);
			// rep1c++;
			// if(rep1c>=rep1) {
			// rep1c=0;
			// }

			// for(int j=0;j<rep1;j++) {
			// g.setColor(Color.getHSBColor(colwave, 1, (float)j/(float)rep1));
			//
			// int y1=0;
			// for(int i=1;i<w;i++) {
			// int y=(int)(h*0.1*(Math.sin(i/tn[j])))+h/2;
			// g.drawLine(i-1, y1, i, y);
			// y1=y;
			// }

			// -----------------

			// long n1=Math.round(w/tn[j]);
			// long x,x1=0;
			// int a1=0;
			// for(long i=0;i<n1;i++) {
			// int a=0;
			// switch ((int)i%4) {
			// case 1:
			// a=1;
			// break;
			// case 3:
			// a=-1;
			// break;
			// }
			// x=Math.round(i*tn[j]);
			// g.drawLine((int)x, s+s*a, (int)x1, s+s*a1);
			// x1=x;
			// a1=a;
			// }
			// }

			// the cursor
			// g.setColor(Color.WHITE);
			// g.drawOval(x1 - 6, y1 - 6, 12, 12);
			// g.drawOval(x1 - 12, y1 - 12, 24, 24);

			// The caption

			g.setColor(Color.getHSBColor(col.collogo, 1, 1));
			// g.setFont(new Font("Verdana",10,30));
			// String txt="misuca to misuco java6 applet 109";
			// int wl=w/txt.length();
			// for(int i=0;i<txt.length();i++) {
			// g.setFont(new Font("Verdana",10,15+(int)(Math.random()*5)));
			// g.drawString(txt.substring(i, i+1), (i+1)*wl, h/2);
			// }
			int oct = 1 + ((scale.getBase() + 3) / 12);
			g.setFont(new Font("Verdana", 10, 10));
			// g.drawString(title, 10, h - 15);
			g
					.drawString(
							title
									+ " "
									+ scale.getBaseName()
									+ " "
									+ oct
									+ " "
									+ scale.getScaleName()
									+ " |||  misuca to misuco . org  ||| copyright 2010 by c1audio.com |||",
							10, h - 15);
			if (caption) {
				String nona = scale.getNoteName(control.getOnnote());
				double nof = scale.getNoteF(control.getOnnote());
				nof += nof / 12 * control.getPitch() / 64;
				nof = Math.round(nof * 100) / 100;
				g.setColor(Color.getHSBColor(col.colcap, 1, 1));
				g.drawString(nona + " " + nof + " hz " + control.getPitch()
						+ " " + vx, 10, 40);
				g.fill3DRect(0, 0, Math.abs(vx) * w / 127, 10, true);
				// g.drawLine(0, y1, w, y1);
				// g.drawLine(x1, 0, x1, h);
			}
		}

		paintDrawers(g);

		// Image os2=offscreen.getScaledInstance(getWidth(), getHeight(),
		// Image.SCALE_AREA_AVERAGING);
		// g1.drawImage(os2, 0, 0, this);
		g.dispose();
		g1.drawImage(offscreen, 0, 0, this);
	}

	public void setCaption(boolean caption) {
		this.caption = caption;
	}

	public int getWbar() {
		return wbar;
	}

	public MisuScale getScale() {
		return scale;
	}

	public void setScale(MisuScale st) {
		scale = st;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setVx(int v) {
		vx = v;
	}

	public MisuSeqGrid getSeqgrid() {
		return seqgrid;
	}

	public void setSeqgrid(MisuSeqGrid seqgrid) {
		this.seqgrid = seqgrid;
	}

	// public void setCcval(int i, int v) {
	// ccval[i] = v;
	// }
	//
	// public void setCcvalName(int i, String ccvalName) {
	// this.ccvalName[i] = ccvalName;
	// }

}