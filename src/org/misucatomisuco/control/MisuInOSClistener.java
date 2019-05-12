package org.misucatomisuco.control;

import java.util.Date;

import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCMessage;

public class MisuInOSClistener implements OSCListener {
	MisuProg mp;
	int base;
	int[] scale;
	int[] chord;

	public MisuInOSClistener(MisuProg mp) {
		super();
		this.mp=mp;
	}

	//@Override
	public void acceptMessage(Date time, OSCMessage message) {
		System.out.println("get message..."+message.getAddress());
		if(message.getAddress().contains("/base")) {
			base=(Integer)message.getArguments()[0];
		} else if(message.getAddress().contains("/scale")) {
			Object[] s=message.getArguments();
			scale=new int[s.length];
			System.out.println("New scale "+s.length);
			for(int i=0;i<s.length;i++) {
				scale[i]=((Integer)s[i]).intValue();
			}
		} else if(message.getAddress().contains("/chord")) {
			Object[] s=message.getArguments();
			chord=new int[s.length];
			for(int i=0;i<s.length;i++) {
				chord[i]=((Integer)s[i]).intValue();
			}
			mp.rcvChord(base,scale,chord);
			System.out.println("rcv chord");
		}
	}
}
