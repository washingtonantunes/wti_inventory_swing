package model.gui.inventory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

import model.entities.Inventory;
import model.gui.MainWindow;
import model.services.inventory.InventoryService;
import model.services.inventory.InventoryTableModel;
import model.services.inventory.TableInventory;

public class InventoryList extends JPanel {

	private static final long serialVersionUID = 1L;

	private final Dimension DIMENSIONBUTTON = new Dimension(130, 35);

	private final Color COLOR1 = new Color(0, 65, 83);
	private final Color COLOR2 = new Color(2, 101, 124);

	private JScrollPane scrollPane;
	private TableInventory table;
	private InventoryTableModel model;

	private List<Inventory> inventories;

	private JLabel label_Show__Quantity;

	private JTextField textField_Filter;
	private TableRowSorter<InventoryTableModel> sorter;

	public InventoryList() {
		this.inventories = loadDataInventories();
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
		//buttonEdit.addActionListener(new buttonEditListener());
		panel.add(buttonEdit);

		JButton buttonView = new JButton("View");
		buttonView.setPreferredSize(DIMENSIONBUTTON);
		buttonView.addActionListener(new buttonViewListener());
		panel.add(buttonView);

		JButton buttonDisable = new JButton("Disable");
		buttonDisable.setPreferredSize(DIMENSIONBUTTON);
		//buttonDisable.addActionListener(new buttonDisableListener());
		panel.add(buttonDisable);

		JButton buttonExport = new JButton("Export");
		buttonExport.setPreferredSize(DIMENSIONBUTTON);
		//buttonExport.addActionListener(new buttonExportListener());
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

	private List<Inventory> loadDataInventories() {
		final InventoryService service = new InventoryService();
		List<Inventory> list = service.findAll();
		list.sort((e1, e2) -> e1.getProject().getName().compareTo(e2.getProject().getName()));
		return list;
	}
	
	private class buttonNewListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (MainWindow.collaborator.getPrivilege() == 2) {
				JOptionPane.showMessageDialog(null, "You do not have access to this function", "access denied", JOptionPane.INFORMATION_MESSAGE);
			} 
			else {
				new NewInventoryForm(model).setVisible(true);
				label_Show__Quantity.setText(String.valueOf(table.getRowCount()));
				repaint();
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
