package model.gui.monitor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.TableRowSorter;

import model.entities.Change;
import model.entities.Monitor;
import model.entities.Option;
import model.gui.MainWindow;
import model.services.OptionService;
import model.services.change.ChangeService;
import model.services.monitor.CreateExlFileMonitor;
import model.services.monitor.MonitorService;
import model.services.monitor.MonitorTableModel;
import model.services.monitor.TableMonitor;

public class MonitorList extends JPanel {

	private static final long serialVersionUID = 1L;

	private final Dimension DIMENSIONBUTTON = new Dimension(140, 40);

	private final Dimension DIMENSIONBUTTONSPANEL = new Dimension(0, 70);
	private final Dimension DIMENSIONNORTHPANEL = new Dimension(0, 95);
	private final Dimension DIMENSIONTITLEPANEL = new Dimension(0, 25);

	private final Color COLOR1 = new Color(4, 77, 92);

	private JScrollPane scrollPane;
	private TableMonitor table;
	private MonitorTableModel model;

	private static List<Change> changes;
	private List<Monitor> monitors;
	private List<Option> options; 
	
	private JLabel label_Show__Quantity;
	
	private TableRowSorter<MonitorTableModel> sorter;

	public MonitorList() {
		changes = loadDataChanges();
		this.monitors = loadDataMonitors();
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
		
		JLabel label_Title = new JLabel("Monitors");
		label_Title.setPreferredSize(DIMENSIONBUTTON);
		label_Title.setBounds(20, 2, 100, 20);
		label_Title.setForeground(Color.WHITE);
		panel.add(label_Title);
		
		JLabel label_Quantity = new JLabel("Quantity:");
		label_Quantity.setPreferredSize(DIMENSIONBUTTON);
		label_Quantity.setBounds(1200, 2, 100, 20);
		label_Quantity.setForeground(Color.WHITE);
		panel.add(label_Quantity);
		
		label_Show__Quantity = new JLabel(String.valueOf(monitors.size()));
		label_Show__Quantity.setPreferredSize(DIMENSIONBUTTON);
		label_Show__Quantity.setBounds(1260, 2, 100, 20);
		label_Show__Quantity.setForeground(Color.WHITE);
		panel.add(label_Show__Quantity);
		
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
		model = new MonitorTableModel(monitors);

		table = new TableMonitor(model);

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

	private List<Monitor> loadDataMonitors() {
		final MonitorService service = new MonitorService();
		List<Monitor> list = service.findAll();
		list.sort((e1, e2) -> e1.getSerialNumber().compareTo(e2.getSerialNumber()));
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
			} else {
				new NewMonitorForm(model, options).setVisible(true);
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
				else  {
					Monitor monitor = model.getMonitor(modelRow);
					if (monitor.getStatus().equals("DISABLED")) {
						JOptionPane.showMessageDialog(null, "This monitor is disabled", "Unable to Edit", JOptionPane.INFORMATION_MESSAGE);
					} 
					else {
						new EditMonitorForm(model, monitor, options, modelRow).setVisible(true);
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
				Monitor monitor = model.getMonitor(modelRow);
				new ViewMonitorForm(monitor).setVisible(true);
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
					Monitor monitor = model.getMonitor(modelRow);
					if (monitor.getStatus().equals("DISABLED")) {
						JOptionPane.showMessageDialog(null, "This monitor already is disabled", "Unable to Disable", JOptionPane.INFORMATION_MESSAGE);
					} 
					else if (monitor.getStatus().equals("IN USE")) {
						JOptionPane.showMessageDialog(null, "This monitor is in use", "Unable to Disable", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						new DisableMonitorForm(model, monitor, options, modelRow).setVisible(true);
					}
				}
			}
		}
	}

	private class buttonFilterListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			sorter = new TableRowSorter<MonitorTableModel>(model);
			table.setRowSorter(sorter);
			new FilterMonitorForm(sorter).setVisible(true);
			model.fireTableDataChanged();
		}
	}

	private class buttonExportListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			List<Monitor> monitors = new ArrayList<Monitor>();
			for(int row = 0; row < table.getRowCount();row++) {
				monitors.add(model.getMonitor(row));
            }
					
			JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView());

			int returnValue = jfc.showSaveDialog(null);

			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = jfc.getSelectedFile();
				new CreateExlFileMonitor(monitors, selectedFile.getAbsolutePath());
			}
		}
	}
}
