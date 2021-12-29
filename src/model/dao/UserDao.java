package model.dao;

import java.util.List;

import entities.User;

public interface UserDao {

	void insert(User obj);

	void update(User obj);

	void updateStatus(String registration, String status);

	List<User> findAll();
}
