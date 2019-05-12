package org.misucatomisuco.test;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.misucatomisuco.control.CMisuPanelFadeInternal;
import org.misucatomisuco.control.IMisuOut;
import org.misucatomisuco.model.MisuScaleBuilder;
import org.misucatomisuco.view.MisuFrame;
import org.misucatomisuco.view.MisuPanelDrawSeqGridBottom;
import org.misucatomisuco.view.MisuPanelDrawWorm;

public class MisuUnitTest extends TestCase {
	IMisuOut io1,io2;
	MisuFrame f;
	
	public MisuUnitTest() {
		super();
	}
	
	public MisuUnitTest(String name) {
		super(name);
	}
	
	protected void setUp() {
//		io1=new MisuOSC1();
//		io2=new MisuOSC2();
	}
	
	public void testMisuIO1() {
		for(int i=0;i<128;i++) {
			io1.noteOn(1, i, 127);
			io1.noteOff(1, i, 127);
		}
		Assert.assertTrue(true);
	}
	
	public void testMisuIO2() {
		for(int i=0;i<128;i++) {
			io2.noteOn(1, i, 127);
			io2.noteOff(1, i, 127);
		}
		Assert.assertTrue(true);
	}
	
	public void testMisuPanel1() {
		f=new MisuFrame();
	}
	
	public void testMisuPanel2() {
		f.repaint();
		f.repaint();
		f.repaint();
		f.repaint();
		f.repaint();
		f.repaint();
		f.repaint();
		f.repaint();
		f.repaint();
		f.repaint();
	}
	
	public void testScaleBuilder() {
		MisuScaleBuilder b=new MisuScaleBuilder();
		b.print();
		Assert.assertTrue(true);
	}
	
	public void testCMisuPanelFadeIntern() {
		CMisuPanelFadeInternal s=new CMisuPanelFadeInternal();
		s.toString();
		Assert.assertTrue(true);
	}
	
	public void testMisuPanelDrawSeqGridBottom() {
		MisuPanelDrawSeqGridBottom s=new MisuPanelDrawSeqGridBottom();
		s.toString();
		Assert.assertTrue(true);
	}
	
	public void testMisuPanelDrawWorm() {
		MisuPanelDrawWorm s=new MisuPanelDrawWorm();
		s.toString();
		Assert.assertTrue(true);
	}
	
	public static Test suite() {
	    TestSuite suite= new TestSuite();
//	    suite.addTest(new MisuUnitTest("testMisuIO1"));
//	    suite.addTest(new MisuUnitTest("testMisuIO2"));
//	    suite.addTest(new MisuUnitTest("testMisuPanel1"));
//	    suite.addTest(new MisuUnitTest("testMisuPanel2"));
//	    suite.addTest(new MisuUnitTest("testScaleBuilder"));
//	    suite.addTest(new MisuUnitTest("testSeq"));
//	    suite.addTest(new MisuUnitTest("testCMisuPanelFadeIntern"));
	    suite.addTest(new MisuUnitTest("testMisuPanelDrawSeqGridBottom"));
	    suite.addTest(new MisuUnitTest("testMisuPanelDrawWorm"));
	    
	    return suite;
	}
}
