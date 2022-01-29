package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import db.DB;
import db.DBException;
import model.dao.MonitorDao;
import model.entities.Monitor;
import model.gui.monitor.MonitorList;

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
					+ "`brand`,"
					+ "`model`,"
					+ "`patrimonyNumber`,"
					+ "`status`,"
					+ "`dateEntry`) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?)");
			
			st.setString(1, obj.getSerialNumber());
			st.setString(2, obj.getBrand());
			st.setString(3, obj.getModel());
			st.setString(4, obj.getPatrimonyNumber());
			st.setString(5, obj.getStatus());
			st.setDate(6, new java.sql.Date(obj.getDateEntry().getTime()));

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
					+ "SET `patrimonyNumber` = ? "
					+ "`brand` = ?, "
					+ "`model` = ? "
					+ "WHERE `serialNumber` = ?");

			st.setString(1, obj.getPatrimonyNumber());
			st.setString(2, obj.getBrand());
			st.setString(3, obj.getModel());
			st.setString(4, obj.getSerialNumber());

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
	public void updateStatus(Monitor obj) {
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
	public void disable(Monitor obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `monitors` " 
					+ "SET `status` = ?, `reason` = ? "
					+ "WHERE `serialNumber` = ?");

			st.setString(1, obj.getStatus());
			st.setString(2, obj.getReason());
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
	public List<Monitor> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM `monitors`");

			rs = st.executeQuery();

			List<Monitor> monitors = new ArrayList<Monitor>();

			while (rs.next()) {
				Monitor monitor = new Monitor();

				monitor.setSerialNumber(rs.getString("serialNumber"));
				monitor.setBrand(rs.getString("brand"));
				monitor.setModel(rs.getString("model"));
				monitor.setPatrimonyNumber(rs.getString("patrimonyNumber"));
				monitor.setStatus(rs.getString("status"));
				monitor.setDateEntry(rs.getDate("dateEntry"));
				monitor.setChanges(MonitorList.getChanges().stream().filter(c -> c.getObject().equals(monitor.getSerialNumber())).collect(Collectors.toList()));
				monitor.setReason(rs.getString("reason"));
				monitors.add(monitor);
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
}
