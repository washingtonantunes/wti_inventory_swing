package model.dao;

import java.util.List;

import model.entities.WorkPosition;

public interface WorkPositionDao {

	void insert(WorkPosition obj);

	void update(WorkPosition obj);

	void updateStatus(String workPoint, String status);

	void disable(String workPoint, String status, String reason);

	List<WorkPosition> findAll();
}
