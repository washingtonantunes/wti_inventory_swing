package model.services.equipment;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import db.DB;
import db.DBException;
import model.dao.ChangeDao;
import model.dao.DaoFactory;
import model.dao.EquipmentDao;
import model.entities.Change;
import model.entities.Equipment;
import model.gui.MainWindow;

public class EquipmentService {

	private EquipmentDao equipmentDao = DaoFactory.createEquipmentDao();
	private ChangeDao changeDao = DaoFactory.createChangeDao();

	public List<Equipment> findAll() {
		return equipmentDao.findAll();
	}

	public void save(Equipment obj) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			equipmentDao.insert(obj);
			changeDao.insert(getChange(obj, obj, 0));

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

	public void disable(Equipment obj) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			equipmentDao.disable(obj);
			changeDao.insert(getChange(obj, obj, 3));

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
		if (type == 0) {
			typeChange = "Equipment Input";
		} 
		else if (type == 1) {
			typeChange = "Equipment Update";
		} 
		else if (type == 2) {
			typeChange = "Equipment Update Status";
		} 
		else if (type == 3) {
			typeChange = "Equipment Deactivation";
		}
		return typeChange;
	}

	private String getChanges(Equipment objOld, Equipment objNew, int type) {
		String changes = "";
		if (type == 0) {
			changes = "New Equipment Added";
		} 
		else if (type == 1) {
			changes = getFieldsUpdated(objOld, objNew);
		} 
		else if (type == 2) {

		} 
		else if (type == 3) {
			changes = "Equipment Disabled for: " + objOld.getReason();
		}
		return changes;
	}

	private String getFieldsUpdated(Equipment objOld, Equipment objNew) {
		String fieldsUpdated = "Fields Updated: ";

		if (!objOld.getHostName().equals(objNew.getHostName())) {
			fieldsUpdated += " 'HostName Old: " + objOld.getHostName() + "',";
		}
		if (!objOld.getAddressMAC().equals(objNew.getAddressMAC())) {
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
			fieldsUpdated += " 'Value Old: " + objOld.getValue() + "'";
		}

		int i = fieldsUpdated.lastIndexOf(",");
		if (i + 1 == fieldsUpdated.length()) {
			fieldsUpdated = fieldsUpdated.substring(0, i);
		}

		return fieldsUpdated;
	}
}
