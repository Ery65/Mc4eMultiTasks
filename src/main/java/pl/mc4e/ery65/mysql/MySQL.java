package pl.mc4e.ery65.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pl.mc4e.ery65.configuration.PluginConfig;

public class MySQL {
	
	private Connection conn;
	private String database, user,password,host;
	private int port;

	public MySQL(String database,String host, int port, String user, String password) throws Exception {
		this.database = database;
		this.user = user;
		this.password = password;
		this.host = host;
		this.port = port;
		this.openConnection();
		createTables();
	}

	public Connection openConnection() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://" + host+":" + port+"/" + this.database, user, password);
		this.conn = conn;
		return conn;
	}

	public Connection getConnection() {
		return this.conn;
	}

	public boolean hasConnection() {
		try {
			return this.conn != null || this.conn.isValid(1);
		} catch (SQLException e) {
			return false;
		}
	}
	
	public void createTables(){
		StringBuilder table = new StringBuilder();
		table.append("CREATE TABLE IF NOT EXISTS `" + PluginConfig.prefix + "_players` ")
		.append("(`id` INT(5) NOT NULL AUTO_INCREMENT, ")
		.append("`name` VARCHAR(16) NOT NULL UNIQUE, ")
		.append("`password` VARCHAR(255) NOT NULL, ")
		.append("`lastlogin` BIGINT(20), ")
		.append("`lastlogout` BIGINT(20),")
		.append("`timeplayed` BIGINT(20), ")
		.append("`email` VARCHAR(64), ")
		.append("`question` TEXT, ")
		.append("`answer` VARCHAR(64), ")
		.append("PRIMARY KEY (`id`), ")
		.append("KEY (`name`))");
		queryUpdate(table.toString());
		
		StringBuilder table2 = new StringBuilder();
		table2.append("CREATE TABLE IF NOT EXISTS `"+ PluginConfig.prefix + "_secure` ")
		.append("(`id` INT(5) NOT NULL AUTO_INCREMENT, ")
		.append("`playerID` INT(5) NOT NULL,")
		.append("`bad_logins` TEXT,")
		.append("`tries` INT(5), ")
		.append("KEY (`id`))");
		queryUpdate(table2.toString());
		
		StringBuilder table3 = new StringBuilder();
		table3.append("CREATE TABLE IF NOT EXISTS `" + PluginConfig.prefix + "_econ` ")
		.append("(`id` INT(5) NOT NULL AUTO_INCREMENT, ")
		.append("`name` VARCHAR(16) NOT NULL UNIQUE, ")
		.append("`money` DECIMAL(15,2) NOT NULL DEFAULT 0, ")
		.append("PRIMARY KEY (`id`), ")
		.append("KEY (`name`))");
		queryUpdate(table3.toString());
	}

	public void queryUpdate(String query) {
		Connection con = conn;
		PreparedStatement st = null;
		try {
			st = con.prepareStatement(query);
			st.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Blad podczas update '" + query + "'.");
			e.printStackTrace();
		} finally {
			this.closeRessources(null, st);
		}
	}

	public void closeRessources(ResultSet rs, PreparedStatement st) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
			}
		}
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
			}
		}
	}

	public void closeConnection() {
		try {
			this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.conn = null;
		}
	}

}
