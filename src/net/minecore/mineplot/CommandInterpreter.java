package net.minecore.mineplot;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class CommandInterpreter implements CommandExecutor {
	
	private MinePlot mp;
	
	public CommandInterpreter(MinePlot minePermit) {
		this.mp = minePermit;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,	String label, String[] args) {
		
		if(args.length == 0)
			return false;
		
		if(args[0].equalsIgnoreCase("init")){


			if(args.length < 2)
				return false;


			World w = mp.getServer().getWorld(args[1]);


			if(w == null){


				if(sender instanceof ConsoleCommandSender)
					mp.log.info("Invalid world!");
				else 
					sender.sendMessage(ChatColor.RED + "Invalid World!");


				return true;
			}


			if(mp.getPWM().getPlotWorld(w) != null){


				if(sender instanceof ConsoleCommandSender)
					mp.log.info("World already initialized!");
				else 
					sender.sendMessage(ChatColor.RED + "World already initialized!");


				return true;
			}


			if(mp.initWorld(w)){


				if(sender instanceof ConsoleCommandSender)
					mp.log.info("World initialized!");
				else 
					sender.sendMessage(ChatColor.GREEN + "World initialized!");
			} else {


				if(sender instanceof ConsoleCommandSender)
					mp.log.info("Couldn't initialize world!");
				else 
					sender.sendMessage(ChatColor.RED + "Couldn't initialize world!");
			}


			return true;

		}
		
		return false;
	}

}
