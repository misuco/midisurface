package org.misucatomisuco.control;

import java.applet.Applet;
import java.util.ArrayList;

import org.misucatomisuco.model.MisuSeqGrid;
import org.misucatomisuco.view.IMisuPanelDrawer;
import org.misucatomisuco.view.MisuFrame;
import org.misucatomisuco.view.MisuPanel;
import org.misucatomisuco.view.MisuPanelDrawSeqGridBottom;
import org.misucatomisuco.view.MisuPanelDrawSeqGridOver;
import org.misucatomisuco.view.MisuPanelString;


public class MisuApplet extends Applet {
	/**
	 * 
	 * 	 */
	private static final long serialVersionUID = 389246531149158002L;
	
	public void init() {
		
		MisuMidi out=new MisuMidi();
		MisuOutMulti outm= new MisuOutMulti();
		outm.add(out);
		
		MisuSeqGrid seqgrid=new MisuSeqGrid(2000);
		
		MisuFrame f=new MisuFrame();
		
			MisuProg mp=new MisuProg();
			int[] progCh=new int[1];
			progCh[0]=1;
			mp.setProgCh(progCh);
				MisuPanel[] pans=new MisuPanel[1];
					pans[0]=new MisuPanelString();
						CMisuPanelString control0=new CMisuPanelString(out,1);
						control0.setSeqgrid(seqgrid);
					pans[0].setControl(control0);
						ArrayList<IMisuPanelDrawer> drawers=new ArrayList<IMisuPanelDrawer>();
						drawers.add(new MisuPanelDrawSeqGridOver());
						drawers.add(new MisuPanelDrawSeqGridBottom());
					pans[0].setDrawers(drawers);
					pans[0].setSeqgrid(seqgrid);
			mp.setPans(pans);
				
			mp.setMm(outm);
			
					MisuSeqPlayer seq=new MisuSeqPlayer();
					seq.setChannel(1);
					seq.setGrid(seqgrid);
						MisuSignalPlayer player=new MisuSignalPlayer();
						player.setOut(out);
					seq.setPlayer(player);
					MisuSeqPlayer[] seqplayers=new MisuSeqPlayer[0];
					
				MisuHeartBeat h=new MisuHeartBeat(seqplayers, f);
				
			mp.setHeartbeat(h);
		
		f.setMp(mp);
		f.setPanels(pans);
	}

}
