package model.dao;

import java.util.List;

import entities.WorkPosition;

public interface WorkPositionDao {

	void insert(WorkPosition obj);

	void update(WorkPosition obj);

	void deleteById(Integer id);

	WorkPosition findById(Integer id);

	List<WorkPosition> findAll();
}
