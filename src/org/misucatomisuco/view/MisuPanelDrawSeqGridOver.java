package org.misucatomisuco.view;

import java.awt.Color;
import java.awt.Graphics;

import org.misucatomisuco.model.MisuSignal;

public class MisuPanelDrawSeqGridOver implements IMisuPanelDrawer {

	public MisuPanelDrawSeqGridOver() {
		super();
	}

	public void paint(Graphics g, MisuPanel m) {
		// draw the way steped Module 3
		// "Timefall"
		int r = 5;
		int ylastOn=0;
		int xlastOn=0;
		int resolution=m.h;
		int heartbeat=m.getHeartbeat();
		int div=resolution*heartbeat;
//		int h2=m.h/2;
		int h2=m.seqgrid.ty[m.seqgrid.pointer];
		
		for (int j = 0; j < m.seqgrid.len; j++) {
			int k = j + m.seqgrid.pointer;
			if (k >= m.seqgrid.len) {
				k -= m.seqgrid.len;
			}

			if (m.seqgrid.tx[k] >= 0 && m.seqgrid.ty[k] >= 0) {
				// calculate y from timestamp
				int yts = h2+m.h
						* (int) ((System.currentTimeMillis() - m.seqgrid.t[k]) % div)
						/ div;
				if(yts>m.h) {
					yts-=m.h;
				}

				// choose color, shape, size depending on related signal
				if (m.seqgrid.ts[k] != null) {
					
					
//					g.setColor(Color.getHSBColor(m.col.colway, 1, 1));
					Color fcol=m.col.getColorF((float)m.seqgrid.ts[k].v1%12f/11f, 1);
					g.setColor(fcol);
					
					if (m.seqgrid.ts[k].type == MisuSignal.NOTE_ON) {
						r = 6;
						xlastOn=m.seqgrid.tx[k];
						ylastOn=yts;
						g.fillOval((m.seqgrid.tx[k] - r / 2), yts, r, r);
						
						g.setColor(fcol);
						g.drawLine((m.seqgrid.tx[k] - r / 2), yts, m.seqgrid.tx[k], m.seqgrid.ty[k]);
						g.drawOval((m.seqgrid.tx[k] - r / 2), yts, r, r);
						
						
					} else if (m.seqgrid.ts[k].type == MisuSignal.NOTE_OFF) {
						r = 4;
						g.fillRect(m.seqgrid.tx[k] - r / 2, yts - r / 2, r, r);
						g.setColor(fcol);
						g.drawRect(m.seqgrid.tx[k] - r / 2, yts - r / 2, r, r);
						if(xlastOn>0 && ylastOn>0 && ylastOn>yts) {
							g.drawLine(m.seqgrid.tx[k], yts, xlastOn, ylastOn);
						}
					} else if (m.seqgrid.ts[k].type == MisuSignal.PITCH) {
						g.drawLine(m.seqgrid.tx[k], yts-1, m.seqgrid.tx[k], yts+1);
						g.drawLine(m.seqgrid.tx[k], yts, m.seqgrid.tx[k] + m.seqgrid.ts[k].v1, yts);
					} else {
						r = 4;
						g.fillRect((m.seqgrid.tx[k] - r / 2), yts, r, r);
					}
				} else {
					g.setColor(Color.getHSBColor(m.col.colway, 0.5f, 1));
					r = 2;
					g.fillOval((m.seqgrid.tx[k] - r / 2), yts, r, r);
				}

			}
		}
		g.drawLine(0, h2, m.w, h2);

	}

}
