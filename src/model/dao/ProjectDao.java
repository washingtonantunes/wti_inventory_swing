package model.dao;

import java.util.List;

import model.entities.Project;

public interface ProjectDao {

	void insert(Project obj);

	void update(Project obj);

	void disable(Project obj);

	List<Project> findAll();
}
