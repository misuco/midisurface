package org.misucatomisuco.control;

import java.net.SocketException;

import com.illposed.osc.OSCPortIn;

public class MisuInOSC {
	OSCPortIn P;
	int port;
	String ip;
	MisuProg mp;
	MisuInOSClistener lst;

	public MisuInOSC(int p, MisuProg mp) {
		super();
		this.mp = mp;
		lst = new MisuInOSClistener(mp);
		port = p;
		try {
			P = new OSCPortIn(port);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		P.addListener("/base", lst);
		P.addListener("/chord", lst);
		P.addListener("/scale", lst);
		P.startListening();
	}
}
