package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.OptionDao;
import model.entities.Option;

public class OptionService {

	private OptionDao optionDao = DaoFactory.createOptionDao();

	public List<Option> findAll() {
		return optionDao.findAll();
	}
}
