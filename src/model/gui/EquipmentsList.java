package model.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import model.entities.Equipment;
import model.services.EquipmentService;
import model.services.EquipmentTableModel;

public class EquipmentsList extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	private final Dimension DIMENSIONBUTTON = new Dimension(140, 40);

	private final Dimension DIMENSIONBUTTONSPANEL = new Dimension(0, 70);

	private final Color COLOR1 = new Color(4, 77, 92);

	private JScrollPane scrollPane;
	private JTable table;
	private EquipmentTableModel model;

	private List<Equipment> equipments;

	public EquipmentsList() {
		super("Equipments");
		this.equipments = loadData();
		initComponents();
	}

	private void initComponents() {
		setLayout(new BorderLayout());

		add(createPanelButton(), BorderLayout.NORTH);
		add(createTable(), BorderLayout.CENTER);
	}

	private JPanel createPanelButton() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
		panel.setPreferredSize(DIMENSIONBUTTONSPANEL);
		panel.setBackground(COLOR1);

		JButton buttonNew = new JButton("New");
		buttonNew.setPreferredSize(DIMENSIONBUTTON);
		panel.add(buttonNew);

		JButton buttonEdit = new JButton("Edit");
		buttonEdit.setPreferredSize(DIMENSIONBUTTON);
		panel.add(buttonEdit);

		JButton buttonView = new JButton("View");
		buttonView.setPreferredSize(DIMENSIONBUTTON);
		panel.add(buttonView);

		JButton buttonRemove = new JButton("Remove");
		buttonRemove.setPreferredSize(DIMENSIONBUTTON);
		panel.add(buttonRemove);

		JButton buttonSearch = new JButton("Search");
		buttonSearch.setPreferredSize(DIMENSIONBUTTON);
		panel.add(buttonSearch);

		JButton buttonFilter = new JButton("Filter");
		buttonFilter.setPreferredSize(DIMENSIONBUTTON);
		panel.add(buttonFilter);

		JButton buttonExport = new JButton("Export");
		buttonExport.setPreferredSize(DIMENSIONBUTTON);
		panel.add(buttonExport);

		return panel;
	}

	private JScrollPane createTable() {
		model = new EquipmentTableModel(equipments);
		
		table = new JTable(model);
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		configureColumn();
		sizeColumn();	
		
		scrollPane = new JScrollPane(table);
		return scrollPane;
	}

	private List<Equipment> loadData() {
		private EquipmentService service = new EquipmentService();
		List<Equipment> list = service.findAll();
		return list;
	}
	
	private void sizeColumn() {
		TableColumn column = null;
		for (int i = 0; i < table.getColumnCount(); i++) {
			column = table.getColumnModel().getColumn(i);
			if (i == 0) {
				column.setPreferredWidth(105); // Serial Number
				column.setResizable(false);
			} else if (i == 1) {
				column.setPreferredWidth(100); // Host Name
				column.setResizable(false);
			} else if (i == 2) {
				column.setPreferredWidth(120); // Address MAC
				column.setResizable(false);
			} else if (i == 3) {
				column.setPreferredWidth(80); // Type
				column.setResizable(false);
			} else if (i == 4) {
				column.setPreferredWidth(120); // Patrimony Number
			} else if (i == 5) {
				column.setPreferredWidth(80); // Brand
			} else if (i == 6) {
				column.setPreferredWidth(150); // Model
			} else if (i == 7) {
				column.setPreferredWidth(90); // Memory Ram
			} else if (i == 8) {
				column.setPreferredWidth(70); // Hard Disk
			} else if (i == 9) {
				column.setPreferredWidth(90); // Cost Type
			} else if (i == 10) {
				column.setPreferredWidth(70); // Value
			} else if (i == 11) {
				column.setPreferredWidth(90); // Status
			} else if (i == 12) {
				column.setPreferredWidth(120); // Date Entry
			} else if (i == 13) {
				column.setPreferredWidth(90); // Reason
			}
		}
	}

	private void configureColumn() {
		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);
		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setPreferredSize(new Dimension(0, 50));

		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setResizable(false);
			
		}
	}
}
