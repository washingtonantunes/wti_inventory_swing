package model.gui.inventory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.TableRowSorter;

import model.entities.Change;
import model.entities.Equipment;
import model.entities.Inventory;
import model.entities.Monitor;
import model.entities.Project;
import model.entities.User;
import model.entities.WorkPosition;
import model.gui.MainWindow;
import model.services.change.ChangeService;
import model.services.equipment.EquipmentService;
import model.services.inventory.CreateExlFileInventory;
import model.services.inventory.InventoryService;
import model.services.inventory.InventoryTableModel;
import model.services.inventory.TableInventory;
import model.services.monitor.MonitorService;
import model.services.project.ProjectService;
import model.services.user.UserService;
import model.services.workposition.WorkPositionService;

public class InventoryList extends JPanel {

	private static final long serialVersionUID = 1L;

	private final Dimension DIMENSIONBUTTON = new Dimension(130, 35);

	private final Color COLOR1 = new Color(0, 65, 83);
	private final Color COLOR2 = new Color(2, 101, 124);

	private JScrollPane scrollPane;
	private TableInventory table;
	private InventoryTableModel model;
	
	private static List<Change> changes;

	private List<Inventory> inventories;
	
	protected List<WorkPosition> workPositions;
	private List<Project> projects;
	private List<User> users;
	private List<Equipment> equipments;
	private List<Monitor> monitors;

	private JLabel label_Show__Quantity;

	private JTextField textField_Filter;
	private TableRowSorter<InventoryTableModel> sorter;

	public InventoryList() {
		changes = loadDataChanges();
		inventories = loadDataInventories();
		workPositions = loadDataWorkPositions();
		projects = loadDataProjects();
		users = loadDataUsers();
		equipments = loadDataEquipments();
		monitors = loadDataMonitors();
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
		panel.setPreferredSize(new Dimension(0, 85));
		panel.add(createPanelTitle(), BorderLayout.NORTH);
		panel.add(createPanelButton(), BorderLayout.SOUTH);
		return panel;
	}

