package org.misucatomisuco.control;

public interface IMisuOut {
	public void noteOn(int c, int f, int v);

	public void noteOff(int c, int f, int v);

	public void pitch(int c, int f);

	public void pc(int c, int p);

	public void mod(int c, int p);

	public void cc(int c, int cc, int p);

	public void scale(int base, int[] scale, int[] chord);

}
