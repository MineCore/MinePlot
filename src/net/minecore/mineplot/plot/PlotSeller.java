package net.minecore.mineplot.plot;

import net.minecore.mineplot.MinePlot;
import net.minecore.mineplot.player.PlotPlayer;
import net.minecore.mineplot.world.PlotWorld;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlotSeller implements Listener {

	private MinePlot mp;

	public PlotSeller(MinePlot mp) {
		this.mp = mp;
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onSignChanged(SignChangeEvent e) {

		PlotWorld pw = mp.getPWM().getPlotWorld(
				e.getBlock().getLocation().getWorld().getName());
		if (pw == null)
			return;

		mp.getPlotPlayerManager().getPlotPlayer(e.getPlayer());
		Plot plot = pw.getContainingPlot(e.getBlock().getLocation());

		if (plot == null)
			return;
		
		if (!plot.getOwner().equals(e.getPlayer().getName()))
			return;

		if (!e.getLine(0).equalsIgnoreCase("MinePlot"))
			return;

		if (!e.getLine(1).equalsIgnoreCase("sell"))
			return;

		int price;

		if (e.getLine(2).length() == 0) {
			price = plot.calculateCost();
		} else {
			try {
				price = Integer.parseInt(e.getLine(2));
			} catch (NumberFormatException e1) {
				e.getPlayer().sendMessage(ChatColor.RED + "Invalid cost!");
				return;
			}
		}
		
		e.setLine(0, "Cost " + ChatColor.GOLD + price);
		e.setLine(1, "From " + plot.getLocation1().getBlockX() + ":" + plot.getLocation1().getBlockZ());
		e.setLine(2, " to " + plot.getLocation2().getBlockX() + ":" + plot.getLocation2().getBlockZ());
		e.setLine(3, "Click to buy!");
		
		if(plot.isBeingSold())
			e.getPlayer().getWorld().getBlockAt(plot.getSellSignLocation()).setType(Material.AIR);
		
		sell(plot, price, e.getBlock().getLocation());
		
		e.getPlayer().sendMessage("Plot marked as for sale!");

	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {

		if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			return;
		
		if(!e.getClickedBlock().getType().equals(Material.SIGN_POST) && !e.getClickedBlock().getType().equals(Material.WALL_SIGN))
			return;
		
		PlotWorld pw = mp.getPWM().getPlotWorld(e.getPlayer().getWorld());
		
		if(pw == null)
			return;
		
		mp.getPlotPlayerManager().getPlotPlayer(e.getPlayer());
		Plot p = pw.getContainingPlot(e.getClickedBlock().getLocation());
		
		if(p == null)
			return;
		
		if(!p.isBeingSold())
			return;
		
		if(!p.getSellSignLocation().equals(e.getClickedBlock().getLocation()))
			return;
		
		if(!mp.getMineCore().getEconomyManager().charge(e.getPlayer(), p.getSellPrice())){
			e.getPlayer().sendMessage(ChatColor.RED + "You dont have enough money to buy this!");
			return;
		}
		
		mp.getMineCore().getEconomyManager().give(mp.getServer().getPlayer(p.getOwner()), p.getSellPrice());
		
		p.getAllowedPlayers().clear();
		p.setSold();
		e.getClickedBlock().setType(Material.AIR);
		
		PlotPlayer pp = mp.getPlotPlayerManager().getPlotPlayer(e.getPlayer());
		
		Player old = mp.getServer().getPlayerExact(p.getOwner());
		if(old != null)
			old.sendMessage(ChatColor.GOLD + "Your plot " + p.getName() + " has been sold!");
		
		mp.getPlotPlayerManager().getPlotPlayer(old).removePlot(p);
		
		String base = "Plot";
		int num = 1;
		while (pp.getPlot(base + num) != null)
			num++;

		p.setName(base + num);
		pp.addPlot(p);
		e.getPlayer().sendMessage(ChatColor.GREEN + "Plot purchased! It is now called " + p.getName() + ", you can change the name with /plot <plot_name> rename <new_name>");

	}

	public void sell(Plot p, int price, Location sign) {
		p.sell(price, sign);
	}
}
