package org.misucatomisuco.model;

public class MisuCcMapItem {
	int cc;
	int min;
	int max;
	String name;
	
	public MisuCcMapItem(int cc, int min, int max, String name) {
		super();
		this.cc = cc;
		this.min = min;
		this.max = max;
		this.name=name;
	}
	
	public MisuCcMapItem() {
		super();
	}

	public int getCc() {
		return cc;
	}
	public void setCc(int cc) {
		this.cc = cc;
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
