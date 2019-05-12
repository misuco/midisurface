package org.misucatomisuco.model;

import java.util.ArrayList;
import java.util.Iterator;

public class MisuScaleBuilder {
	String[] noteName = { "c", "c#", "d", "d#", "e", "f", "f#", "g", "g#", "a",
			"a#", "b" };
	
	double[] noteF=new double[noteName.length];

	ArrayList<MisuChord> chords;
	ArrayList<MScale> scales;

	String[] chordNames2 = { "terz", "quart", "quint", "sebte",
			"major", "six", "maj7", 
			"nine", "6add9", "maj9", "minor", 
			"m7", "m9", "7sus4", "mmaj7", 
			"dim", "m6", "aug",	"seven", 
			"add9", "11", "13",
			"7-5", "7maj5", "sus" };
//	 "-5", 
	int[][] chords2 = { 
			{ 3 }, { 4 }, { 5 }, { 7 }, { 4, 7 }, { 4, 7, 9 }, { 4, 7, 11 },
			{ 4, 7, 10, 14, 17 }, { 4, 7, 9, 14 }, { 4, 7, 11, 14 }, { 3, 7 },
			{ 3, 7, 10 }, { 3, 7, 10, 14 }, { 5, 7, 10 }, { 3, 7, 11 },
			{ 3, 6 }, { 3, 7, 9 }, { 4, 8 }, { 4, 7, 10 }, { 4, 7, 10, 14 },
			{ 4, 7, 12 }, { 4, 7, 10, 14, 17, 21 }, 
			{ 4, 6, 10 }, { 4, 8, 10 }, { 5, 7 }
// { 4, 6 },
	};

	/*
	3,4,5   6,7,8   9,10,11,12    14,  17,   21
	*/
	
	String[] scaleName2 = { "dur", "moll rein", "moll harmonisch",
			"zigeuner moll", "penta", "hexa", "hepta", "penta dur",
			"penta mol", "penta kuomi", "penta iwato", "penta pelog",
			"penta hyoio", "penta china", "penta egypt", "nine" };

	int[] DUR = { 2, 2, 1, 2, 2, 2, 1 };
	int[] MOLL_R = { 2, 1, 2, 2, 1, 2, 2 };
	int[] MOLL_H = { 2, 1, 2, 2, 1, 3, 1 };
	int[] MOLL_Z = { 2, 1, 3, 1, 1, 3, 1 };
	int[] DORISCH = { 2, 1, 2, 2, 2, 1, 2 };
	int[] PENTA = { 2, 3, 4, 3 };
	int[] HEXA = { 2, 3, 2, 2, 3 };
	int[] HEPTA = { 2, 3, 1, 2, 1, 3 };
	int[] PENTA_DUR = { 2, 2, 3, 2, 3 };
	int[] PENTA_MOL = { 3, 2, 2, 3, 2 };
	int[] PENTA_KUOMI = { 1, 4, 4, 1, 2 };
	int[] PENTA_IWATO = { 1, 4, 1, 4, 2 };
	int[] PENTA_PELOG = { 1, 2, 4, 1, 4 };
	int[] PENTA_HYOJO = { 2, 3, 2, 2, 3 };
	int[] PENTA_CHINA = { 4, 2, 1, 4, 1 };
	int[] PENTA_EGYPT = { 2, 3, 2, 3, 2 };
	int[] NINE = { 2, 2, 1, 2, 3, 2 };
	int[][] scales2 = { DUR, MOLL_R, MOLL_H, MOLL_Z, PENTA, HEXA, HEPTA,
			PENTA_DUR, PENTA_MOL, PENTA_KUOMI, PENTA_IWATO, PENTA_PELOG,
			PENTA_HYOJO, PENTA_CHINA, PENTA_EGYPT, NINE };

	public MisuScaleBuilder() {
		super();
		
		for(double i=0;i<noteName.length;i++) {
			noteF[(int)i]=8*Math.pow(2,(i/noteName.length));
		}
		
		chords = new ArrayList<MisuChord>();
		scales = new ArrayList<MScale>();
		
		// pick the 3 finger chords first
		for (int k=1;k<7;k++) {
			for (int i = 0; i < chords2.length; i++) {
				if(chords2[i].length==k) {
					MisuChord t = new MisuChord(chords2[i], chordNames2[i], false);
//					System.out.println(t.getName() + " " + t.getIntVal() + " ");
//					System.out.println(Integer.toBinaryString(t.getIntVal()));

					MScale s1 = null;
					MScale s2 = null;
					for (int j = 0; j < scales2.length; j++) {
						MisuChord t1 = new MisuChord(scales2[j], scaleName2[j], true);
						if (t.isComp(t1)) {
//							System.out.print(Integer.toBinaryString(t1.getIntVal()));
//							System.out.println(" " + t1.getName() + " "
//									+ t1.getIntVal());
							s1 = new MScale(scales2[j], scaleName2[j]);
							if(s2==null || s1.getF().length<s2.getF().length) {
								s2=s1;
							}
							t.getScales().add(s1);
						}
					}
					if(s1!=null) {
						chords.add(t);
						scales.add(s2);
					}
				}
			}
		}
		System.out.println("MisuScaleBuilder default loaded ...");

	}

	public void print() {
		Iterator<MisuChord> i = chords.iterator();
		while (i.hasNext()) {
			MisuChord c = (MisuChord) i.next();
			System.out.print(chords.indexOf(c));
			System.out.println(c.getName());
			int st = c.getF().length;
			for (int j = 0; j < st; j++) {
				System.out.print(c.getF()[j] + " ");
			}
			System.out.println("***");
		}

	}

	public String getScalename(int i) {
		return scales.get(i).getName();
	}

	public String getChordname(int i) {
		return chords.get(i).getName();
	}

	public int[] getScale(int i) {
		return scales.get(i).getF();
	}

	public int[] getChord(int i) {
		return chords.get(i).getF();
	}

	public int[] getChord(String name) {
		for(MisuChord c : chords) {
			if(c.getName().contains(name)) {
				return c.getF();
			}
		}
		return null;
	}

	public int[] getScale(String name) {
		for(MisuChord c : chords) {
			if(c.getName().contains(name)) {
				ArrayList<MScale> a= c.getScales();
				return a.get(0).getF();
			}
		}
		return null;
	}

	public String getNoteName(int i) {
		return noteName[i%noteName.length];
	}

	public double getNoteF(int i) {
		return noteF[i%noteF.length]*Math.pow(2,Math.round(i/noteF.length));
	}

}
