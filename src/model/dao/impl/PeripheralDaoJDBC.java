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
import model.dao.PeripheralDao;
import model.entities.Change;
import model.entities.Peripheral;
import model.gui.MainWindow;

public class PeripheralDaoJDBC implements PeripheralDao {
	
	private Connection conn;

	public PeripheralDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Peripheral obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO `peripherals` "
					+ "(`name`,"
					+ "`value`,"
					+ "`quantity`,"
					+ "`status`) "
					+ "VALUES " 
					+ "(?, ?, ?, ?)");

			st.setString(1, obj.getName());
			st.setDouble(2, obj.getValue());
			st.setInt(3, obj.getQuantity());
			st.setString(4, obj.getStatus());

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
	public void update(Peripheral obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `peripherals` "
					+ "SET `value` = ?"
					+ "WHERE `name` = ?");

			st.setDouble(1, obj.getValue());
			st.setString(2, obj.getName());

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
	public void updateQuantity(Peripheral obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `peripherals` "
					+ "SET `quantity` = ?"
					+ "WHERE `name` = ?");

			st.setDouble(1, obj.getQuantity());
			st.setString(2, obj.getName());

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
	public void disable(Peripheral obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `peripherals` " 
					+ "SET `status` = ? "
					+ "WHERE `name` = ?");

			st.setString(1, obj.getStatus());
			st.setString(2, obj.getName());

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
	public Map<String, Peripheral> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM `peripherals`");

			rs = st.executeQuery();

			Map<String, Peripheral> peripherals = new HashMap<String, Peripheral>();

			while (rs.next()) {
				Peripheral peripheral = new Peripheral();

				peripheral.setName(rs.getString("name"));
				peripheral.setValue(rs.getDouble("value"));
				peripheral.setQuantity(rs.getInt("quantity"));
				peripheral.setStatus(rs.getString("status"));
				peripheral.setChanges(instatiateChanges(peripheral.getName()));
				peripherals.put(peripheral.getName(), peripheral);
			}
			return peripherals;
		} 
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private List<Change> instatiateChanges(String name) {
		List<Change> changes = MainWindow.getChanges().stream().filter(c -> c.getObject().equals(name)) .collect(Collectors.toList());
		return changes;
	}
}

