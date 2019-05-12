package org.misucatomisuco.control;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortOut;

public class MisuMidiOSC implements IMisuOut {
	OSCPortOut P;
	String destIP;
	int destPort;

	public MisuMidiOSC(String d, int p) {
		destPort=p;
		destIP=d;
		setup();
	}
	
	void setup() {
		System.out.println("setup MisuMidiOSC "+destIP+" "+destPort);
		try {
			P=new OSCPortOut(InetAddress.getByName(destIP),destPort);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

//	public MisuMidiOSC() {
//		this("127.0.0.1",6995);
//	}

	public void cc(int c, int cc, int p) {
		OSCMessage m = new OSCMessage("/cc");
		m.addArgument(new Float(cc));
		m.addArgument(new Float(p));
		m.addArgument(new Float(c));
		try {
			P.send(m);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void mod(int c, int p) {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void noteOff(int c, int f, int v) {
		noteOn(c,f,0);
	}

	//@Override
	public void noteOn(int c, int f, int v) {
		OSCMessage m = new OSCMessage("/note");
		m.addArgument(new Float(f));
		m.addArgument(new Float(v));
		m.addArgument(new Float(c));
		try {
			P.send(m);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//@Override
	public void pc(int c, int p) {
		OSCMessage m = new OSCMessage("/pc");
		m.addArgument(new Float(p));
		m.addArgument(new Float(c));
		try {
			P.send(m);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	//@Override
	public void pitch(int c, int f) {
		int p=(f)*127;
		OSCMessage m = new OSCMessage("/pitch");
		m.addArgument(new Float(p));
		m.addArgument(new Float(c));
		try {
			P.send(m);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//@Override
	public void scale(int base, int[] scale, int[] chord) {
		// TODO Auto-generated method stub
		
	}

}
