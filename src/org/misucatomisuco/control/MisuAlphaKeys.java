/*
 * CMisuAlphaKeys.java
 *
 * Copyright (C) 2009-2010 Claudio Zopfi
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
public class MisuAlphaKeys implements KeyListener {

	MisuProg mp;
	int oct=4;
	int base=oct*12;
	int basenote=0;
	int prog=0;
	
	boolean[] pressed=new boolean[800];

	public MisuAlphaKeys(MisuProg p) {
		super();
		mp=p;
	}

	public MisuAlphaKeys() {
		super();
	}

	//@Override
	public void keyPressed(KeyEvent e) {
		int kc=e.getKeyCode();
		int kch=e.getKeyChar();
		if(!pressed[kc]) {
			switch (kch) {
			case 97:
				basenote(0);
				break;
			case 115:
				basenote(1);
				break;
			case 100:
				basenote(2);
				break;
			case 102:
				basenote(3);
				break;
			case 103:
				basenote(4);
				break;
			case 104:
				basenote(5);
				break;
			case 106:
				basenote(6);
				break;
			case 107:
				basenote(7);
				break;
			case 108:
				basenote(8);
				break;
			case 246:
				basenote(9);
				break;
			case 228:
				basenote(10);
				break;
			case 36:
				basenote(11);
				break;
				
			}
			switch (kc) {
			
			case 37:	// cursor left
				prog--;
				if(prog<0) {
					prog=127;
				}
				mp.setProg(prog);
				break;
			case 38:	// cursor up
				mp.inc();
				break;
			case 39:	// cursor right
				prog++;
				if(prog>127) {
					prog=0;
				}
				mp.setProg(prog);
				break;
			case 40:	// cursor down
				mp.dec();
				break;
			case 27:	// ESC-Key
				mp.saveSongs();
				System.exit(0);
				break;
				
			case 112:	// F-Keys
				baseoct(3);
				mp.setRange(1);
				break;
			case 113:
				baseoct(3);
				mp.setRange(3);
				break;
			case 114:
				baseoct(4);
				mp.setRange(4);
				break;
			case 115:
				baseoct(2);
				mp.setRange(8);
				break;
				
			case 116:
				baseoct(5);
				mp.setRange(1);
				break;
			case 117:
				baseoct(5);
				mp.setRange(3);
				break;
			case 118:
				baseoct(5);
				mp.setRange(4);
				break;
			case 119:
				baseoct(5);
				mp.setRange(6);
				break;
				
			case 120:
				baseoct(6);
				mp.setRange(1);
				break;
			case 121:
				baseoct(6);
				mp.setRange(2);
				break;
			case 122:
				baseoct(6);
				mp.setRange(3);
				break;
			case 123:
				baseoct(6);
				mp.setRange(4);
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
				mp.setScale(kc-39);
				break;

			case 48:	// 0
				mp.setScale(19);
				break;
//			case 222:	// '
//				mp.setScale(20);
//				break;

			/* qwertzuiop */
			case 81:
				mp.setScale(0);
				//baseoct(0);
				break;
			case 87:
				mp.setScale(1);
				break;
			case 69:
				mp.setScale(2);
				break;
			case 82:
				mp.setScale(3);
				break;
			case 84:
				mp.setScale(4);
				break;
			case 90:
				mp.setScale(5);
				break;
			case 85:
				mp.setScale(6);
				break;
			case 73:
				mp.setScale(7);
				break;
			case 79:
				mp.setScale(8);
				break;
			case 80:
				mp.setScale(9);
				break;
			case 135:
				mp.setScale(10);
				break;
				
			/* yxcvbnm,.- */
			case 89:
				mp.updChord(1);
				break;
			case 88:
				mp.updChord(2);
				break;
			case 67:
				mp.updChord(3);
				break;
			case 86:
				mp.updChord(4);
				break;
			case 66:
				mp.updChord(5);
				break;
			case 78:
				mp.updChord(6);
				break;
			case 77:
				mp.updChord(7);
				break;
			case 44:
				mp.updChord(8);
				break;
			case 46:
				mp.updChord(9);
				break;
			case 45:
				mp.updChord(10);
				break;
			default:
//				System.out.println(kc);
			}
			pressed[kc]=true;
			
		}
	}

	//@Override
	public void keyReleased(KeyEvent e) {

		int kc=e.getKeyCode();
		switch (kc) {
			case 89:
				mp.stopChord(1);
				break;
			case 88:
				mp.stopChord(2);
				break;
			case 67:
				mp.stopChord(3);
				break;
			case 86:
				mp.stopChord(4);
				break;
			case 66:
				mp.stopChord(5);
				break;
			case 78:
				mp.stopChord(6);
				break;
			case 77:
				mp.stopChord(7);
				break;
			case 44:
				mp.stopChord(8);
				break;
			case 46:
				mp.stopChord(9);
				break;
			case 45:
				mp.stopChord(10);
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
			case 97:
				
			case 115:
			case 100:
			case 102:
			case 103:
			case 104:
			case 106:
			case 107:
			case 108:
			case 246:
			case 228:
			case 36:
				
			mp.stopChord();
			
		}
		pressed[kc]=false;
	}

	//@Override
	public void keyTyped(KeyEvent e) {

	}
	
	private void basenote(int i) {
		basenote=i;
		mp.setBase(base+basenote);
	}

	private void baseoct(int i) {
		oct=i;
		base=oct*12;
		mp.setBase(base+basenote);
	}

}
