package model.dao;

import java.util.List;

import model.entities.Option;

public interface OptionDao {

	void insert(Option obj);

	void update(Option obj);

	void disable(Integer idOption, String status);

	List<Option> findAll();
}
