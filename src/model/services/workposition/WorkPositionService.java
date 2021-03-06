package model.services.workposition;

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
import model.dao.WorkPositionDao;
import model.entities.Change;
import model.entities.WorkPosition;

public class WorkPositionService {

	private WorkPositionDao workPositionDao = DaoFactory.createWorkPositionDao();
	private ChangeDao changeDao = DaoFactory.createChangeDao();

	public Map<String, WorkPosition> findAll() {
		return workPositionDao.findAll();
	}

	public void save(WorkPosition obj) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			workPositionDao.insert(obj); // Insert object into the database

			//Change
			Change change = getChange(obj, obj, 1); 
			changeDao.insert(change);
			obj.addChange(change);

			//Insert into running list
			LoadData.addChange(change);
			LoadData.addWorkPosition(obj);

			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
				throw new DBException("Transaction rolled back! Cause by: " + e.getMessage());
			} catch (SQLException e1) {
				throw new DBException("Error trying to rollback! Cause by: " + e1.getMessage());
			}
		}
	}

	public void update(WorkPosition objOld, WorkPosition objNew) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			workPositionDao.update(objNew); //Update object into the database

			//Change
			Change change = getChange(objOld, objNew, 2);
			changeDao.insert(change);
			objNew.addChange(change);
			
			//Insert into running list
			LoadData.addChange(change);

			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
				throw new DBException("Transaction rolled back! Cause by: " + e.getMessage());
			} catch (SQLException e1) {
				throw new DBException("Error trying to rollback! Cause by: " + e1.getMessage());
			}
		}
	}

	public void disable(WorkPosition obj) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			workPositionDao.disable(obj); //Update object into the database

			//Change
			Change change = getChange(obj, obj, 4);
			changeDao.insert(change);
			obj.addChange(change);
			
			//Insert into running list
			LoadData.addChange(change);

			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
				throw new DBException("Transaction rolled back! Cause by: " + e.getMessage());
			} catch (SQLException e1) {
				throw new DBException("Error trying to rollback! Cause by: " + e1.getMessage());
			}
		}
	}

	private Change getChange(WorkPosition objOld, WorkPosition objNew, int type) {
		Change change = new Change();
		change.setObject(objNew.getWorkPoint());
		change.setType(getTypeChange(type));
		change.setChanges(getChanges(objOld, objNew, type));
		change.setDate(new Date());
		change.setAuthor(MainWindow.collaborator.getName());
		return change;
	}

	private String getTypeChange(int type) {
		String typeChange = "";
		if (type == 1) {
			typeChange = "Work Position Input";
		} else if (type == 2) {
			typeChange = "Work Position Update";
		} else if (type == 3) {
			typeChange = "Work Position Update Status";
		} else if (type == 4) {
			typeChange = "Work Position Deactivation";
		}
		return typeChange;
	}

	private String getChanges(WorkPosition objOld, WorkPosition objNew, int type) {
		String changes = "";
		if (type == 1) {
			changes = "New Work Position Added";
		} else if (type == 2) {
			changes = getFieldsUpdated(objOld, objNew);
		} else if (type == 3) {

		} else if (type == 4) {
			changes = "Work Position Disabled for: " + objOld.getReason();
		}
		return changes;
	}

	//Get the old value of fields that were changed
	private String getFieldsUpdated(WorkPosition objOld, WorkPosition objNew) {
		String fieldsUpdated = "Fields Updated: ";

		if (!objOld.getLocation().equals(objNew.getLocation())) {
			fieldsUpdated += " 'Location Old: " + objOld.getLocation() + "',";
		}
		if (!objOld.getFloor().equals(objNew.getFloor())) {
			fieldsUpdated += " 'Floor Old: " + objOld.getFloor() + "',";
		}
		if (objOld.getNetPoint() == null && objNew.getNetPoint() != null) {
			fieldsUpdated += " 'NetPoint Old: NULL " + "',";
		} else if (objOld.getNetPoint() != null && objNew.getNetPoint() == null) {
			fieldsUpdated += " 'NetPoint Old: " + objOld.getNetPoint() + "',";
		} else if (!objOld.getNetPoint().equals(objNew.getNetPoint())) {
			fieldsUpdated += " 'NetPoint Old: " + objOld.getNetPoint() + "'";
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
