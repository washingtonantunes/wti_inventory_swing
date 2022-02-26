package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.ObjectWithUserDao;
import model.entities.utilitary.EquipmentWithUser;
import model.entities.utilitary.LicenseWithUser;
import model.entities.utilitary.MonitorWithUser;
import model.entities.utilitary.PeripheralWithUser;

public class ObjectWithUserService {

	private ObjectWithUserDao objectWithUserDao = DaoFactory.createObjectWithUserDao();

	public List<EquipmentWithUser> findAllEquipmentWithUser() {
		return objectWithUserDao.findAllEquipmentWithUser();
	}
	
	public List<MonitorWithUser> findAllMonitorWithUser() {
		return objectWithUserDao.findAllMonitorWithUser();
	}
	
	public List<PeripheralWithUser> findAllPeripheralWithUser() {
		return objectWithUserDao.findAllPeripheralWithUser();
	}
	
	public List<LicenseWithUser> findAllLicenseWithUser() {
		return objectWithUserDao.findAllLicenseWithUser();
	}
}
