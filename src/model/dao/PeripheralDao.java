package model.dao;

import java.util.Map;

import model.entities.Peripheral;

public interface PeripheralDao {

	void insert(Peripheral obj);

	void update(Peripheral obj);

	void updateQuantity(Peripheral obj);

	void disable(Peripheral obj);

	Map<String, Peripheral> findAll();
}
