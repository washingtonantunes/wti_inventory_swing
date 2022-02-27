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
import model.dao.OptionDao;
import model.entities.Option;

public class OptionDaoJDBC implements OptionDao {
	
	private Connection conn;

	public OptionDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Option obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO `options` "
					+ "(`option`,"
					+ "`type`,"
					+ "`status`,"
					+ "`dateEntry`) "
					+ "VALUES "
					+ "(?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getOption());
			st.setString(2, obj.getType());
			st.setString(3, obj.getStatus());
			st.setDate(4, new java.sql.Date(obj.getDateEntry().getTime()));

			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
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
	public void updateSort(int idOld, int idNew) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `options` "
					+ "SET `id` = ? "
					+ "WHERE `id` = ?");
			
			st.setInt(1, idNew);
			st.setInt(2, idOld);
			

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Option obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `options` "
					+ "SET `option` = ? "
					+ "WHERE `id` = ?");

			st.setString(1, obj.getOption());
			st.setInt(2, obj.getId());

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
	public void disable(Integer idOption, String status) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `options` " 
					+ "SET `status` = ? "
					+ "WHERE `id` = ?");

			st.setString(1, status);
			st.setInt(2, idOption);
			
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
	public List<Option> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM `options`");

			rs = st.executeQuery();

			List<Option> options = new ArrayList<>();

			while (rs.next()) {
				Option option = new Option();
				
				option.setId(rs.getInt("id"));
				option.setOption(rs.getString("option"));
				option.setType(rs.getString("type"));
				option.setStatus(rs.getString("status"));
				option.setDateEntry(rs.getDate("dateEntry"));
				options.add(option);	
			}
			return options;
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
