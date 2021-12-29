package model.dao;

import java.util.List;

import entities.Option;

public interface OptionDao {

	void insert(Option obj);

	void update(Option obj);

	void updateStatus(Integer idOption, String status);

	List<Option> findAll();
}
