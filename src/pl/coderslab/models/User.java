package pl.coderslab.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

public class User {	
	
	private int id;
	private String username;
	private String email;
	private String password;
	private int user_group_id;
	
	public User() {}
	
	public User(String username, String email, String password, int user_group_id) {
		this.username = username;
		this.email = email;
		this.setPassword(password);
		this.user_group_id = user_group_id;
	}
	
	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = BCrypt.hashpw(password, BCrypt.gensalt());
	}

	public int getUser_group_id() {
		return user_group_id;
	}

	public void setUser_group_id(int user_group_id) {
		this.user_group_id = user_group_id;
	}

	public void saveToDB(Connection conn) throws SQLException {
		if (this.id == 0) {
			String sql = "INSERT INTO users(username, email, password, user_group_id) VALUES (?, ?, ?,?)";
			String generatedColumns[] = { "ID" };
			PreparedStatement ps;
			ps = conn.prepareStatement(sql, generatedColumns);
			ps.setString(1, this.username);
			ps.setString(2, this.email);
			ps.setString(3, this.password);
			ps.setInt(4, this.user_group_id);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			}
		}
	}
	
	public void modifyUser (Connection conn) throws SQLException {
		if (this.id == 0) {
			saveToDB(conn);
		} else {
			String sql = "UPDATE users SET username=?, email=?, password=?, user_group_id=? where id = ?";
			PreparedStatement ps;
			ps = conn.prepareStatement(sql);
			ps.setString(1, this.username);
			ps.setString(2, this.email);
			ps.setString(3, this.password);
			ps.setInt(4, this.user_group_id);
			ps.setInt(5, this.id);
			ps.executeUpdate();
		}
	}
	
	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE FROM users WHERE id= ?";
			PreparedStatement ps;
			ps = conn.prepareStatement(sql);
			ps.setInt(1, this.id);
			ps.executeUpdate();
			this.id=0;
		}
	}
	
	static public User loadUserById(Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM users where id=?";
		PreparedStatement ps;
		ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			User loadedUser = new User();
			loadedUser.id = rs.getInt("id");
			loadedUser.username = rs.getString("username");
			loadedUser.password = rs.getString("password");
			loadedUser.email = rs.getString("email");
			loadedUser.user_group_id = rs.getInt("user_group_id");
			return loadedUser;
		}
		return null;
	}
	
	static public User loadUserByEmail(Connection conn, String email) throws SQLException {
		String sql = "SELECT * FROM users where email=?";
		PreparedStatement ps;
		ps = conn.prepareStatement(sql);
		ps.setString(1, email);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			User loadedUser = new User();
			loadedUser.id = rs.getInt("id");
			loadedUser.username = rs.getString("username");
			loadedUser.password = rs.getString("password");
			loadedUser.email = rs.getString("email");
			loadedUser.user_group_id = rs.getInt("user_group_id");
			return loadedUser;
		}
		return null;
	}
	
	static public User[] loadAllUsers(Connection conn) throws SQLException {
		ArrayList<User> users = new ArrayList<User>();
		String sql = "SELECT * FROM users"; 
		PreparedStatement ps;
		ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			User loadedUser = new User();
			loadedUser.id = rs.getInt("id");
			loadedUser.username = rs.getString("username");
			loadedUser.password = rs.getString("password");
			loadedUser.email = rs.getString("email");
			loadedUser.user_group_id = rs.getInt("user_group_id");
			users.add(loadedUser);
		}
		User[] uArray = new User[users.size()]; 
		uArray = users.toArray(uArray);
		return uArray;
	}
	
	static public User[] loadAllByGroupId(Connection conn, int user_group_id) throws SQLException {
		ArrayList<User> users = new ArrayList<User>();
		String sql = "SELECT * FROM users where user_group_id=?"; 
		PreparedStatement ps;
		ps = conn.prepareStatement(sql);
		ps.setInt(1, user_group_id);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			User loadedUser = new User();
			loadedUser.id = rs.getInt("id");
			loadedUser.username = rs.getString("username");
			loadedUser.password = rs.getString("password");
			loadedUser.email = rs.getString("email");
			loadedUser.user_group_id = rs.getInt("user_group_id");
			users.add(loadedUser);
		}
		User[] uArray = new User[users.size()]; 
		uArray = users.toArray(uArray);
		return uArray;
	}
	
}


