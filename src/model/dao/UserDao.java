package model.dao;

import java.util.List;

import entities.User;

public interface UserDao {

	void insert(User obj);

	void update(User obj);

	void disable(String registration, String status, String reason);

	List<User> findAll();
}
