package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DBException;
import entities.WorkPosition;
import model.dao.WorkPositionDao;

public class WorkPositionDaoJDBC implements WorkPositionDao {
	
	private Connection conn;

	public WorkPositionDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(WorkPosition obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO `work_positions` "
					+ "(`workPoint`,"
					+ "`location`,"
					+ "`floors`,"
					+ "`netPoint`,"
					+ "`statusWorkPosition`,"
					+ "`dateEntry`) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?)");
			
			st.setString(1, obj.getWorkPoint());
			st.setString(2, obj.getLocation());
			st.setString(3, obj.getFloors());
			st.setString(4, obj.getNetPoint());
			st.setString(5, obj.getStatusWorkPoint());
			st.setDate(6, new java.sql.Date(obj.getDateEntry().getTime()));

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(WorkPosition obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `work_positions` "
					+ "SET `netPoint` =  ? "
					+ "WHERE `workPoint` = ?");

			st.setString(1, obj.getNetPoint());
			st.setString(2, obj.getWorkPoint());
			
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
	public void updateStatus(String workPoint, String status) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `work_positions` "
					+ "SET `statusWorkPosition` = ? "
					+ "WHERE `workPoint` = ?");

			st.setString(1, status);
			st.setString(2, workPoint);

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
	public void disable(String workPoint, String status, String reason) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `work_positions` " 
					+ "SET `statusWorkPosition` = ?, `reason` = ? "
					+ "WHERE `workPoint` = ?");

			st.setString(1, status);
			st.setString(2, reason);
			st.setString(3, workPoint);
			
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
	public List<WorkPosition> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM `work_positions`");

			rs = st.executeQuery();

			List<WorkPosition> workPositions = new ArrayList<WorkPosition>();

			while (rs.next()) {
				WorkPosition workPosition = new WorkPosition();

				workPosition.setWorkPoint(rs.getString("workPoint"));
				workPosition.setLocation(rs.getString("location"));
				workPosition.setFloors(rs.getString("floors"));
				workPosition.setNetPoint(rs.getString("netPoint"));
				workPosition.setStatusWorkPoint(rs.getString("statusWorkPosition"));
				workPosition.setDateEntry(rs.getDate("dateEntry"));
				//workPosition.setChanges(Window.getChange().stream().filter(c -> c.getObject().equals(workPosition.getWorkPoint())).collect(Collectors.toList()));
				workPosition.setReason(rs.getString("reason"));
				workPositions.add(workPosition);
			}
			return workPositions;
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
