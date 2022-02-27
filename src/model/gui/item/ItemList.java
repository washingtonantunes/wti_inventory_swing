package model.gui.item;

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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import application.LoadData;
import application.MainWindow;
import model.entities.Equipment;
import model.entities.Monitor;
import model.entities.User;
import model.entities.utilitary.Item;
import model.gui.equipment.ViewEquipmentForm;
import model.gui.monitor.ViewMonitorForm;
import model.services.itens.ItemTableModel;
import model.services.itens.TableItem;
import model.util.MyButton;
import model.util.MyLabel;

public class ItemList extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private final Color COLOR1 = new Color(0, 65, 83);
	private final Color COLOR2 = new Color(2, 101, 124);

	private JScrollPane scrollPane;
	private TableItem table;
	private ItemTableModel model;
	
	private User user;

	private List<Item> itens;

	private JLabel label_Show_Quantity;
	private JLabel label_Show_CostTotal;

	public ItemList(List<Item> itens, User user) {
		this.itens = itens;
		this.user = user;
		initComponents();
	}

	private void initComponents() {
		setTitle("Itens");
		setModal(true);
		setPreferredSize(new Dimension(850, 500));
		setResizable(false);
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		
		add(createPanelNorth(), BorderLayout.NORTH);
		add(createTable(), BorderLayout.CENTER);
		add(createPanelSouth(), BorderLayout.SOUTH);
		
		pack();
		setLocationRelativeTo(null);
	}

	private JPanel createPanelNorth() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		panel.setPreferredSize(new Dimension(860, 55));

		panel.add(createPanelButtonWest());
		panel.add(createPanelButtonEast());

		return panel;
	}

	private JPanel createPanelButtonWest() {
		final JPanel panelButtonWest = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		panelButtonWest.setPreferredSize(new Dimension(665, 55));
		panelButtonWest.setBackground(COLOR1);

		final JButton buttonDelivery = new MyButton("Delivery", 2);
		buttonDelivery.addActionListener(new buttonDeliveryListener());
		panelButtonWest.add(buttonDelivery);
		
		final JButton buttonExchange = new MyButton("Exchange", 2);
		buttonExchange.addActionListener(new buttonExchangeListener());
		panelButtonWest.add(buttonExchange);
		
		final JButton buttonDevolution = new MyButton("Devolution", 2);
		buttonDevolution.addActionListener(new buttonDevolutionListener());
		panelButtonWest.add(buttonDevolution);
		
		final JButton buttonReport = new MyButton("Report", 2);
		buttonReport.addActionListener(new buttonReportListener());
		panelButtonWest.add(buttonReport);

		return panelButtonWest;
	}

	private JPanel createPanelButtonEast() {
		final JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 10));
		panel.setPreferredSize(new Dimension(165, 55));
		panel.setBackground(COLOR1);

		final JLabel label_Quantity = new MyLabel("Quantity:", 1, 4, 2);
		panel.add(label_Quantity);

		label_Show_Quantity = new MyLabel(String.valueOf(itens.size()), 8, 4, 2);
		panel.add(label_Show_Quantity);

		return panel;
	}

	private JScrollPane createTable() {
		model = new ItemTableModel(itens);

		table = new TableItem(model);
		table.addMouseListener(new MouseListener());

		scrollPane = new JScrollPane(table);
		return scrollPane;
	}

	private JPanel createPanelSouth() {
		final JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 10));
		panel.setPreferredSize(new Dimension(850, 50));
		panel.setBackground(COLOR2);

		final JLabel label_CostTotal = new MyLabel("Cost Total:", 2, 4, 2);
		panel.add(label_CostTotal);

		label_Show_CostTotal = new MyLabel(String.format("R$ %.2f", getCostTotal()), 1, 4, 2);
		panel.add(label_Show_CostTotal);

		return panel;
	}

	private Double getCostTotal() {
		double costTotal = 0.0;
		for (Item item : itens) {
			costTotal += item.getValue();
		}

		return costTotal;
	}
	
	private class buttonDeliveryListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (MainWindow.collaborator.getPrivilege() == 2) {
				JOptionPane.showMessageDialog(null, "You do not have access to this function", "access denied", JOptionPane.INFORMATION_MESSAGE);
			} 
			else {
				new AddItemForm(user, itens);
				label_Show_Quantity.setText(String.valueOf(itens.size()));
				label_Show_CostTotal.setText(String.format("R$ %.2f", getCostTotal()));
				repaint();
			}
		}
	}

	private class buttonExchangeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (MainWindow.collaborator.getPrivilege() == 2) {
				JOptionPane.showMessageDialog(null, "You do not have access to this function", "Access denied",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				int lineSelected = -1;
				lineSelected = table.getSelectedRow();
				if (lineSelected < 0) {
					JOptionPane.showMessageDialog(null, "It is necessary to select a line", "No lines selected",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					Item item = model.getItem(lineSelected);
					new ExchangeItemForm(model, lineSelected, item, user, getType(item));
					label_Show_Quantity.setText(String.valueOf(itens.size()));
					label_Show_CostTotal.setText(String.format("R$ %.2f", getCostTotal()));
					repaint();
				}
			}
		}
	}
	
	private class buttonDevolutionListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (MainWindow.collaborator.getPrivilege() == 2) {
				JOptionPane.showMessageDialog(null, "You do not have access to this function", "Access denied", JOptionPane.INFORMATION_MESSAGE);
			} 
			else {
				int lineSelected = -1;
				lineSelected = table.getSelectedRow();
				if (lineSelected < 0) {
					JOptionPane.showMessageDialog(null, "It is necessary to select a line", "No lines selected", JOptionPane.INFORMATION_MESSAGE);
				} 
				else {
					int i = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove");
					if (i == JOptionPane.OK_OPTION) {
						Item item = model.getItem(lineSelected);
						System.out.println(item);
						label_Show_Quantity.setText(String.valueOf(itens.size()));
						label_Show_CostTotal.setText(String.format("R$ %.2f", getCostTotal()));
						repaint();
					}
				}
			}
		}
	}
	
	private class buttonReportListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int i = table.getRowCount();
			if (i <= 0) {
				JOptionPane.showMessageDialog(null, "There is no data to export", "Unable to Export", JOptionPane.INFORMATION_MESSAGE);
			} else {
				System.out.println("buttonReportListener");
//				List<Inventory> Inventorys = new ArrayList<Inventory>();
//
//				for (int row = 0; row < table.getRowCount(); row++) {
//					int modelRow = table.convertRowIndexToModel(row);
//					Inventorys.add(model.getInventory(modelRow));
//				}
//
//				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView());
//
//				int returnValue = jfc.showSaveDialog(null);
//
//				if (returnValue == JFileChooser.APPROVE_OPTION) {
//					File selectedFile = jfc.getSelectedFile();
//					new CreateExlFileInventory(Inventorys, selectedFile.getAbsolutePath());
//				}
			}
		}
	}
	
	private class MouseListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent evt) {
			if (evt.getClickCount() == 2) {
				int lineSelected = table.getSelectedRow();
				Item item = model.getItem(lineSelected);

				String type = item.getType();
				String code = item.getCode();
				
				if (type.equals("Equipment")) {
					Equipment equipment = LoadData.getEquipment(code);
					new ViewEquipmentForm(equipment).setVisible(true);;
				} else if (type.equals("Monitor")) {
					Monitor monitor = LoadData.getMonitor(code);
					new ViewMonitorForm(monitor).setVisible(true);;
				} else if (type.equals("Peripheral")) {
					//Peripheral peripheral = LoadData.getPeripheral(code);
					
				} else if (type.equals("License")) {
					//License license = LoadData.getLicense(code);
				}
			}
		}
	}
	
	private Integer getType (Item item) {
		String type = item.getType();
		if (type.equals("Equipment")) {
			return 1;
		} 
		else if (type.equals("Monitor")) {
			return 2;
		}
		else if (type.equals("Peripheral")) {
			return 3;
		} 
		else if (type.equals("License")) {
			return 4;
		}
		return null;
	}
}
