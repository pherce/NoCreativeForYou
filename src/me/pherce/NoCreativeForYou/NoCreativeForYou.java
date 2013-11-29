package me.pherce.NoCreativeForYou;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class NoCreativeForYou extends JavaPlugin implements Listener {
	
	final String BYPASS_PERM = "noCreativeForYou.bypass";
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
        System.out.println("NoCreativeForYou enabled");
    }
    
    @Override
	public void onDisable() {
        System.out.println("NoCreativeForYou disabled");
    }
   
    //Detects players switching from Survival Mode to Creative Mode and ban them if they're not op.
    @EventHandler
	public void gameModeSwitch(PlayerGameModeChangeEvent event){
    	Player player = (Player)event.getPlayer();
    	
    	if(player.hasPermission(BYPASS_PERM) || player.isOp())
    		return;
    	
		if(event.getNewGameMode() == GameMode.CREATIVE){
			event.setCancelled(true);
			this.getServer().banIP(player.getAddress().getAddress().getHostAddress());
			player.setBanned(true);
        	player.kickPlayer("No hacking in Creative mode! NO SOUP FOR YOU!");
        	
        	this.getServer().broadcastMessage(ChatColor.RED.toString() + 
        			player.getDisplayName() + " was banned for cheating in Creative Mode!");
        }
    }
    
    //Detects Creative Mode on PlayerJoin and bans them if they're not op. Useful if players hacked Creative Mode before the server ran this plugin.
    @EventHandler
	public void playerJoin(PlayerJoinEvent event){
       	Player player = (Player)event.getPlayer();
       	
    	if(player.hasPermission(BYPASS_PERM) || player.isOp())
    		return;
       	
		if(player.getGameMode() == GameMode.CREATIVE){
			this.getServer().banIP(player.getAddress().getAddress().getHostAddress());
			player.setBanned(true);
        	player.kickPlayer("No hacking in Creative mode! NO SOUP FOR YOU!");
        	
        	this.getServer().broadcastMessage(ChatColor.RED.toString() + 
        			player.getDisplayName() + " was banned for cheating in Creative Mode!");      	
        	
        }
    }
}
