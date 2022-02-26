package model.services.change;

import java.util.Date;
import java.util.List;

import application.MainWindow;
import model.dao.ChangeDao;
import model.dao.DaoFactory;
import model.entities.Change;
import model.entities.Equipment;
import model.entities.License;
import model.entities.Monitor;
import model.entities.Peripheral;
import model.entities.Project;
import model.entities.User;
import model.entities.WorkPosition;

public class ChangeService {

	private ChangeDao changeDao = DaoFactory.createChangeDao();

	public List<Change> findAll() {
		return changeDao.findAll();
	}

	// GET Change Equipment
	public Change setChange(Equipment obj, String typeChange, String Changes) {
		Change change = new Change();

		change.setObject(obj.getSerialNumber());
		change.setType(typeChange);
		change.setChanges(Changes);
		change.setDate(new Date());
		change.setAuthor(MainWindow.collaborator.getName());

		return change;
	}

	// GET Change Monitor
	public Change setChange(Monitor obj, String typeChange, String Changes) {
		Change change = new Change();

		change.setObject(obj.getSerialNumber());
		change.setType(typeChange);
		change.setChanges(Changes);
		change.setDate(new Date());
		change.setAuthor(MainWindow.collaborator.getName());

		return change;
	}

	// GET Change Peripheral
	public Change setChange(Peripheral obj, String typeChange, String Changes) {
		Change change = new Change();

		change.setObject(obj.getCode());
		change.setType(typeChange);
		change.setChanges(Changes);
		change.setDate(new Date());
		change.setAuthor(MainWindow.collaborator.getName());

		return change;
	}

	// GET Change License
	public Change setChange(License obj, String typeChange, String Changes) {
		Change change = new Change();

		change.setObject(obj.getCode());
		change.setType(typeChange);
		change.setChanges(Changes);
		change.setDate(new Date());
		change.setAuthor(MainWindow.collaborator.getName());

		return change;
	}

	// GET Change WorkPosition
	public Change setChange(WorkPosition obj, String typeChange, String Changes) {
		Change change = new Change();

		change.setObject(obj.getWorkPoint());
		change.setType(typeChange);
		change.setChanges(Changes);
		change.setDate(new Date());
		change.setAuthor(MainWindow.collaborator.getName());

		return change;
	}

	// GET Change Project
	public Change setChange(Project obj, String typeChange, String Changes) {
		Change change = new Change();

		change.setObject(obj.getCostCenter());
		change.setType(typeChange);
		change.setChanges(Changes);
		change.setDate(new Date());
		change.setAuthor(MainWindow.collaborator.getName());

		return change;
	}

	// GET Change User
	public Change setChange(User obj, String typeChange, String Changes) {
		Change change = new Change();

		change.setObject(obj.getRegistration());
		change.setType(typeChange);
		change.setChanges(Changes);
		change.setDate(new Date());
		change.setAuthor(MainWindow.collaborator.getName());

		return change;
	}
}
