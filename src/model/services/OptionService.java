package model.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DBException;
import model.dao.DaoFactory;
import model.dao.OptionDao;
import model.entities.Option;

public class OptionService {

	private OptionDao optionDao = DaoFactory.createOptionDao();

	public List<Option> findAll() {
		return optionDao.findAll();
	}
	
	public void update(int idOld, int idNew) {
		Connection conn = DB.getConnection();
		try {
			conn.setAutoCommit(false);

			optionDao.updateSort(idOld, idNew);

			conn.commit();
		} 
		catch (SQLException e) {
			try {
				conn.rollback();
				throw new DBException("Transaction rolled back! Cause by: " + e.getMessage());
			} 
			catch (SQLException e1) {
				throw new DBException("Error trying to rollback! Cause by: " + e1.getMessage());
			}
		}
	}
}
