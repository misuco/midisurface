/*
 * MisuOSC1.java
 *
 * Copyright (C) 2010 Claudio Zopfi
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
package org.misucatomisuco.control;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;

import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortOut;
/*
 * used in the first tests with pd
 */
public class MisuOSC1 implements IMisuOut {
	OSCPortOut P;
	float[] pitch= {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	float[] vel= {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	float[] note= {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	HashMap<Float,Integer> voices=new HashMap<Float,Integer>();
	
	String destIP="127.0.0.1";
	int destPort=10000;

	public MisuOSC1() {
		this("10.0.0.1",10000);
	}
	
	public MisuOSC1(String destIP, int destPort) {
		super();
		this.destIP = destIP;
		this.destPort = destPort;
		setup();
		System.out.println("MisuOSC1 setup");
	}

	void setup() {
		try {
			P=new OSCPortOut(InetAddress.getByName(destIP),destPort);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	//@Override
	public void cc(int c, int cc, int p) {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void mod(int c, int p) {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void noteOff(int c, int f, int v) {
		System.out.println("note off "+f);
		note[c]=f;
		vel[c]=0;
		updVoice(c,f);
	}
	
	private void updVoice(int c) {
		
	}
	
	synchronized private void updVoice(int c, int f) {
		System.out.println("upd voice c:"+c+" f:"+f);
		int v=getVoiceN(c,f);
		if(vel[c]==0) {
			releaseVoice(c,f);
		}
		OSCMessage m = new OSCMessage("/SY/"+c+"/V/"+v);
		m.addArgument(new Float(midi2f(note[c]+pitch[c])));
		m.addArgument(new Float(vel[c]));
		try {
			P.send(m);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private float getVoiceKey(int c, int f) {
		return (float)c*100000+f;
	}
	
	private void releaseVoice(int c, int f) {
		Float key=getVoiceKey(c,f);
		System.out.println("release voice "+key);
		voices.remove(key);
	}
	
	private int getVoiceN(int c, int f) {
		Float key=getVoiceKey(c,f);
		if(voices.containsKey(key)) {
			return voices.get(key);
		} else {
			int i=0;
			while(i<=voices.size() && voices.containsValue(i)) {
				i++;
			}
			voices.put(key, i);
			System.out.println("new voice "+i+" key: "+key);
			return i;
		}
	}

	//@Override
	public void noteOn(int c, int f, int v) {
		System.out.println("note on "+f);
		note[c]=f;
		vel[c]=v;
		updVoice(c,f);
	}
	
	public double midi2f(float m) {
		return (440/32)*Math.pow(2,((m-9)/12));
	}

	//@Override
	public void pc(int c, int p) {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void pitch(int c, int f) {
		pitch[c]=12*((float)f-63)/128;
		updVoice(c);
	}

	public String getDestIP() {
		return destIP;
	}

	public void setDestIP(String destIP) {
		this.destIP = destIP;
		setup();
	}

	public int getDestPort() {
		return destPort;
	}

	public void setDestPort(int destPort) {
		this.destPort = destPort;
		setup();
	}

	//@Override
	public void scale(int base, int[] scale, int[] chord) {
		// TODO Auto-generated method stub
		
	}

}
