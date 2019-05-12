package org.misucatomisuco.model;

import java.util.ArrayList;

public class MisuChord {
	int[] f;
	String name;
	ArrayList<MScale> scales =new ArrayList<MScale>();

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

	public MisuChord(int[] f, String name, boolean scale) {
		super();
		if(scale) {
			int val=0;
			int st=f.length;
			this.f=new int[st*2];
			for(int j=0;j<st*2;j++) {
				val+=f[j%st];
				this.f[j]=val;
			} 
		} else {
			this.f = f;
		}
		this.name = name;
	}
	
	public int getIntVal() {
		int v=(int)Math.pow(2, 24);
		for(int i=0;i<f.length;i++) {
			v+=Math.pow(2, 24-f[i]);
		}
		return v;
	}

	public MisuChord() {
		super();
	}

	public boolean isComp(MisuChord c) {
		if((getIntVal() & c.getIntVal()) == getIntVal()) {
			return true;
		} else {
			return false;
		}
	}

	public ArrayList<MScale> getScales() {
		return scales;
	}

	public void setScales(ArrayList<MScale> scales) {
		this.scales = scales;
	}

}
