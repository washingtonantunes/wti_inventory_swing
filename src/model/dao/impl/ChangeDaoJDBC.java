package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
					+ "`type`,"
					+ "`changes`,"
					+ "`date`,"
					+ "`author`) "
					+ "VALUES "
					+ "(?, ?, ?, ?,  ?)");
			
			st.setString(1, obj.getObject());
			st.setString(2, obj.getType());
			st.setString(3, obj.getChanges());
			st.setDate(4, new java.sql.Date(obj.getDate().getTime()));
			st.setString(5, obj.getAuthor());

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
	public List<Change> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM `changes`");

			rs = st.executeQuery();

			List<Change> changes = new ArrayList<Change>();

			while (rs.next()) {
				Change change = new Change();

				change.setChanges(rs.getString("changes"));
				change.setDate(rs.getDate("date"));
				change.setObject(rs.getString("object"));
				change.setType(rs.getString("type"));
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
