package model.dao;

import java.util.Map;

import model.entities.User;

public interface UserDao {

	void insert(User obj);

	void update(User obj);

	void disable(User obj);

	Map<String, User> findAll();
}
