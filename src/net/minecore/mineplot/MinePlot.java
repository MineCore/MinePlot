package net.minecore.mineplot;
 
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import net.minecore.Metrics;
import net.minecore.MineCore;
import net.minecore.mineplot.player.PlotPlayer;
import net.minecore.mineplot.player.PlotPlayerManager;
import net.minecore.mineplot.world.PlotWorld;
import net.minecore.mineplot.world.PlotWorldManager;

import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class MinePlot extends JavaPlugin {

	public Logger log;
	private FileConfiguration conf;
	private PlotWorldManager pwm;
	private PlotPlayerManager mm;
	
	@Override
	public void onLoad(){
		log = this.getLogger();
		
		conf = this.getConfig();
		conf.options().copyDefaults(true);
		saveConf();
		
		mm = new PlotPlayerManager(this);
		
		log.info("Loaded!");
	}
	
	@Override
	public void onDisable(){
		
		for(PlotPlayer p : mm.getPlotPlayers())
			if(!p.savePlots())
				log.warning("Couldn't save data for player " + p.getName());
	}
	
	public void save(){
		
		for(String name : pwm.getWorlds().keySet()){
			log.info("Saving world " + name);
			pwm.getPlotWorld(name).save(conf.getConfigurationSection("worlds." + name));
		}
		
		log.info("Worlds saved");
		
		saveConf();
	}
	
	private void loadWorlds(){
		
		ConfigurationSection worlds = conf.getConfigurationSection("worlds");
		if(worlds == null)
			worlds = conf.createSection("worlds");
		
		pwm = new PlotWorldManager();
		
		for(String s : worlds.getKeys(false)){
			World w;
			if((w = this.getServer().getWorld(s)) == null)
				log.warning("Configuration values for world " + s + " can't be loaded because the world doesn't exist.");
			else{
				log.info("Loading world " + s);
				pwm.addPlotWorld(PlotWorld.getNewPlotWorld(worlds.getConfigurationSection(s), w));
			}
		}
	}
	
	public boolean initWorld(World w){
		
		if(pwm.getPlotWorld(w) != null)
			return false;
		
		ConfigurationSection worlds = conf.getConfigurationSection("worlds");
		pwm.addPlotWorld(PlotWorld.getNewPlotWorld(worlds.createSection(w.getName()), w));
		
		saveConf();
		
		return true;
	}
	
	private void saveConf(){
		try {
			conf.save(this.getDataFolder().getPath() + File.separator + "config.yml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onEnable(){
		
		loadWorlds();
		
		saveConf();
		
		
		getCommand("plot").setExecutor(new PlotCommandInterpreter(this));
		getCommand("mineplot").setExecutor(new CommandInterpreter(this));
		
		this.getServer().getPluginManager().registerEvents(new WorldListener(this), this);
		
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		log.info("Enabled!");
		
		
	}

	/**
	 * @return the pwm
	 */
	public PlotWorldManager getPWM() {
		return pwm;
	}

	public PlotPlayerManager getPlotPlayerManager() {
		return mm;
	}

	public MineCore getMineCore() {
		return ((MineCore)getServer().getPluginManager().getPlugin("MineCore"));
	}
}
