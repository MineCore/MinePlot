package net.minecore.mineplot.miner;

import java.util.ArrayList;

import net.minecore.mineplot.MinePlot;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class MinerManager {
	
	private ArrayList<Miner> miners;
	private MinePlot minepermmit;
	
	public MinerManager(MinePlot m){
		this.minepermmit = m;
		
		miners = new ArrayList<Miner>();
	}
	
	public Miner getMiner(String name){
		for(Miner m : miners){
			if(m.getName().equals(name))
				return m;
		}
		
		Miner m = new Miner(name);
		
		miners.add(m);
		
		return m;
	}
	
	public Permit getPermit(Location l){
		for(Miner m : miners){
			Permit p = m.getPermit(l);
			if(p != null) return p;
		}
		
		return null;
	}

	public Miner getMiner(Player player) {
		return getMiner(player.getName());
		
	}
}
