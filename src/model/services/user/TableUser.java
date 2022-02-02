package model.services.user;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class TableUser extends JTable {

	private static final long serialVersionUID = 1L;

	public TableUser(UserTableModel model) {
		super(model);
		initComponents();
	}

	private void initComponents() {
		setFillsViewportHeight(true);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		configureHeader();
		configureSizeColumn();
		configureColumnDate();
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
				column.setPreferredWidth(70); // Registration
				column.setResizable(false);
			} 
			else if (i == 1) {
				column.setPreferredWidth(200); // Name
				column.setResizable(false);
			} 
			else if (i == 2) {
				column.setPreferredWidth(90); // CPF
				column.setResizable(false);
			} 
			else if (i == 3) {
				column.setPreferredWidth(90); // Phone
				column.setResizable(false);
			} 
			else if (i == 4) {
				column.setPreferredWidth(90); // Project
				column.setResizable(false);
			} 
			else if (i == 5) {
				column.setPreferredWidth(200); // Email
				column.setResizable(false);
			} 
			else if (i == 6) {
				column.setPreferredWidth(90); // Department
				column.setResizable(false);
			} 
			else if (i == 7) {
				column.setPreferredWidth(70); // Status
				column.setResizable(false);
			} 
			else if (i == 8) {
				column.setPreferredWidth(70); // Date Entry
				column.setResizable(false);
			} 
			else if (i == 9) {
				column.setPreferredWidth(90); // Reason
				column.setResizable(false);
			}
		}
	}

	private void configureColumnDate() {
		TableCellRenderer tableCellRenderer = new DefaultTableCellRenderer() {

			private static final long serialVersionUID = 1L;

			SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");

			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				if (value instanceof Date) {
					value = f.format(value);
				}
				return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			}
		};

		this.getColumnModel().getColumn(8).setCellRenderer(tableCellRenderer);
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