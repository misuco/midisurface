package org.misucatomisuco.control;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import org.misucatomisuco.view.MisuPanel;

public interface ICMisuPanel extends MouseMotionListener, MouseListener {

	public void setMisuPanel(MisuPanel misuPanel) ;
	public int getPitch();
	public int getOnnote();

}
