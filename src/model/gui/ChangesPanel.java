package model.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import model.entities.Change;
import model.services.ChangeTableModel;

public class ChangesPanel extends JDialog {

	private static final long serialVersionUID = 1L;

	private final Dimension DIMENSIONDISPLAYPANEL = new Dimension(948, 350);

	private JTable table;
	private JScrollPane scrollPane;

	private ChangeTableModel model;

	private List<Change> changes;

	public ChangesPanel(List<Change> changes) {
		this.changes = changes;
		initComponents();
	}
	
	private void initComponents() {
		setModal(true);

		add(createTable());

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Changes");
		setPreferredSize(DIMENSIONDISPLAYPANEL);
		setResizable(false);

		pack();
		setLocationRelativeTo(null);
	}
	
	private JScrollPane createTable() {
		model = new ChangeTableModel(changes);

		table = new JTable(model);
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		configureColumn();
		sizeColumn();
		configureDate();

		scrollPane = new JScrollPane(table);
		return scrollPane;
	}

	private void configureColumn() {
		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
		.setHorizontalAlignment(SwingConstants.CENTER);
		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setPreferredSize(new Dimension(0, 40));

		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setResizable(false);
			table.getTableHeader().setReorderingAllowed(false);
		}
	}

	private void sizeColumn() {
		TableColumn column = null;
		for (int i = 0; i < table.getColumnCount(); i++) {
			column = table.getColumnModel().getColumn(i);
			if (i == 0) {
				column.setPreferredWidth(100);  //Date
			} else if (i == 1) {
				column.setPreferredWidth(150); // Type
			} else if (i == 2) {
				column.setPreferredWidth(500); // Changes
			} else if (i == 3) {
				column.setPreferredWidth(160); // Author
			}
		}
	}
	
	private void configureDate() {
		TableCellRenderer tableCellRenderer = new DefaultTableCellRenderer() {

			private static final long serialVersionUID = 1L;
			
			SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");

		    public Component getTableCellRendererComponent(JTable table,
		            Object value, boolean isSelected, boolean hasFocus,
		            int row, int column) {
		        if( value instanceof Date) {
		            value = f.format(value);
		        }
		        return super.getTableCellRendererComponent(table, value, isSelected,
		                hasFocus, row, column);
		    }
		};

		table.getColumnModel().getColumn(0).setCellRenderer(tableCellRenderer);
	}
}
