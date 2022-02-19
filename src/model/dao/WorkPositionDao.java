package model.dao;

import java.util.Map;

import model.entities.WorkPosition;

public interface WorkPositionDao {

	void insert(WorkPosition obj);

	void update(WorkPosition obj);

	void updateStatus(WorkPosition obj);

	void disable(WorkPosition obj);

	Map<String, WorkPosition> findAll();
}
