/*
 * MisuFramePerf.java
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
package org.misucatomisuco.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.misucatomisuco.control.MisuAlphaKeys;
import org.misucatomisuco.control.MisuProg;

public class MisuFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3158210219035758030L;
	GraphicsDevice gd;
	JComboBox mosel;
	int h;
	int w;

	MisuProg mp;

	// s MisuScale scale;
	JPanel main;

	MisuPanel[] panels;

	public MisuFrame() {
		Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
		Dimension ss = toolkit.getScreenSize();
		h = ss.height;
		w = ss.width;
		setSize(w, h);
		setUndecorated(true);
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
//		setResizable(true);
	}

	public MisuProg getMp() {
		return mp;
	}

	public void setMp(MisuProg mp) {
		this.mp = mp;
		addKeyListener(new MisuAlphaKeys(mp));
	}

	public MisuPanel[] getPanels() {
		return panels;
	}

	public void setPanels1(MisuPanel[] p) {
		panels = p;
		GridLayout lay=new GridLayout(panels.length, 0);
		
		main = new JPanel(lay);
		
		add(main);
		for (int i = 0; i < panels.length; i++) {
			main.add(panels[i]);
		}
		setVisible(true);
	}

	public void setPanels(MisuPanel[] p) {
		panels = p;
		GridBagLayout lay=new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;
//		c.gridheight=panels.length;
//		c.gridwidth=1;
		
		float[] weights={0.6f,0.2f,0.2f};
		main = new JPanel(lay);
		
		add(main);
		for (int i = 0; i < panels.length; i++) {
			c.gridx=0;
			c.gridy=i;
			c.weightx=1;
			c.weighty=weights[i];
			main.add(panels[i],c);
		}
		setVisible(true);
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
		setSize(w, h);
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
		setSize(w, h);
	}

}
;