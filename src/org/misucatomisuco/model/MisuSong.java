package org.misucatomisuco.model;


public class MisuSong {

	static int nameNr=1;
	
	MisuScale[] ST;
	int p; // prog nr.
	String name;

	public MisuSong() {
		this(5, 1);
	}

	public MisuSong(int n, int p) {
		super();
		ST = new MisuScale[n];
		this.p = p;
		this.name="song "+nameNr++;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getP() {
		return p;
	}
	
	public void setP(int p) {
		this.p = p;
	}

	public MisuScale[] getST() {
		return ST;
	}

	public void setST(MisuScale[] s) {
		ST = s;
	}
	
	public MisuScale getSTi(int i) {
		return ST[i];
	}
	
	public void setSTi(MisuScale s, int i) {
		ST[i]=s;
	}

	public void randomize() {
		for (int i = 0; i < ST.length; i++) {
			ST[i] = new MisuScale(24 + (int) (Math.random() * 48),
					1 + (int) (Math.random() * 6), (int) (Math.random() * 10));
		}
	}


}
