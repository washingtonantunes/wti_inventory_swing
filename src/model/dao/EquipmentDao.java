package model.dao;

import java.util.List;

import entities.Equipment;

public interface EquipmentDao {

	void insert(Equipment obj);

	void update(Equipment obj);

	void deleteById(Integer id);

	Equipment findById(Integer id);

	List<Equipment> findAll();
}
