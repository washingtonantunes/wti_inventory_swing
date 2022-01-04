package model.dao;

import java.util.List;

import model.entities.Change;

public interface ChangeDao {

	void insert(Change obj);

	List<Change> findAll();
}
