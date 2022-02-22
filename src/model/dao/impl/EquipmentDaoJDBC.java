package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import db.DB;
import db.DBException;
import model.dao.EquipmentDao;
import model.entities.Change;
import model.entities.Equipment;
import model.entities.User;
import model.entities.WorkPosition;
import model.gui.MainWindow;

public class EquipmentDaoJDBC implements EquipmentDao {

	private Connection conn;

	public EquipmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Equipment obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO `equipments` "
					+ "(`serialNumber`,"
					+ "`hostName`,"
					+ "`addressMAC`,"
					+ "`type`,"
					+ "`patrimonyNumber`,"
					+ "`brand`,"
					+ "`model`,"
					+ "`memoryRam`,"
					+ "`hardDisk`,"
					+ "`costType`,"
					+ "`value`,"
					+ "`location`,"
					+ "`noteEntry`,"
					+ "`note`,"
					+ "`status`,"
					+ "`dateEntry`) "
					+ "VALUES " 
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			st.setString(1, obj.getSerialNumber());
			st.setString(2, obj.getHostName());
			st.setString(3, obj.getAddressMAC());
			st.setString(4, obj.getType());
			st.setString(5, obj.getPatrimonyNumber());
			st.setString(6, obj.getBrand());
			st.setString(7, obj.getModel());
			st.setString(8, obj.getMemoryRam());
			st.setString(9, obj.getHardDisk());
			st.setString(10, obj.getCostType());
			st.setDouble(11, obj.getValue());
			st.setString(12, obj.getLocation());
			st.setString(13, obj.getNoteEntry());
			st.setString(14, obj.getNote());
			st.setString(15, obj.getStatus());
			st.setDate(16, new java.sql.Date(obj.getDateEntry().getTime()));

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
	public void update(Equipment obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `equipments` "
					+ "SET `hostName` = ?, "
					+ "`addressMAC` = ?, "
					+ "`type` = ?, "
					+ "`patrimonyNumber` = ?, "
					+ "`brand` = ?, "
					+ "`model` = ?, "
					+ "`memoryRam` = ?, "
					+ "`hardDisk` = ?, "
					+ "`costType` = ?, "
					+ "`value` = ?, "
					+ "`noteEntry` = ?, "
					+ "`note` = ? "
					+ "WHERE `serialNumber` = ?");

			st.setString(1, obj.getHostName());
			st.setString(2, obj.getAddressMAC());
			st.setString(3, obj.getType());
			st.setString(4, obj.getPatrimonyNumber());
			st.setString(5, obj.getBrand());
			st.setString(6, obj.getModel());
			st.setString(7, obj.getMemoryRam());
			st.setString(8, obj.getHardDisk());
			st.setString(9, obj.getCostType());
			st.setDouble(10, obj.getValue());
			st.setString(11, obj.getNoteEntry());
			st.setString(12, obj.getNote());
			st.setString(13, obj.getSerialNumber());

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
	public void updateStatusForUser(Equipment obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `equipments` "
					+ "SET `status` = ? "
					+ "`user` = ? "
					+ "WHERE `serialNumber` = ?");

			st.setString(1, obj.getStatus());
			st.setString(2, obj.getUser().getRegistration());
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
	public void updateStatusForWorkPosition(Equipment obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `equipments` "
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
	public void disable(Equipment obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `equipments` " 
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
	public Map<String, Equipment> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM `equipments`");

			rs = st.executeQuery();

			Map<String, Equipment> equipments = new HashMap<String, Equipment>();

			while (rs.next()) {
				Equipment equipment = new Equipment();

				equipment.setSerialNumber(rs.getString("serialNumber"));
				equipment.setHostName(rs.getString("hostName"));
				equipment.setAddressMAC(rs.getString("addressMAC"));
				equipment.setType(rs.getString("type"));
				equipment.setPatrimonyNumber(rs.getString("patrimonyNumber"));
				equipment.setBrand(rs.getString("brand"));
				equipment.setModel(rs.getString("model"));
				equipment.setMemoryRam(rs.getString("memoryRam"));
				equipment.setHardDisk(rs.getString("hardDisk"));
				equipment.setCostType(rs.getString("costType"));
				equipment.setValue(rs.getDouble("value"));
				equipment.setStatus(rs.getString("status"));
				equipment.setLocation(rs.getString("location"));
				equipment.setNoteEntry(rs.getString("noteEntry"));
				equipment.setDateEntry(rs.getDate("dateEntry"));				
				equipment.setNote(rs.getString("note"));
				equipment.setUser(instatiateUser(rs));
				equipment.setWorkPosition(instatiateWorkPosition(rs));
				equipment.setChanges(instatiateChanges(equipment.getSerialNumber()));
				equipments.put(equipment.getSerialNumber(), equipment);
			}
			return equipments;
		} 
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	private User instatiateUser(ResultSet rs) throws SQLException {
		User user = MainWindow.getUser(rs.getString("user"));
		return user;
	}
	
	private WorkPosition instatiateWorkPosition(ResultSet rs) throws SQLException {
		WorkPosition workPosition = MainWindow.getWorkPosition(rs.getString("workPosition"));
		return workPosition;
	}
	
	private List<Change> instatiateChanges(String serialNumber) {
		List<Change> changes = MainWindow.getChanges().stream().filter(c -> c.getObject().equals(serialNumber)).collect(Collectors.toList());
		return changes;
	}
}
