package model.services.workposition;

import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.entities.WorkPosition;

public class WorkPositionTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private static final int COL_WORK_POINT = 0;
	private static final int COL_LOCATION = 1;
	private static final int COL_FLOOR = 2;
	private static final int COL_NET_POINT = 3;
	private static final int COL_STATUS = 4;
	private static final int COL_DATE_ENTRY = 5;
	private static final int COL_REASON = 6;

	List<WorkPosition> lines;

	private String[] columns = new String[] { "Work Point", "Location", "Floor", "Net Point",
			"Status", "Date Enty", "Reason" };

	public WorkPositionTableModel(List<WorkPosition> workPositions) {
		this.lines = workPositions;
	}

	@Override
	public int getRowCount() {
		return lines.size();
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
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == COL_DATE_ENTRY) {
			return Date.class;
		}
		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int row, int column) {

		WorkPosition w = lines.get(row);

		if (column == COL_WORK_POINT) {
			return w.getWorkPoint();
		} 
		else if (column == COL_LOCATION) {
			return w.getLocation();
		} 
		else if (column == COL_FLOOR) {
			return w.getFloor();
		} 
		else if (column == COL_NET_POINT) {
			return w.getNetPoint();
		} 
		else if (column == COL_STATUS) {
			return w.getStatus();
		} 
		else if (column == COL_DATE_ENTRY) {
			return w.getDateEntry();
		} 
		else if (column == COL_REASON) {
			return w.getReason();
		}
		return "";
	}

	public WorkPosition getWorkPosition(int indexLine) {
		return lines.get(indexLine);
	}

	public void addWorkPosition(WorkPosition workPosition) {
		lines.add(workPosition);
		int lastIndex = getRowCount() - 1;
		fireTableRowsInserted(lastIndex, lastIndex);
	}

	public void updateWorkPosition(int indexLine, WorkPosition workPosition) {
		lines.set(indexLine, workPosition);
		fireTableRowsUpdated(indexLine, indexLine);
	}

	public void removeWorkPosition(int indexLine) {
		lines.remove(indexLine);
		fireTableRowsDeleted(indexLine, indexLine);
	}
}
