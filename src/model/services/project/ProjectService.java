package model.services.project;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import db.DB;
import db.DBException;
import exception.ObjectException;
import model.dao.ChangeDao;
import model.dao.DaoFactory;
import model.dao.ProjectDao;
import model.entities.Change;
import model.entities.Project;
import model.gui.MainWindow;

public class ProjectService {

	private ProjectDao projectDao = DaoFactory.createProjectDao();
	private ChangeDao changeDao = DaoFactory.createChangeDao();

	public Map<String, Project> findAll() {
		return projectDao.findAll();
	}

	public void save(Project obj) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			projectDao.insert(obj);

			Change change = getChange(obj, obj, 0);
			changeDao.insert(change);
			obj.addChange(change);

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

	public void update(Project objOld, Project objNew) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			projectDao.update(objNew);

			Change change = getChange(objOld, objNew, 1);
			changeDao.insert(change);
			objNew.addChange(change);

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

	public void disable(Project obj) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			projectDao.disable(obj);

			Change change = getChange(obj, obj, 3);
			changeDao.insert(change);
			obj.addChange(change);

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

	private Change getChange(Project objOld, Project objNew, int type) {
		Change change = new Change();
		change.setObject(objOld.getCostCenter());
		change.setType(getTypeChange(type));
		change.setChanges(getChanges(objOld, objNew, type));
		change.setDate(new Date());
		change.setAuthor(MainWindow.collaborator.getName());
		return change;
	}

	private String getTypeChange(int type) {
		String typeChange = "";
		if (type == 0) {
			typeChange = "Project Input";
		} 
		else if (type == 1) {
			typeChange = "Project Update";
		} 
		else if (type == 2) {
			typeChange = "Project Update Status";
		} 
		else if (type == 3) {
			typeChange = "Project Deactivation";
		}
		return typeChange;
	}

	private String getChanges(Project objOld, Project objNew, int type) {
		String changes = "";
		if (type == 0) {
			changes = "New Project Added";
		} 
		else if (type == 1) {
			changes = getFieldsUpdated(objOld, objNew);
		} 
		else if (type == 2) {

		} 
		else if (type == 3) {
			changes = "Project Disabled for: " + objOld.getReason();
		}
		return changes;
	}

	private String getFieldsUpdated(Project objOld, Project objNew) {
		String fieldsUpdated = "Fields Updated: ";

		if (!objOld.getName().equals(objNew.getName())) {
			fieldsUpdated += " 'Name Old: " + objOld.getName() + "',";
		}
		if (!objOld.getCity().equals(objNew.getCity())) {
			fieldsUpdated += " 'City Old: " + objOld.getCity() + "'";
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
