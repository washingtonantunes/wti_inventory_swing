package model.services;

import java.util.List;

import model.dao.ChangeDao;
import model.dao.DaoFactory;
import model.dao.OptionDao;
import model.entities.Option;

public class OptionService {

	private OptionDao equipmentDao = DaoFactory.createOptionDao();
	private ChangeDao changeDao = DaoFactory.createChangeDao();

	public List<Option> findAll() {
		return equipmentDao.findAll();
	}
}
