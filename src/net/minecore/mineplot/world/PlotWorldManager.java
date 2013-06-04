package net.minecore.mineplot.world;

import java.util.TreeMap;

import org.bukkit.World;

public class PlotWorldManager {
	
	private TreeMap<String, PlotWorld> worlds;
	
	public PlotWorldManager(){
		worlds = new TreeMap<String, PlotWorld>();
	}
	
	public void addPlotWorld(PlotWorld pw){
		worlds.put(pw.getWorld().getName(), pw);
	}
	
	public PlotWorld getPlotWorld(String name){
		return worlds.get(name);
	}
	
	public PlotWorld getPlotWorld(World w) {
		return getPlotWorld(w.getName());
	}

	/**
	 * @return the worlds
	 */
	public TreeMap<String, PlotWorld> getWorlds() {
		return worlds;
	}

}
