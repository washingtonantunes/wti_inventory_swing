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
import model.dao.UserDao;
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
					+ "`nameUser`,"
					+ "`cpf`,"
					+ "`phone`,"
					+ "`project`,"
					+ "`email`,"
					+ "`department`,"
					+ "`status`,"
					+ "`dateEntry`) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
			st.setString(1, obj.getRegistration());
			st.setString(2, obj.getName());
			st.setString(3, obj.getCPF());
			st.setString(4, obj.getPhone());
			st.setString(5, obj.getProject());
			st.setString(6, obj.getEmail());
			st.setString(7, obj.getDepartment());
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
					+ "SET `nameUser` = ?, "
					+ "`cpf` = ?, "
					+ "`phone` = ?, "
					+ "`project` = ?, "
					+ "`email` = ?, "
					+ "`department` = ? "
					+ "WHERE `registration` = ?");

			st.setString(1, obj.getName());
			st.setString(2, obj.getCPF());
			st.setString(3, obj.getPhone());
			st.setString(4, obj.getProject());
			st.setString(5, obj.getEmail());
			st.setString(6, obj.getDepartment());
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
					+ "SET `status` = ?, `reason` = ? "
					+ "WHERE `registration` = ?");

			st.setString(1, obj.getStatus());
			st.setString(2, obj.getReason());
			st.setString(3, obj.getRegistration());

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
	public List<User> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM `users`");

			rs = st.executeQuery();

			List<User> users = new ArrayList<User>();

			while (rs.next()) {
				User user = new User();

				user.setRegistration(rs.getString("registration"));
				user.setName(rs.getString("nameUser"));
				user.setCPF(rs.getString("cpf"));
				user.setPhone(rs.getString("phone"));
				user.setProject(rs.getString("project"));
				user.setEmail(rs.getString("email"));
				user.setDepartment(rs.getString("department"));
				user.setStatus(rs.getString("status"));
				user.setDateEntry(rs.getDate("dateEntry"));
				user.setChanges(MainWindow.getChanges().stream().filter(c -> c.getObject().equals(user.getRegistration())).collect(Collectors.toList()));
				user.setReason(rs.getString("reason"));
				users.add(user);
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
}
