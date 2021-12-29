package model.dao;

import java.util.List;

import entities.Equipment;

public interface EquipmentDao {

	void insert(Equipment obj);

	void update(Equipment obj);

	void updateStatus(String serialNumberEquipment, String status);
	
	void disable(String serialNumberEquipment, String status, String reason);

	List<Equipment> findAll();
}
