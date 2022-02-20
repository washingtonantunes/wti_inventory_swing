package model.services.inventory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DBException;
import model.dao.DaoFactory;
import model.dao.InventoryDao;
import model.entities.Inventory;

public class InventoryService {
	
	private InventoryDao inventoryDao = DaoFactory.createInventoryDao();

	public List<Inventory> findAll() {
		return inventoryDao.findAll();
	}
	
	public void save(Inventory obj) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			inventoryDao.insert(obj);
			
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
	
	public void update(Inventory objOld, Inventory objNew) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			inventoryDao.update(objNew);

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
	
	public void delete(Inventory obj) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			inventoryDao.deleteById(obj.getId());

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
	
//	private void updateStatusObj(Inventory obj, int type) {
//		// Validation Work Position
//		if (obj.getWorkPosition() != null && obj.getWorkPosition().getWorkPoint() != "HOME-OFFICE" && obj.getWorkPosition().getWorkPoint() != "HIBRIDO") {
//			WorkPositionService service = new WorkPositionService();
//		}
//
//		// Validation Project
//		if (obj.getProject() != null) {
//			ProjectService service = new ProjectService();
//		}
//		// Validation User
//		if (obj.getProject() != null) {
//			UserService service = new UserService();
//		}
//
//		// Validation Equipment
//		if (obj.getEquipment() != null) {
//			EquipmentService service = new EquipmentService();
//		}
//
//		// Validation Monitor1
//		if (obj.getMonitor1() != null) {
//			MonitorService service = new MonitorService();
//		}
//
//		// Validation Monitor2
//		if (obj.getMonitor2() != null) {
//			MonitorService service = new MonitorService();
//		}
//	}
}
