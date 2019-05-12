package org.misucatomisuco.view;

import java.awt.Color;
import java.awt.Graphics;

public class MisuPanelDrawWorm implements IMisuPanelDrawer {

	public MisuPanelDrawWorm() {
		super();
	}

	public void paint(Graphics g, MisuPanel m) {
		// draw the way steped Module 1
		// "Worm"
		for (int j = 0; j < m.seqgrid.len; j++) {
			int k = j + m.seqgrid.pointer;
			if (k >= m.seqgrid.len) {
				k -= m.seqgrid.len;
			}
			if (m.seqgrid.tx[k] >= 0 && m.seqgrid.ty[k] >= 0) {
				g.setColor(Color.getHSBColor(m.col.colway, 1, (float) j
						/ (float) m.seqgrid.len));
				int r = Math.round((float) j / 5
						* (50 - 50 * j / m.seqgrid.len));
				// int r2=Math.round((float)j/30*(rep2-rep2*j/rep2));
				g.drawOval((m.seqgrid.tx[k] - r / 2),
						(m.seqgrid.ty[k] - r / 2), r, r);
				// g.fillOval((tx[k]-r2/2), (ty[k]-r2/2), r2, r2);
			}
		}
	}
}
