package pl.mc4e.ery65.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

import pl.mc4e.ery65.Mc4eMultiTasks;
import pl.mc4e.ery65.configuration.PluginConfig;
import pl.mc4e.ery65.mysql.MySQL;

public class Mc4ePlayer {
	
	protected String password;
	private String name;
	//private String mail;
	private boolean registered;
	private long lastlogin = 0;
	
	public Mc4ePlayer(String name){
		this.name = name;
		registered = checkPlayer();
		if (registered){
			password = getPassword();
		}
	}
	
	public Mc4ePlayer(Player player){
		name = player.getName();
		registered = checkPlayer();
		if (registered){
			password = getPassword();
		}
	}
	
	public boolean isValidPassword(String password){
		return MD5HASH.isMatch(password, this.password);
	}
	
	public boolean isRegistered(){
		return registered;
	}
	
	private String getPassword(){
		MySQL sql = Mc4eMultiTasks.getMySQL();
		String pass = null;
		if (!sql.hasConnection())
			try {
				sql.openConnection();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM `" + PluginConfig.prefix + "_players`")
		.append(" WHERE `name`=?");
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = sql.getConnection().prepareStatement(query.toString());
			st.setString(1, name);
			rs = st.executeQuery();
			if (rs.next()){
				pass = rs.getString("password");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return pass;
	}
	
	public boolean checkPlayer(){
		MySQL mysql = Mc4eMultiTasks.getMySQL();
		if (mysql == null)
			return false;
		if (!mysql.hasConnection())
			try {
				mysql.openConnection();
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		StringBuilder com = new StringBuilder();
		com.append("SELECT * ")
		.append("FROM `" + PluginConfig.prefix + "_players` ")
		.append("WHERE `name`=?");
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = mysql.getConnection().prepareStatement(com.toString());
			ps.setString(1, name);
			rs = ps.executeQuery();
			if (rs.next()){
				lastlogin = System.currentTimeMillis();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public void save(){
		MySQL mysql = Mc4eMultiTasks.getMySQL();
		if (mysql == null)
			return;
		if (!mysql.hasConnection())
			try {
				mysql.openConnection();
			} catch (Exception e){
				e.printStackTrace();
				return;
			}
		StringBuilder com = new StringBuilder();
		long timeplayed = System.currentTimeMillis()-lastlogin;
		com.append("INSERT INTO `"+ PluginConfig.prefix + "_players` ")
		.append("(`id`,`name`,`password`, `lastlogin`, `lastlogout`, `timeplayed`, `email`, ")
		.append("`question`, `answer`) VALUES (")
		.append("null, \"" + name + "\", \"" + password +"\"," + lastlogin +", ")
		.append(System.currentTimeMillis()+ ",`timeplayed`+"+timeplayed+" , null,null,null)")
		.append(" ON DUPLECATE KEY UPDATE ")
		.append("`timeplayed`=VALUES(`timeplayed`");
		mysql.queryUpdate(com.toString());
	}

	public void register(String hashedPassword) {
		password = hashedPassword;
		MySQL mysql = Mc4eMultiTasks.getMySQL();
		if (mysql == null)
			return;
		if (!mysql.hasConnection())
			try {
				mysql.openConnection();
			} catch (Exception e){
				e.printStackTrace();
				return;
			}
		StringBuilder com = new StringBuilder();
		com.append("INSERT INTO `" + PluginConfig.prefix + "_players` ")
		.append("(`id`,`name`,`password`, `lastlogin`, `lastlogout`, `timeplayed`, `email`, ")
		.append("`question`, `answer`) VALUES (") 
		.append("null, \"" + name + "\", \"" + hashedPassword +"\", " + System.currentTimeMillis() + ", ")
		.append("0, 0, null,null,null)");
		mysql.queryUpdate(com.toString());
		lastlogin = System.currentTimeMillis();
	}
	

}
