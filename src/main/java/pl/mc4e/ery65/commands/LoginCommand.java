package pl.mc4e.ery65.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.mc4e.ery65.Mc4eMultiTasks;

public class LoginCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args){
		if (cs instanceof Player){
			if (args.length == 1){
				if (Mc4eMultiTasks.getPlayerManager().containsNotLogin((Player) cs)){
					if (Mc4eMultiTasks.getPlayerManager().isRegistered((Player)cs)){
						if (Mc4eMultiTasks.getPlayerManager().getMc4ePlayer((Player) cs).isValidPassword(args[0])){
							Mc4eMultiTasks.getPlayerManager().removeNotLogin((Player) cs);
							cs.sendMessage("§aZalogowano pomyslnie!");
							return true;
						} else {
							cs.sendMessage("§cNiepoprawne haslo, sprobuj jeszcze raz!");
							return true;
						}
					} else {
						cs.sendMessage("§cNie jestes zarejestrowany! Uzyj §b/register <haslo> <powtorz haslo>");
						return true;
					}
				} else {
					cs.sendMessage("§cJestes juz zalogowany!");
					return true;
				}
			} else {
				cs.sendMessage("§6Poprawne uzycie: §b/login <haslo>");
				return true;
			}
		} else {
			cs.sendMessage("§cKomenda tylko dla gracza!");
			return true;
		}
		//return false;
	}

}
