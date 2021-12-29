package model.dao;

import java.util.List;

import entities.Collaborator;

public interface CollaboratorDao {

	void insert(Collaborator obj);

	void update(Collaborator obj);

	void deleteById(Integer id);

	Collaborator findById(Integer id);

	List<Collaborator> findAll();
}
