package model.dao;

import java.util.List;

import model.entities.Equipment;
import model.entities.License;
import model.entities.Monitor;
import model.entities.Peripheral;
import model.entities.User;
import model.entities.utilitary.EquipmentWithUser;
import model.entities.utilitary.LicenseWithUser;
import model.entities.utilitary.MonitorWithUser;
import model.entities.utilitary.PeripheralWithUser;

public interface ObjectWithUserDao {

	List<EquipmentWithUser> findAllEquipmentWithUser();

	List<MonitorWithUser> findAllMonitorWithUser();

	List<PeripheralWithUser> findAllPeripheralWithUser();

	List<LicenseWithUser> findAllLicenseWithUser();
	
	public void insertEquipmentWithUser(User user, Equipment equipment);
	
	public void insertMonitorWithUser(User user, Monitor monitor);
	
	public void insertPeripheralWithUser(User user, Peripheral peripheral);
	
	public void insertLicenseWithUser(User user, License license);
}
