package model.services.license;

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
import model.dao.LicenseDao;
import model.dao.ObjectWithUserDao;
import model.entities.Change;
import model.entities.License;
import model.entities.User;

public class LicenseService {

	private LicenseDao licenseDao = DaoFactory.createLicenseDao();
	private ChangeDao changeDao = DaoFactory.createChangeDao();
	private ObjectWithUserDao objectWithUserDao = DaoFactory.createObjectWithUserDao();

	public Map<String, License> findAll() {
		return licenseDao.findAll();
	}

	public void save(License obj) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			licenseDao.insert(obj); // Insert object into the database
			
			//Change
			Change change = getChange(obj, obj, 1); 
			changeDao.insert(change);
			obj.addChange(change);

			//Insert into running list
			LoadData.addChange(change);
			LoadData.addLicense(obj);
			
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

	public void update(License objOld, License objNew) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			licenseDao.update(objNew); //Update object into the database
			
			//Change
			Change change = getChange(objOld, objNew, 2);
			changeDao.insert(change);
			objNew.addChange(change);
			
			//Insert into running list
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
	
	public void updateForUser(License obj, User user) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			obj.setQuantity(obj.getQuantity() - 1);
			obj.setUser(user.getName());
			licenseDao.updateQuantity(obj); //Update object into the database
			
			user.addLicense(obj);
			
			//Change
			Change change = getChange(obj, obj, 3);
			changeDao.insert(change);
			obj.addChange(change);
			
			//Insert into running list
			LoadData.addChange(change);
			
			//Insert user/License relationship
			objectWithUserDao.insertLicenseWithUser(user, obj);

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

	public void disable(License obj) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			licenseDao.disable(obj); //Update object into the database
			
			//Change
			Change change = getChange(obj, obj, 4);
			changeDao.insert(change);
			obj.addChange(change);
			
			//Insert into running list
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

	private Change getChange(License objOld, License objNew, int type) {
		Change change = new Change();
		change.setObject(objNew.getCode());
		change.setType(getTypeChange(type));
		change.setChanges(getChanges(objOld, objNew, type));
		change.setDate(new Date());
		change.setAuthor(MainWindow.collaborator.getName());
		return change;
	}

	private String getTypeChange(int type) {
		String typeChange = "";
		if (type == 1) {
			typeChange = "License Input";
		} 
		else if (type == 2) {
			typeChange = "License Update";
		} 
		else if (type == 3) {
			typeChange = "License Update Quantity";
		} 
		else if (type == 4) {
			typeChange = "License Deactivation";
		}
		return typeChange;
	}

	private String getChanges(License objOld, License objNew, int type) {
		String changes = "";
		if (type == 1) {
			changes = "New License Added";
		} 
		else if (type == 2) {
			changes = getFieldsUpdated(objOld, objNew);
		} 
		else if (type == 3) {
			changes = objNew.getName() + " " + objNew.getBrand() + " delivered to the user: " + objNew.getUser();
		} 
		else if (type == 4) {
			changes = "License Disabled for: " + objOld.getReason();
		}
		return changes;
	}

	//Get the old value of fields that were changed
	private String getFieldsUpdated(License objOld, License objNew) {
		String fieldsUpdated = "Fields Updated: ";

		if (!objOld.getValue().equals(objNew.getValue())) {
			fieldsUpdated += " 'Value Old: " + objOld.getValue() + "'";
		}
		
		//Validation if there was a change
		String validation = fieldsUpdated.substring(16);
		if (validation.length() == 0) { 
			throw new ObjectException("There is no change");
		}

		return fieldsUpdated;
	}
}
