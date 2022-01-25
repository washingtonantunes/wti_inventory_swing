package model.gui.equipment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import model.entities.Equipment;
import model.entities.Option;
import model.gui.MainWindow;
import model.services.OptionService;
import model.services.equipment.CreateExlFileEquipment;
import model.services.equipment.EquipmentService;
import model.services.equipment.EquipmentTableModel;

public class EquipmentsList extends JPanel {

	private static final long serialVersionUID = 1L;

	private final Dimension DIMENSIONBUTTON = new Dimension(140, 40);

	private final Dimension DIMENSIONBUTTONSPANEL = new Dimension(0, 70);
	private final Dimension DIMENSIONNORTHPANEL = new Dimension(0, 95);
	private final Dimension DIMENSIONTITLEPANEL = new Dimension(0, 25);

	private final Color COLOR1 = new Color(4, 77, 92);

	private JScrollPane scrollPane;
	private JTable table;
	private EquipmentTableModel model;

	private List<Equipment> equipments;
	private List<Option> options; 
	
	private TableRowSorter<EquipmentTableModel> sorter;

	public EquipmentsList() {
		this.equipments = loadDataEquipments();
		this.options = loadDataOptions();
		initComponents();
	}

	private void initComponents() {
		setLayout(new BorderLayout());
		setVisible(true);
		
		add(createPanelNorth(), BorderLayout.NORTH);
		add(createTable(), BorderLayout.CENTER);
	}
	
