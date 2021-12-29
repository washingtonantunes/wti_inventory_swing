package model.dao;

import java.util.List;

import entities.Option;

public interface OptionDao {

	void insert(Option obj);

	void update(Option obj);

	void deleteById(Integer id);

	Option findById(Integer id);

	List<Option> findAll();
}
