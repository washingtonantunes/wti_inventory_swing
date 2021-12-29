package model.dao;

import java.util.List;

import entities.WorkPosition;

public interface WorkPositionDao {

	void insert(WorkPosition obj);

	void update(WorkPosition obj);

	void updateStatus(String workPoint, String status);

	List<WorkPosition> findAll();
}