	private JPanel createPanelTitle() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(0, 25));
		panel.setBackground(COLOR1);

		JLabel label_Title = new JLabel("Inventories");
		label_Title.setPreferredSize(new Dimension(130, 35));
		label_Title.setBounds(20, 2, 100, 20);
		label_Title.setForeground(Color.WHITE);
		panel.add(label_Title);

		return panel;
	}

	private JPanel createPanelButton() {
		JPanel panel = new JPanel(new FlowLayout());
		panel.setPreferredSize(new Dimension(0, 60));
		panel.setBackground(COLOR1);

		panel.add(createPanelButton1());
		panel.add(createPanelButton2());

		return panel;
	}

	private JPanel createPanelButton1() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		panel.setPreferredSize(new Dimension(800, 60));
		panel.setBackground(COLOR2);

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

		JButton buttonRemove = new JButton("Remove");
		buttonRemove.setPreferredSize(DIMENSIONBUTTON);
		buttonRemove.addActionListener(new buttonRemoveListener());
		panel.add(buttonRemove);

		JButton buttonExport = new JButton("Export");
		buttonExport.setPreferredSize(DIMENSIONBUTTON);
		buttonExport.addActionListener(new buttonExportListener());
		panel.add(buttonExport);

		return panel;
	}

	private JPanel createPanelButton2() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(450, 60));
		panel.setBackground(COLOR2);

		final JLabel label_Search = new JLabel("Enter to filter");
		label_Search.setBounds(20, 15, 100, 25);
		label_Search.setForeground(Color.WHITE);
		panel.add(label_Search);

		textField_Filter = new JTextField();
		textField_Filter.setBounds(100, 15, 130, 25);
		textField_Filter.addActionListener(new textFieldFilterListener());
		panel.add(textField_Filter);

		JLabel label_Quantity = new JLabel("Quantity:");
		label_Quantity.setPreferredSize(new Dimension(80, 35));
		label_Quantity.setBounds(340, 15, 80, 25);
		label_Quantity.setForeground(Color.WHITE);
		panel.add(label_Quantity);

		label_Show__Quantity = new JLabel(String.valueOf(inventories.size()));
		label_Show__Quantity.setPreferredSize(new Dimension(30, 35));
		label_Show__Quantity.setBounds(400, 15, 50, 25);
		label_Show__Quantity.setForeground(Color.WHITE);
		panel.add(label_Show__Quantity);

		return panel;
	}

	private JScrollPane createTable() {
		model = new InventoryTableModel(inventories);

		table = new TableInventory(model);
		table.addMouseListener(new MouseListener());

		scrollPane = new JScrollPane(table);
		return scrollPane;
	}
	
	public static List<Change> getChanges() {
		return changes;
	}

	private List<Change> loadDataChanges() {
		final ChangeService service = new ChangeService();
		List<Change> list = service.findAll();
		return list;
	}

	private List<Inventory> loadDataInventories() {
		final InventoryService service = new InventoryService();
		List<Inventory> list = service.findAll();
		list.sort((e1, e2) -> e1.getProject().getName().compareTo(e2.getProject().getName()));
		return list;
	}
	
	private List<WorkPosition> loadDataWorkPositions() {
		final WorkPositionService service = new WorkPositionService();

		Map<String, WorkPosition> workPositions = service.findAll();
		List<WorkPosition> list = new ArrayList<WorkPosition>();
		
		for (String entry : workPositions.keySet()) {
			list.add(workPositions.get(entry));
		}
		
		list = list.stream().filter(e -> e.getStatus().equals("FREE")).collect(Collectors.toList());
		list.sort((w1, w2) -> w1.getWorkPoint().compareTo(w2.getWorkPoint()));
		return list;
	}
	
	private List<Project> loadDataProjects() {
		final ProjectService service = new ProjectService();
		Map<String, Project> projects = service.findAll();
		List<Project> list = new ArrayList<Project>();
		
		for (String entry : projects.keySet()) {
			list.add(projects.get(entry));
		}

		list = list.stream().filter(e -> e.getStatus().equals("ACTIVE")).collect(Collectors.toList());
		list.sort((w1, w2) -> w1.getName().compareTo(w2.getName()));
		return list;
	}
	
	private List<User> loadDataUsers() {
		final UserService service = new UserService();
		Map<String, User> users = service.findAll();
		List<User> list = new ArrayList<User>();
		
		for (String entry : users.keySet()) {
			list.add(users.get(entry));
		}
		
		list = list.stream().filter(e -> e.getStatus().equals("ACTIVE")).collect(Collectors.toList());
		list.sort((w1, w2) -> w1.getRegistration().compareTo(w2.getRegistration()));
		return list;
	}
	
	private List<Equipment> loadDataEquipments() {
		final EquipmentService service = new EquipmentService();
		List<Equipment> list = service.findAll();
		list = list.stream().filter(e -> e.getStatus().equals("STAND BY")).collect(Collectors.toList());
		list.sort((w1, w2) -> w1.getSerialNumber().compareTo(w2.getSerialNumber()));
		return list;
	}
	
	private List<Monitor> loadDataMonitors() {
		final MonitorService service = new MonitorService();
		List<Monitor> list = service.findAll();
		list = list.stream().filter(e -> e.getStatus().equals("STAND BY")).collect(Collectors.toList());
		list.sort((w1, w2) -> w1.getSerialNumber().compareTo(w2.getSerialNumber()));
		return list;
	}
	
	private class buttonNewListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (MainWindow.collaborator.getPrivilege() == 2) {
				JOptionPane.showMessageDialog(null, "You do not have access to this function", "access denied", JOptionPane.INFORMATION_MESSAGE);
			} 
			else {
				new NewInventoryForm(model, workPositions, projects, users, equipments, monitors).setVisible(true);
				label_Show__Quantity.setText(String.valueOf(table.getRowCount()));
				repaint();
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
				else {
					Inventory inventory = model.getInventory(modelRow);
					new EditInventoryForm(model, inventory, modelRow, workPositions, projects, users, equipments, monitors).setVisible(true);
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
			else {
				Inventory inventory = model.getInventory(modelRow);
				new ViewInventoryForm(inventory).setVisible(true);
			}
		}
	}
	
	private class buttonRemoveListener implements ActionListener {

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
				else {
					int i = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove");
					if (i == JOptionPane.OK_OPTION) {
						Inventory inventory = model.getInventory(modelRow);
						InventoryService service = new InventoryService();
						service.delete(inventory);
						model.removeInventory(modelRow);
					}
				}
			}
		}
	}
	
	private class textFieldFilterListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			sorter = new TableRowSorter<InventoryTableModel>(model);
			table.setRowSorter(sorter);

			String text = textField_Filter.getText().toUpperCase();
			if (text.length() == 0) {
				sorter.setRowFilter(null);
				label_Show__Quantity.setText(String.valueOf(table.getRowCount()));
				repaint();
			} 
			else {
				sorter.setRowFilter(RowFilter.regexFilter(text));
				label_Show__Quantity.setText(String.valueOf(table.getRowCount()));
				repaint();
			}
		}
	}
	
	private class buttonExportListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			List<Inventory> inventorys = new ArrayList<Inventory>();
			for (int row = 0; row < table.getRowCount(); row++) {
				inventorys.add(model.getInventory(row));
			}

			JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView());

			int returnValue = jfc.showSaveDialog(null);

			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = jfc.getSelectedFile();
				new CreateExlFileInventory(inventorys, selectedFile.getAbsolutePath());
			}
		}
	}
	
	private class MouseListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent evt) {
			if (evt.getClickCount() == 2) {
				int lineSelected = table.getSelectedRow();
				int modelRow = table.convertRowIndexToModel(lineSelected);
				Inventory inventory = model.getInventory(modelRow);
				new ViewInventoryForm(inventory).setVisible(true);
			}
		}
	}
}
