package model.services.user;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import db.DB;
import db.DBException;
import exception.ObjectException;
import model.dao.ChangeDao;
import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entities.Change;
import model.entities.User;
import model.gui.MainWindow;

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

			userDao.insert(obj);
			
			Change change = getChange(obj, 1);
			
			change.setChanges("New User Added");
			
			changeDao.insert(change);
			obj.addChange(change);
			MainWindow.addChange(change);
			
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
			
			Change change = getChange(objOld, 2);
			
			change.setChanges(getFieldsUpdated(objOld, objNew));
			
			changeDao.insert(change);
			objNew.addChange(change);
			MainWindow.addChange(change);

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
	
	public void updateItem(User obj) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

//			userDao.updateItem(obj);

			System.out.println(obj);

			conn.commit();
		} 
		catch (SQLException db1) {
			try {
				conn.rollback();
				throw new DBException("Transaction rolled back! Cause by: " + db1.getMessage());
			} 
			catch (SQLException db2) {
				throw new DBException("Error trying to rollback! Cause by: " + db2.getMessage());
			}
		}
	}

	public void disable(User obj) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			userDao.disable(obj);
			
			Change change = getChange(obj, 4);
			
			change.setChanges("User Disabled for: " + obj.getReason());
			
			changeDao.insert(change);
			obj.addChange(change);
			MainWindow.addChange(change);

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
	
	private Change getChange(User obj, int type) {
		Change change = new Change();
		change.setObject(obj.getRegistration());
		change.setType(getTypeChange(type));
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
			typeChange = "User Update Status";
		} 
		else if (type == 4) {
			typeChange = "User Deactivation";
		}
		return typeChange;
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