package pl.coderslab.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Solution {
	
	private int id;
	private String created;
	private String updated;
	private String title;
	private String description;
	private int exercise_id;
	private int users_id;
	
	public Solution() {}
	
	public Solution(String created, String updated, String title, String description, int exercise_id, int users_id) {
		this.created = created;
		this.updated = updated;
		this.title = title;
		this.description = description;
		this.exercise_id = exercise_id;
		this.users_id = users_id;
	}

	public int getId() {
		return id;
	}
	
	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getExercise_id() {
		return exercise_id;
	}

	public void setExercise_id(int exercise_id) {
		this.exercise_id = exercise_id;
	}

	public int getUsers_id() {
		return users_id;
	}
	
	public void setUsers_id(int users_id) {
		this.users_id = users_id;
	}

	public void saveToDB(Connection conn) throws SQLException {
		if (this.id == 0) {
			String sql = "INSERT INTO solution(created, updated, title, description, exercise_id, users_id) VALUES (?,?,?,?,?,?)";
			String generatedColumns[] = { "ID" };
			PreparedStatement ps;
			ps = conn.prepareStatement(sql, generatedColumns);
			ps.setString(1, this.created);
			ps.setString(2, this.updated);
			ps.setString(3, this.title);
			ps.setString(4, this.description);
			ps.setInt(5, this.exercise_id);
			ps.setInt(6, this.users_id);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			}
		}
	}
	
	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE FROM solution WHERE id= ?";
			PreparedStatement ps;
			ps = conn.prepareStatement(sql);
			ps.setInt(1, this.id);
			ps.executeUpdate();
			this.id=0;
		}
	}
	
	static public Solution loadById(Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM solution where id=?";
		PreparedStatement ps;
		ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.id = rs.getInt("id");
			loadedSolution.created = rs.getString("created");
			loadedSolution.updated = rs.getString("updated");
			loadedSolution.title = rs.getString("title");
			loadedSolution.description = rs.getString("description");
			loadedSolution.exercise_id = rs.getInt("exercise_id");
			loadedSolution.users_id = rs.getInt("users_id");
			return loadedSolution;
		}
		return null;
	}
	
	static public Solution[] loadAllSolutions(Connection conn) throws SQLException {
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		String sql = "SELECT * FROM solution"; 
		PreparedStatement ps;
		ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.id = rs.getInt("id");
			loadedSolution.created = rs.getString("created");
			loadedSolution.updated = rs.getString("updated");
			loadedSolution.title = rs.getString("title");
			loadedSolution.description = rs.getString("description");
			loadedSolution.exercise_id = rs.getInt("exercise_id");
			loadedSolution.users_id = rs.getInt("users_id");
			solutions.add(loadedSolution);
		}
		Solution[] sArray = new Solution[solutions.size()]; 
		sArray = solutions.toArray(sArray);
		return sArray;
	}

}
