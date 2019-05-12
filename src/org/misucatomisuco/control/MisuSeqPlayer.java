package org.misucatomisuco.control;

import org.misucatomisuco.model.MisuSeqGrid;
import org.misucatomisuco.model.MisuSignal;

public class MisuSeqPlayer {
	long time;
	MisuSeqGrid grid;
	MisuSignalPlayer player;
	int channel=1;
	int div;
	int resolution=300;
	int heartbeat=50;
	long t1;
	
	public MisuSeqPlayer() {
		super();
		time=System.currentTimeMillis();
	}

	public void setGrid(MisuSeqGrid grid) {
		this.grid = grid;
	}

	public void setPlayer(MisuSignalPlayer player) {
		this.player = player;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	void setTime(long t) {
		time=t;
	}

	void advance() {
		long t=System.currentTimeMillis();
		div=resolution*heartbeat;
		long div1=div/32;
//		System.out.println(t+" "+div1+" "+time%div1+">"+t%div1);
		if(time%div1>t%div1) {
			MisuSignal s=new MisuSignal(MisuSignal.NOTE_OFF,66,0);
			player.play(s, channel);
			
			s=new MisuSignal(MisuSignal.NOTE_ON,66,127);
			player.play(s, channel);
			System.out.println("click..");
		}
//		div=resolution*50;
		for(int i=0;i<grid.len;i++) {
//			if(grid.t[i]%grid.len>time%grid.len && grid.t[i]%grid.len<=t1%grid.len) {
			if(grid.t[i]%div>time%div && grid.t[i]%div<=t%div) {
				player.play(grid.ts[i], channel);
			}
		}

		time=t;
	}

	public int getHeartbeat() {
		return heartbeat;
	}

	public void setHeartbeat(int heartbeat) {
		this.heartbeat = heartbeat;
	}

	public int getResolution() {
		return resolution;
	}

	public void setResolution(int resolution) {
		this.resolution = resolution;
	}
}
