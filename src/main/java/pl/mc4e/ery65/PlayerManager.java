package pl.mc4e.ery65;

import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import pl.mc4e.ery65.utils.MD5HASH;
import pl.mc4e.ery65.utils.Mc4ePlayer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class PlayerManager {
	
	private Map<String, Mc4ePlayer> reg = Maps.newHashMap();
	private List<Player> notLogin = Lists.newArrayList();
	
	public boolean isRegistered(Player player){
		return reg.containsKey(player.getName());
	}
	
	public boolean addToRegisteredPlayers(Player player){
		Mc4ePlayer p = new Mc4ePlayer(player);
		if (p.isRegistered()){
			reg.put(player.getName().toLowerCase(), p);
		}
		return p.isRegistered();
	}
	
	public void registerPlayer(Player player, String pass){
		Mc4ePlayer p = new Mc4ePlayer(player);
		p.register(MD5HASH.getHashedPassword(pass));
	}
	
	public void addNotLogin(Player p){
		notLogin.add(p);
	}
	
	public void removeNotLogin(Player p){
		notLogin.remove(p);
		if (notLogin == null|| notLogin.size()==0)
			notLogin = Lists.newArrayList();
	}
	
	public boolean containsNotLogin(Player p){
		return notLogin.contains(p);
	}
	
	public boolean SizeNotZero(){
		return notLogin.size()!=0;
	}
	
	
}
