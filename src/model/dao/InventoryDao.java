package model.dao;

import java.util.List;

import entities.Inventory;

public interface InventoryDao {

	void insert(Inventory obj);

	void update(Inventory obj);

	void deleteById(Integer id);

	Inventory findById(Integer id);

	List<Inventory> findAll();
}
