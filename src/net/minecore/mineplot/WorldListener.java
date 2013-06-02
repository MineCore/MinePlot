package net.minecore.mineplot;

import net.minecore.mineplot.miner.Miner;
import net.minecore.mineplot.plot.Plot;
import net.minecore.mineplot.world.PermitWorld;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class WorldListener implements Listener {
	
	private MinePermit mp;

	public WorldListener(MinePermit mp){
		this.mp = mp;
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		Miner m = mp.getMinerManager().getMiner(e.getPlayer());
		
		PermitWorld pw = mp.getPWM().getPermitWorld(e.getBlock().getLocation().getWorld().getName());
		if(pw == null)
			return;
		Plot plot = pw.getContainingPlot(e.getBlock().getLocation());
		
		if(plot == null){
			e.getPlayer().sendMessage(ChatColor.RED + "Sorry! You cannot mine here. If you would like to buy a plot use the command /plot");
			e.setCancelled(true);
			return;
		}
		
		if(!plot.canUse(e.getPlayer())){
			e.getPlayer().sendMessage(ChatColor.RED + "Sorry! You cannot mine here, this plot is owned by someone else. If you would like to use it, they can give you permission.");
			e.setCancelled(true);
			return;
		}
	}
}