	private JPanel createPanelNorth() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(DIMENSIONNORTHPANEL);
		panel.add(createPanelTitle(), BorderLayout.NORTH);
		panel.add(createPanelButton(), BorderLayout.SOUTH);
		return panel;
	}
	
	private JPanel createPanelTitle() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(DIMENSIONTITLEPANEL);
		panel.setBackground(COLOR1);
		
		JLabel label_Title = new JLabel("Equipments");
		label_Title.setPreferredSize(DIMENSIONBUTTON);
		label_Title.setBounds(20, 2, 100, 20);
		label_Title.setForeground(Color.WHITE);
		panel.add(label_Title);
		
		return panel;
	}

	private JPanel createPanelButton() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
		panel.setPreferredSize(DIMENSIONBUTTONSPANEL);
		panel.setBackground(COLOR1);

		JButton buttonNew = new JButton("New");
		buttonNew.setPreferredSize(DIMENSIONBUTTON);
		buttonNew.addActionListener(new buttonNewListener());
		panel.add(buttonNew);

		JButton buttonEdit = new JButton("Edit");
		buttonEdit.setPreferredSize(DIMENSIONBUTTON);
		buttonEdit.addActionListener(new buttonEditListener());
		panel.add(buttonEdit);

		JButton buttonView = new JButton("View");
		buttonView.setPreferredSize(DIMENSIONBUTTON);
		buttonView.addActionListener(new buttonViewListener());
		panel.add(buttonView);

		JButton buttonDisable = new JButton("Disable");
		buttonDisable.setPreferredSize(DIMENSIONBUTTON);
		buttonDisable.addActionListener(new buttonDisableListener());
		panel.add(buttonDisable);

		JButton buttonFilter = new JButton("Filter");
		buttonFilter.setPreferredSize(DIMENSIONBUTTON);
		buttonFilter.addActionListener(new buttonFilterListener());
		panel.add(buttonFilter);

		JButton buttonExport = new JButton("Export");
		buttonExport.setPreferredSize(DIMENSIONBUTTON);
		buttonExport.addActionListener(new buttonExportListener());
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
		configureDate();

		scrollPane = new JScrollPane(table);
		return scrollPane;
	}

	private List<Equipment> loadDataEquipments() {
		final EquipmentService service = new EquipmentService();
		List<Equipment> list = service.findAll();
		list.sort((e1, e2) -> e1.getSerialNumber().compareTo(e2.getSerialNumber()));
		return list;
	}
	
	private List<Option> loadDataOptions() {
		final OptionService service = new OptionService();
		List<Option> list = service.findAll();
		list.sort((o1, o2) -> o1.getOption().compareTo(o2.getOption()));
		return list;
	}

	private void sizeColumn() {
		TableColumn column = null;
		for (int i = 0; i < table.getColumnCount(); i++) {
			column = table.getColumnModel().getColumn(i);
			if (i == 0) {
				column.setPreferredWidth(110); // Serial Number
			} 
			else if (i == 1) {
				column.setPreferredWidth(110); // Host Name
			} 
			else if (i == 2) {
				column.setPreferredWidth(130); // Address MAC
			} 
			else if (i == 3) {
				column.setPreferredWidth(90); // Type
			} 
			else if (i == 4) {
				column.setPreferredWidth(90); // Patrimony Number
			} 
			else if (i == 5) {
				column.setPreferredWidth(80); // Brand
			} 
			else if (i == 6) {
				column.setPreferredWidth(180); // Model
			} 
			else if (i == 7) {
				column.setPreferredWidth(60); // Memory Ram
			} 
			else if (i == 8) {
				column.setPreferredWidth(60); // Hard Disk
			} 
			else if (i == 9) {
				column.setPreferredWidth(90); // Cost Type
			} 
			else if (i == 10) {
				column.setPreferredWidth(60); // Value
			} 
			else if (i == 11) {
				column.setPreferredWidth(100); // Status
			} 
			else if (i == 12) {
				column.setPreferredWidth(100); // Date Entry
			} 
			else if (i == 13) {
				column.setPreferredWidth(90); // Reason
			}
		}
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

		table.getColumnModel().getColumn(12).setCellRenderer(tableCellRenderer);
	}

	private class buttonNewListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (MainWindow.collaborator.getPrivilege() == 2) {
				JOptionPane.showMessageDialog(null, "You do not have access to this function", "access denied", JOptionPane.INFORMATION_MESSAGE);
			} else {
				new NewEquipmentForm(model, options).setVisible(true);
			}
		}
	}

	private class buttonEditListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (MainWindow.collaborator.getPrivilege() == 2) {
				JOptionPane.showMessageDialog(null, "You do not have access to this function", "access denied", JOptionPane.INFORMATION_MESSAGE);
			} 
			else {
				int lineSelected = -1;
				lineSelected = table.getSelectedRow();
				int modelRow = table.convertRowIndexToModel(lineSelected);
				if (lineSelected < 0) {
					JOptionPane.showMessageDialog(null, "It is necessary to select a line", "No lines selected", JOptionPane.INFORMATION_MESSAGE);
				} 
				else  {
					Equipment equipment = model.getEquipment(modelRow);
					if (equipment.getStatus().equals("DISABLED")) {
						JOptionPane.showMessageDialog(null, "This equipment is disabled", "Unable to Edit", JOptionPane.INFORMATION_MESSAGE);
					} 
					else {
						new EditEquipmentForm(model, equipment, options, modelRow).setVisible(true);
					}
				}
			}
		}
	}

	private class buttonViewListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int lineSelected = -1;
			lineSelected = table.getSelectedRow();
			int modelRow = table.convertRowIndexToModel(lineSelected);
			if (lineSelected < 0) {
				JOptionPane.showMessageDialog(null, "It is necessary to select a line", "No lines selected", JOptionPane.INFORMATION_MESSAGE);
			} 
			else  {
				Equipment equipment = model.getEquipment(modelRow);
				new ViewEquipmentForm(equipment).setVisible(true);
			}
		}
	}

	private class buttonDisableListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (MainWindow.collaborator.getPrivilege() == 2) {
				JOptionPane.showMessageDialog(null, "You do not have access to this function", "Access denied", JOptionPane.INFORMATION_MESSAGE);
			} 
			else {
				int lineSelected = -1;
				lineSelected = table.getSelectedRow();
				int modelRow = table.convertRowIndexToModel(lineSelected);
				if (lineSelected < 0) {
					JOptionPane.showMessageDialog(null, "It is necessary to select a line", "No lines selected", JOptionPane.INFORMATION_MESSAGE);
				} 
				else  {
					Equipment equipment = model.getEquipment(modelRow);
					if (equipment.getStatus().equals("DISABLED")) {
						JOptionPane.showMessageDialog(null, "This equipment already is disabled", "Unable to Disable", JOptionPane.INFORMATION_MESSAGE);
					} 
					else if (equipment.getStatus().equals("IN USE")) {
						JOptionPane.showMessageDialog(null, "This equipment is in use", "Unable to Disable", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						new DisableEquipmentForm(model, equipment, options, modelRow).setVisible(true);
					}
				}
			}
		}
	}

	private class buttonFilterListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			sorter = new TableRowSorter<EquipmentTableModel>(model);
			table.setRowSorter(sorter);
			new FilterEquipmentForm(sorter).setVisible(true);
			model.fireTableDataChanged();
		}
	}

	private class buttonExportListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			List<Equipment> equipments = new ArrayList<Equipment>();
			for(int row = 0; row < table.getRowCount();row++) {
				equipments.add(model.getEquipment(row));
            }
					
			JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView());

			int returnValue = jfc.showSaveDialog(null);

			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = jfc.getSelectedFile();
				new CreateExlFileEquipment(equipments, selectedFile.getAbsolutePath());
			}
		}
	}
}
