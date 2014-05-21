package pl.mc4e.ery65;

import org.bukkit.plugin.java.JavaPlugin;

import pl.mc4e.ery65.configuration.PluginConfig;
import pl.mc4e.ery65.mysql.MySQL;

public class Mc4eMultiTasks extends JavaPlugin {
	
	private static Mc4eMultiTasks plugin;
	private static MySQL mysql;
	
	public void onEnable(){
		plugin = this;
		getBase();
	}
	
	public void onDisable(){
		//none
		
	}
	
	private void getBase(){
		if (PluginConfig.enabled){
			String host = PluginConfig.host;
			String database = PluginConfig.database;
			int port = PluginConfig.port;
			String user = PluginConfig.user;
			String password = PluginConfig.password;
			try {
				mysql = new MySQL(database, host, port, user, password);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static MySQL getMySQL(){
		return mysql;
	}
	
	public static Mc4eMultiTasks getInstance(){
		return plugin;
	}

}