package org.misucatomisuco.model;

public class MisuInitSongs {
	static MisuScaleBuilder sb = new MisuScaleBuilder();
	final String[][] song = { {
	/*
	 * Intro: (G) (Em) (C) (D)
	 * 
	 * (G) This street that we (Em) live on (C) Ain't no yellow brick (D) road
	 * (G) This paycheck that I (Em) bring home (C) Sure ain't no pot of (D)
	 * gold (C) (C/B) (Am) When people dream they don't (G/B) wish they were us
	 * (C) (C/B) (Am) But they don't know we got (D) More than en(C)ough, (D)
	 * we're
	 * 
	 * (G) Somewhere un(G/F#)der the (Em) rainbow Just watching the (C) late
	 * show Living on (D) love (G) Dancing slow (G/F#) to the (Em) radio Just
	 * (C) holding on (Am) Some(G/B)where (C) un(D)der the (G) rainbow.
	 * 
	 * Some folks spend their whole life Dreaming about the other side Where the
	 * rain never falls and those bluebirds fly But I take forever where the
	 * skies aren"t always blue 'Cause when I'm in your arms I know dreams come
	 * true
	 */
	"Raga Jam Rainbow", 
	"G major", 
	"F# major",
	"E minor", 
	"C major", 
	"D major", 
	"A minor", 
	"B major", 
	"C major" 
	}, {
		"Sun Rise Raga",
		"E minor",
		"G seven",
		"C maj7",
		"F# m7",
		"B seven",
		"F seven",
		"E m9",
		"E add9"
	}, {
		"Sun Up Again",
		"F minor",
		"G# seven",
		"C# maj7",
		"G m7",
		"C seven",
		"F# seven",
		"F m9",
		"F add9"
	}, {
		"So far from me",
		"C major",
		"D major",
		"A minor",
		"G major",
		"D major",
		"C major",
		"A minor",
		"G major"
	}, {
		"hello where is the pary",
		"E major",
		"A major",
		"G major",
		"C major",
		"E maj7",
		"A maj7",
		"G maj7",
		"C maj7"
	}, {
		"Summer in the city",
		"D six",
		"G seven",
		"A seven",
		"D seven",
		"G seven",
		"C 7sus4",
		"F 7sus4",
		"F seven",
		"A seven",
		"G seven",
		"E nine"
	}, {
		"Raga Jazz",
		"A 6add9",
		"D seven",
		"E seven",
		"E nine",
		"G# seven",
		"C# seven",
		"F# seven",
		"D seven",
		"G seven",
		"C seven",
		"F 7sus4"
	}, {
		"Ebbe Flut",
		"A minor",
		"E major",
		"G major",
		"D minor",
		"C major",
		"D minor",
		"C major",
		"F major"
	}, {
		"am horizont",
		"F major",
		"G minor",
		"A# major",
		"C major",
		"F maj7",
		"G mmaj7",
		"A# maj7",
		"C maj7"
	}, {
		"moral im bezirk",
		"A major",
		"G major",
		"D major",
		"E major",
		"A major",
		"C major",
		"B minor",
		"E major"
	}
	};

	public MisuSong[] get() {
		System.out.println("Generate songs.xml from HC init");
		MisuSong[] songs=new MisuSong[song.length];
		for(int k=0;k<songs.length;k++) {
			MisuSong s = new MisuSong(song[k].length, k);
			s.setName(song[k][0]);
			s.setSTi(new MisuScale(50, 8, 1), 0);
			for (int i = 1; i < song[k].length; i++) {
				s.setSTi(new MisuScale(50,3,1), i); //pre init
				String[] p = song[k][i].split(" ");
				int base = 12;
				if (p[0].contains("-----")) {
					base += 0;
				} else if (p[0].contains("C#")) {
					base += 1;
				} else if (p[0].contains("D#")) {
					base += 3;
				} else if (p[0].contains("F#")) {
					base += 6;
				} else if (p[0].contains("G#")) {
					base += 8;
				} else if (p[0].contains("A#")) {
					base += 10;
				} else if (p[0].contains("C") ) {
					base += 0;
				} else if (p[0].contains("D") ) {
					base += 2;
				} else if (p[0].contains("E")) {
					base += 4;
				} else if (p[0].contains("F")) {
					base += 5;
				} else if (p[0].contains("G")) {
					base += 7;
				} else if (p[0].contains("A")) {
					base += 9;
				} else if (p[0].contains("B")) {
					base += 11;
				} else {
					base=-1;
					System.out.println("Unknown "+p[0]);
				}

				if(base>=0) {
					int[] ch = sb.getChord(p[1]);
					if (ch != null) {
						int[] sc = sb.getScale(p[1]);
						if (sc != null) {
							s.setSTi(new MisuScale(base, sc, ch), i);
						}
					} else System.out.println("Unknown "+p[1]);
				}
			}
			songs[k]=s;
		}
		return songs;
	}
}
