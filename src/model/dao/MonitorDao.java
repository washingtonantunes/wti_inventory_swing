package model.dao;

import java.util.List;

import model.entities.Monitor;

public interface MonitorDao {

	void insert(Monitor obj);

	void update(Monitor obj);

	void updateStatus(String serialNumberMonitor, String status);

	void disable(String serialNumberMonitor, String status, String reason);

	List<Monitor> findAll();
}
