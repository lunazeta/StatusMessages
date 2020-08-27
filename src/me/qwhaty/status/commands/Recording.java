package me.qwhaty.status.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.qwhaty.status.commands.DoNotDisturb;
import me.qwhaty.status.commands.Streaming;
import me.qwhaty.status.commands.Building;
import me.clip.placeholderapi.PlaceholderAPI;
import me.qwhaty.status.Main;
import me.qwhaty.status.Variables;

public class Recording implements CommandExecutor {
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public Recording(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("recording").setExecutor(this);
	}
	
	static List<String> recordingEnabled =  new ArrayList<String>();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player) || !(sender.hasPermission("status.recording"))) {
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
		
		if(!recordingEnabled.contains(playerName)) {
			
			if(DoNotDisturb.doNotDisturbed.contains(playerName))
				DoNotDisturb.doNotDisturbed.remove(playerName);
			if(Streaming.streamingEnabled.contains(playerName))
				Streaming.streamingEnabled.remove(playerName);
			if(Building.buildingEnabled.contains(playerName))
				Building.buildingEnabled.remove(playerName);
			
			
			if(Variables.essentialsDetected) {
				player.setPlayerListName(currentPlayerNick + " " + ChatColor.RED + ChatColor.BOLD + "*Recording*" + ChatColor.RESET + ChatColor.WHITE);
				recordingEnabled.add(currentPlayerNick);
				Variables.currentStatus.put(player.getUniqueId(), player.getPlayerListName());
				return true;
			} else {
				player.setPlayerListName(playerName + " " + ChatColor.RED + ChatColor.BOLD + "*Recording*" + ChatColor.RESET + ChatColor.WHITE);
				recordingEnabled.add(playerName);
				Variables.currentStatus.put(player.getUniqueId(), player.getPlayerListName());
				return true;
			}
		} else {
			if(Variables.essentialsDetected) {
				player.setPlayerListName(currentPlayerNick);
				Variables.currentStatus.put(player.getUniqueId(), currentPlayerNick);
				recordingEnabled.remove(playerName);
				return true;
			} else {
				player.setPlayerListName(playerName);
				Variables.currentStatus.put(player.getUniqueId(), playerName);
				recordingEnabled.remove(playerName);
				return true;
		}
	}
}
}
