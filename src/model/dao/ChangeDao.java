package model.dao;

import java.util.List;

import model.entities.Change;

public interface ChangeDao {

	void insert(Change obj);
	
	void updateDefault(Change obj);

	void updateSort(int idOld, int idNew);

	void toSetDefault(int lastIndex);

	List<Change> findAll();
}
