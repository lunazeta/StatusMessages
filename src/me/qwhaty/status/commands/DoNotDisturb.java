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

public class DoNotDisturb implements CommandExecutor {
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public DoNotDisturb(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("donotdisturb").setExecutor(this);
	}
	
	static List<String> doNotDisturbed =  new ArrayList<String>();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player) || !(sender.hasPermission("status.donotdisturb"))) {
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
		
		if(!doNotDisturbed.contains(playerName)) {
			
			if(Streaming.streamingEnabled.contains(playerName))
				Streaming.streamingEnabled.remove(playerName);
			if(Building.buildingEnabled.contains(playerName))
				Building.buildingEnabled.remove(playerName);
			if(Recording.recordingEnabled.contains(playerName))
				Recording.recordingEnabled.remove(playerName);
			
			if(Variables.essentialsDetected) {
				player.setPlayerListName(currentPlayerNick + " " + ChatColor.DARK_RED + ChatColor.BOLD + "*Do Not Disturb*" + ChatColor.RESET + ChatColor.WHITE);
				doNotDisturbed.add(playerName);
				Variables.currentStatus.put(player.getUniqueId(), player.getPlayerListName());
				return true;
			} else {
				player.setPlayerListName(playerName + " " + ChatColor.DARK_RED + ChatColor.BOLD + "*Do Not Disturb*" + ChatColor.RESET + ChatColor.WHITE);
				doNotDisturbed.add(playerName);
				Variables.currentStatus.put(player.getUniqueId(), player.getPlayerListName());
				return true;
			}
		} else {
			if(Variables.essentialsDetected) {
				player.setPlayerListName(currentPlayerNick);
				Variables.currentStatus.put(player.getUniqueId(), currentPlayerNick);
				doNotDisturbed.remove(playerName);
				return true;
			} else {
				player.setPlayerListName(playerName);
				Variables.currentStatus.put(player.getUniqueId(), playerName);
				doNotDisturbed.remove(playerName);
				return true;
			}
		}
	}
}