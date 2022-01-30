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
import model.dao.EquipmentDao;
import model.entities.Equipment;
import model.gui.equipment.EquipmentList;

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
					+ "`status`,"
					+ "`dateEntry`) "
					+ "VALUES " 
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

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
			st.setString(12, obj.getStatus());
			st.setDate(13, new java.sql.Date(obj.getDateEntry().getTime()));

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
					+ "`value` = ? "
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
			st.setString(11, obj.getSerialNumber());

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
	public void updateStatus(Equipment obj) {
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
	public void disable(Equipment obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `equipments` " 
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
	public List<Equipment> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM `equipments`");

			rs = st.executeQuery();

			List<Equipment> equipments = new ArrayList<Equipment>();

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
				equipment.setDateEntry(rs.getDate("dateEntry"));
				equipment.setChanges(EquipmentList.getChanges().stream().filter(c -> c.getObject().equals(equipment.getSerialNumber().toString())).collect(Collectors.toList()));
				equipment.setReason(rs.getString("reason"));
				equipments.add(equipment);
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
}
