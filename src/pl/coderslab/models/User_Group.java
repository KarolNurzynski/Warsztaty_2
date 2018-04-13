package pl.coderslab.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User_Group {
	
	private int id;
	private String name;
	
	public User_Group() {}
	
	public User_Group(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void saveToDB(Connection conn) throws SQLException {
		if (this.id == 0) {
			String sql = "INSERT INTO user_group(name) VALUES (?)";
			String generatedColumns[] = { "ID" };
			PreparedStatement ps;
			ps = conn.prepareStatement(sql, generatedColumns);
			ps.setString(1, this.name);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			}
		}
	}
	
	public void modifyGroup (Connection conn) throws SQLException {
		if (this.id == 0) {
			saveToDB(conn);
		} else {
			String sql = "UPDATE user_group SET name=? where id = ?";
			PreparedStatement ps;
			ps = conn.prepareStatement(sql);
			ps.setString(1, this.name);
			ps.setInt(2, this.id);
			ps.executeUpdate();
		}
	}
	
	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE FROM user_group WHERE id= ?";
			PreparedStatement ps;
			ps = conn.prepareStatement(sql);
			ps.setInt(1, this.id);
			ps.executeUpdate();
			this.id=0;
		}
	}
	
	static public User_Group loadById(Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM user_group where id=?";
		PreparedStatement ps;
		ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			User_Group loadedGroup = new User_Group();
			loadedGroup.id = rs.getInt("id");
			loadedGroup.name = rs.getString("name");
			return loadedGroup;
		}
		return null;
	}
	
	static public User_Group[] loadAllGroups(Connection conn) throws SQLException {
		ArrayList<User_Group> user_groups = new ArrayList<User_Group>();
		String sql = "SELECT * FROM user_group"; 
		PreparedStatement ps;
		ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			User_Group loadedGroup = new User_Group();
			loadedGroup.id = rs.getInt("id");
			loadedGroup.name = rs.getString("name");
			user_groups.add(loadedGroup);
		}
		User_Group[] gArray = new User_Group[user_groups.size()]; 
		gArray = user_groups.toArray(gArray);
		return gArray;
	}

}
