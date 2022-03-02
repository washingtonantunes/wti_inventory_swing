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
import model.util.Utils;

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

	public void addForUser(Equipment obj, User user) {
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
			
			//Change User
			Change changeUser = getChange(obj, obj, 3);
			changeUser.setObject(user.getRegistration());
			changeUser.setChanges(getChangeUser(obj, 1));
			changeDao.insert(changeUser);
			user.addChange(changeUser);
			
			//Insert into running list
			LoadData.addChange(changeUser);
			
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
	
	public void exchangeForUser(Equipment equipmentOld, Equipment EquipmentNew, User user) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);
			
			final String typeChange = "Equipment Exchange";
			
			{//Equipment Old
				//Change Equipment
				Change change = getChange(equipmentOld, equipmentOld, 4);
				change.setType(typeChange);
				changeDao.insert(change);
				equipmentOld.addChange(change);
				
				//Insert into running list
				LoadData.addChange(change);
				
				//Change User
				Change changeUser = getChange(equipmentOld, equipmentOld, 4);
				changeUser.setObject(user.getRegistration());
				changeUser.setChanges(getChangeUser(equipmentOld, 2));
				changeUser.setType(typeChange);
				changeDao.insert(changeUser);
				user.addChange(changeUser);
				
				//Insert into running list
				LoadData.addChange(changeUser);
				
				equipmentOld.setLocation(Utils.getLocationEquipment());
				equipmentOld.setStatus("STAND BY");
				equipmentOld.setUser(null);
				equipmentOld.setProject(null);
				equipmentDao.updateStatusForUser(equipmentOld); //Update object into the database
				
				user.removeEquipment(equipmentOld);

				//Insert user/equipment relationship
				objectWithUserDao.removeEquipmentWithUser(user, equipmentOld);
			}


			{//Equipment New
				EquipmentNew.setLocation("WITH USER");
				EquipmentNew.setStatus("IN USE");
				EquipmentNew.setUser(user);
				EquipmentNew.setProject(user.getProject());
				equipmentDao.updateStatusForUser(EquipmentNew); //Update object into the database
				
				user.addEquipment(EquipmentNew);

				//Change
				Change change = getChange(EquipmentNew, EquipmentNew, 3);
				change.setType(typeChange);
				changeDao.insert(change);
				EquipmentNew.addChange(change);
				
				//Insert into running list
				LoadData.addChange(change);
				
				//Change User
				Change changeUser = getChange(EquipmentNew, EquipmentNew, 3);
				changeUser.setObject(user.getRegistration());
				changeUser.setChanges(getChangeUser(EquipmentNew, 1));
				changeUser.setType(typeChange);
				changeDao.insert(changeUser);
				user.addChange(changeUser);
				
				//Insert into running list
				LoadData.addChange(changeUser);
				
				//Insert user/equipment relationship
				objectWithUserDao.insertEquipmentWithUser(user, EquipmentNew);
			}
			
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
	
	public void removeForUser(Equipment obj, User user) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);
			
			//Change Equipment
			Change change = getChange(obj, obj, 4);
			changeDao.insert(change);
			obj.addChange(change);
			
			//Insert into running list
			LoadData.addChange(change);
			
			//Change User
			Change changeUser = getChange(obj, obj, 4);
			changeUser.setObject(user.getRegistration());
			changeUser.setChanges(getChangeUser(obj, 2));
			changeDao.insert(changeUser);
			user.addChange(changeUser);
			
			//Insert into running list
			LoadData.addChange(changeUser);
			
			obj.setLocation(Utils.getLocationEquipment());
			obj.setStatus("STAND BY");
			obj.setUser(null);
			obj.setProject(null);
			equipmentDao.updateStatusForUser(obj); //Update object into the database
			
			user.removeEquipment(obj);

			//Insert user/equipment relationship
			objectWithUserDao.removeEquipmentWithUser(user, obj);

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
			Change change = getChange(obj, obj, 5);
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
			typeChange = "Equipment Exit";
		} 
		else if (type == 4) {
			typeChange = "Equipment Return";
		} 
		else if (type == 5) {
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
			changes = "Equipment returned by the user: " + objNew.getUser().getName();
		} 
		else if (type == 5) {
			changes = "Equipment Disabled for: " + objOld.getReason();
		}
		return changes;
	}
	
	private String getChangeUser(Equipment equipment, int type) {
		String changes = "";
		
		if (type == 1) {
			changes = "Equipment "+ equipment.getSerialNumber() + " delivered to the user";
		} 
		else if (type == 2) {
			changes = "Equipment "+ equipment.getSerialNumber() + " returned by the user";
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
		if (objOld.getNoteEntry() == null && objNew.getNoteEntry() != null) {
			fieldsUpdated += " 'NoteEntry Old: NULL " + "',";
		} else if (!objOld.getNoteEntry().equals(objNew.getNoteEntry())) {
			fieldsUpdated += " 'NoteEntry Old: " + objOld.getNoteEntry() + "',";
		}
		if (objOld.getNote() == null && objNew.getNote() != null) {
			fieldsUpdated += " 'Note Old: NULL " + "',";
		} else if (objOld.getNote() != null && objNew.getNote() == null) {
			fieldsUpdated += " 'Note Old: " + objOld.getNote() + "',";
		} else if (!objOld.getNote().equals(objNew.getNote())) {
			fieldsUpdated += " 'Note Old: " + objOld.getNote() + "'";
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
