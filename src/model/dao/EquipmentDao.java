package model.dao;

import java.util.List;

import model.entities.Equipment;

public interface EquipmentDao {

	void insert(Equipment obj);

	void update(Equipment obj);

	void updateStatus(Equipment obj);
	
	void disable(Equipment obj);

	List<Equipment> findAll();
}
