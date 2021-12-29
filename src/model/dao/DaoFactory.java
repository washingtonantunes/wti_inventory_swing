package model.dao;

import db.DB;
import model.dao.impl.ChangeDaoJDBC;
import model.dao.impl.CollaboratorDaoJDBC;
import model.dao.impl.EquipmentDaoJDBC;
import model.dao.impl.InventoryDaoJDBC;
import model.dao.impl.MonitorDaoJDBC;
import model.dao.impl.OptionDaoJDBC;
import model.dao.impl.ProjectDaoJDBC;
import model.dao.impl.UserDaoJDBC;
import model.dao.impl.WorkPositionDaoJDBC;

public class DaoFactory {
	
	public static CollaboratorDao createCollaboratorDao() {
		return new CollaboratorDaoJDBC(DB.getConnection());
	}
	
	public static OptionDao createOptionDao() {
		return new OptionDaoJDBC(DB.getConnection());
	}	

	public static ChangeDao createChangeDao() {
		return new ChangeDaoJDBC(DB.getConnection());
	}
	
	public static InventoryDao createInventoryDao() {
		return new InventoryDaoJDBC(DB.getConnection());
	}
	
	public static WorkPositionDao createWorkPositionDao() {
		return new WorkPositionDaoJDBC(DB.getConnection());
	}
	
	public static ProjectDao createProjectDao() {
		return new ProjectDaoJDBC(DB.getConnection());
	}
	
	public static UserDao createUserDao() {
		return new UserDaoJDBC(DB.getConnection());
	}
	
	public static EquipmentDao createEquipmentDao() {
		return new EquipmentDaoJDBC(DB.getConnection());
	}
	
	public static MonitorDao createMonitorDao() {
		return new MonitorDaoJDBC(DB.getConnection());
	}	
}
