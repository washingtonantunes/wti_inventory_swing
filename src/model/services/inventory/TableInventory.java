package model.services.inventory;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class TableInventory extends JTable {

	private static final long serialVersionUID = 1L;

	public TableInventory(InventoryTableModel model) {
		super(model);
		initComponents();
	}

	private void initComponents() {
		setFillsViewportHeight(true);
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		configureHeader();
		configureSizeColumn();
	}

	private void configureHeader() {
		this.getTableHeader().setPreferredSize(new Dimension(0, 40));
		this.getTableHeader().setReorderingAllowed(false);

		((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);
	}

	private void configureSizeColumn() {
		TableColumn column = null;
		for (int i = 0; i < this.getColumnCount(); i++) {
			column = this.getColumnModel().getColumn(i);
			if (i == 0) {
				column.setPreferredWidth(110); // Work Point
				column.setResizable(false);
			} else if (i == 1) {
				column.setPreferredWidth(150); // Name Project
				column.setResizable(false);
			} else if (i == 2) {
				column.setPreferredWidth(100); // City Project
				column.setResizable(false);
			} else if (i == 3) {
				column.setPreferredWidth(80); // Registration User
				column.setResizable(false);
			} else if (i == 4) {
				column.setPreferredWidth(250); // Name User
				column.setResizable(false);
			} else if (i == 5) {
				column.setPreferredWidth(110); // Serial Number Equipment
				column.setResizable(false);
			} else if (i == 6) {
				column.setPreferredWidth(110); // Host Name Equipment
				column.setResizable(false);
			} else if (i == 7) {
				column.setPreferredWidth(100); // Type Equipment
				column.setResizable(false);
			} else if (i == 8) {
				column.setPreferredWidth(75); // Patrimony Number Equipment
				column.setResizable(false);
			} else if (i == 9) {
				column.setPreferredWidth(80); // Brand Equipment
				column.setResizable(false);
			} else if (i == 10) {
				column.setPreferredWidth(170); // Model Equipment
				column.setResizable(false);
			} else if (i == 11) {
				column.setPreferredWidth(140); // Serial Number Monitor 1
				column.setResizable(false);
			} else if (i == 12) {
				column.setPreferredWidth(140); // Model Monitor 1
				column.setResizable(false);
			} else if (i == 13) {
				column.setPreferredWidth(120); // Patrimony Number Monitor 1
				column.setResizable(false);
			} else if (i == 14) {
				column.setPreferredWidth(140); // Serial Number Monitor 2
				column.setResizable(false);
			} else if (i == 15) {
				column.setPreferredWidth(140); // Model Monitor 1
				column.setResizable(false);
			} else if (i == 16) {
				column.setPreferredWidth(120); // Patrimony Number Monitor 1
				column.setResizable(false);
			}
		}
	}

	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		Component c = super.prepareRenderer(renderer, row, column);
		Color color1 = new Color(220, 220, 220);
		Color color2 = Color.WHITE;
		if (!c.getBackground().equals(getSelectionBackground())) {
			Color coleur = (row % 2 == 0 ? color1 : color2);
			c.setBackground(coleur);
			coleur = null;
		}
		return c;
	}
}