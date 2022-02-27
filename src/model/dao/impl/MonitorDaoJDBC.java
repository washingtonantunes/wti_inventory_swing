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
import model.dao.MonitorDao;
import model.entities.Change;
import model.entities.Monitor;
import model.entities.Project;
import model.entities.User;
import model.entities.WorkPosition;

public class MonitorDaoJDBC implements MonitorDao {

	private Connection conn;

	public MonitorDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Monitor obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO `monitors` "
					+ "(`serialNumber`,"
					+ "`patrimonyNumber`,"
					+ "`brand`,"
					+ "`model`,"
					+ "`costType`,"
					+ "`value`,"
					+ "`location`,"
					+ "`noteEntry`,"
					+ "`note`,"
					+ "`status`,"
					+ "`dateEntry`) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
			st.setString(1, obj.getSerialNumber());
			st.setString(2, obj.getPatrimonyNumber());
			st.setString(3, obj.getBrand());
			st.setString(4, obj.getModel());
			st.setString(5, obj.getCostType());
			st.setDouble(6, obj.getValue());
			st.setString(7, obj.getLocation());
			st.setString(8, obj.getNoteEntry());
			st.setString(9, obj.getNote());
			st.setString(10, obj.getStatus());
			st.setDate(11, new java.sql.Date(obj.getDateEntry().getTime()));

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Monitor obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `monitors` "
					+ "SET `patrimonyNumber` = ?, "
					+ "`brand` = ?, "
					+ "`model` = ?, "
					+ "`costType` = ?, "
					+ "`value` = ?, "
					+ "`noteEntry` = ?, "
					+ "`note` = ? "
					+ "WHERE `serialNumber` = ?");

			st.setString(1, obj.getPatrimonyNumber());
			st.setString(2, obj.getBrand());
			st.setString(3, obj.getModel());
			st.setString(4, obj.getCostType());
			st.setDouble(5, obj.getValue());
			st.setString(6, obj.getNoteEntry());
			st.setString(7, obj.getNote());
			st.setString(8, obj.getSerialNumber());

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
	public void updateStatusForUser(Monitor obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `monitors` "
					+ "SET `location` = ?, "
					+ "`status` = ?, "
					+ "`project` = ?, "
					+ "`user` = ? "
					+ "WHERE `serialNumber` = ?");

			st.setString(1, obj.getLocation());
			st.setString(2, obj.getStatus());
			st.setString(3, obj.getProject() != null ? obj.getUser().getProject().getCostCenter() : "NULL");
			st.setString(4, obj.getUser() != null ? obj.getUser().getRegistration() : "NULL");
			st.setString(5, obj.getSerialNumber());

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
	public void updateStatusForWorkPosition(Monitor obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `monitors` " 
					+ "SET `status` = ? " 
					+ "`workPosition` = ? " 
					+ "WHERE `serialNumber` = ?");

			st.setString(1, obj.getStatus());
			st.setString(2, obj.getWorkPosition().getWorkPoint());
			st.setString(3, obj.getSerialNumber());

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
	public void disable(Monitor obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `monitors` " 
					+ "SET `status` = ? "
					+ "WHERE `serialNumber` = ?");

			st.setString(1, obj.getStatus());
			st.setString(2, obj.getSerialNumber());
			
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
	public Map<String, Monitor> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM `monitors`");

			rs = st.executeQuery();

			Map<String, Monitor> monitors = new HashMap<String, Monitor>();

			while (rs.next()) {
				Monitor monitor = new Monitor();

				monitor.setSerialNumber(rs.getString("serialNumber"));
				monitor.setPatrimonyNumber(rs.getString("patrimonyNumber"));
				monitor.setBrand(rs.getString("brand"));
				monitor.setModel(rs.getString("model"));
				monitor.setCostType(rs.getString("costType"));
				monitor.setValue(rs.getDouble("value"));
				monitor.setStatus(rs.getString("status"));
				monitor.setLocation(rs.getString("location"));
				monitor.setNoteEntry(rs.getString("noteEntry"));
				monitor.setDateEntry(rs.getDate("dateEntry"));
				monitor.setProject(instatiateProject(rs));
				monitor.setUser(instatiateUser(rs));
				monitor.setWorkPosition(instatiateWorkPosition(rs));
				monitor.setChanges(instatiateChanges(monitor.getSerialNumber()));
				monitors.put(monitor.getSerialNumber(), monitor);
			}
			return monitors;
		} 
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	private Project instatiateProject(ResultSet rs) throws SQLException {
		return LoadData.getProject(rs.getString("project"));
	}
	
	private User instatiateUser(ResultSet rs) throws SQLException {
		return LoadData.getUser(rs.getString("user"));
	}
	
	private WorkPosition instatiateWorkPosition(ResultSet rs) throws SQLException {
		return LoadData.getWorkPosition(rs.getString("workPosition"));
	}
	
	private List<Change> instatiateChanges(String serialNumber) {
		return LoadData.getChangesByObject(serialNumber);
	}
}
