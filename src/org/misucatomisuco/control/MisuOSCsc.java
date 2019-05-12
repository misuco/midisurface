package org.misucatomisuco.control;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortOut;

public class MisuOSCsc implements IMisuOut {
	OSCPortOut P;
	String destIP;
	int destPort;
	
	public MisuOSCsc(String d, int p) {
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

	public MisuOSCsc() {
		this("127.0.0.1",57120);
	}

	//@Override
	public void cc(int c, int cc, int p) {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void mod(int c, int p) {
		OSCMessage m = new OSCMessage("/mod/"+c);
		m.addArgument(new Float(p));
		try {
			P.send(m);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//@Override
	public void noteOff(int c, int f, int v) {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void noteOn(int c, int f, int v) {
		OSCMessage m = new OSCMessage("/note/"+c);
		m.addArgument(new Float(f));
		m.addArgument(new Float(v));
		try {
			P.send(m);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//@Override
	public void pc(int c, int p) {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void pitch(int c, int f) {
		OSCMessage m = new OSCMessage("/pitch");
		m.addArgument(new Float(f));
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
