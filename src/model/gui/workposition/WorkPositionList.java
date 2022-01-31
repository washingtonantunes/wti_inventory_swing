package model.gui.workposition;

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

import model.entities.Change;
import model.entities.Option;
import model.entities.WorkPosition;
import model.gui.MainWindow;
import model.services.OptionService;
import model.services.change.ChangeService;
import model.services.workposition.CreateExlFileWorkPosition;
import model.services.workposition.TableWorkPosition;
import model.services.workposition.WorkPositionService;
import model.services.workposition.WorkPositionTableModel;

public class WorkPositionList extends JPanel {

	private static final long serialVersionUID = 1L;

	private final Dimension DIMENSIONBUTTON = new Dimension(130, 35);

	private final Color COLOR2 = new Color(0, 65, 83);
	private final Color COLOR3 = new Color(2, 101, 124);

	private JScrollPane scrollPane;
	private TableWorkPosition table;
	private WorkPositionTableModel model;

	private static List<Change> changes;
	private List<WorkPosition> workPositions;
	private List<Option> options;

	private JLabel label_Show__Quantity;

	private JTextField textField_Filter;
	private TableRowSorter<WorkPositionTableModel> sorter;

	public WorkPositionList() {
		changes = loadDataChanges();
		workPositions = loadDataWorkPositions();
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
		panel.setPreferredSize(new Dimension(0, 85));
		panel.add(createPanelTitle(), BorderLayout.NORTH);
		panel.add(createPanelButton(), BorderLayout.SOUTH);
		return panel;
	}

	private JPanel createPanelTitle() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(0, 25));
		panel.setBackground(COLOR2);

		JLabel label_Title = new JLabel("Work Positions");
		label_Title.setPreferredSize(new Dimension(130, 35));
		label_Title.setBounds(20, 2, 100, 20);
		label_Title.setForeground(Color.WHITE);
		panel.add(label_Title);

		return panel;
	}

	private JPanel createPanelButton() {
		JPanel panel = new JPanel(new FlowLayout());
		panel.setPreferredSize(new Dimension(0, 60));
		panel.setBackground(COLOR2);

		panel.add(createPanelButton1());
		panel.add(createPanelButton2());

		return panel;
	}

	private JPanel createPanelButton1() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		panel.setPreferredSize(new Dimension(800, 60));
		panel.setBackground(COLOR3);

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
		panel.setBackground(COLOR3);

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

		label_Show__Quantity = new JLabel(String.valueOf(workPositions.size()));
		label_Show__Quantity.setPreferredSize(new Dimension(50, 35));
		label_Show__Quantity.setBounds(400, 15, 50, 25);
		label_Show__Quantity.setForeground(Color.WHITE);
		panel.add(label_Show__Quantity);

		return panel;
	}

	private JScrollPane createTable() {
		model = new WorkPositionTableModel(workPositions);

		table = new TableWorkPosition(model);
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

	private List<WorkPosition> loadDataWorkPositions() {
		final WorkPositionService service = new WorkPositionService();
		List<WorkPosition> list = service.findAll();
		list.sort((w1, w2) -> w1.getWorkPoint().compareTo(w2.getWorkPoint()));
		return list;
	}

	private List<Option> loadDataOptions() {
		final OptionService service = new OptionService();
		List<Option> list = service.findAll();
		list.sort((o1, o2) -> o1.getOption().compareTo(o2.getOption()));
		return list;
	}

	private class buttonNewListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (MainWindow.collaborator.getPrivilege() == 2) {
				JOptionPane.showMessageDialog(null, "You do not have access to this function", "access denied", JOptionPane.INFORMATION_MESSAGE);
			} 
			else {
				new NewWorkPositionForm(model, options).setVisible(true);
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
					WorkPosition workPosition = model.getWorkPosition(modelRow);
					if (workPosition.getStatus().equals("DISABLED")) {
						JOptionPane.showMessageDialog(null, "This work position is disabled", "Unable to Edit", JOptionPane.INFORMATION_MESSAGE);
					} 
					else {
						new EditWorkPositionForm(model, workPosition, options, modelRow).setVisible(true);
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
			else {
				WorkPosition workPosition = model.getWorkPosition(modelRow);
				new ViewWorkPositionForm(workPosition).setVisible(true);
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
				else {
					WorkPosition workPosition = model.getWorkPosition(modelRow);
					if (workPosition.getStatus().equals("DISABLED")) {
						JOptionPane.showMessageDialog(null, "This work position already is disabled", "Unable to Disable", JOptionPane.INFORMATION_MESSAGE);
					} 
					else if (workPosition.getStatus().equals("IN USE")) {
						JOptionPane.showMessageDialog(null, "This work position is in use", "Unable to Disable", JOptionPane.INFORMATION_MESSAGE);
					} 
					else {
						new DisableWorkPositionForm(model, workPosition, options, modelRow).setVisible(true);
					}
				}
			}
		}
	}

	private class textFieldFilterListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			sorter = new TableRowSorter<WorkPositionTableModel>(model);
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
			List<WorkPosition> workPositions = new ArrayList<WorkPosition>();
			for (int row = 0; row < table.getRowCount(); row++) {
				workPositions.add(model.getWorkPosition(row));
			}

			JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView());

			int returnValue = jfc.showSaveDialog(null);

			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = jfc.getSelectedFile();
				new CreateExlFileWorkPosition(workPositions, selectedFile.getAbsolutePath());
			}
		}
	}
	
	private class MouseListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent evt) {
			if (evt.getClickCount() == 2) {
				int lineSelected = table.getSelectedRow();
				int modelRow = table.convertRowIndexToModel(lineSelected);
				WorkPosition workPosition = model.getWorkPosition(modelRow);
				new ViewWorkPositionForm(workPosition).setVisible(true);
			}
		}
	}
}
