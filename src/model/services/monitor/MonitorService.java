package model.services.monitor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import db.DB;
import db.DBException;
import model.dao.ChangeDao;
import model.dao.DaoFactory;
import model.dao.MonitorDao;
import model.entities.Change;
import model.entities.Monitor;
import model.gui.MainWindow;

public class MonitorService {

	private MonitorDao equipmentDao = DaoFactory.createMonitorDao();
	private ChangeDao changeDao = DaoFactory.createChangeDao();

	public List<Monitor> findAll() {
		return equipmentDao.findAll();
	}

	public void save(Monitor obj) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			equipmentDao.insert(obj);
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

	public void update(Monitor objOld, Monitor objNew) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			equipmentDao.update(objNew);
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

	public void disable(Monitor obj) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			equipmentDao.disable(obj);
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
	
	private Change getChange(Monitor objOld, Monitor objNew, int type) {
		Change change = new Change();
		change.setObject(objOld.getSerialNumber());
		change.setType(getTypeChange(type));
		change.setChanges(getChanges(objOld, objNew, type));
		change.setDate(new Date());
		change.setAuthor(MainWindow.collaborator.getName());
		return change;
	}
	
	private Change getChange(Monitor obj, int type) {
		Change change = new Change();
		change.setObject(obj.getSerialNumber());
		change.setType(getTypeChange(type));
		change.setChanges(getChanges(null, null, type));
		change.setDate(new Date());
		change.setAuthor(MainWindow.collaborator.getName());
		return change;
	}
	
	private String getTypeChange(int type) {
		String typeChange = "";
		if (type == 0) {
			typeChange = "Monitor Input";
		} else if (type == 1) {
			typeChange = "Monitor Update";
		} else if (type == 2) {
			typeChange = "Monitor Update Status";
		} else if (type == 3) {
			typeChange = "Monitor Deactivation";
		}
		return typeChange;
	}
	
	private String getChanges(Monitor objOld, Monitor objNew, int type) {
		String changes = "";
		if (type == 0) {
			changes = "New Monitor Added";
		} else if (type == 1) {
			changes = getFieldsUpdated(objOld, objNew);
		} else if (type == 2) {
			
		} else if (type == 3) {
			changes = "Monitor Disabled for: " + objOld.getReason();
		}
		return changes;
	}
	
	private String getFieldsUpdated(Monitor objOld, Monitor objNew) {
		String fieldsUpdated = "Fields Updated: ";
		
		if (!objOld.getPatrimonyNumber().equals(objNew.getPatrimonyNumber())) {
			fieldsUpdated += " 'PatrimonyNumber Old: " + objOld.getPatrimonyNumber() + "',";
		}
		if (!objOld.getBrand().equals(objNew.getBrand())) {
			fieldsUpdated += " 'Brand Old: " + objOld.getBrand() + "',";
		}
		if (!objOld.getModel().equals(objNew.getModel())) {
			fieldsUpdated += " 'Model Old: " + objOld.getModel() + "'";
		}
		
		int i = fieldsUpdated.lastIndexOf(",");
		if(i + 1 == fieldsUpdated.length()) {
			fieldsUpdated = fieldsUpdated.substring(0, i);
		}
		
		return fieldsUpdated;
	}
}
