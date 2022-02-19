package model.dao;

import java.util.Map;

import model.entities.Project;

public interface ProjectDao {

	void insert(Project obj);

	void update(Project obj);

	void disable(Project obj);

	Map<String, Project> findAll();
}
