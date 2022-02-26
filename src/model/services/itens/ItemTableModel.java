package model.services.itens;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.entities.utilitay.Item;

public class ItemTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private static final int COL_INDEX = 0;
	private static final int COL_TYPE = 1;
	private static final int COL_CODE = 2;
	private static final int COL_NAME = 3;
	private static final int COL_BRAND = 4;
	private static final int COL_VALUE = 5;

	private List<Item> itens;

	private String[] columns = new String[] { "Index", "Type", "Code", "Name", "Brand", "Value" };

	public ItemTableModel(List<Item> itens) {
		this.itens = itens;
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

		Item i = itens.get(row);

		if (column == COL_INDEX) {
			return i.getIndex();
		} 
		else if (column == COL_TYPE) {
			return i.getType();
		} 
		else if (column == COL_CODE) {
			return i.getCode();
		} 
		else if (column == COL_NAME) {
			return i.getName();
		} 
		else if (column == COL_BRAND) {
			return i.getBrand();
		} 
		else if (column == COL_VALUE) {
			return i.getValue();
		}
		return "";
	}

	public Item getItem(int indexLine) {
		return itens.get(indexLine);
	}

	public void addItem(Item Item) {
		itens.add(Item);
		int lastIndex = getRowCount() - 1;
		fireTableRowsInserted(lastIndex, lastIndex);
	}

	public void updateItem(int indexLine, Item Item) {
		itens.set(indexLine, Item);
		fireTableRowsUpdated(indexLine, indexLine);
	}

	public void removeItem(int indexLine) {
		itens.remove(indexLine);
		fireTableRowsDeleted(indexLine, indexLine);
	}
}
