package net.minecore.mineplot.miner;

import java.util.ArrayList;

import net.minecore.mineplot.plot.Plot;

public class Miner{
	
	private ArrayList<Plot> plots;
	private String name;
	
	public Miner(String name){
		
		this.name = name;
		plots = new ArrayList<Plot>();
		
	}

	public ArrayList<Plot> getPlots() {
		return plots;
	}
	
	public boolean removePlot(Plot p){
		return plots.remove(p);
	}
	
	public boolean addPlot(Plot p){
		if(p.getOwner() != null && !p.getOwner().equals(name))
			return false;
		
		plots.add(p);
		p.setOwner(name);
		return true;
	}

	public Plot getPlot(String name) {
		for(Plot p : plots)
			if(p.getName().equals(name))
				return p;
		return null;
	}

	public String getName() {
		return name;
	}
}
