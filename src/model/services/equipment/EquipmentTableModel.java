package model.services.equipment;

import java.util.ArrayList;
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

	private List<Equipment> equipments;

	private String[] columns = new String[] { "<html><center>Serial <br>Number</html>",
			"Host Name", "<html><center>Address<br>MAC</html>", "Type",
			"<html><center>Patrimony <br>Number</html>", "Brand", "Model", "<html><center>Memory <br>RAM</html>",
			"<html><center>Hard <br>Disk</html>", "Cost Type", "Value", "Status", "Date Enty"};

	public EquipmentTableModel(List<Equipment> equipments) {
		this.equipments = new ArrayList<>(equipments);
	}

	@Override
	public int getRowCount() {
		return equipments.size();
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

		Equipment e = equipments.get(row);

		if (column == COL_SERIAL_NUMBER) {
			return e.getSerialNumber();
		} 
		else if (column == COL_HOST_NAME) {
			return e.getHostName();
		} 
		else if (column == COL_ADDRESSMAC) {
			return e.getAddressMAC();
		} 
		else if (column == COL_TYPE) {
			return e.getType();
		} 
		else if (column == COL_PATRIMONY_NUMBER) {
			return e.getPatrimonyNumber();
		} 
		else if (column == COL_BRAND) {
			return e.getBrand();
		} 
		else if (column == COL_MODEL) {
			return e.getModel();
		} 
		else if (column == COL_MEMORY_RAM) {
			return e.getMemoryRam();
		} 
		else if (column == COL_HARD_DISK) {
			return e.getHardDisk();
		} 
		else if (column == COL_COST_TYPE) {
			return e.getCostType();
		} 
		else if (column == COL_VALUE) {
			return e.getValue();
		} 
		else if (column == COL_STATUS) {
			return e.getStatus();
		} 
		else if (column == COL_DATE_ENTRY) {
			return e.getDateEntry();
		} 
		return "";
	}

	public Equipment getEquipment(int indexLine) {
		return equipments.get(indexLine);
	}

	public void addEquipment(Equipment equipment) {
		equipments.add(equipment);
		int lastIndex = getRowCount() - 1;
		fireTableRowsInserted(lastIndex, lastIndex);
	}

	public void updateEquipment(int indexLine, Equipment equipment) {
		equipments.set(indexLine, equipment);
		fireTableRowsUpdated(indexLine, indexLine);
	}

	public void removeEquipment(int indexLine) {
		equipments.remove(indexLine);
		fireTableRowsDeleted(indexLine, indexLine);
	}
}
