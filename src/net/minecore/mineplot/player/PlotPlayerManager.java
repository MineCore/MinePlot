package net.minecore.mineplot.player;

import java.util.ArrayList;

import net.minecore.mineplot.MinePlot;

import org.bukkit.entity.Player;

public class PlotPlayerManager {
	
	private ArrayList<PlotPlayer> miners;
	private MinePlot mineplot;
	
	public PlotPlayerManager(MinePlot m){
		this.mineplot = m;
		
		miners = new ArrayList<PlotPlayer>();
	}
	
	public PlotPlayer getPlotPlayer(String name){
		for(PlotPlayer m : miners){
			if(m.getName().equals(name))
				return m;
		}
		
		PlotPlayer m = new PlotPlayer(mineplot.getMineCore().getMiner(name), mineplot);
		
		miners.add(m);
		
		return m;
	}

	public PlotPlayer getPlotPlayer(Player player) {
		return getPlotPlayer(player.getName());
		
	}

	public ArrayList<PlotPlayer> getPlotPlayers() {
		return miners;
	}
}
