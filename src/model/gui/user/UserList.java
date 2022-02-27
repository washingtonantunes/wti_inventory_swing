package model.gui.user;

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

import application.LoadData;
import application.MainWindow;
import model.entities.User;
import model.services.user.CreateExlFileUser;
import model.services.user.TableUser;
import model.services.user.UserTableModel;
import model.util.MyButton;
import model.util.MyLabel;

public class UserList extends JPanel {

private static final long serialVersionUID = 1L;

	private final Color COLOR1 = new Color(0, 65, 83);
	private final Color COLOR2 = new Color(2, 101, 124);

	private final int SIZEBUTTONS = 2; 	

	private JScrollPane scrollPane;
	private TableUser table;
	private UserTableModel model;

	private List<User> users;

	private JLabel label_Show_Quantity;

	private JTextField textField_Filter;
	private TableRowSorter<UserTableModel> sorter;

	public UserList() {
		this.users = LoadData.getUsersList();
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
		final JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		panel.setPreferredSize(new Dimension(0, 25));
		panel.setBackground(COLOR1);

		final JLabel label_Registration = new MyLabel("Users", 1, 4, 1);
		panel.add(label_Registration);

		return panel;
	}

	private JPanel createPanelButton() {
		JPanel panel = new JPanel(new FlowLayout());
		panel.setPreferredSize(new Dimension(0, 60));
		panel.setBackground(COLOR1);

		panel.add(createPanelButtonWest());
		panel.add(createPanelButtonEast());

		return panel;
	}

	private JPanel createPanelButtonWest() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		panel.setPreferredSize(new Dimension(800, 50));
		panel.setBackground(COLOR2);

		JButton buttonNew = new MyButton("New", SIZEBUTTONS);
		buttonNew.addActionListener(new buttonNewListener());
		panel.add(buttonNew);

		JButton buttonEdit = new MyButton("Edit", SIZEBUTTONS);
		buttonEdit.addActionListener(new buttonEditListener());
		panel.add(buttonEdit);

		JButton buttonView = new MyButton("View", SIZEBUTTONS);
		buttonView.addActionListener(new buttonViewListener());
		panel.add(buttonView);

		JButton buttonDisable = new MyButton("Disable", SIZEBUTTONS);
		buttonDisable.addActionListener(new buttonDisableListener());
		panel.add(buttonDisable);

		JButton buttonExport = new MyButton("Export", SIZEBUTTONS);
		buttonExport.addActionListener(new buttonExportListener());
		panel.add(buttonExport);

		return panel;
	}

	private JPanel createPanelButtonEast() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(450, 50));
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
		label_Quantity.setBounds(340, 15, 80, 25);
		label_Quantity.setForeground(Color.WHITE);
		panel.add(label_Quantity);

		label_Show_Quantity = new JLabel(String.valueOf(users.size()));
		label_Show_Quantity.setBounds(400, 15, 50, 25);
		label_Show_Quantity.setForeground(Color.WHITE);
		panel.add(label_Show_Quantity);

		return panel;
	}

	private JScrollPane createTable() {
		model = new UserTableModel(users);

		table = new TableUser(model);
		table.addMouseListener(new MouseListener());

		scrollPane = new JScrollPane(table);
		return scrollPane;
	}

	private class buttonNewListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (MainWindow.collaborator.getPrivilege() == 2) {
				JOptionPane.showMessageDialog(null, "You do not have access to this function", "access denied", JOptionPane.INFORMATION_MESSAGE);
			} 
			else {
				new NewUserForm(model);
				label_Show_Quantity.setText(String.valueOf(table.getRowCount()));
				repaint();
			}
		}
	}

	private class buttonEditListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (MainWindow.collaborator.getPrivilege() == 2) {
				JOptionPane.showMessageDialog(null, "You do not have access to this function", "access denied", JOptionPane.INFORMATION_MESSAGE);
			} else {
				int lineSelected = -1;
				lineSelected = table.getSelectedRow();
				if (lineSelected < 0) {
					JOptionPane.showMessageDialog(null, "It is necessary to select a line", "No lines selected", JOptionPane.INFORMATION_MESSAGE);
				} 
				else {
					int modelRow = table.convertRowIndexToModel(lineSelected);
					User user = model.getUser(modelRow);
					if (user.getStatus().equals("DISABLED")) {
						JOptionPane.showMessageDialog(null, "This user is disabled", "Unable to Edit", JOptionPane.INFORMATION_MESSAGE);
					} 
					else {
						new EditUserForm(model, user, modelRow);
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
			if (lineSelected < 0) {
				JOptionPane.showMessageDialog(null, "It is necessary to select a line", "No lines selected", JOptionPane.INFORMATION_MESSAGE);
			} 
			else {
				int modelRow = table.convertRowIndexToModel(lineSelected);
				User user = model.getUser(modelRow);
				new ViewUserForm(user);
			}
		}
	}

	private class buttonDisableListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (MainWindow.collaborator.getPrivilege() == 2) {
				JOptionPane.showMessageDialog(null, "You do not have access to this function", "Access denied", JOptionPane.INFORMATION_MESSAGE);
			} else {
				int lineSelected = -1;
				lineSelected = table.getSelectedRow();
				if (lineSelected < 0) {
					JOptionPane.showMessageDialog(null, "It is necessary to select a line", "No lines selected", JOptionPane.INFORMATION_MESSAGE);
				} else {
					int modelRow = table.convertRowIndexToModel(lineSelected);
					User user = model.getUser(modelRow);
					if (user.getStatus().equals("DISABLED")) {
						JOptionPane.showMessageDialog(null, "This user already is disabled", "Unable to Disable", JOptionPane.INFORMATION_MESSAGE);
					} else if (user.getEquipments().size() > 0 || user.getMonitors().size() > 0 || user.getPeripherals().size() > 0 || user.getLicenses().size() > 0) {
						JOptionPane.showMessageDialog(null, "This user has equipment or peripherals under his/her responsibility.", "Unable to Disable", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						new DisableUserForm(model, user, modelRow);
					}
				}
			}
		}
	}

	private class textFieldFilterListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			sorter = new TableRowSorter<UserTableModel>(model);
			table.setRowSorter(sorter);

			String text = textField_Filter.getText().toUpperCase();
			if (text.length() == 0) {
				sorter.setRowFilter(null);
				label_Show_Quantity.setText(String.valueOf(table.getRowCount()));
				repaint();
			} 
			else {
				sorter.setRowFilter(RowFilter.regexFilter(text));
				label_Show_Quantity.setText(String.valueOf(table.getRowCount()));
				repaint();
			}
		}
	}

	private class buttonExportListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int i = table.getRowCount();
			if (i <= 0) {
				JOptionPane.showMessageDialog(null, "There is no data to export", "Unable to Export", JOptionPane.INFORMATION_MESSAGE);
			} 
			else {
				List<User> Users = new ArrayList<User>();

				for (int row = 0; row < table.getRowCount(); row++) {
					int modelRow = table.convertRowIndexToModel(row);
					Users.add(model.getUser(modelRow));
				}

				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView());

				int returnValue = jfc.showSaveDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jfc.getSelectedFile();
					new CreateExlFileUser(Users, selectedFile.getAbsolutePath());
				}
			}
		}
	}

	private class MouseListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent evt) {
			if (evt.getClickCount() == 2) {
				int lineSelected = -1;
				lineSelected = table.getSelectedRow();
				if (lineSelected >= 0) {
					int modelRow = table.convertRowIndexToModel(lineSelected);
					User user = model.getUser(modelRow);
					new ViewUserForm(user);

				}
			}
		}
	}
}
