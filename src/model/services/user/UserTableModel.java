package model.services.user;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.entities.User;

public class UserTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private static final int COL_REGISTRATION = 0;
	private static final int COL_NAME = 1;
	private static final int COL_CPF = 2;
	private static final int COL_PHONE = 3;
	private static final int COL_EMAIL = 4;
	private static final int COL_DEPARTMENT = 5;
	private static final int COL_STATUS = 6;
	private static final int COL_DATE_ENTRY = 7;
	private static final int COL_PROJECT = 8;

	List<User> users;

	private String[] columns = new String[] { "Registration", "Name", "CPF", "Phone", "Email", "Department", "Status", "Date Entry", "Project" };

	public UserTableModel(List<User> users) {
		this.users = users;
	}

	@Override
	public int getRowCount() {
		return users.size();
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columns[columnIndex];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int row, int column) {

		User p = users.get(row);

		if (column == COL_REGISTRATION) {
			return p.getRegistration();
		} 
		else if (column == COL_NAME) {
			return p.getName();
		} 
		else if (column == COL_CPF) {
			return p.getCpf();
		} 
		else if (column == COL_PHONE) {
			return p.getPhone();
		} 
		else if (column == COL_EMAIL) {
			return p.getEmail();
		} 
		else if (column == COL_DEPARTMENT) {
			return p.getDepartment();
		} 
		else if (column == COL_STATUS) {
			return p.getStatus();
		} 
		else if (column == COL_DATE_ENTRY) {
			return p.getDateEntry();
		} 
		else if (column == COL_PROJECT) {
			return p.getProject();
		} 
		return "";
	}

	public User getUser(int indexLine) {
		return users.get(indexLine);
	}

	public void addUser(User user) {
		users.add(user);
		int lastIndex = getRowCount() - 1;
		fireTableRowsInserted(lastIndex, lastIndex);
	}
	
	public List<User> getUsers() {
		return users;
	}

	public void updateUser(int indexLine, User user) {
		users.set(indexLine, user);
		fireTableRowsUpdated(indexLine, indexLine);
	}

	public void removeUser(int indexLine) {
		users.remove(indexLine);
		fireTableRowsDeleted(indexLine, indexLine);
	}
}
