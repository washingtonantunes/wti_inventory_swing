package model.services.monitor;

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
import model.dao.MonitorDao;
import model.dao.ObjectWithUserDao;
import model.entities.Change;
import model.entities.Monitor;
import model.entities.User;

public class MonitorService {

	private MonitorDao monitorDao = DaoFactory.createMonitorDao();
	private ChangeDao changeDao = DaoFactory.createChangeDao();
	private ObjectWithUserDao objectWithUserDao = DaoFactory.createObjectWithUserDao();

	public Map<String, Monitor> findAll() {
		return monitorDao.findAll();
	}

	public void save(Monitor obj) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			monitorDao.insert(obj); // Insert object into the database

			// Change
			Change change = getChange(obj, obj, 1);
			changeDao.insert(change);
			obj.addChange(change);

			// Insert into running list
			LoadData.addChange(change);
			LoadData.addMonitor(obj);

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

	public void update(Monitor objOld, Monitor objNew) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			monitorDao.update(objNew); // Update object into the database

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

	public void updateStatusForUser(Monitor obj, User user) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			obj.setLocation("WITH USER");
			obj.setStatus("IN USE");
			obj.setUser(user);
			obj.setProject(user.getProject());
			monitorDao.updateStatusForUser(obj); // Update object into the database
			
			user.addMonitor(obj);

			// Change
			Change change = getChange(obj, obj, 3);
			changeDao.insert(change);
			obj.addChange(change);

			// Insert into running list
			LoadData.addChange(change);

			// Insert user/monitor relationship
			objectWithUserDao.insertMonitorWithUser(user, obj);

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

	public void disable(Monitor obj) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			monitorDao.disable(obj); // Update object into the database

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

	private Change getChange(Monitor objOld, Monitor objNew, int type) {
		Change change = new Change();
		change.setObject(objOld.getSerialNumber());
		change.setType(getTypeChange(type));
		change.setChanges(getChanges(objOld, objNew, type));
		change.setDate(new Date());
		change.setAuthor(MainWindow.collaborator.getName());
		return change;
	}

	private String getTypeChange(int type) {
		String typeChange = "";
		if (type == 1) {
			typeChange = "Monitor Input";
		} 
		else if (type == 2) {
			typeChange = "Monitor Update";
		} 
		else if (type == 3) {
			typeChange = "Monitor Update Status";
		} 
		else if (type == 4) {
			typeChange = "Monitor Deactivation";
		}
		return typeChange;
	}

	private String getChanges(Monitor objOld, Monitor objNew, int type) {
		String changes = "";
		if (type == 1) {
			changes = "New Monitor Added";
		} 
		else if (type == 2) {
			changes = getFieldsUpdated(objOld, objNew);
		} 
		else if (type == 3) {
			changes = "Monitor delivered to the user: " + objNew.getUser().getName();
		} 
		else if (type == 4) {
			changes = "Monitor Disabled for: " + objOld.getReason();
		}
		return changes;
	}

	// Get the old value of fields that were changed
	private String getFieldsUpdated(Monitor objOld, Monitor objNew) {
		String fieldsUpdated = "Fields Updated: ";

		if (!objOld.getPatrimonyNumber().equals(objNew.getPatrimonyNumber())) {
			fieldsUpdated += " 'PatrimonyNumber Old: " + objOld.getPatrimonyNumber() + "',";
		}
		if (!objOld.getBrand().equals(objNew.getBrand())) {
			fieldsUpdated += " 'Brand Old: " + objOld.getBrand() + "',";
		}
		if (!objOld.getModel().equals(objNew.getModel())) {
			fieldsUpdated += " 'Model Old: " + objOld.getModel() + "',";
		}
		if (objOld.getCostType() == null && objNew.getCostType() != null) {
			fieldsUpdated += " 'CostType Old: NULL " + "',";
		} 
		else if (!objOld.getCostType().equals(objNew.getCostType())) {
			fieldsUpdated += " 'CostType Old: " + objOld.getCostType() + "',";
		}
		if (!objOld.getValue().equals(objNew.getValue())) {
			fieldsUpdated += " 'Value Old: " + objOld.getValue() + "',";
		}
		if (!objOld.getNoteEntry().equals(objNew.getNoteEntry())) {
			fieldsUpdated += " 'Note Entry Old: " + objOld.getNoteEntry() + "'";
		}

		// Remove the ',' at the end of the String
		int i = fieldsUpdated.lastIndexOf(",");
		if (i + 1 == fieldsUpdated.length()) {
			fieldsUpdated = fieldsUpdated.substring(0, i).trim();
		}

		// Validation if there was a change
		String validation = fieldsUpdated.substring(16);
		if (validation.length() == 0) {
			throw new ObjectException("There is no change");
		}

		return fieldsUpdated;
	}
}
