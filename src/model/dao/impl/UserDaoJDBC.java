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
import model.dao.UserDao;
import model.entities.Change;
import model.entities.Equipment;
import model.entities.Monitor;
import model.entities.Project;
import model.entities.User;
import model.gui.MainWindow;

public class UserDaoJDBC implements UserDao {
	
	private Connection conn;

	public UserDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(User obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO `users` "
					+ "(`registration`,"
					+ "`name`,"
					+ "`cpf`,"
					+ "`phone`,"
					+ "`email`,"
					+ "`department`,"
					+ "`project`,"
					+ "`status`,"
					+ "`dateEntry`) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
			st.setString(1, obj.getRegistration());
			st.setString(2, obj.getName());
			st.setString(3, obj.getCpf());
			st.setString(4, obj.getPhone());
			st.setString(5, obj.getEmail());
			st.setString(6, obj.getDepartment());
			st.setString(7, obj.getProject().getCostCenter());
			st.setString(8, obj.getStatus());
			st.setDate(9, new java.sql.Date(obj.getDateEntry().getTime()));

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(User obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `users` "
					+ "SET `name` = ?, "
					+ "`cpf` = ?, "
					+ "`phone` = ?, "
					+ "`email` = ?, "
					+ "`department` = ?, "
					+ "`project` = ? "
					+ "WHERE `registration` = ?");

			st.setString(1, obj.getName());
			st.setString(2, obj.getCpf());
			st.setString(3, obj.getPhone());
			st.setString(4, obj.getEmail());
			st.setString(5, obj.getDepartment());
			st.setString(6, obj.getProject().getCostCenter());
			st.setString(7, obj.getRegistration());

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
	public void disable(User obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `users` "
					+ "SET `status` = ?"
					+ "WHERE `registration` = ?");

			st.setString(1, obj.getStatus());
			st.setString(2, obj.getRegistration());

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
	public Map<String, User> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM `users`");

			rs = st.executeQuery();

			Map<String, User> users = new HashMap<String, User>();

			while (rs.next()) {
				User user = new User();

				user.setRegistration(rs.getString("registration"));
				user.setName(rs.getString("name"));
				user.setCpf(rs.getString("cpf"));
				user.setPhone(rs.getString("phone"));
				user.setEmail(rs.getString("email"));
				user.setDepartment(rs.getString("department"));
				user.setStatus(rs.getString("status"));
				user.setDateEntry(rs.getDate("dateEntry"));
				user.setProject(instatiateProject(rs));
				user.setEquipment(instatiateEquipment(rs));
				user.setMonitor1(instatiateMonitor1(rs));
				user.setMonitor2(instatiateMonitor2(rs));
				user.setChanges(instatiateChanges(user.getRegistration()));
				users.put(user.getRegistration(), user);
			}
			return users;
		} 
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	private Project instatiateProject(ResultSet rs) throws SQLException {
		Project project = MainWindow.getProject(rs.getString("project"));
		return project;
	}
	
	private Equipment instatiateEquipment(ResultSet rs) throws SQLException {
		Equipment equipment = new Equipment(rs.getString("equipment"));
		return equipment;
	}
	
	private Monitor instatiateMonitor1(ResultSet rs) throws SQLException {
		Monitor monitor = new Monitor(rs.getString("monitor1"));
		return monitor;
	}
	
	private Monitor instatiateMonitor2(ResultSet rs) throws SQLException {
		Monitor monitor = new Monitor(rs.getString("monitor1"));
		return monitor;
	}
	
	private List<Change> instatiateChanges(String registration) {
		List<Change> changes = MainWindow.getChanges().stream().filter(c -> c.getObject().equals(registration)).collect(Collectors.toList());
		return changes;
	}
}
