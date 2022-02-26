package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.LoadData;
import db.DB;
import db.DBException;
import model.dao.ProjectDao;
import model.entities.Change;
import model.entities.Project;

public class ProjectDaoJDBC implements ProjectDao {

	private Connection conn;

	public ProjectDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Project obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO `projects` " 
					+ "(`costCenter`," 
					+ "`name`," 
					+ "`city`,"
					+ "`status`," 
					+ "`dateEntry`) " 
					+ "VALUES " + "(?, ?, ?, ?, ?)");

			st.setString(1, obj.getCostCenter());
			st.setString(2, obj.getName());
			st.setString(3, obj.getCity());
			st.setString(4, obj.getStatus());
			st.setDate(5, new java.sql.Date(obj.getDateEntry().getTime()));

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
	public void update(Project obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `projects` " 
					+ "SET `name` = ?, " 
					+ "`city` = ? " 
					+ "WHERE `costCenter` = ?");

			st.setString(1, obj.getName());
			st.setString(2, obj.getCity());
			st.setString(3, obj.getCostCenter());

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
	public void disable(Project obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `projects` " 
					+ "SET `status` = ? " 
					+ "WHERE `costCenter` = ?");

			st.setString(1, obj.getStatus());
			st.setString(2, obj.getCostCenter());

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
	public Map<String, Project> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM `projects`");

			rs = st.executeQuery();

			Map<String, Project> projects = new HashMap<String, Project>();

			while (rs.next()) {
				Project project = new Project();

				project.setCostCenter(rs.getString("costCenter"));
				project.setName(rs.getString("name"));
				project.setCity(rs.getString("city"));
				project.setStatus(rs.getString("status"));
				project.setDateEntry(rs.getDate("dateEntry"));
				project.setQuantityDesktops(rs.getInt("quantityDesktop"));
				project.setQuantityNotebooks(rs.getInt("quantityNotebook"));
				project.setCostTotal(rs.getDouble("costTotal"));
				project.setChanges(instatiateChanges(project.getCostCenter()));
				projects.put(project.getCostCenter(), project);
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

	private List<Change> instatiateChanges(String serialNumber) {
		return LoadData.getChangesByObject(serialNumber);
	}
}
