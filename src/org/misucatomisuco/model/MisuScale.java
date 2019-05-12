/*
/*
 * MisuScale.java
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
package org.misucatomisuco.model;

public class MisuScale {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int[] notes;
	int base;
	int range;
	int[] scale;
	int scaleN;
	int[] chord;
	public static MisuScaleBuilder builder = new MisuScaleBuilder();


	public MisuScale() {
		this(-1, -1, -1); // required for serialisation
	}

	public MisuScale(int base, int range, int scale) {
		super();
		this.base = base;
		this.range = range;
		this.scaleN = scale;
		this.setup();
	}

	public MisuScale(int base, int[] scale, int[] chord) {
		super();
		this.base = base;
		this.scale = scale;
		this.chord = chord;
		this.range=7;
		this.setupDetails();
	}

	private void setup() {
		if (base >= 0 && range > 0 && scaleN >= 0) {
			this.scale = builder.getScale(scaleN);
			chord = builder.getChord(scaleN);
			setupDetails();
		} else {
			notes = new int[0]; // required for serialisation
		}
	}
	
	private void setupDetails() {
		this.notes = new int[this.scale.length * this.range];
		this.notes[0] = this.base;
		for (int i = 1; i < notes.length; i++) {
			this.notes[i] = this.notes[i - 1]
					+ this.scale[(i - 1) % this.scale.length];
		}
		
	}

	public int getNote(int i) {
		return this.notes[i];
	}

	public String getScaleName() {
		return builder.getChordname(scaleN)+" "+builder.getScalename(scaleN);
	}

	public int getLenth() {
		return this.notes.length;
	}

	public int getScaleN() {
		return scaleN;
	}

	public int getRange() {
		return range;
	}

	public int getBase() {
		return base;
	}

	public String getBaseName() {
		return builder.getNoteName(base % 12);
	}

	public void setBase(int b) {
		base = b;
		setup();
	}

	public void setScaleN(int val) {
		scaleN = val;
		this.scale = builder.getScale(scaleN);
		setup();
	}

	public void setRange(int val) {
		range = val;
		setup();
	}

	public void setup(int b, int s, int r) {
		base = b;
		scaleN = s;
		// scale = scales[s];
		// chord = chords[s];
		range = r;
		if (r <= 1) {
			r = 1;
		}
		setup();
	}

	public int[] getChord() {
		return chord;
	}

	public int[] getScale() {
		return scale;
	}

	public void setScale(int[] scale) {
		this.scale = scale;
	}

	public void setChord(int[] chord) {
		this.chord = chord;
	}

	public String getNoteName(int n) {
		return builder.getNoteName(n);
	}

	public double getNoteF(int n) {
		return builder.getNoteF(n);
	}

}
