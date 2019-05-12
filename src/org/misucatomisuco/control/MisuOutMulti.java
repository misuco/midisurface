package org.misucatomisuco.control;

import java.util.ArrayList;

public class MisuOutMulti implements IMisuOut {

	ArrayList<IMisuOut> ios = new ArrayList<IMisuOut>();

	public MisuOutMulti() {
		super();
	}

	public void add(IMisuOut io) {
		ios.add(io);
	}

	// @Override
	public void cc(int c, int cc, int p) {
		for (IMisuOut n : ios) {
			n.cc(c, cc, p);
		}
	}

	// @Override
	public void mod(int c, int p) {
		for (IMisuOut n : ios) {
			n.mod(c, p);
		}
	}

	// @Override
	public void noteOff(int c, int f, int v) {
		for (IMisuOut n : ios) {
			n.noteOff(c, f, v);
		}
	}

	// @Override
	public void noteOn(int c, int f, int v) {
		for (IMisuOut n : ios) {
			n.noteOn(c, f, v);
		}
	}

	// @Override
	public void pc(int c, int p) {
		for (IMisuOut n : ios) {
			n.pc(c, p);
		}
	}

	// @Override
	public void pitch(int c, int f) {
		for (IMisuOut n : ios) {
			n.pitch(c, f);
		}
	}

	// @Override
	public void scale(int base, int[] scale, int[] chord) {
		for (IMisuOut n : ios) {
			n.scale(base, scale, chord);
		}
	}

	public ArrayList<IMisuOut> getIos() {
		return ios;
	}

	public void setIos(ArrayList<IMisuOut> ios) {
		this.ios = ios;
	}
}
