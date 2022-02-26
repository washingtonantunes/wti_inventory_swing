package model.services.itens;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class TableItem extends JTable {

	private static final long serialVersionUID = 1L;

	public TableItem(ItemTableModel model) {
		super(model);
		initComponents1();
	}
	
	public TableItem(DeliveryTableModel model) {
		super(model);
		initComponents2();
	}
	
	private void initComponents1() {
		setFillsViewportHeight(true);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setRowHeight(30);
		setFont(new java.awt.Font(null, Font.BOLD, 20));
		configureHeader();
		configureSizeColumn1();
		configureColumnValue();
	}

	private void initComponents2() {
		setFillsViewportHeight(true);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setRowHeight(30);
		setFont(new java.awt.Font(null, Font.BOLD, 20));
		configureHeader();
		configureSizeColumn2();
	}

	private void configureHeader() {
		this.getTableHeader().setPreferredSize(new Dimension(0, 40));
		this.getTableHeader().setReorderingAllowed(false);

		((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);
	}

	private void configureSizeColumn1() {
		TableColumn column = null;
		for (int i = 0; i < this.getColumnCount(); i++) {
			column = this.getColumnModel().getColumn(i);
			if (i == 0) {
				column.setPreferredWidth(50); // Index
				column.setCellRenderer(new CellRenderer());
				column.setResizable(false);
			} 
			else if (i == 1) {
				column.setPreferredWidth(180); // Type
				column.setResizable(false);
			} 
			else if (i == 2) {
				column.setPreferredWidth(300); // Code
				column.setResizable(false);
			} 
			else if (i == 3) {
				column.setPreferredWidth(350); // Name
				column.setResizable(false);
			} 
			else if (i == 4) {
				column.setPreferredWidth(150); // Value
				column.setResizable(false);
			}
		}
	}
	
	private void configureSizeColumn2() {
		TableColumn column = null;
		for (int i = 0; i < this.getColumnCount(); i++) {
			column = this.getColumnModel().getColumn(i);
			if (i == 0) {
				column.setPreferredWidth(180); // Type
				column.setResizable(false);
			} 
			else if (i == 1) {
				column.setPreferredWidth(300); // Code
				column.setResizable(false);
			} 
			else if (i == 2) {
				column.setPreferredWidth(350); // Name
				column.setResizable(false);
			} 
		}
	}

	public void configureColumnValue() {
		TableCellRenderer tableCellRenderer = new DefaultTableCellRenderer() {

			private static final long serialVersionUID = 1L;

			String format = "R$ %.2f";

			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				value = String.format(format, value);
				return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			}
		};
		this.getColumnModel().getColumn(4).setCellRenderer(tableCellRenderer);
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
	
	public class CellRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		public CellRenderer() {
			super();
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			this.setHorizontalAlignment(CENTER);

			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}
	}
}
