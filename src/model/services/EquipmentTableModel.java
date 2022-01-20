package model.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.entities.Equipment;

public class EquipmentTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private static final int COL_SERIAL_NUMBER = 0;
	private static final int COL_HOST_NAME = 1;
	private static final int COL_ADDRESSMAC = 2;
	private static final int COL_TYPE = 3;
	private static final int COL_PATRIMONY_NUMBER = 4;
	private static final int COL_BRAND = 5;
	private static final int COL_MODEL = 6;
	private static final int COL_MEMORY_RAM = 7;
	private static final int COL_HARD_DISK = 8;
	private static final int COL_COST_TYPE = 9;
	private static final int COL_VALUE = 10;
	private static final int COL_STATUS = 11;
	private static final int COL_DATE_ENTRY = 12;
	private static final int COL_REASON = 13;

	List<Equipment> lines;

	private String[] columns = new String[] { "<html><center>Serial <br>Number</html>",
			"<html><center>Host <br>Name</html>", "<html><center>Address<br>MAC</html>", "Type",
			"<html><center>Patrimony <br>Number</html>", "Brand", "Model",
			"<html><center>Memory <br>RAM</html>", "<html><center>Hard <br>Disk</html>",
			"<html><center>Cost <br>Type</html>", "Value", "Status",
			"<html><center>Date <br>Enty</html>", "Reason" };
	
	public EquipmentTableModel(List<Equipment> equipments) {
		this.lines = new ArrayList<>(equipments);
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

		Equipment e = lines.get(row);

		if (column == COL_SERIAL_NUMBER) {
			return e.getSerialNumber();
		} else if (column == COL_HOST_NAME) {
			return e.getHostName();
		} else if (column == COL_ADDRESSMAC) {
			return e.getAddressMAC();
		} else if (column == COL_TYPE) {
			return e.getType();
		} else if (column == COL_PATRIMONY_NUMBER) {
			return e.getPatrimonyNumber();
		} else if (column == COL_BRAND) {
			return e.getBrand();
		} else if (column == COL_MODEL) {
			return e.getModel();
		} else if (column == COL_MEMORY_RAM) {
			return e.getMemoryRam();
		} else if (column == COL_HARD_DISK) {
			return e.getHardDisk();
		} else if (column == COL_COST_TYPE) {
			return e.getCostType();
		} else if (column == COL_VALUE) {
			return e.getValue();
		} else if (column == COL_STATUS) {
			return e.getStatus();
		} else if (column == COL_DATE_ENTRY) {
			return e.getDateEntry();
		} else if (column == COL_REASON) {
			return e.getReason();
		}
		return "";
	}

	public Equipment getEquipment(int indexLine) {
		return lines.get(indexLine);
	}

	public void addEquipment(Equipment equipment) {
		lines.add(equipment);
		int lastIndex = getRowCount() - 1;
		fireTableRowsInserted(lastIndex, lastIndex);
	}

	public void updateEquipment(int indexLine, Equipment equipment) {
		lines.set(indexLine, equipment);
		fireTableRowsUpdated(indexLine, indexLine);
	}

	public void removeEquipment(int indexLine) {
		lines.remove(indexLine);
		fireTableRowsDeleted(indexLine, indexLine);
	}
}
