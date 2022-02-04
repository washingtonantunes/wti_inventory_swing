package model.services.collaborator;

import java.util.Map;

import model.dao.CollaboratorDao;
import model.dao.DaoFactory;
import model.entities.Collaborator;

public class CollaboratorService {

	private CollaboratorDao collaboratorDao = DaoFactory.createCollaboratorDao();

	public Map<String, Collaborator> findAll() {
		return collaboratorDao.findAll();
	}
}
