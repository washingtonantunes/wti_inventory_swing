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
import model.dao.ChangeDao;
import model.entities.Change;

public class ChangeDaoJDBC implements ChangeDao {
	
	private Connection conn;

	public ChangeDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Change obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO `changes` " 
					+ "(`object`,"
					+ "`typeChange`,"
					+ "`changes`,"
					+ "`dateChange`,"
					+ "`author`) "
					+ "VALUES "
					+ "(?, ?, ?, ?,  ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getObject());
			st.setString(2, obj.getTypeChange());
			st.setString(3, obj.getChanges());
			st.setDate(4, new java.sql.Date(obj.getDateChange().getTime()));
			st.setString(5, obj.getAuthor());

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
	public List<Change> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM `changes`");

			rs = st.executeQuery();

			List<Change> changes = new ArrayList<Change>();

			while (rs.next()) {
				Change change = new Change();

				change.setId(rs.getInt("id"));
				change.setChanges(rs.getString("changes"));
				change.setDateChange(rs.getDate("dateChange"));
				change.setObject(rs.getString("object"));
				change.setTypeChange(rs.getString("typeChange"));
				change.setAuthor(rs.getString("author"));

				changes.add(change);
			}
			return changes;
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
