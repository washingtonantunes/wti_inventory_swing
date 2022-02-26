package model.services.itens;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.table.AbstractTableModel;

import model.entities.utilitary.ItemDelivery;

public class DeliveryTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private static final int COL_TYPE = 0;
	private static final int COL_CODE = 1;
	private static final int COL_NAME = 2;

	private List<ItemDelivery> itens;

	private String[] columns = new String[] { "Type", "Code", "Name" };

	public DeliveryTableModel() {
		this.itens = new ArrayList<ItemDelivery>();
	}

	@Override
	public int getRowCount() {
		return itens.size();
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

		ItemDelivery i = itens.get(row);

		if (column == COL_TYPE) {
			return i.getType();
		} 
		else if (column == COL_CODE) {
			return i.getCode();
		}
		else if (column == COL_NAME) {
			return i.getName();
		}
		return "";
	}
	
	public List<ItemDelivery> getItems() {
		return itens;
	}
	
	public List<ItemDelivery> getItemDelivery(String type) {
		return itens.stream().filter(i -> i.getType().equals(type)).collect(Collectors.toList());
	}
	
	public ItemDelivery getItemDelivery(int indexLine) {
		return itens.get(indexLine);
	}

	public void addItem(ItemDelivery Item) {
		itens.add(Item);
		int lastIndex = getRowCount() - 1;
		fireTableRowsInserted(lastIndex, lastIndex);
	}

	public void updateItem(int indexLine, ItemDelivery Item) {
		itens.set(indexLine, Item);
		fireTableRowsUpdated(indexLine, indexLine);
	}

	public void removeItem(int indexLine) {
		itens.remove(indexLine);
		fireTableRowsDeleted(indexLine, indexLine);
	}

}
