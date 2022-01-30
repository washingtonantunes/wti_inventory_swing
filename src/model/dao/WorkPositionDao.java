package model.dao;

import java.util.List;

import model.entities.WorkPosition;

public interface WorkPositionDao {

	void insert(WorkPosition obj);

	void update(WorkPosition obj);

	void updateStatus(WorkPosition obj);

	void disable(WorkPosition obj);

	List<WorkPosition> findAll();
}
