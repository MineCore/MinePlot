package net.minecore.mineplot.world;

import java.util.TreeMap;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;

public class BlockPriceDefinition {
	
	TreeMap<Material, Integer> blocks;
	
	public BlockPriceDefinition(){
		blocks = new TreeMap<Material, Integer>();
	}
	
	public int getBlockPrice(Block b){
		Integer i = blocks.get(b);
		return i == null? 0 : i;
	}
	
	public void setBlockPrice(Material id, int cost){
		blocks.put(id, cost);
	}

	
	public static BlockPriceDefinition getNewDefinition(ConfigurationSection cs){
		BlockPriceDefinition b = new BlockPriceDefinition();
		
		cs.addDefault(Material.LAPIS_ORE.name(), 50);
		cs.addDefault(Material.GOLD_ORE.name(), 30);
		cs.addDefault(Material.IRON_ORE.name(), 20);
		cs.addDefault(Material.COAL_ORE.name(), 10);
		cs.addDefault(Material.DIAMOND_ORE.name(), 150);
		cs.addDefault(Material.REDSTONE_ORE.name(), 80);
		cs.addDefault(Material.EMERALD_ORE.name(), 130);
		
		for(String s : cs.getKeys(false)){
			try{
				b.setBlockPrice(Material.matchMaterial(s), cs.getInt(s));
				
			} catch (NumberFormatException e){
				cs.set(s, null);
			}
		}
		
		return b;
		
	}

	public void save(ConfigurationSection cs) {
		
		for(Material i : blocks.keySet()){
			cs.set(i.name(), blocks.get(i));
		}
		
	}
}
