package model.dao;

import java.util.Map;

import model.entities.Monitor;

public interface MonitorDao {

	void insert(Monitor obj);

	void update(Monitor obj);

	void updateStatusForUser(Monitor obj);

	void updateStatusForWorkPosition(Monitor obj);

	void disable(Monitor obj);

	Map<String, Monitor> findAll();
}
