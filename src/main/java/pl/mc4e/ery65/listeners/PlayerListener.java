package pl.mc4e.ery65.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import pl.mc4e.ery65.Mc4eMultiTasks;

public class PlayerListener implements Listener {
	
	@EventHandler
	void onJoin(PlayerJoinEvent e){
		Mc4eMultiTasks.getPlayerManager().addNotLogin(e.getPlayer());
		if (Mc4eMultiTasks.getPlayerManager().addToRegisteredPlayers(e.getPlayer())){
			e.getPlayer().sendMessage("§aWpisz §b/login <haslo> §aaby sie zalogowac.");
		} else {
			e.getPlayer().sendMessage("§6Wpisz §b/register <haslo> <haslo> §6aby sie zarejestrowac.");
		}
	}
	
	@EventHandler
	void onDisconnect(PlayerQuitEvent e){
		if (Mc4eMultiTasks.getPlayerManager().SizeNotZero()){
			if (Mc4eMultiTasks.getPlayerManager().containsNotLogin(e.getPlayer())){
				Mc4eMultiTasks.getPlayerManager().removeNotLogin(e.getPlayer());
			}
		}
	}
	
	@EventHandler
	void onMove(PlayerMoveEvent e){
		if (Mc4eMultiTasks.getPlayerManager().SizeNotZero()){
			if (Mc4eMultiTasks.getPlayerManager().containsNotLogin(e.getPlayer())){
				e.setTo(e.getFrom());
			}
		}
	}
	
	@EventHandler
	void onChat(AsyncPlayerChatEvent e){
		if (Mc4eMultiTasks.getPlayerManager().SizeNotZero()){
			if (Mc4eMultiTasks.getPlayerManager().containsNotLogin(e.getPlayer())){
				e.setCancelled(true);
			} else {
				for (Player p : Mc4eMultiTasks.getPlayerManager().getNoLogin()){
					e.getRecipients().remove(p);
				}
			}
		}
	}
	
	@EventHandler
	void onCommand(PlayerCommandPreprocessEvent e){
		if (Mc4eMultiTasks.getPlayerManager().SizeNotZero()){
			String msg = e.getMessage();
			if (msg.contains("/l")|| msg.contains("log") || msg.contains("reg")||msg.contains("rej")
					||msg.contains("rejestruj")||msg.contains("register")||msg.contains("l")||msg.contains("login")){
			} else {
				e.getPlayer().sendMessage("§cNie mozesz uzyc komend przed zalogowaniem sie!");
				e.setCancelled(true);
			}
		}
	}
	

}
