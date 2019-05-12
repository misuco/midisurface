/*
 * MisuOSC2.java
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
import java.util.ArrayList;
import java.util.HashMap;

import com.illposed.osc.OSCBundle;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortOut;

public class MisuOSC2 implements IMisuOut {
	OSCPortOut P;
	int sendcnt=0;
	String destIP;
	int destPort;
//	float[] pitch= {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
//	float[] vel= {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
//	float[] note= {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	int nch=16;
	int nvb=8;
	int noff=3;
	HashMap<Integer,Integer> vnr=new HashMap<Integer,Integer>(); // The on-voices ch-freq -> voicenr
	HashMap<Integer,Integer> voff=new HashMap<Integer,Integer>(); // The off-voices ch-freq -> voicenr
	HashMap<Integer,Integer> vcoff=new HashMap<Integer,Integer>(); // The off-voices ch-voice -> voicenr
	int[][] vbuf=new int[nch][nvb];
	int[] pvbuf=new int[nch];
	
	public MisuOSC2(String d, int p) {
		destPort=p;
		destIP=d;
		try {
			P=new OSCPortOut(InetAddress.getByName(destIP),destPort);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public MisuOSC2() {
		this("127.0.0.1",6995);
		System.out.println("127.0.0.1 opened");
	}

	//@Override
	public void cc(int c, int cc, int p) {
		OSCMessage m = new OSCMessage("/sy"+c+"/cc"+cc);
		m.addArgument(new Float(p));
		try {
			P.send(m);
			sendcnt++;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//@Override
	public void mod(int c, int p) {
		OSCMessage m = new OSCMessage("/sy"+c+"/cc1");
		m.addArgument(new Float(p));
		try {
			P.send(m);
			sendcnt++;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//@Override
	public synchronized void noteOff(int c, int f, int vel) {
		Integer key=vnrKey(c, f);
		if(vnr.containsKey(key)) {
			int voice=vnr.get(key);
			Integer vkey=vchKey(c, voice);
			voff.put(key, voice);
			vcoff.put(vkey, noff);
			vnr.remove(key);
			
			ArrayList<Integer> toremove=new ArrayList<Integer>();
			for(Integer k : voff.keySet()) {
				OSCMessage m = new OSCMessage("/sy"+keyC(k)+"/v"+voff.get(k));
				m.addArgument(keyF(k));
				m.addArgument(0f);
				try {
					P.send(m);
					sendcnt++;
				} catch (IOException e) {
					e.printStackTrace();
				}
				vkey=vchKey(c, voff.get(k));
				if(vcoff.containsKey(vkey)) {
					int noffd=vcoff.get(vkey);
					vcoff.remove(vkey);
					if(noffd>0) {
						vcoff.put(vkey, noffd-1);
					} else {
						toremove.add(k);
					}
					
				}
			}
			for(Integer k : toremove) {
				voff.remove(k);
			}

			
			
		}
	}
	
//	private void updVoice(int c) {
//		OSCMessage m = new OSCMessage("/sy"+c+"/note");
////		m.addArgument(new Float(note[c]));
////		m.addArgument(new Float(vel[c]));
//		try {
//			P.send(m);
//			sendcnt++;
////			System.out.println("sent:"+sendcnt);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	//@Override
	public void noteOn(int c, int f, int v) {
		Integer key=vnrKey(c, f);
		int voice=pvbuf[c];
		if(vnr.containsKey(key)) {
			voice=vnr.get(key);
			vnr.remove(key);
		}
		vnr.put(key, pvbuf[c]);
		vbuf[c][pvbuf[c]++]=f;
		if(pvbuf[c]>=nvb) {
			pvbuf[c]=0;
		}
		
		Integer vkey=vchKey(c,voice);
		if(voff.containsKey(key) || vcoff.containsKey(vkey)) {
			voff.remove(key);
			vcoff.remove(vkey);
		}
		
		OSCMessage m = new OSCMessage("/sy"+c+"/v"+voice);
		m.addArgument(new Float(f));
		m.addArgument(new Float(v));
		try {
			P.send(m);
			sendcnt++;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Integer vchKey(int c, int v) {
		return c*100000+v;
	}
	
	private Integer vnrKey(int c, int f) {
		return c*100000+f;
	}
	
	private int keyC(int k) {
		return k/100000;
	}
	
	private int keyF(int k) {
		return k-(k/100000)*100000;
	}
	
	public double midi2f(float m) {
		return (440/32)*Math.pow(2,((m-9)/12));
	}

	//@Override
	public void pc(int c, int p) {
		OSCMessage m = new OSCMessage("/sy"+c+"/pc");
		m.addArgument(new Float(p));
		try {
			P.send(m);
			sendcnt++;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//@Override
	public void pitch(int c, int f) {
//		f=(int)(4192*((float)f-63)/128);
		OSCMessage m = new OSCMessage("/sy"+c+"/pitch");
		m.addArgument(new Float(f));
		try {
			P.send(m);
			sendcnt++;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//@Override
	public void scale(int base, int[] scale, int[] chord) {
		OSCBundle b=new OSCBundle();
		
		OSCMessage m = new OSCMessage("/base");
		m.addArgument(base);
		b.addPacket(m);
		
		m = new OSCMessage("/scale");
		for(int i=0;i<scale.length;i++) {
			m.addArgument(scale[i]);
		}
		b.addPacket(m);
		
		m = new OSCMessage("/chord");
		for(int i=0;i<chord.length;i++) {
			m.addArgument(chord[i]);
		}
		b.addPacket(m);
		
		try {
			P.send(b);
			sendcnt++;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
