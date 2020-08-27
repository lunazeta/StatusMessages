package me.qwhaty.status;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.qwhaty.status.commands.DoNotDisturb;
import me.qwhaty.status.commands.Streaming;
import me.clip.placeholderapi.PlaceholderAPI;
import me.qwhaty.status.commands.Building;
import me.qwhaty.status.commands.Recording;

public class Main extends JavaPlugin implements Listener {
	
	public PluginManager manager = getServer().getPluginManager();
	
	@Override
	public void onEnable() {
		manager.registerEvents(this, this);
		new DoNotDisturb(this);
		new Streaming(this);
		new Building(this);
		new Recording(this);
		if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            new Placeholders(this).register();
            getLogger().info("PlaceholderAPI detected");
		} else {
			getLogger().warning("PlaceholderAPI is required for this plugin to work!");
			manager.disablePlugin(this);
		}
		if(manager.getPlugin("Essentials") != null) {
			Variables.essentialsDetected = true;
		} else {
			Variables.essentialsDetected = false;
		}
		getLogger().info("StatusMessages has been loaded");
	}
	
	public void onDisable() {
		
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		Player playerJoined = event.getPlayer();
		
		if(Variables.essentialsDetected) {
			Variables.currentStatus.put(playerJoined.getUniqueId(), PlaceholderAPI.setPlaceholders(playerJoined, Variables.playerNick));
		} else {
			Variables.currentStatus.put(playerJoined.getUniqueId(), playerJoined.getName());
		}
	}
	
}
