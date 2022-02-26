package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DBException;
import model.dao.ObjectWithUserDao;
import model.entities.Equipment;
import model.entities.License;
import model.entities.Monitor;
import model.entities.Peripheral;
import model.entities.User;
import model.entities.utilitary.EquipmentWithUser;
import model.entities.utilitary.LicenseWithUser;
import model.entities.utilitary.MonitorWithUser;
import model.entities.utilitary.PeripheralWithUser;

public class ObjectWithUserDaoJDBC implements ObjectWithUserDao {
	
	private Connection conn;

	public ObjectWithUserDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<EquipmentWithUser> findAllEquipmentWithUser() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM `equipmentsWithUsers`");

			rs = st.executeQuery();

			List<EquipmentWithUser> equipmentsWithUsers = new ArrayList<EquipmentWithUser>();

			while (rs.next()) {
				EquipmentWithUser equipmentWithUser = new EquipmentWithUser();

				equipmentWithUser.setRegistration(rs.getString("registration"));
				equipmentWithUser.setSerialNumber(rs.getString("serialNumber"));
				equipmentsWithUsers.add(equipmentWithUser);
			}
			return equipmentsWithUsers;
		} 
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<MonitorWithUser> findAllMonitorWithUser() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM `monitorsWithUsers`");

			rs = st.executeQuery();

			List<MonitorWithUser> monitorsWithUsers = new ArrayList<MonitorWithUser>();

			while (rs.next()) {
				MonitorWithUser monitorWithUser = new MonitorWithUser();

				monitorWithUser.setRegistration(rs.getString("registration"));
				monitorWithUser.setSerialNumber(rs.getString("serialNumber"));
				monitorsWithUsers.add(monitorWithUser);
			}
			return monitorsWithUsers;
		} 
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	@Override
	public List<PeripheralWithUser> findAllPeripheralWithUser() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM `peripheralsWithUsers`");

			rs = st.executeQuery();

			List<PeripheralWithUser> peripheralsWithUsers = new ArrayList<PeripheralWithUser>();

			while (rs.next()) {
				PeripheralWithUser peripheralWithUser = new PeripheralWithUser();

				peripheralWithUser.setRegistration(rs.getString("registration"));
				peripheralWithUser.setCode(rs.getString("code"));
				peripheralsWithUsers.add(peripheralWithUser);
			}
			return peripheralsWithUsers;
		} 
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	@Override
	public List<LicenseWithUser> findAllLicenseWithUser() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM `licensesWithUsers`");

			rs = st.executeQuery();

			List<LicenseWithUser> licensesWithUsers = new ArrayList<LicenseWithUser>();

			while (rs.next()) {
				LicenseWithUser licenseWithUser = new LicenseWithUser();

				licenseWithUser.setRegistration(rs.getString("registration"));
				licenseWithUser.setCode(rs.getString("code"));
				licensesWithUsers.add(licenseWithUser);
			}
			return licensesWithUsers;
		} 
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	@Override
	public void insertEquipmentWithUser(User user, Equipment equipment) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO `equipmentsWithUsers` "
					+ "(`registration`,"
					+ "`serialNumber`) "
					+ "VALUES " 
					+ "(?, ?)");

			st.setString(1, user.getRegistration());
			st.setString(2, equipment.getSerialNumber());

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
	public void insertMonitorWithUser(User user, Monitor monitor) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO `monitorsWithUsers` "
					+ "(`registration`,"
					+ "`serialNumber`) "
					+ "VALUES " 
					+ "(?, ?)");

			st.setString(1, user.getRegistration());
			st.setString(2, monitor.getSerialNumber());

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
	public void insertPeripheralWithUser(User user, Peripheral peripheral) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO `peripheralsWithUsers` "
					+ "(`registration`,"
					+ "`code`) "
					+ "VALUES " 
					+ "(?, ?)");

			st.setString(1, user.getRegistration());
			st.setString(2, peripheral.getCode());

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
	public void insertLicenseWithUser(User user, License license) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO `licensesWithUsers` "
					+ "(`registration`,"
					+ "`code`) "
					+ "VALUES " 
					+ "(?, ?)");

			st.setString(1, user.getRegistration());
			st.setString(2, license.getCode());

			st.executeUpdate();
		} 
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}
}
