package model.dao;

import java.util.List;

import entities.Project;

public interface ProjectDao {

	void insert(Project obj);

	void update(Project obj);

	void deleteById(Integer id);

	Project findById(Integer id);

	List<Project> findAll();
}
