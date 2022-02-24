package model.dao;

import java.util.Map;

import model.entities.License;

public interface LicenseDao {

	void insert(License obj);

	void update(License obj);

	void updateQuantity(License obj);

	void disable(License obj);

	Map<String, License> findAll();
}
