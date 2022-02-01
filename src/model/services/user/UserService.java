package model.services.user;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import db.DB;
import db.DBException;
import model.dao.ChangeDao;
import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entities.Change;
import model.entities.User;
import model.gui.MainWindow;

public class UserService {

	private UserDao userDao = DaoFactory.createUserDao();
	private ChangeDao changeDao = DaoFactory.createChangeDao();

	public List<User> findAll() {
		return userDao.findAll();
	}

	public void save(User obj) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			userDao.insert(obj);
			changeDao.insert(getChange(obj, 0));

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

			userDao.update(objNew);
			changeDao.insert(getChange(objOld, objNew, 1));

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

			userDao.disable(obj);
			changeDao.insert(getChange(obj, 3));

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
		change.setObject(objOld.getRegistration());
		change.setType(getTypeChange(type));
		change.setChanges(getChanges(objOld, objNew, type));
		change.setDate(new Date());
		change.setAuthor(MainWindow.collaborator.getName());
		return change;
	}

	private Change getChange(User obj, int type) {
		Change change = new Change();
		change.setObject(obj.getRegistration());
		change.setType(getTypeChange(type));
		change.setChanges(getChanges(null, null, type));
		change.setDate(new Date());
		change.setAuthor(MainWindow.collaborator.getName());
		return change;
	}

	private String getTypeChange(int type) {
		String typeChange = "";
		if (type == 0) {
			typeChange = "User Input";
		} 
		else if (type == 1) {
			typeChange = "User Update";
		} 
		else if (type == 2) {
			typeChange = "User Update Status";
		} 
		else if (type == 3) {
			typeChange = "User Deactivation";
		}
		return typeChange;
	}

	private String getChanges(User objOld, User objNew, int type) {
		String changes = "";
		if (type == 0) {
			changes = "New User Added";
		} 
		else if (type == 1) {
			changes = getFieldsUpdated(objOld, objNew);
		} 
		else if (type == 2) {

		} 
		else if (type == 3) {
			changes = "User Disabled for: " + objOld.getReason();
		}
		return changes;
	}

	private String getFieldsUpdated(User objOld, User objNew) {
		String fieldsUpdated = "Fields Updated: ";

		if (!objOld.getName().equals(objNew.getName())) {
			fieldsUpdated += " 'Name Old: " + objOld.getName() + "',";
		}
		if (!objOld.getCPF().equals(objNew.getCPF())) {
			fieldsUpdated += " 'CPF Old: " + objOld.getCPF() + "',";
		}
		if (!objOld.getPhone().equals(objNew.getPhone())) {
			fieldsUpdated += " 'Phone Old: " + objOld.getPhone() + "'";
		}
		if (!objOld.getProject().equals(objNew.getProject())) {
			fieldsUpdated += " 'Project Old: " + objOld.getProject() + "'";
		}
		if (!objOld.getEmail().equals(objNew.getEmail())) {
			fieldsUpdated += " 'Email Old: " + objOld.getEmail() + "'";
		}
		if (!objOld.getDepartment().equals(objNew.getDepartment())) {
			fieldsUpdated += " 'Department Old: " + objOld.getDepartment() + "'";
		}

		int i = fieldsUpdated.lastIndexOf(",");
		if (i + 1 == fieldsUpdated.length()) {
			fieldsUpdated = fieldsUpdated.substring(0, i);
		}

		return fieldsUpdated;
	}
}
