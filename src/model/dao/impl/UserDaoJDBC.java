package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DBException;
import model.dao.UserDao;
import model.entities.User;

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
					+ "`statusUser`,"
					+ "`dateEntry`) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
			st.setString(1, obj.getRegistration());
			st.setString(2, obj.getNameUser());
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
					+ "SET `nameUser` = ?, `phone` = ?, `project` = ?, `email` = ?, `department` = ? "
					+ "WHERE `registration` = ?");

			st.setString(1, obj.getNameUser());
			st.setString(2, obj.getPhone());
			st.setString(3, obj.getProject());
			st.setString(4, obj.getEmail());
			st.setString(5, obj.getDepartment());
			st.setString(6, obj.getRegistration());

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
	public void disable(String registration, String status, String reason) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `users` "
					+ "SET `statusUser` = ?, `reason` = ? "
					+ "WHERE `registration` = ?");

			st.setString(1, status);
			st.setString(2, reason);
			st.setString(3, registration);

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
				user.setNameUser(rs.getString("nameUser"));
				user.setCPF(rs.getString("cpf"));
				user.setPhone(rs.getString("phone"));
				user.setProject(rs.getString("project"));
				user.setEmail(rs.getString("email"));
				user.setDepartment(rs.getString("department"));
				user.setStatus(rs.getString("statusUser"));
				user.setDateEntry(rs.getDate("dateEntry"));
				//user.setChanges(Window.getChange().stream().filter(c -> c.getObject().equals(user_.getRegistration().toString())).collect(Collectors.toList()));
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
