package org.misucatomisuco.control;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.illposed.osc.OSCBundle;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortOut;

public class MisuOSC3 implements IMisuOut {
	OSCPortOut P;
	String destIP;
	int destPort;
	
	public MisuOSC3(String d, int p) {
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

	public MisuOSC3() {
		this("127.0.0.1",6995);
	}

	//@Override
	public void cc(int c, int cc, int p) {
		OSCMessage m = new OSCMessage("/sy"+c+"/cc"+cc);
		m.addArgument(new Float(p));
		try {
			P.send(m);
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//@Override
	public synchronized void noteOff(int c, int f, int vel) {
		OSCMessage m = new OSCMessage("/sy"+c+"/note");
		m.addArgument(f);
		m.addArgument(0f);
		try {
			P.send(m);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//@Override
	public void noteOn(int c, int f, int v) {
		OSCMessage m = new OSCMessage("/sy"+c+"/note");
		m.addArgument(new Float(f));
		m.addArgument(new Float(v));
		try {
			P.send(m);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
