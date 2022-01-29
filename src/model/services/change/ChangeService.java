package model.services.change;

import java.util.List;

import model.dao.ChangeDao;
import model.dao.DaoFactory;
import model.entities.Change;

public class ChangeService {
	
	private ChangeDao changeDao = DaoFactory.createChangeDao();

	public List<Change> findAll() {
		return changeDao.findAll();
	}
}
