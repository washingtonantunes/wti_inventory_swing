package model.dao;

import db.DB;
import model.dao.impl.ChangeDaoJDBC;
import model.dao.impl.CollaboratorDaoJDBC;
import model.dao.impl.EquipmentDaoJDBC;
import model.dao.impl.MonitorDaoJDBC;

public class DaoFactory {

	public static ChangeDao createChangeDao() {
		return new ChangeDaoJDBC(DB.getConnection());
	}
	
	public static CollaboratorDao createCollaboratorDao() {
		return new CollaboratorDaoJDBC(DB.getConnection());
	}
	
	public static EquipmentDao createEquipmentDao() {
		return new EquipmentDaoJDBC(DB.getConnection());
	}
	
	public static MonitorDao createMonitorDao() {
		return new MonitorDaoJDBC(DB.getConnection());
	}
}
