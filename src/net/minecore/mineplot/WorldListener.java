package net.minecore.mineplot;

import net.minecore.mineplot.player.PlotPlayer;
import net.minecore.mineplot.plot.Plot;
import net.minecore.mineplot.world.PlotWorld;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class WorldListener implements Listener {
	
	private MinePlot mp;

	public WorldListener(MinePlot mp){
		this.mp = mp;
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		PlotPlayer m = mp.getPlotPlayerManager().getPlotPlayer(e.getPlayer());
		
		PlotWorld pw = mp.getPWM().getPlotWorld(e.getBlock().getLocation().getWorld().getName());
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
