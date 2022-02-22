package model.dao;

import java.util.Map;

import model.entities.Equipment;

public interface EquipmentDao {

	void insert(Equipment obj);

	void update(Equipment obj);

	void updateStatusForUser(Equipment obj);
	
	void updateStatusForWorkPosition(Equipment obj);

	void disable(Equipment obj);

	Map<String, Equipment> findAll();
}
