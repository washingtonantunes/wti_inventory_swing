package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import db.DB;
import db.DBException;
import entities.Collaborator;
import model.dao.CollaboratorDao;

public class CollaboratorDaoJDBC implements CollaboratorDao {
	
	private Connection conn;

	public CollaboratorDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Collaborator obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO `collaborators` "
					+ "(`name`,"
					+ "`registration`,"
					+ "`password`,"
					+ "`privilege`,"
					+ "`office`,"
					+ "`statusCollaborator`,"
					+ "`dateEntry`) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?)");
			
			st.setString(1, obj.getName());
			st.setString(2, obj.getRegistration());
			st.setString(3, obj.getPassword());
			st.setString(4, obj.getPrivilege());
			st.setString(5, obj.getOffice());
			st.setString(6, obj.getStatusCollaborator());
			st.setDate(7, new java.sql.Date(obj.getDateEntry().getTime()));

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Collaborator obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE `collaborators` "
					+ "SET `password` = ?, `privilege` = ?, `office` = ? "
					+ "WHERE `registration` = ?");

			st.setString(1, obj.getPassword());
			st.setString(2, obj.getPrivilege());
			st.setString(3, obj.getOffice());
			st.setString(4, obj.getRegistration());

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
					"UPDATE `collaborators` " 
					+ "SET `statusCollaborator` = ?, `reason` = ? "
					+ "WHERE `serialNumber` = ?");

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
	public Map<String, Collaborator> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM `collaborators`");

			rs = st.executeQuery();

			Map<String, Collaborator> collaborators = new HashMap<>();

			while (rs.next()) {
				Collaborator collaborator = new Collaborator();

				collaborator.setName(rs.getString("name"));
				collaborator.setRegistration(rs.getString("registration"));
				collaborator.setPassword(rs.getString("password"));
				collaborator.setPrivilege(rs.getString("privilege"));
				collaborator.setOffice(rs.getString("office"));
				collaborator.setStatusCollaborator(rs.getString("statusCollaborator"));
				collaborator.setDateEntry(rs.getDate("dateEntry"));
				collaborators.put(collaborator.getRegistration(), collaborator);
			}
			return collaborators;
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
