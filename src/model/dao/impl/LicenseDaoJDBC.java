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
import model.dao.LicenseDao;
import model.entities.Change;
import model.entities.License;

public class LicenseDaoJDBC implements LicenseDao {
	
	private Connection conn;

	public LicenseDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(License obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO `licenses` "
					+ "(`code`,"
					+ "`name`,"
					+ "`brand`,"
					+ "`value`,"
					+ "`quantity`,"
					+ "`status`) "
					+ "VALUES " 
					+ "(?, ?, ?, ?, ?, ?)");

			st.setString(1, obj.getCode());
			st.setString(2, obj.getName());
			st.setString(3, obj.getBrand());
			st.setDouble(4, obj.getValue());
			st.setInt(5, obj.getQuantity());
			st.setString(6, obj.getStatus());

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
	public void update(License obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `licenses` "
					+ "SET `value` = ?"
					+ "WHERE `code` = ?");

			st.setDouble(1, obj.getValue());
			st.setString(2, obj.getCode());

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
	public void updateQuantity(License obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `licenses` "
					+ "SET `quantity` = ?"
					+ "WHERE `code` = ?");

			st.setDouble(1, obj.getQuantity());
			st.setString(2, obj.getCode());

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
	public void disable(License obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `licenses` " 
					+ "SET `status` = ? "
					+ "WHERE `code` = ?");

			st.setString(1, obj.getStatus());
			st.setString(2, obj.getCode());

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
	public Map<String, License> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM `licenses`");

			rs = st.executeQuery();

			Map<String, License> licenses = new HashMap<String, License>();

			while (rs.next()) {
				License license = new License();

				license.setCode(rs.getString("code"));
				license.setName(rs.getString("name"));
				license.setBrand(rs.getString("brand"));
				license.setValue(rs.getDouble("value"));
				license.setQuantity(rs.getInt("quantity"));
				license.setStatus(rs.getString("status"));
				license.setChanges(instatiateChanges(license.getCode()));
				licenses.put(license.getCode(), license);
			}
			return licenses;
		} 
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	private List<Change> instatiateChanges(String code) {
		return LoadData.getChangesByObject(code);
	}
}

