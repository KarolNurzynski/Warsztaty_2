package pl.coderslab.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

public class Solution {
	
	private int id;
	private String created;
	private String updated;
	private String description;
	private int exercise_id;
	private int users_id;
	
	public Solution() {}
	
	public Solution(String created, String updated, String description, int exercise_id, int users_id) {
		this.created = created;
		this.updated = updated;
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
			String sql = "INSERT INTO solution(created, updated, description, exercise_id, users_id) VALUES (?,?,?,?,?);";
			String generatedColumns[] = { "ID" };
			PreparedStatement ps;
			ps = conn.prepareStatement(sql, generatedColumns);
			ps.setString(1, this.created);
			ps.setString(2, this.updated);
			ps.setString(3, this.description);
			ps.setInt(4, this.exercise_id);
			ps.setInt(5, this.users_id);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			}
		}
	}
	
	public void modifySolution (Connection conn) throws SQLException {
		if (this.id == 0) {
			saveToDB(conn);
		} else {
			String sql = "UPDATE solution SET created=?, updated=?, description=?, exercise_id=?, users_id=? where id = ?";
			PreparedStatement ps;
			ps = conn.prepareStatement(sql);
			ps.setString(1, this.created);
			ps.setString(2, this.updated);
			ps.setString(3, this.description);
			ps.setInt(4, this.exercise_id);
			ps.setInt(5, this.users_id);
			ps.setInt(6, this.id);
			ps.executeUpdate();
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
			loadedSolution.description = rs.getString("description");
			loadedSolution.exercise_id = rs.getInt("exercise_id");
			loadedSolution.users_id = rs.getInt("users_id");
			solutions.add(loadedSolution);
		}
		Solution[] sArray = new Solution[solutions.size()]; 
		sArray = solutions.toArray(sArray);
		return sArray;
	}
	
	static public Solution[] loadAllByUserId(Connection conn, int id) throws SQLException {
		ArrayList<Solution> solutionsArray = new ArrayList<Solution>();
		String sql = "SELECT * FROM solution where users_id=?"; 
		PreparedStatement ps;
		ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.id = rs.getInt("id");
			loadedSolution.created = rs.getString("created");
			loadedSolution.updated = rs.getString("updated");
			loadedSolution.description = rs.getString("description");
			loadedSolution.exercise_id = rs.getInt("exercise_id");
			loadedSolution.users_id = rs.getInt("users_id");
			solutionsArray.add(loadedSolution);
		}
		Solution[] sArray = new Solution[solutionsArray.size()]; 
		sArray = solutionsArray.toArray(sArray);
		return sArray;
	}
	
	static public Solution[] loadAllExerciseId(Connection conn, int exercise_id) throws SQLException {
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		String sql = "SELECT * FROM solution where exercise_id=?"; 
		PreparedStatement ps;
		ps = conn.prepareStatement(sql);
		ps.setInt(1, exercise_id);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.id = rs.getInt("id");
			loadedSolution.created = rs.getString("created");
			loadedSolution.updated = rs.getString("updated");
			loadedSolution.description = rs.getString("description");
			loadedSolution.exercise_id = rs.getInt("exercise_id");
			loadedSolution.users_id = rs.getInt("users_id");
			solutions.add(loadedSolution);
		}
		
		Collections.sort(solutions, new Comparator<Solution>() {
			public int compare(Solution s1, Solution s2) {
				return s1.getCreated().compareTo(s2.getCreated());
			}
		});
			
		Solution[] sArray = new Solution[solutions.size()]; 
		sArray = solutions.toArray(sArray);
		return sArray;
	}
	
	public static String getCurrentDateTime() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

}
