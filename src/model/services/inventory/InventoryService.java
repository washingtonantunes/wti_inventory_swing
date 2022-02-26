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
}