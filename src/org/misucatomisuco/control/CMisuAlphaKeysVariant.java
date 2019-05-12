/*
 * CMisuAlphaKeysVaria.java
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

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CMisuAlphaKeysVariant implements KeyListener {

	MisuProg mp;
	int base=48;
	long[] timeup=new long[255];

	public CMisuAlphaKeysVariant(MisuProg p) {
		super();
		mp=p;
	}

	public CMisuAlphaKeysVariant() {
		super();
	}

	//@Override
	public void keyPressed(final KeyEvent e) {
		int kc=e.getKeyCode();
		long diff=e.getWhen()-timeup[kc];
		System.out.println("kc " + kc + " diff: "+diff+" = when "+e.getWhen()+" - timeup "+timeup[kc]);
		if (diff <10) {
			timeup[kc] = 0;
			return;
		}
		int kch=e.getKeyChar();
//		System.out.println("Keycode:"+kch);
		switch (kch) {
		case 97:
			mp.setBase(base);
			break;
		case 115:
			mp.setBase(base+1);
			break;
		case 100:
			mp.setBase(base+2);
			break;
		case 102:
			mp.setBase(base+3);
			break;
		case 103:
			mp.setBase(base+4);
			break;
		case 104:
			mp.setBase(base+5);
			break;
		case 106:
			mp.setBase(base+6);
			break;
		case 107:
			mp.setBase(base+7);
			break;
		case 108:
			mp.setBase(base+8);
			break;
		case 246:
			mp.setBase(base+9);
			break;
		case 228:
			mp.setBase(base+10);
			break;
		case 36:
			mp.setBase(base+11);
			break;
			
		}
		switch (kc) {
		
		case 37:	// cursor left
			base-=12;
			break;
			
		case 38:	// cursor up
			mp.inc();
			break;
			
		case 39:	// cursor right
			base+=12;
			break;
			
		case 40:	// cursor down
			mp.dec();
			break;
			
		case 112:	// F-Keys
		case 113:
		case 114:
		case 115:
		case 116:
		case 117:
		case 118:
		case 119:
		case 120:
		case 121:
		case 122:
		case 123:
			mp.setProg(kc-112);
			break;
		case 27:	// ESC-Key
			mp.saveSongs();
			System.exit(0);
			break;
			
		case 49:	// Numbers
		case 50:
		case 51:
		case 52:
		case 53:
		case 54:
		case 55:
		case 56:
		case 57:
		case 58:
			mp.setScale(kc-48);
			break;

		/* qwertzuiop */
		case 81:
			mp.setRange(1);
			break;
		case 87:
			mp.setRange(2);
			break;
		case 69:
			mp.setRange(3);
			break;
		case 82:
			mp.setRange(4);
			break;
		case 84:
			mp.setRange(5);
			break;
		case 90:
			mp.setRange(6);
			break;
		case 85:
			mp.setRange(7);
			break;
		case 73:
			mp.setRange(8);
			break;
		case 79:
			mp.setRange(9);
			break;
		case 80:
			mp.setRange(10);
			break;
		case 135:
			mp.setRange(11);
			break;
			
		/* yxcvbnm,.- */
		case 89:
			mp.updChord(0);
			break;
		case 88:
			mp.updChord(1);
			break;
		case 67:
			mp.updChord(2);
			break;
		case 86:
			mp.updChord(3);
			break;
		case 66:
			mp.updChord(4);
			break;
		case 78:
			mp.updChord(5);
			break;
		case 77:
			mp.updChord(6);
			break;
		case 44:
			mp.updChord(7);
			break;
		case 46:
			mp.updChord(8);
			break;
		case 45:
			mp.updChord(9);
			break;
		}
	}

	//@Override
	public void keyReleased(final KeyEvent e) {

		int kc=e.getKeyCode();
		timeup[kc] = e.getWhen();
		System.out.println("released "+ kc +" timeup "+timeup[kc]);
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				int kc=e.getKeyCode();
				if (0 != timeup[kc]) {
//					System.out.println("Keycode:"+kch);
					switch (kc) {
						case 89:
						case 88:
						case 67:
						case 86:
						case 66:
						case 78:
						case 77:
						case 44:
						case 46:
						case 45:
							mp.stopChord();
					}
				}
			}
		});
	}

	//@Override
	public void keyTyped(KeyEvent e) {

	}

}
