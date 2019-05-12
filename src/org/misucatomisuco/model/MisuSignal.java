package org.misucatomisuco.model;

public class MisuSignal {
	public static final int NOP=0;
	public static final int NOTE_ON=1;
	public static final int NOTE_OFF=2;
	public static final int PITCH=3;
	public static final int CC=4;
	
	public int type;
	public int v1;
	public int v2;
	
	public MisuSignal(int type, int v1, int v2) {
		super();
		this.type = type;
		this.v1 = v1;
		this.v2 = v2;
	};

}
