package application;

import java.util.List;
import java.util.Map;

import entities.Collaborator;
import entities.Equipment;
import entities.Monitor;
import model.dao.ChangeDao;
import model.dao.CollaboratorDao;
import model.dao.DaoFactory;
import model.dao.EquipmentDao;
import model.dao.MonitorDao;

public class Program {

	public static void main(String[] args) {

		ChangeDao changeDao = DaoFactory.createChangeDao();
		EquipmentDao equipmentDao = DaoFactory.createEquipmentDao();
		MonitorDao monitorDao = DaoFactory.createMonitorDao();
		CollaboratorDao collaboratorDao = DaoFactory.createCollaboratorDao();

		System.out.println("=== TESTE Equipment ===");
		List<Equipment> list1 = equipmentDao.findAll();
		for (Equipment e : list1) {
			System.out.println(e);
		}
		System.out.println();

		System.out.println("=== TESTE Monitor ===");
		List<Monitor> list2 = monitorDao.findAll();
		for (Monitor m : list2) {
			System.out.println(m);
		}
		System.out.println();

		System.out.println("=== TESTE Collaborator ===");
		Map<String, Collaborator> list3 = collaboratorDao.findAll();
		for (String s : list3.keySet()) {
			System.out.println(list3.get(s));
		}
		System.out.println();
	}

}
