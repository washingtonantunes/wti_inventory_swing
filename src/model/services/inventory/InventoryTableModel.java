package model.services.inventory;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.entities.Inventory;

public class InventoryTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private static final int COL_WORK_POINT = 0;
	private static final int COL_NAME_PROJECT = 1;
	private static final int COL_CITY_PROJECT = 2;
	private static final int COL_REGISTRATION_USER = 3;
	private static final int COL_NAME_USER = 4;
	private static final int COL_SERIAL_NUMBER_EQUIPMENT = 5;
	private static final int COL_HOST_NAME_EQUIPMENT = 6;
	private static final int COL_TYPE_EQUIPMENT = 7;
	private static final int COL_PATRIMONY_NUMBER_EQUIPMENT = 8;
	private static final int COL_BRAND_EQUIPMENT = 9;
	private static final int COL_MODEL_EQUIPMENT = 10;
	private static final int COL_SERIAL_NUMBER_MONITOR_1 = 11;
	private static final int COL_MODEL_MONITOR_1 = 12;
	private static final int COL_PATRIMONY_NUMBER_MONITOR_1 = 13;
	private static final int COL_SERIAL_NUMBER_MONITOR_2 = 14;
	private static final int COL_MODEL_MONITOR_2 = 15;
	private static final int COL_PATRIMONY_NUMBER_MONITOR_2 = 16;

	private List<Inventory> inventories;

	private String[] columns = new String[] { "Work Point", "Project", "City", "Registration", "Name User",
			"<html><center>Serial <br>Number</html>", "Host Name", "Type", "<html><center>Patrimony <br>Number</html>",
			"Brand", "Model", "<html><center>Serial Number<br>Monitor 1</html>",
			"<html><center>Model <br>Monitor 1</html>", "<html><center>Patrimony <br>Number<br> Monitor 1</html>",
			"<html><center>Serial Number<br>Monitor 2</html>", "<html><center>Model <br>Monitor 2</html>",
			"<html><center>Patrimony <br>Number<br> Monitor 2</html>" };

	public InventoryTableModel(List<Inventory> inventories) {
		this.inventories = new ArrayList<>(inventories);
	}

	@Override
	public int getRowCount() {
		return inventories.size();
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

		Inventory i = inventories.get(row);

		if (column == COL_WORK_POINT) {
			return i.getWorkPosition().getWorkPoint();
		} 
		else if (column == COL_NAME_PROJECT) {
			return i.getProject().getName();
		} 
		else if (column == COL_CITY_PROJECT) {
			return i.getProject().getName();
		} 
		else if (column == COL_REGISTRATION_USER) {
			return i.getUser().getRegistration();
		} 
		else if (column == COL_NAME_USER) {
			return i.getUser().getName();
		} 
		else if (column == COL_SERIAL_NUMBER_EQUIPMENT) {
			return i.getEquipment().getSerialNumber();
		} 
		else if (column == COL_HOST_NAME_EQUIPMENT) {
			return i.getEquipment().getHostName();
		} 
		else if (column == COL_TYPE_EQUIPMENT) {
			return i.getEquipment().getType();
		} 
		else if (column == COL_PATRIMONY_NUMBER_EQUIPMENT) {
			return i.getEquipment().getPatrimonyNumber();
		} 
		else if (column == COL_BRAND_EQUIPMENT) {
			return i.getEquipment().getBrand();
		} 
		else if (column == COL_MODEL_EQUIPMENT) {
			return i.getEquipment().getModel();
		} 
		else if (column == COL_SERIAL_NUMBER_MONITOR_1) {
			return i.getMonitor1().getSerialNumber();
		} 
		else if (column == COL_MODEL_MONITOR_1) {
			return i.getMonitor1().getModel();
		} 
		else if (column == COL_PATRIMONY_NUMBER_MONITOR_1) {
			return i.getMonitor1().getPatrimonyNumber();
		} 
		else if (column == COL_SERIAL_NUMBER_MONITOR_2) {
			return i.getMonitor2().getSerialNumber();
		} 
		else if (column == COL_MODEL_MONITOR_2) {
			return i.getMonitor2().getModel();
		} 
		else if (column == COL_PATRIMONY_NUMBER_MONITOR_2) {
			return i.getMonitor2().getPatrimonyNumber();
		}
		return "";
	}

	public Inventory getInventory(int indexLine) {
		return inventories.get(indexLine);
	}

	public void addInventory(Inventory inventory) {
		inventories.add(inventory);
		int lastIndex = getRowCount() - 1;
		fireTableRowsInserted(lastIndex, lastIndex);
	}

	public void updateInventory(int indexLine, Inventory inventory) {
		inventories.set(indexLine, inventory);
		fireTableRowsUpdated(indexLine, indexLine);
	}

	public void removeInventory(int indexLine) {
		inventories.remove(indexLine);
		fireTableRowsDeleted(indexLine, indexLine);
	}
}
