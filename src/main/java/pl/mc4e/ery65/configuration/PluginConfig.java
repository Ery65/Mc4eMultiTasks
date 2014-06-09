package pl.mc4e.ery65.configuration;

import org.bukkit.configuration.file.FileConfiguration;

import pl.mc4e.ery65.Mc4eMultiTasks;

public class PluginConfig {
	
	private static FileConfiguration cfg;
	public static String user,password,database,host;
	public static String prefix;
	public static int port;
	public static boolean enabled;
	
	static {
		cfg = Mc4eMultiTasks.getInstance().getConfig();
		enabled = cfg.getBoolean("MySQL.enabled", false);
		if (enabled){
			user = cfg.getString("MySQL.user","root");
			password = cfg.getString("MySQL.password","root");
			host = cfg.getString("MySQL.host", "localhost");
			database = cfg.getString("MySQL.database", "minecraft");
			port = cfg.getInt("MySQL.port",3306);
			prefix = cfg.getString("MySQL.prefix");
		}
	}

}