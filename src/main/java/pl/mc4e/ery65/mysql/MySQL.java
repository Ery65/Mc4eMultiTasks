package pl.mc4e.ery65.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

	public void queryUpdate(String query) {
		Connection conn = this.conn;
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(query);
			st.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Blad podczas update '" + query + "'.");
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
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
				}
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
