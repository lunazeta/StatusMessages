package me.qwhaty.status;

import org.bukkit.OfflinePlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.qwhaty.status.Variables;
import me.qwhaty.status.Main;

public class Placeholders extends PlaceholderExpansion {
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public Placeholders(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean persist() {
		return true;
	}
	
    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public String getAuthor(){
        return "qwhaty";
    }

    @Override
    public String getIdentifier(){
        return "playerstatus";
    }

    @Override
    public String getVersion(){
        return "1.0.0";
    }
  
    @Override
    public String onRequest(OfflinePlayer player, String identifier){
  
        // %playerstatus_status%
        if(identifier.equals("status")){
            return Variables.currentStatus.get(player.getUniqueId());
        }

        // We return null if an invalid placeholder
        // was provided
        return null;
    }
}