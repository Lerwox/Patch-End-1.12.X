package fr.lerwox.patchend;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public class PlListener implements Listener {

	@EventHandler
	public void onDragonDeath(EntityDeathEvent event) {//Dragon meurt on fait la commande essentials:butcher endercrystals world
		
		if (!(event.getEntity() instanceof EnderDragon)) return;
		
		EnderDragon enderdragon = (EnderDragon) event.getEntity();
		String wname =  enderdragon.getWorld().getName();
		
		ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
		String command = "essentials:butcher endercrystals "+wname;
		//System.out.println(command);
		Bukkit.dispatchCommand(console, command);
	}
	
	@EventHandler
	public void OnEndercystalDamage(ProjectileHitEvent event) {//quand un fleche touche un end crystal on remove l'endercrystal
		if (event.getHitEntity() == null) return;
		Entity en = event.getHitEntity();
		World w = en.getWorld();
		w.getEnvironment();
		
		if (!(event.getEntity() instanceof Arrow && event.getHitEntity() instanceof EnderCrystal)) return;
		
		if (Environment.THE_END != null) {
			Bukkit.getWorld(w.getName()).createExplosion(en.getLocation(), 3.0F);
			if (en.isDead()) return;
			en.remove();
			if (event.getEntity().isDead()) return;
			event.getEntity().remove();
		}
	}
}

class Commands implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if (sender instanceof Player) {
			Player p = (Player) sender;
			p.getWorld().getEnvironment();
			
			if(cmd.getName().equalsIgnoreCase("endercrystal")) {// quand /endercrystal on check le monde et les entités proche du joueur, si EnderCrystal alors on remove l'entité
				if(Environment.THE_END != null) {
					List<Entity> near = ((Player) sender).getNearbyEntities(2, 2, 2);
					for(Entity en : near) {
						if (en.getType() == EntityType.ENDER_CRYSTAL) {
							en.remove();
							p.sendMessage("§e[§c!§e]§a EnderCrystal correctement supprimé");
							Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Command /endercrystal perform");
							return true;
						}
					}
					p.sendMessage("§e[§c!§e]§a Aucun EnderCrystal n'a été supprimé");
				}
				
			}
		}
		return false;
	}
	
}