package pl.mc4e.ery65;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.plugin.java.JavaPlugin;

import pl.mc4e.ery56.listeners.PlayerListener;
import pl.mc4e.ery65.configuration.PluginConfig;
import pl.mc4e.ery65.filter.Log4uFilter;
import pl.mc4e.ery65.mysql.MySQL;

public class Mc4eMultiTasks extends JavaPlugin {
	
	private static Mc4eMultiTasks plugin;
	private static MySQL mysql;
	private static PlayerManager man;
	
	public void onEnable(){
		plugin = this;
		File cfg = new File(this.getDataFolder() + File.separator + "config.yml");
		if (!cfg.exists()){
			this.getDataFolder().mkdirs();
			copy(this.getResource("config.yml"), cfg);
			reloadConfig();
		}
		man = new PlayerManager();
		getBase();
		Logger log = (Logger) LogManager.getRootLogger();
		log.addFilter(new Log4uFilter());
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
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
	
	public static PlayerManager getPlayerManager(){
		return man;
	}
	
	public static Mc4eMultiTasks getInstance(){
		return plugin;
	}
	
	void copy(InputStream from, File to) {
		try {
			OutputStream out = new FileOutputStream(to);
			byte[] buffer = new byte[1024];
			int size = 0;
			
			while((size = from.read(buffer)) != -1) {
				out.write(buffer, 0, size);
			}
			
			out.close();
			from.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}