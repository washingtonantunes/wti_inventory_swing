package model.dao;

import java.util.List;

import model.entities.Monitor;

public interface MonitorDao {

	void insert(Monitor obj);

	void update(Monitor obj);

	void updateStatus(Monitor obj);

	void disable(Monitor obj);

	List<Monitor> findAll();
}
