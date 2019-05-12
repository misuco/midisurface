package org.misucatomisuco.model;

public class MisuState {
	int scale;
	int base;
	int range=1;
	public MisuState(int scale, int base, int r) {
		super();
		this.scale = scale;
		this.base = base;
		if(r>0) {
			range = r;
		}
	}
	public int getScale() {
		return scale;
	}
	public int getBase() {
		return base;
	}
	public int getRange() {
		return range;
	}
	public void setBase(int b) {
		base=b;
	}
	public void setRange(int r) {
		range=r;
	}
	public void setScale(int s) {
		scale=s;
	}
}
