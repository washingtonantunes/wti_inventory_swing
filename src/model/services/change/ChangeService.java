package model.services.change;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import application.MainWindow;
import db.DB;
import db.DBException;
import model.dao.ChangeDao;
import model.dao.DaoFactory;
import model.entities.Change;
import model.entities.Equipment;
import model.entities.License;
import model.entities.Monitor;
import model.entities.Peripheral;
import model.entities.Project;
import model.entities.User;
import model.entities.WorkPosition;

public class ChangeService {

	private ChangeDao changeDao = DaoFactory.createChangeDao();

	public List<Change> findAll() {
		return changeDao.findAll();
	}
	
	public void insert(Change change) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			changeDao.insert(change);

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
	
	public void updateDefault(Change change) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			changeDao.updateDefault(change);

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
	
	public void updateSort(int idOld, int idNew) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			changeDao.updateSort(idOld, idNew);

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
	
	public void toSetDefault(int lastIndex) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			changeDao.toSetDefault(lastIndex);

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

	// GET Change Equipment
	public Change setChange(Equipment obj, String typeChange, String Changes) {
		Change change = new Change();

		change.setObject(obj.getSerialNumber());
		change.setType(typeChange);
		change.setChanges(Changes);
		change.setDate(new Date());
		change.setAuthor(MainWindow.collaborator.getName());

		return change;
	}

	// GET Change Monitor
	public Change setChange(Monitor obj, String typeChange, String Changes) {
		Change change = new Change();

		change.setObject(obj.getSerialNumber());
		change.setType(typeChange);
		change.setChanges(Changes);
		change.setDate(new Date());
		change.setAuthor(MainWindow.collaborator.getName());

		return change;
	}

	// GET Change Peripheral
	public Change setChange(Peripheral obj, String typeChange, String Changes) {
		Change change = new Change();

		change.setObject(obj.getCode());
		change.setType(typeChange);
		change.setChanges(Changes);
		change.setDate(new Date());
		change.setAuthor(MainWindow.collaborator.getName());

		return change;
	}

	// GET Change License
	public Change setChange(License obj, String typeChange, String Changes) {
		Change change = new Change();

		change.setObject(obj.getCode());
		change.setType(typeChange);
		change.setChanges(Changes);
		change.setDate(new Date());
		change.setAuthor(MainWindow.collaborator.getName());

		return change;
	}

	// GET Change WorkPosition
	public Change setChange(WorkPosition obj, String typeChange, String Changes) {
		Change change = new Change();

		change.setObject(obj.getWorkPoint());
		change.setType(typeChange);
		change.setChanges(Changes);
		change.setDate(new Date());
		change.setAuthor(MainWindow.collaborator.getName());

		return change;
	}

	// GET Change Project
	public Change setChange(Project obj, String typeChange, String Changes) {
		Change change = new Change();

		change.setObject(obj.getCostCenter());
		change.setType(typeChange);
		change.setChanges(Changes);
		change.setDate(new Date());
		change.setAuthor(MainWindow.collaborator.getName());

		return change;
	}

	// GET Change User
	public Change setChange(User obj, String typeChange, String Changes) {
		Change change = new Change();

		change.setObject(obj.getRegistration());
		change.setType(typeChange);
		change.setChanges(Changes);
		change.setDate(new Date());
		change.setAuthor(MainWindow.collaborator.getName());

		return change;
	}
}
