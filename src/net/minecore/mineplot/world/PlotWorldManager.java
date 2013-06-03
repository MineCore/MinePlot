package net.minecore.mineplot.world;

import java.util.TreeMap;

import org.bukkit.World;

public class PlotWorldManager {
	
	private TreeMap<String, PlotWorld> worlds;
	
	public PlotWorldManager(){
		worlds = new TreeMap<String, PlotWorld>();
	}
	
	public void addPermitWorld(PlotWorld pw){
		worlds.put(pw.getWorld().getName(), pw);
	}
	
	public PlotWorld getPermitWorld(String name){
		return worlds.get(name);
	}
	
	public PlotWorld getPermitWorld(World w) {
		return getPermitWorld(w.getName());
	}

	/**
	 * @return the worlds
	 */
	public TreeMap<String, PlotWorld> getWorlds() {
		return worlds;
	}

}
