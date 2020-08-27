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

public class Streaming implements CommandExecutor {
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public Streaming(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("streaming").setExecutor(this);
	}
	
	static List<String> streamingEnabled =  new ArrayList<String>();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player) || !(sender.hasPermission("status.streaming"))) {
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
		
		if(!streamingEnabled.contains(playerName)) {
			
			if(DoNotDisturb.doNotDisturbed.contains(playerName))
				DoNotDisturb.doNotDisturbed.remove(playerName);
			if(Building.buildingEnabled.contains(playerName))
				Building.buildingEnabled.remove(playerName);
			if(Recording.recordingEnabled.contains(playerName))
				Recording.recordingEnabled.remove(playerName);
			
			if(Variables.essentialsDetected) {
				player.setPlayerListName(currentPlayerNick + " " + ChatColor.DARK_PURPLE + ChatColor.BOLD + "*Streaming*" + ChatColor.RESET + ChatColor.WHITE);
				streamingEnabled.add(currentPlayerNick);
				Variables.currentStatus.put(player.getUniqueId(), player.getPlayerListName());
				return true;
			} else {
				player.setPlayerListName(playerName + " " + ChatColor.DARK_PURPLE + ChatColor.BOLD + "*Streaming*" + ChatColor.RESET + ChatColor.WHITE);
				streamingEnabled.add(playerName);
				Variables.currentStatus.put(player.getUniqueId(), player.getPlayerListName());
				return true;
			}
		} else {
			if(Variables.essentialsDetected) {
				player.setPlayerListName(currentPlayerNick);
				Variables.currentStatus.put(player.getUniqueId(), currentPlayerNick);
				streamingEnabled.remove(playerName);
				return true;
			} else {
				player.setPlayerListName(playerName);
				Variables.currentStatus.put(player.getUniqueId(), playerName);
				streamingEnabled.remove(playerName);
				return true;
		}
	}
}
}