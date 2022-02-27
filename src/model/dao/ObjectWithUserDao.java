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

	// Equipment
	public void insertEquipmentWithUser(User user, Equipment equipment);

	public void removeEquipmentWithUser(User user, Equipment equipment);

	List<EquipmentWithUser> findAllEquipmentWithUser();

	// Monitor
	public void insertMonitorWithUser(User user, Monitor monitor);

	List<MonitorWithUser> findAllMonitorWithUser();

	public void removeMonitorWithUser(User user, Monitor monitor);

	// Peripheral
	public void insertPeripheralWithUser(User user, Peripheral peripheral);

	List<PeripheralWithUser> findAllPeripheralWithUser();

	public void removePeripheralWithUser(User user, Peripheral peripheral);

	// License
	public void insertLicenseWithUser(User user, License license);

	public void removeLicenseWithUser(User user, License license);

	List<LicenseWithUser> findAllLicenseWithUser();
}
