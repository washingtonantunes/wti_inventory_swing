package model.dao;

import java.util.List;

import model.entities.Inventory;

public interface InventoryDao {

	void insert(Inventory obj);

	void update(Inventory obj);

	void deleteById(Integer id);

	List<Inventory> findAll();
}
