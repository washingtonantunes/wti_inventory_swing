package model.dao;

import java.util.List;

import entities.Project;

public interface ProjectDao {

	void insert(Project obj);

	void update(Project obj);

	void disable(Integer idProject, String status, String reason);

	List<Project> findAll();
}
