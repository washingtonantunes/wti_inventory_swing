package model.services.peripheral;

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
import model.dao.ObjectWithUserDao;
import model.dao.PeripheralDao;
import model.entities.Change;
import model.entities.Peripheral;
import model.entities.User;

public class PeripheralService {

	private PeripheralDao peripheralDao = DaoFactory.createPeripheralDao();
	private ChangeDao changeDao = DaoFactory.createChangeDao();
	private ObjectWithUserDao objectWithUserDao = DaoFactory.createObjectWithUserDao();

	public Map<String, Peripheral> findAll() {
		return peripheralDao.findAll();
	}

	public void save(Peripheral obj) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			peripheralDao.insert(obj); // Insert object into the database
			
			//Change
			Change change = getChange(obj, obj, 1); 
			changeDao.insert(change);
			obj.addChange(change);

			//Insert into running list
			LoadData.addChange(change);
			LoadData.addPeripheral(obj);

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

	public void update(Peripheral objOld, Peripheral objNew) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			peripheralDao.update(objNew); //Update object into the database
			
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
	
	public void updateForUser(Peripheral obj, User user) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			obj.setQuantity(obj.getQuantity() - 1);
			obj.setUser(user.getName());
			peripheralDao.updateQuantity(obj);
			
			user.addPeripheral(obj);
			
			//Change
			Change change = getChange(obj, obj, 3);
			changeDao.insert(change);
			obj.addChange(change);
			
			//Insert into running list
			LoadData.addChange(change);
			
			//Insert user/peripheral relationship
			objectWithUserDao.insertPeripheralWithUser(user, obj);

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

	public void disable(Peripheral obj) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			peripheralDao.disable(obj); //Update object into the database
			
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

	private Change getChange(Peripheral objOld, Peripheral objNew, int type) {
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
			typeChange = "Peripheral Input";
		} 
		else if (type == 2) {
			typeChange = "Peripheral Update";
		} 
		else if (type == 3) {
			typeChange = "Peripheral Update Quantity";
		} 
		else if (type == 4) {
			typeChange = "Peripheral Deactivation";
		}
		return typeChange;
	}

	private String getChanges(Peripheral objOld, Peripheral objNew, int type) {
		String changes = "";
		if (type == 1) {
			changes = "New Peripheral Added";
		} 
		else if (type == 2) {
			changes = getFieldsUpdated(objOld, objNew);
		} 
		else if (type == 3) {
			changes = objNew.getName() + " " + objNew.getBrand() + " delivered to the user: " + objNew.getUser();
		} 
		else if (type == 4) {
			changes = "Peripheral Disabled for: " + objOld.getReason();
		}
		return changes;
	}

	//Get the old value of fields that were changed
	private String getFieldsUpdated(Peripheral objOld, Peripheral objNew) {
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
