package pl.mc4e.ery65.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.mc4e.ery65.Mc4eMultiTasks;
import pl.mc4e.ery65.utils.MD5HASH;
import pl.mc4e.ery65.utils.Mc4ePlayer;

public class RegisterCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (cs instanceof Player){
			if (args.length == 2){
				if (Mc4eMultiTasks.getPlayerManager().containsNotLogin((Player)cs)){
					if (!Mc4eMultiTasks.getPlayerManager().isRegistered((Player)cs)){
						if (args[0].equals(args[1])){
							Mc4ePlayer a = new Mc4ePlayer(cs.getName());
							a.register(MD5HASH.getHashedPassword(args[0]));
							if (Mc4eMultiTasks.getPlayerManager().addToRegisteredPlayers((Player)cs)){
								Mc4eMultiTasks.getPlayerManager().removeNotLogin((Player)cs);
								cs.sendMessage("§aZarejestrowales sie pomyslnie!");
								return true;
							} else {
								cs.sendMessage("§cBlad podczas rejestracji! Skontaktuj sie z administratorem");
								return true;
							}
						} else {
							cs.sendMessage("§cWpisane hasla nie pasuja do siebie! sprobuj jeszcze raz!");
							return true;
						}
					} else {
						cs.sendMessage("§cJuz sie rejestrowales!");
						return true;
					}
				} else {
					//cs.sendMessage("§cJuz sie rejestrowales!");
					return true;
				}
			} else {
				cs.sendMessage("§cPoprawne uzycie: §b/" + label + " <haslo> <powtorz haslo>");
				return true;
			}
		} else {
			cs.sendMessage("§cKomenda tylko dla gracza");
			return true;
		}
		//return false;
	}
	
}
