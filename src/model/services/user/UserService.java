package model.services.user;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import application.LoadData;
import application.MainWindow;
import db.DB;
import db.DBException;
import exception.ObjectException;
import model.dao.ChangeDao;
import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entities.Change;
import model.entities.User;

public class UserService {

	private UserDao userDao = DaoFactory.createUserDao();
	private ChangeDao changeDao = DaoFactory.createChangeDao();

	public Map<String, User> findAll() {
		return userDao.findAll();
	}

	public void save(User obj) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			userDao.insert(obj); // Insert object into the database

			// Change
			Change change = getChange(obj, obj, 1);
			changeDao.insert(change);
			obj.addChange(change);

			// Insert into running list
			LoadData.addChange(change);
			LoadData.addUser(obj);

			conn.commit();
		} 
		catch (SQLException e) {
			try {
				conn.rollback();
				throw new DBException("Transaction rolled back! Cause by: " + e.getMessage());
			} 
			catch (SQLException e1) {
				throw new DBException("Error trying to rollback! Cause by: " + e1.getMessage());
			}
		}
	}

	public void update(User objOld, User objNew) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			userDao.update(objNew); // Update object into the database

			// Change
			Change change = getChange(objOld, objNew, 2);
			changeDao.insert(change);
			objNew.addChange(change);

			// Insert into running list
			LoadData.addChange(change);

			conn.commit();
		} 
		catch (SQLException e) {
			try {
				conn.rollback();
				throw new DBException("Transaction rolled back! Cause by: " + e.getMessage());
			} 
			catch (SQLException e1) {
				throw new DBException("Error trying to rollback! Cause by: " + e1.getMessage());
			}
		}
	}

	public void disable(User obj) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			userDao.disable(obj); // Update object into the database

			// Change
			Change change = getChange(obj, obj, 4);
			changeDao.insert(change);
			obj.addChange(change);

			// Insert into running list
			LoadData.addChange(change);

			conn.commit();
		} 
		catch (SQLException e) {
			try {
				conn.rollback();
				throw new DBException("Transaction rolled back! Cause by: " + e.getMessage());
			} 
			catch (SQLException e1) {
				throw new DBException("Error trying to rollback! Cause by: " + e1.getMessage());
			}
		}
	}

	private Change getChange(User objOld, User objNew, int type) {
		Change change = new Change();
		change.setObject(objNew.getRegistration());
		change.setType(getTypeChange(type));
		change.setChanges(getChanges(objOld, objNew, type));
		change.setDate(new Date());
		change.setAuthor(MainWindow.collaborator.getName());
		return change;
	}

	private String getTypeChange(int type) {
		String typeChange = "";
		if (type == 1) {
			typeChange = "User Input";
		} 
		else if (type == 2) {
			typeChange = "User Update";
		} 
		else if (type == 3) {
			typeChange = "User Deactivation";
		}
		return typeChange;
	}

	private String getChanges(User objOld, User objNew, int type) {
		String changes = "";
		if (type == 1) {
			changes = "New User Added";
		} 
		else if (type == 2) {
			changes = getFieldsUpdated(objOld, objNew);
		} 
		else if (type == 3) {
			changes = "User Disabled for: " + objOld.getReason();
		}
		return changes;
	}

	private String getFieldsUpdated(User objOld, User objNew) {
		String fieldsUpdated = "Fields Updated: ";

		if (!objOld.getName().equalsIgnoreCase(objNew.getName())) {
			fieldsUpdated += " 'Name Old: " + objOld.getName() + "',";
		}
		if (!objOld.getCpf().equals(objNew.getCpf())) {
			fieldsUpdated += " 'CPF Old: " + objOld.getCpf() + "',";
		}
		if (!objOld.getPhone().equals(objNew.getPhone())) {
			fieldsUpdated += " 'Phone Old: " + objOld.getPhone() + "',";
		}
		if (!objOld.getEmail().equalsIgnoreCase(objNew.getEmail())) {
			fieldsUpdated += " 'Email Old: " + objOld.getEmail() + "',";
		}
		if (!objOld.getDepartment().equals(objNew.getDepartment())) {
			fieldsUpdated += " 'Department Old: " + objOld.getDepartment() + "',";
		}
		if (!objOld.getProject().equals(objNew.getProject())) {
			fieldsUpdated += " 'Project Old: " + objOld.getProject() + "'";
		}

		int i = fieldsUpdated.lastIndexOf(",");
		if (i + 1 == fieldsUpdated.length()) {
			fieldsUpdated = fieldsUpdated.substring(0, i).trim();
		}

		String validation = fieldsUpdated.substring(16);
		if (validation.length() == 0) {
			throw new ObjectException("There is no change");
		}

		return fieldsUpdated;
	}
}