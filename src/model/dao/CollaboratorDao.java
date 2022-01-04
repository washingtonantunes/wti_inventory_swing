package model.dao;

import java.util.Map;

import model.entities.Collaborator;

public interface CollaboratorDao {

	void insert(Collaborator obj);

	void update(Collaborator obj);
	
	void disable(String registration, String status, String reason);

	Map<String, Collaborator> findAll();
}
