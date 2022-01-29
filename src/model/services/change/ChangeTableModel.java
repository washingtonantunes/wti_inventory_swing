package model.services.change;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.entities.Change;

public class ChangeTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private static final int COL_DATE = 0;
	private static final int COL_TYPE = 1;
	private static final int COL_CHANGES = 2;
	private static final int COL_AUTHOR = 3;

	private List<Change> lines;

	private String[] columns = new String[] { "Date", "Type", "Changes", "Autor" };

	public ChangeTableModel(List<Change> changes) {
		this.lines = changes;
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
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int row, int column) {

		Change c = lines.get(row);

		if (column == COL_DATE) {
			return c.getDate();
		} else if (column == COL_TYPE) {
			return c.getType();
		} else if (column == COL_CHANGES) {
			return c.getChanges();
		} else if (column == COL_AUTHOR) {
			return c.getAuthor();
		}
		return "";
	}

	public Change getChange(int indexLine) {
		return lines.get(indexLine);
	}

	public void addChange(Change change) {
		lines.add(change);
		int lastIndex = getRowCount() - 1;
		fireTableRowsInserted(lastIndex, lastIndex);
	}

	public void updateChange(int indexLine, Change marca) {
		lines.set(indexLine, marca);
		fireTableRowsUpdated(indexLine, indexLine);
	}

	public void removeChange(int indexLine) {
		lines.remove(indexLine);
		fireTableRowsDeleted(indexLine, indexLine);
	}
}
