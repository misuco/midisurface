package org.misucatomisuco.model;

public class MScale {
	int[] f;
	String name;
	public int[] getF() {
		return f;
	}

	public void setF(int[] f) {
		this.f = f;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MScale() {
		super();
	}

	public MScale(int[] f, String name) {
		super();
		this.f = f;
		this.name = name;
	}

}
