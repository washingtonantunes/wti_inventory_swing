package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DBException;
import entities.Equipment;
import model.dao.EquipmentDao;

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
					+ "`hostname`,"
					+ "`addressMAC`,"
					+ "`typeEquipment`,"
					+ "`patrimonyNumberEquipment`,"
					+ "`brandEquipment`,"
					+ "`modelEquipment`,"
					+ "`memoryRam`,"
					+ "`hardDisk`,"
					+ "`costType`,"
					+ "`valueEquipment`,"
					+ "`statusEquipment`,"
					+ "`dateEntry`) "
					+ "VALUES " 
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			st.setString(1, obj.getSerialNumber());
			st.setString(2, obj.getHostName());
			st.setString(3, obj.getAddressMAC());
			st.setString(4, obj.getTypeEquipment());
			st.setString(5, obj.getPatrimonyNumberEquipment());
			st.setString(6, obj.getBrandEquipment());
			st.setString(7, obj.getModelEquipment());
			st.setString(8, obj.getMemoryRam());
			st.setString(9, obj.getHardDisk());
			st.setString(10, obj.getCostType());
			st.setDouble(11, obj.getValueEquipment());
			st.setString(12, obj.getStatusEquipment());
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
					+ "SET `memoryRam` = ?, hardDisk = ?, costType = ?, valueEquipment = ? "
					+ "WHERE serialNumber = ?");

			st.setString(1, obj.getMemoryRam());
			st.setString(2, obj.getHardDisk());
			st.setString(3, obj.getCostType());
			st.setDouble(4, obj.getValueEquipment());
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
	public void updateStatus(String serialNumberEquipment, String status) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `equipments` "
					+ "SET `statusEquipment` = ? "
					+ "WHERE `serialNumber` = ?");

			st.setString(1, status);
			st.setString(2, serialNumberEquipment);

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
	public void disable(String serialNumberEquipment, String status, String reason) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `equipments` " 
					+ "SET `statusEquipment` = ?, `reason` = ? "
					+ "WHERE `serialNumber` = ?");

			st.setString(1, status);
			st.setString(2, reason);
			st.setString(3, serialNumberEquipment);

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
				equipment.setHostName(rs.getString("hostname"));
				equipment.setAddressMAC(rs.getString("addressMAC"));
				equipment.setTypeEquipment(rs.getString("typeEquipment"));
				equipment.setPatrimonyNumberEquipment(rs.getString("patrimonyNumberEquipment"));
				equipment.setBrandEquipment(rs.getString("brandEquipment"));
				equipment.setModelEquipment(rs.getString("modelEquipment"));
				equipment.setMemoryRam(rs.getString("memoryRam"));
				equipment.setHardDisk(rs.getString("hardDisk"));
				equipment.setCostType(rs.getString("costType"));
				equipment.setValueEquipment(rs.getDouble("valueEquipment"));
				equipment.setStatusEquipment(rs.getString("statusEquipment"));
				equipment.setDateEntry(rs.getDate("dateEntry"));
				// equipment.setChanges(Window.getChange().stream().filter(c ->
				// c.getObject().equals(equipment.getSerialNumber())).collect(Collectors.toList()));
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
