package org.misucatomisuco.control;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import org.misucatomisuco.model.MisuCcMapItem;
import org.misucatomisuco.view.MisuPanelString;

public class CMisuPanelFadeInternal extends CMisuPanelFade implements MouseMotionListener, MouseListener {
	
	MisuPanelString wdst;
	MisuProg pdst;
	
	public CMisuPanelFadeInternal() {
		super(null, 0, null);
//		MisuCcMapItem[] ccvalInt={
//				 new MisuCcMapItem(0,1,8,"range"),
//				 new MisuCcMapItem(0,0,60,"base"),
//				 new MisuCcMapItem(0,1,24,"scale")
//			};
//			ccmap=ccvalInt;
	}
	
	public CMisuPanelFadeInternal(MisuCcMapItem[] cc) {
		super(null,0,cc);
	}

	public CMisuPanelFadeInternal(MisuPanelString wd) {
		super(null, 0, null);
		wdst=wd;
	}
	
	public void setPdst(MisuProg pdst) {
		this.pdst = pdst;
	}

	//@Override
	public void mousePressed(MouseEvent e) {
		
		px=e.getX();
		py=e.getY();
		
		int faderNo=e.getY() / w.getWbar();
		if(vertical) {
			faderNo=e.getX() / w.getWbar();
		}
		
		if(faderNo<ccmap.length ) {
			int nbar=ccmap[faderNo].getMax()-ccmap[faderNo].getMin();
			int hbar=w.getWidth()/nbar;
			int ccv = ccmap[faderNo].getMin() + e.getX()  * nbar / w.getWidth();
			if(vertical) {
				hbar=w.getHeight()/nbar;
				ccv = ccmap[faderNo].getMin() + e.getY()  * nbar / w.getHeight();
			}
			
			if(ccprev[faderNo]!=ccv) {
				w.setCcval(faderNo, (ccv-ccmap[faderNo].getMin())*hbar);
				
//				if(vertical) {
//					w.setCcval(faderNo, py);
//				} else {
//					w.setCcval(faderNo, px);
//				}
				switch (faderNo) {
				case 0:
					wdst.getScale().setRange(ccv);
					break;
				case 1:
					wdst.getScale().setBase(ccv);
					break;
				case 2:
					wdst.getScale().setScaleN(ccv);
					break;
				case 3:
					pdst.pc(ccv);
					break;
				case 4:
					pdst.settHb(ccv);
					break;
				case 5:
					wdst.getSeqgrid().setLen(ccv);
					break;
				}
				ccprev[faderNo]=ccv;
			}
		}
		w.setCaption(true);
		w.repaint();
	}

	public MisuPanelString getWdst() {
		return wdst;
	}

	public void setWdst(MisuPanelString wdst) {
		this.wdst = wdst;
	}
}
