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
import model.dao.WorkPositionDao;
import model.entities.WorkPosition;
import model.gui.workposition.WorkPositionList;

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
					+ "`floor`,"
					+ "`netPoint`,"
					+ "`status`,"
					+ "`dateEntry`) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?)");
			
			st.setString(1, obj.getWorkPoint());
			st.setString(2, obj.getLocation());
			st.setString(3, obj.getFloor());
			st.setString(4, obj.getNetPoint());
			st.setString(5, obj.getStatus());
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
					+ "SET `location` = ?, "
					+ "`floor` = ?, "
					+ "`netPoint` =  ? "
					+ "WHERE `workPoint` = ?");

			st.setString(1, obj.getLocation());
			st.setString(2, obj.getFloor());
			st.setString(3, obj.getNetPoint());
			st.setString(4, obj.getWorkPoint());
			
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
	public void updateStatus(WorkPosition obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `work_positions` "
					+ "SET `status` = ? "
					+ "WHERE `workPoint` = ?");

			st.setString(1, obj.getStatus());
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
	public void disable(WorkPosition obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `work_positions` " 
					+ "SET `status` = ?, `reason` = ? "
					+ "WHERE `workPoint` = ?");

			st.setString(1, obj.getStatus());
			st.setString(2, obj.getReason());
			st.setString(3, obj.getWorkPoint());
			
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
				workPosition.setFloor(rs.getString("floor"));
				workPosition.setNetPoint(rs.getString("netPoint"));
				workPosition.setStatus(rs.getString("status"));
				workPosition.setDateEntry(rs.getDate("dateEntry"));
				workPosition.setChanges(WorkPositionList.getChanges().stream().filter(c -> c.getObject().equals(workPosition.getWorkPoint())).collect(Collectors.toList()));
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
