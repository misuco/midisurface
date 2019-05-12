package org.misucatomisuco.model;

import java.awt.Color;

public class MisuColor {
	public MisuColor() {
		super();
	}

	public float colstart = 0.0f;
	public float colrange = 0.2f;
	public float colway = 0.05f;
	public float colwave = 0;
	public float colcap = 0;
	public float collogo = 0;
	public float colrangemin = 0.2f;

	public Color getColorCaption() {
		return null;
	}

	public Color getColorF(float f, float sat) {
		return Color.getHSBColor(colstart + f * colrange, 1f, sat);	}
	
	public void rotate() {
			
//		colstart += 0.01;
//		if (colstart > 0.9)
//			colstart = 0f;
	}
	
	public void rotate2() {
		// color rotation
		int rotdiv = 100;
		colstart += (Math.random() - 0.5) / rotdiv;
		if (colstart > 0.9)
			colstart = 0.9f;
		if (colstart < 0)
			colstart = 0;
		colrange += (Math.random() - 0.5) / rotdiv;
		if (colrange + colstart > 1)
			colrange = 1f - colstart;

		colway += (Math.random() - 0.5) / rotdiv;
		if (colway > 1.0)
			colway = 0.9f;
		if (colway < 0)
			colway = 0;
		colwave = colway;
		colcap = colway;

		collogo += (Math.random() - 0.5) / rotdiv;
		if (collogo > 1.0)
			collogo = 0.9f;
		if (collogo < 0)
			collogo = 0;

	}

}
