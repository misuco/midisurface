package org.misucatomisuco.model;

public class MisuSeqGrid {
	public int len; 		// repeat pos display
	public int pointer; 	// repeat write point
	public long[] t;		// timestamp
	public int[] tx;		// x-pos at timestamp
	public int[] ty;		// y-pos at timestamp
	public MisuSignal[] ts;	// Signal triggered
	
	public MisuSeqGrid(int n) {
		super();
		this.len = n;
		this.pointer = 0;
		this.t = new long[n];
		this.tx = new int[n];
		this.ty = new int[n];
		this.ts = new MisuSignal[n];
		for (int i = 0; i < len; i++) {
			tx[i] = -1;
			ty[i] = -1;
		}	
	}
	
	public void addPoint(long t, int x, int y, MisuSignal s) {
		pointer++;
		if (pointer >= len) {
			pointer = 0;
		}
		this.t[pointer]=t;
		this.tx[pointer]=x;
		this.ty[pointer]=y;
		this.ts[pointer]=s;
	}
	public void setLen(int l) {
		if(len>l) {
			for(int i=len-1;i>l-1;i--) {
				this.t[i]=-1;
				this.tx[i]=-1;
				this.ty[i]=-1;
				this.ts[i]=null;
			}
		}
		len=l;
	}
}