package model.services;

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
	
	private Equipment objOld;
	private Equipment objNew;

	public List<Equipment> findAll() {
		return equipmentDao.findAll();
	}

	public void save(Equipment obj) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			equipmentDao.insert(obj);
			changeDao.insert(getChange(obj.getSerialNumber(), 0));

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
		this.objOld = objOld;
		this.objNew = objNew;
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			equipmentDao.update(objNew);
			changeDao.insert(getChange(objOld.getSerialNumber(), 1));

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

	public void disable(Equipment obj, Change change) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			equipmentDao.disable(obj.getSerialNumber(), obj.getStatus(), obj.getReason());
			//changeDao.insert(getChange(obj.getSerialNumber()));

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
	
	private Change getChange(String obj, int type) {
		Change change = new Change();
		change.setObject(obj);
		change.setTypeChange(getTypeChange(type));
		change.setChanges(getChanges(type));
		change.setDateChange(new Date());
		change.setAuthor(MainWindow.author);
		return change;
	}
	
	private String getTypeChange(int type) {
		String typeChange = "";
		if (type == 0) {
			typeChange = "Equipment Input";
		} else if (type == 1) {
			typeChange = "Equipment Update";
		} else if (type == 2) {
			typeChange = "Equipment Update Status";
		} else if (type == 3) {
			typeChange = "Equipment Disable";
		}
		return typeChange;
	}
	
	private String getChanges(int type) {
		String changes = "";
		if (type == 0) {
			changes = "New Equipment Added";
		} else if (type == 1) {
			changes = getFieldsUpdated();
		} else if (type == 2) {
			
		} else if (type == 3) {
			
		}
		return changes;
	}
	
	private String getFieldsUpdated() {
		String fieldsUpdated = "Fields Updated: ";

		if (!objOld.getMemoryRam().equals(objNew.getMemoryRam())) {
			fieldsUpdated += "'MemoryRam Old: " + objOld.getMemoryRam() + "', ";
		}
		if (!objOld.getHardDisk().equals(objNew.getHardDisk())) {
			fieldsUpdated += "'HardDisk Old: " + objOld.getHardDisk() + "', ";
		}
		if (!objOld.getCostType().equals(objNew.getCostType())) {
			fieldsUpdated += "'CostType Old: " + objOld.getCostType() + "', ";
		}
		if (!objOld.getValue().equals(objNew.getValue())) {
			fieldsUpdated += "'Value Old: " + objOld.getValue() + "' ";
		}
		return fieldsUpdated;
	}
}
