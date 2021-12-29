package model.dao;

import java.util.List;

import entities.Monitor;

public interface MonitorDao {

	void insert(Monitor obj);

	void update(Monitor obj);

	void deleteById(Integer id);

	Monitor findById(Integer id);

	List<Monitor> findAll();
}
