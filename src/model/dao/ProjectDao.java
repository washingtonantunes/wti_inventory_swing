package model.dao;

import java.util.List;

import entities.Project;

public interface ProjectDao {

	void insert(Project obj);

	void update(Project obj);

	void updateStatus(Integer idProject, String status);

	List<Project> findAll();
}
