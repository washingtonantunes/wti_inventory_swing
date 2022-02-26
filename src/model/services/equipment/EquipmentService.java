package model.services.equipment;

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
import model.dao.EquipmentDao;
import model.dao.ObjectWithUserDao;
import model.entities.Change;
import model.entities.Equipment;
import model.entities.User;

public class EquipmentService {

	private EquipmentDao equipmentDao = DaoFactory.createEquipmentDao();
	private ChangeDao changeDao = DaoFactory.createChangeDao();
	private ObjectWithUserDao objectWithUserDao = DaoFactory.createObjectWithUserDao();

	public Map<String, Equipment> findAll() {
		return equipmentDao.findAll();
	}

	public void save(Equipment obj) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			equipmentDao.insert(obj); // Insert object into the database

			//Change
			Change change = getChange(obj, obj, 1); 
			changeDao.insert(change);
			obj.addChange(change);

			//Insert into running list
			LoadData.addChange(change);
			LoadData.addEquipment(obj);

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

	public void update(Equipment objOld, Equipment objNew) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			equipmentDao.update(objNew); //Update object into the database

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

	public void updateStatusForUser(Equipment obj, User user) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);
			
			obj.setLocation("WITH USER");
			obj.setStatus("IN USE");
			obj.setUser(user);
			obj.setProject(user.getProject());
			equipmentDao.updateStatusForUser(obj); //Update object into the database
			
			user.addEquipment(obj);

			//Change
			Change change = getChange(obj, obj, 3);
			changeDao.insert(change);
			obj.addChange(change);
			
			//Insert into running list
			LoadData.addChange(change);
			
			//Insert user/equipment relationship
			objectWithUserDao.insertEquipmentWithUser(user, obj);

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

	public void disable(Equipment obj) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			equipmentDao.disable(obj); //Update object into the database

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

	private Change getChange(Equipment objOld, Equipment objNew, int type) {
		Change change = new Change();
		change.setObject(objNew.getSerialNumber());
		change.setType(getTypeChange(type));
		change.setChanges(getChanges(objOld, objNew, type));
		change.setDate(new Date());
		change.setAuthor(MainWindow.collaborator.getName());
		return change;
	}

	private String getTypeChange(int type) {
		String typeChange = "";
		if (type == 1) {
			typeChange = "Equipment Input";
		} 
		else if (type == 2) {
			typeChange = "Equipment Update";
		} 
		else if (type == 3) {
			typeChange = "Equipment Update Status";
		} 
		else if (type == 4) {
			typeChange = "Equipment Deactivation";
		}
		return typeChange;
	}

	private String getChanges(Equipment objOld, Equipment objNew, int type) {
		String changes = "";
		if (type == 1) {
			changes = "New Equipment Added";
		} 
		else if (type == 2) {
			changes = getFieldsUpdated(objOld, objNew);
		} 
		else if (type == 3) {
			changes = "Equipment delivered to the user: " + objNew.getUser().getName();
		} 
		else if (type == 4) {
			changes = "Equipment Disabled for: " + objOld.getReason();
		}
		return changes;
	}

	// Get the old value of fields that were changed
	private String getFieldsUpdated(Equipment objOld, Equipment objNew) {
		String fieldsUpdated = "Fields Updated: ";

		if (!objOld.getHostName().equalsIgnoreCase(objNew.getHostName())) {
			fieldsUpdated += " 'HostName Old: " + objOld.getHostName() + "',";
		}
		if (!objOld.getAddressMAC().equalsIgnoreCase(objNew.getAddressMAC())) {
			fieldsUpdated += " 'AddressMAC Old: " + objOld.getAddressMAC() + "',";
		}
		if (!objOld.getType().equals(objNew.getType())) {
			fieldsUpdated += " 'Type Old: " + objOld.getType() + "',";
		}
		if (!objOld.getPatrimonyNumber().equals(objNew.getPatrimonyNumber())) {
			fieldsUpdated += " 'PatrimonyNumber Old: " + objOld.getPatrimonyNumber() + "',";
		}
		if (!objOld.getBrand().equals(objNew.getBrand())) {
			fieldsUpdated += " 'Brand Old: " + objOld.getBrand() + "',";
		}
		if (!objOld.getModel().equals(objNew.getModel())) {
			fieldsUpdated += " 'Model Old: " + objOld.getModel() + "',";
		}
		if (!objOld.getMemoryRam().equals(objNew.getMemoryRam())) {
			fieldsUpdated += " 'MemoryRam Old: " + objOld.getMemoryRam() + "',";
		}
		if (!objOld.getHardDisk().equals(objNew.getHardDisk())) {
			fieldsUpdated += " 'HardDisk Old: " + objOld.getHardDisk() + "',";
		}
		if (objOld.getCostType() == null && objNew.getCostType() != null) {
			fieldsUpdated += " 'CostType Old: NULL " + "',";
		} else if (!objOld.getCostType().equals(objNew.getCostType())) {
			fieldsUpdated += " 'CostType Old: " + objOld.getCostType() + "',";
		}
		if (!objOld.getValue().equals(objNew.getValue())) {
			fieldsUpdated += " 'Value Old: " + objOld.getValue() + "',";
		}
		if (!objOld.getNoteEntry().equalsIgnoreCase(objNew.getNoteEntry())) {
			fieldsUpdated += " 'NoteEntry Old: " + objOld.getNoteEntry() + "'";
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
