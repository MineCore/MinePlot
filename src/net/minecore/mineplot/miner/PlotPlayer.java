package net.minecore.mineplot.miner;

import java.util.ArrayList;

import net.minecore.Miner;
import net.minecore.mineplot.plot.Plot;

public class PlotPlayer{
	
	private ArrayList<Plot> plots;
	private Miner m;
	
	public PlotPlayer(Miner m){
		
		plots = new ArrayList<Plot>();
		this.m = m;
	}

	public ArrayList<Plot> getPlots() {
		return plots;
	}
	
	public boolean removePlot(Plot p){
		return plots.remove(p);
	}
	
	public boolean addPlot(Plot p){
		if(p.getOwner() != null && !p.getOwner().equals(m.getPlayerName()))
			return false;
		
		plots.add(p);
		p.setOwner(m.getPlayerName());
		return true;
	}

	public Plot getPlot(String name) {
		for(Plot p : plots)
			if(p.getName().equals(name))
				return p;
		return null;
	}

	public String getName() {
		return m.getPlayerName();
	}
	
	public Miner getMiner(){
		return m;
	}
	
	public void save(){
		
	}
}
