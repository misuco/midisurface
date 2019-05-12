package org.misucatomisuco.view;

import java.awt.Graphics;

import org.misucatomisuco.model.MisuSignal;

public class MisuPanelDrawSeqGridBottom implements IMisuPanelDrawer {

	public MisuPanelDrawSeqGridBottom() {
		super();
	}

	public void paint(Graphics g, MisuPanel m) {
		// draw the way steped Module 2
		// "Timeline"
		int rmax=20;
		int resolution=m.h;
		int heartbeat=m.getHeartbeat();
		int div=resolution*heartbeat;
		int prevon=-1;
		int prevony=-1;
		
		int note=0;
		int pitch=0;
		
		long systime=System.currentTimeMillis();
		
		for (int j = 0; j < m.seqgrid.len; j++) {
//		for (int j = m.seqgrid.len-1; j >=0 ; j--) {
			int k = m.seqgrid.pointer-m.seqgrid.len+j;
			if (k >= m.seqgrid.len) {
				k -= m.seqgrid.len;
			}
			if (k <0) {
				k += m.seqgrid.len;
			}
			// float timef=0.7f*j/m.seqgrid.len;
			float timef = 1f-((float)(systime - m.seqgrid.t[k]) % (float)div)
				/ (float)div;
//			float timef=(float)timefd;
//			float timef=(float)j/(float)m.seqgrid.len;
			if (m.seqgrid.tx[k] >= 0 && m.seqgrid.ty[k] >= 0) {
//				g.setColor(Color.getHSBColor(m.col.colway, 1, timef));
				
				int r = j*rmax/m.seqgrid.len;
				
				
				// oval at timeline
//				int xseq=m.w-(j*m.w/m.seqgrid.len);
				int xseq = m.w
				* (int) ((systime - m.seqgrid.t[k]) % div)
				/ div;
				int yseq = m.h
				* (int) ((systime - m.seqgrid.t[k]) % div)
				/ div;
				if(m.seqgrid.ts[k]!=null) {
					if(m.seqgrid.ts[k].type==MisuSignal.NOTE_ON) {
						note=m.seqgrid.ts[k].v1%12;
					} else if(m.seqgrid.ts[k].type==MisuSignal.PITCH) {
						pitch=m.seqgrid.ts[k].v1;
					}
					float col=((float)note+((float)pitch/127f))/11f;
//					System.out.println("pitch"+pitch+" note"+note+" col"+col);
//					g.setColor(m.col.getColorF((float)note/11f, timef*timef*timef));
					g.setColor(m.col.getColorF(col, timef*timef*timef));

					
					// oval at previous position
					g.drawOval((m.seqgrid.tx[k] - r / 2), (m.seqgrid.ty[k] - r / 2), r, r);
					g.fillOval((m.seqgrid.tx[k] - r / 2), (m.seqgrid.ty[k] - r / 2), r, r);
					
//					if(m.seqgrid.ts[k].type==MisuSignal.NOTE_OFF) {
//						prevon=xseq;
//					} else if(m.seqgrid.ts[k].type==MisuSignal.NOTE_ON && prevon>0 ) {
//						g.fillOval(prevon, m.h-rmax, rmax, rmax);
//						g.fillRect(prevon, m.h-rmax, prevon-xseq, rmax);
//						g.setColor(Color.getHSBColor(m.collogo, 1, 0.7f));
						
//						g.drawOval(prevon, m.h-rmax, rmax, rmax);
//						g.drawRect(xseq, m.h-rmax, prevon-xseq, rmax);
//						
//					}
//					if(j==0) {
//						prevon= m.w
//						* (int) ((System.currentTimeMillis() - m.seqgrid.t[k+1]) % div)
//						/ div;
//					}
					

					if(j==0 && m.seqgrid.ts[k].type==MisuSignal.NOTE_OFF) {
						int k1=k+1;
						if (k1 >= m.seqgrid.len) {
							k1 -= m.seqgrid.len;
						}
						prevon= m.w
						* (int) ((systime - m.seqgrid.t[k-1]) % div)
						/ div;
						prevony= m.h
						* (int) ((systime - m.seqgrid.t[k-1]) % div)
						/ div;
					}
					if(m.seqgrid.ts[k].type==MisuSignal.NOTE_ON ) {
						prevon=xseq;
						prevony=yseq;
					} else if(m.seqgrid.ts[k].type==MisuSignal.NOTE_OFF && prevon>0 ) {
						g.fillRect(xseq, m.h-rmax, prevon-xseq, rmax);
						g.drawRect(xseq, m.h-rmax, prevon-xseq, rmax);
						
						g.fillRect(m.w-xseq, 0, prevon-xseq, rmax);
						g.drawRect(m.w-xseq, 0, prevon-xseq, rmax);
						
						g.fillRect(m.w-rmax, m.h-yseq, prevony-yseq, rmax);
						g.drawRect(m.w-rmax, m.h-yseq, prevony-yseq, rmax);
						
						g.fillRect(0, yseq, prevony-yseq, rmax);
						g.drawRect(0, yseq, prevony-yseq, rmax);
						
					}
				}

				// vertical: g.drawOval(rmax, j, rmax, rmax);
				
				g.drawLine((m.seqgrid.tx[k]), (m.seqgrid.ty[k]), xseq, m.h-rmax);
				g.drawLine((m.seqgrid.tx[k]), (m.seqgrid.ty[k]), m.w-xseq, rmax);
				
				g.drawLine((m.seqgrid.tx[k]), (m.seqgrid.ty[k]),  m.w-rmax, m.h-yseq);
				g.drawLine((m.seqgrid.tx[k]), (m.seqgrid.ty[k]), rmax, yseq );
				// vertical: g.drawLine((m.seqgrid.tx[k]), (m.seqgrid.ty[k]), (int)(1.5*rmax), j+rmax/2);
			}
		}

	}

}
