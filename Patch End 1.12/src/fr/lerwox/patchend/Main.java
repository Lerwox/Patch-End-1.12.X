package fr.lerwox.patchend;

import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin{
	
	@Override
	public void onEnable() {
		getCommand("endercrystal").setExecutor(new Commands());
		getServer().getPluginManager().registerEvents(new PlListener(), this);	
	}
}