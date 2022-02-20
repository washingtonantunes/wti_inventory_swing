package model.services.project;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.entities.Project;

public class ProjectTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private static final int COL_COST_CENTER = 0;
	private static final int COL_NAME = 1;
	private static final int COL_CITY = 2;
	private static final int COL_STATUS = 3;
	private static final int COL_DATE_ENTRY = 4;

	List<Project> projects;

	private String[] columns = new String[] { "Cost Center", "Name", "City", "Status", "Date Entry" };

	public ProjectTableModel(List<Project> projects) {
		this.projects = projects;
	}

	@Override
	public int getRowCount() {
		return projects.size();
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

		Project p = projects.get(row);

		if (column == COL_COST_CENTER) {
			return p.getCostCenter();
		} 
		else if (column == COL_NAME) {
			return p.getName();
		} 
		else if (column == COL_CITY) {
			return p.getCity();
		} 
		else if (column == COL_STATUS) {
			return p.getStatus();
		} 
		else if (column == COL_DATE_ENTRY) {
			return p.getDateEntry();
		} 
		return "";
	}

	public Project getProject(int indexLine) {
		return projects.get(indexLine);
	}

	public void addProject(Project project) {
		projects.add(project);
		int lastIndex = getRowCount() - 1;
		fireTableRowsInserted(lastIndex, lastIndex);
	}

	public void updateProject(int indexLine, Project project) {
		projects.set(indexLine, project);
		fireTableRowsUpdated(indexLine, indexLine);
	}

	public void removeProject(int indexLine) {
		projects.remove(indexLine);
		fireTableRowsDeleted(indexLine, indexLine);
	}
}
