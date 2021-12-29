package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DBException;
import entities.Project;
import model.dao.ProjectDao;

public class ProjectDaoJDBC implements ProjectDao {

	private Connection conn;

	public ProjectDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Project obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO `projects` "
					+ "(`nameProject`,"
					+ "`locality`,"
					+ "`costCenter`,"
					+ "`statusProject`,"
					+ "`dateEntry`) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getNameProject());
			st.setString(2, obj.getLocality());
			st.setString(3, obj.getCostCenter());
			st.setString(4, obj.getStatusProject());
			st.setDate(5, new java.sql.Date(obj.getDateEntry().getTime()));

			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setIdProject(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DBException("Unexpected error! No rows affected!");
			}
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Project obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `projects` "
					+ "SET `locality` = ?, `costCenter` = ? "
					+ "WHERE `idProject` = ?");

			st.setString(1, obj.getLocality());
			st.setString(2, obj.getCostCenter());
			st.setInt(3, obj.getIdProject());

			st.executeUpdate();
		} 
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void disable(Integer idProject, String status, String reason) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `projects` "
					+ "SET `statusProject` = ?, `reason` = ? "
					+ "WHERE `idProject` = ?");

			st.setString(1, status);
			st.setString(2, reason);
			st.setInt(3, idProject);

			st.executeUpdate();
		} 
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public List<Project> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM `projects`");

			rs = st.executeQuery();

			List<Project> projects = new ArrayList<Project>();

			while (rs.next()) {
				Project project = new Project();

				project.setIdProject(rs.getInt("idProject"));
				project.setNameProject(rs.getString("nameProject"));
				project.setLocality(rs.getString("locality"));
				project.setCostCenter(rs.getString("costCenter"));
				project.setStatusProject(rs.getString("statusProject"));
				project.setDateEntry(rs.getDate("dateEntry"));
				//project.setChanges(Window.getChange().stream().filter(c -> c.getObject().equals(project.getNameProject())).collect(Collectors.toList()));
				project.setReason(rs.getString("reason"));
				projects.add(project);
			}
			return projects;
		} 
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
}
