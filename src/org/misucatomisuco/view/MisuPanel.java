package org.misucatomisuco.view;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.misucatomisuco.control.ICMisuPanel;
import org.misucatomisuco.model.MisuColor;
import org.misucatomisuco.model.MisuScale;
import org.misucatomisuco.model.MisuSeqGrid;


public abstract class MisuPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8995482059907456773L;
	int wbar;

	ICMisuPanel control;
	MisuColor col;
	MisuSeqGrid seqgrid;
	int w,h;
	int heartbeat=50;		// tick time in ms
	
	ArrayList<IMisuPanelDrawer> drawers;
	
	String title = "";

	boolean caption = false;

	public MisuPanel() {
		super();
		this.col=new MisuColor();
	}
		
	public void setControl(ICMisuPanel control) {
		this.control = control;
	}

	public ICMisuPanel getControl() {
		return control;
	}

	public void paint(Graphics g1) {
	}
	
	public void paintDrawers(Graphics g1) {
		if(drawers!=null) {
			for( IMisuPanelDrawer m : drawers ) {
				m.paint(g1, this);
			}
			
		}
	}

	public void setSeqgrid(MisuSeqGrid seqgrid) {
		this.seqgrid = seqgrid;
	}

	public void setDrawers(ArrayList<IMisuPanelDrawer> drawers) {
		System.out.println("set drawers");
		this.drawers = drawers;
	}

	public void setCaption(boolean caption) {
		this.caption = caption;
	}

	public int getWbar() {
		return wbar;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setOnnote(int n) {
	}

//	public void setPitch(int p) {
//	}

	public void setVx(int v) {
	}
	
	public MisuScale getScale() {
		return null;
	}

	public void setCcval(int faderNo, int py) {
		// only for misupanelfade
		
	}

	public MisuSeqGrid getSeqgrid() {
		return seqgrid;
	}

	public int getHeartbeat() {
		return heartbeat;
	}

	public void setHeartbeat(int heartbeat) {
		this.heartbeat = heartbeat;
	}
	
//	public int getWidth() {
//		return w;
//	}
//	
//	public int getHeight() {
//		return h;
//	}
}

//	public void setCcval(int i, int v) {
//	}
//
//	public void setCcvalName(int i, String ccvalName) {
//	}


/*
public interface MisuPanel {
	public void rotate() ;
	public void setPix(int x, int y) ;
	public void setCaption(boolean caption);
	public int getWbar();
	public MisuScale getScale() ;
	public void setScale(MisuScale st) ;
	public void setTitle(String title) ;
	public void setOnnote(int n) ;
	public void setPitch(int p) ;
	public void setVx(int v) ;
	public void setCcval(int i, int v) ;
	public void setCcvalName(int i, String ccvalName) ;
	public int getChannel() ;
	public void setChannel(int channel) ; */

