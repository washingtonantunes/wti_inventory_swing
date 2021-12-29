package model.dao;

import java.util.List;

import entities.Change;

public interface ChangeDao {

	void insert(Change obj);

	void update(Change obj);

	void deleteById(Integer id);

	Change findById(Integer id);

	List<Change> findAll();
}
