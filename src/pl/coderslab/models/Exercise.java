package pl.coderslab.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Exercise {
	
	private int id;
	private String title;
	private String description;
	
	
	public Exercise() {}
	
	public Exercise(String title, String description) {
		this.title = title;
		this.description = description;
	}

	public int getId() {
		return id;
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

	public void saveToDB(Connection conn) throws SQLException {
		if (this.id == 0) {
			String sql = "INSERT INTO exercise(title, description) VALUES (?,?)";
			String generatedColumns[] = { "ID" };
			PreparedStatement ps;
			ps = conn.prepareStatement(sql, generatedColumns);
			ps.setString(1, this.title);
			ps.setString(2, this.description);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			}
		}
	}
	
	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE FROM exercise WHERE id= ?";
			PreparedStatement ps;
			ps = conn.prepareStatement(sql);
			ps.setInt(1, this.id);
			ps.executeUpdate();
			this.id=0;
		}
	}
	
	static public Exercise loadById(Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM exercise where id=?";
		PreparedStatement ps;
		ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			Exercise loadedExercise = new Exercise();
			loadedExercise.id = rs.getInt("id");
			loadedExercise.title = rs.getString("title");
			loadedExercise.description = rs.getString("description");
			return loadedExercise;
		}
		return null;
	}
	
	static public Exercise[] loadAllExercises(Connection conn) throws SQLException {
		ArrayList<Exercise> exercises = new ArrayList<Exercise>();
		String sql = "SELECT * FROM exercise"; 
		PreparedStatement ps;
		ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Exercise loadedExercise = new Exercise();
			loadedExercise.id = rs.getInt("id");
			loadedExercise.title = rs.getString("title");
			loadedExercise.description = rs.getString("description");
			exercises.add(loadedExercise);
		}
		Exercise[] eArray = new Exercise[exercises.size()]; 
		eArray = exercises.toArray(eArray);
		return eArray;
	}

}
