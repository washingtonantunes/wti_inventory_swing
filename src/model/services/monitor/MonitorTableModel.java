package model.services.monitor;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.entities.Monitor;

public class MonitorTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private static final int COL_SERIAL_NUMBER = 0;
	private static final int COL_BRAND = 1;
	private static final int COL_MODEL = 2;
	private static final int COL_PATRIMONY_NUMBER = 3;
	private static final int COL_STATUS = 4;
	private static final int COL_DATE_ENTRY = 5;
	private static final int COL_REASON = 6;

	private List<Monitor> monitors;

	private String[] columns = new String[] { "Serial Number", "Brand", "Model", "Patrimony Number", "Status", "Date Enty", "Reason" };

	public MonitorTableModel(List<Monitor> monitors) {
		this.monitors = new ArrayList<>(monitors);
	}

	@Override
	public int getRowCount() {
		return monitors.size();
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

		Monitor e = monitors.get(row);

		if (column == COL_SERIAL_NUMBER) {
			return e.getSerialNumber();
		} 
		else if (column == COL_BRAND) {
			return e.getBrand();
		} 
		else if (column == COL_MODEL) {
			return e.getModel();
		} 
		else if (column == COL_PATRIMONY_NUMBER) {
			return e.getPatrimonyNumber();
		} 
		else if (column == COL_STATUS) {
			return e.getStatus();
		} 
		else if (column == COL_DATE_ENTRY) {
			return e.getDateEntry();
		} 
		else if (column == COL_REASON) {
			return e.getReason();
		}
		return "";
	}

	public Monitor getMonitor(int indexLine) {
		return monitors.get(indexLine);
	}

	public void addMonitor(Monitor monitor) {
		monitors.add(monitor);
		int lastIndex = getRowCount() - 1;
		fireTableRowsInserted(lastIndex, lastIndex);
	}

	public void updateMonitor(int indexLine, Monitor monitor) {
		monitors.set(indexLine, monitor);
		fireTableRowsUpdated(indexLine, indexLine);
	}

	public void removeMonitor(int indexLine) {
		monitors.remove(indexLine);
		fireTableRowsDeleted(indexLine, indexLine);
	}
}
