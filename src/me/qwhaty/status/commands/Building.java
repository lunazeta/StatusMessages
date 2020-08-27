package me.qwhaty.status.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import me.qwhaty.status.Main;
import me.qwhaty.status.Variables;

public class Building implements CommandExecutor {
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public Building(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("building").setExecutor(this);
	}
	
	static List<String> buildingEnabled =  new ArrayList<String>();
	
	String buildingSuffix = " " + ChatColor.AQUA + (ChatColor.BOLD + "*Building*" + ChatColor.RESET + ChatColor.WHITE);
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player) || !(sender.hasPermission("status.building")))  {
			if(sender.isOp()) {
			} else {
				sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to run the command:");
				return false;
			}
		}
	
		Player player = (Player) sender;
		String playerName = sender.getName();
		String currentPlayerNick = null;
		if(Variables.essentialsDetected) {
			currentPlayerNick = PlaceholderAPI.setPlaceholders(player, Variables.playerNick);
		}
		
		if(!buildingEnabled.contains(playerName)) {
			
			if(DoNotDisturb.doNotDisturbed.contains(playerName))
				DoNotDisturb.doNotDisturbed.remove(playerName);
			if(Streaming.streamingEnabled.contains(playerName))
				Streaming.streamingEnabled.remove(playerName);
			if(Recording.recordingEnabled.contains(playerName))
				Recording.recordingEnabled.remove(playerName);
			
			if(Variables.essentialsDetected) {
				player.setPlayerListName(currentPlayerNick + buildingSuffix);
				Variables.currentStatus.put(player.getUniqueId(), player.getPlayerListName());
				buildingEnabled.add(playerName);
				return true;
			} else {
				player.setPlayerListName(playerName + buildingSuffix);
				Variables.currentStatus.put(player.getUniqueId(), player.getPlayerListName());
				buildingEnabled.add(playerName);
				return true;
			}
		} else {
			if(Variables.essentialsDetected) {
				player.setPlayerListName(currentPlayerNick);
				Variables.currentStatus.put(player.getUniqueId(), currentPlayerNick);
				buildingEnabled.remove(playerName);
				return true;
			} else {
				player.setPlayerListName(playerName);
				Variables.currentStatus.put(player.getUniqueId(), playerName);
				buildingEnabled.remove(playerName);
				return true;
			}
		}
	}

}