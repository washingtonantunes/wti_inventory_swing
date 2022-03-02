package model.dao;

import java.util.List;

import model.entities.Option;

public interface OptionDao {

	void insert(Option obj);

	void update(Option obj);

	void disable(Integer idOption, String status);

	void updateSort(int idOld, int idNew);

	void toSetDefault(int lastIndex);

	List<Option> findAll();
}
