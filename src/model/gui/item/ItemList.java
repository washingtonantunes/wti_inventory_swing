package model.gui.item;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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

import model.entities.Item;
import model.gui.MainWindow;
import model.services.itens.ItemTableModel;
import model.services.itens.TableItem;

public class ItemList extends JDialog {

	private static final long serialVersionUID = 1L;

	private final Dimension DIMENSIONBUTTON = new Dimension(90, 30);

	private final Color COLOR1 = new Color(0, 65, 83);
	private final Color COLOR2 = new Color(2, 101, 124);

	private JScrollPane scrollPane;
	private TableItem table;
	private ItemTableModel model;

	private List<Item> itens;

	private JLabel label_Show_Quantity;
	private JLabel label_Show_CostTotal;

	public ItemList(List<Item> itens) {
		this.itens = itens;
		initComponents();
	}

	private void initComponents() {
		setModal(true);

		add(createPanelNorth(), BorderLayout.NORTH);
		add(createTable(), BorderLayout.CENTER);
		add(createPanelSouth(), BorderLayout.SOUTH);

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Itens");
		setPreferredSize(new Dimension(600, 500));
		setResizable(false);

		pack();
		setLocationRelativeTo(null);
	}

	private JPanel createPanelNorth() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		panel.setPreferredSize(new Dimension(600, 55));

		panel.add(createPanelButtonWest());
		panel.add(createPanelButtonEast());

		return panel;
	}

	private JPanel createPanelButtonWest() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		panel.setPreferredSize(new Dimension(460, 55));
		panel.setBackground(COLOR1);

		JButton buttonNew = new JButton("New");
		buttonNew.setPreferredSize(DIMENSIONBUTTON);
		buttonNew.addActionListener(new buttonNewListener());
		panel.add(buttonNew);

		JButton buttonRemove = new JButton("Remove");
		buttonRemove.setPreferredSize(DIMENSIONBUTTON);
		buttonRemove.addActionListener(new buttonRemoveListener());
		panel.add(buttonRemove);

		JButton buttonReport = new JButton("Report");
		buttonReport.setPreferredSize(DIMENSIONBUTTON);
		buttonReport.addActionListener(new buttonReportListener());
		panel.add(buttonReport);

		return panel;
	}

	private JPanel createPanelButtonEast() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
		panel.setPreferredSize(new Dimension(120, 55));
		panel.setBackground(COLOR1);

		JLabel label_Quantity = new JLabel("Quantity:");
		label_Quantity.setPreferredSize(new Dimension(80, 35));
		label_Quantity.setFont(new java.awt.Font(null, Font.BOLD, 15));
		label_Quantity.setForeground(Color.WHITE);
		panel.add(label_Quantity);

		label_Show_Quantity = new JLabel(String.valueOf(itens.size()));
		label_Show_Quantity.setPreferredSize(new Dimension(30, 35));
		label_Show_Quantity.setFont(new java.awt.Font(null, Font.BOLD, 15));
		label_Show_Quantity.setForeground(Color.WHITE);
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
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(450, 50));
		panel.setBackground(COLOR2);

		JLabel label_Quantity = new JLabel("Cost Total:");
		label_Quantity.setPreferredSize(new Dimension(80, 35));
		label_Quantity.setBounds(310, 15, 110, 25);
		label_Quantity.setFont(new java.awt.Font(null, Font.BOLD, 20));
		label_Quantity.setForeground(Color.WHITE);
		panel.add(label_Quantity);

		label_Show_CostTotal = new JLabel(String.format("R$ %.2f", getCostTotal()));
		label_Show_CostTotal.setPreferredSize(new Dimension(30, 35));
		label_Show_CostTotal.setBounds(460, 15, 110, 25);
		label_Show_CostTotal.setFont(new java.awt.Font(null, Font.BOLD, 20));
		label_Show_CostTotal.setForeground(Color.WHITE);
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
	
	private class buttonNewListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (MainWindow.collaborator.getPrivilege() == 2) {
				JOptionPane.showMessageDialog(null, "You do not have access to this function", "access denied", JOptionPane.INFORMATION_MESSAGE);
			} 
			else {
				System.out.println("buttonNewListener");
				label_Show_Quantity.setText(String.valueOf(table.getRowCount()));
				label_Show_CostTotal.setText(String.format("R$ %.1f", getCostTotal()));
				repaint();
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
						System.out.println("buttonRemoveListener");
						label_Show_Quantity.setText(String.valueOf(table.getRowCount()));
						label_Show_CostTotal.setText(String.format("R$ %.1f", getCostTotal()));
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
				JOptionPane.showMessageDialog(null, "There is no data to export", "Unable to Export",
						JOptionPane.INFORMATION_MESSAGE);
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
				int modelRow = table.convertRowIndexToModel(lineSelected);
				System.out.println("MouseListener");
			}
		}
	}
}
