package model.gui.monitor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import db.DBException;
import exception.ValidationException;
import model.entities.Monitor;
import model.entities.Option;
import model.services.monitor.MonitorService;
import model.services.monitor.MonitorTableModel;
import model.util.JTextFieldFilter;

public class EditMonitorForm extends JDialog {

	private static final long serialVersionUID = 1L;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private static final int COLUMN1 = 20;
	private static final int COLUMN2 = 170;
	private static final int COLUMN3 = 330;

	private static final int WIDTH = 150;
	private static final int HEIGHT = 25;

	private static final Dimension DIMENSIONMAINPANEL = new Dimension(600, 350);

	private JTextField textField_PatrimonyNumber;
	private JComboBox<String> comboBox_Brand;
	private JComboBox<String> comboBox_Model;

	private JLabel labelError_PatrimonyNumber;
	private JLabel labelError_Brand;
	private JLabel labelError_Model;

	private MonitorTableModel model;
	private Monitor monitorOld;
	private List<Option> options;
	private int lineSelected;

	public EditMonitorForm(MonitorTableModel model, Monitor monitorOld, List<Option> options, int lineSelected) {
		this.model = model;
		this.monitorOld = monitorOld;
		this.options = options;
		this.lineSelected = lineSelected;
		initComponents();
	}

	private void initComponents() {
		setModal(true);

		add(createPanelMain());

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Edit Monitor");
		setPreferredSize(DIMENSIONMAINPANEL);
		setResizable(false);

		pack();
		setLocationRelativeTo(null);
	}

	private JPanel createPanelMain() {
		final JPanel panel = new JPanel(new FlowLayout());
		panel.setLayout(null);

		addLabels(panel);
		addTextFieldsAndComboBoxes(panel);
		addLabelsError(panel);
		addButtons(panel);

		return panel;
	}

	private void addLabels(JPanel panel) {
		final JLabel label_SerialNumber = new JLabel("Serial Number:");
		label_SerialNumber.setBounds(COLUMN1, 10, WIDTH, HEIGHT);
		panel.add(label_SerialNumber);

		final JLabel label_PatrimonyNumber = new JLabel("PatrimonyNumber:");
		label_PatrimonyNumber.setBounds(COLUMN1, 50, WIDTH, HEIGHT);
		panel.add(label_PatrimonyNumber);

		final JLabel label_Brand = new JLabel("Brand:");
		label_Brand.setBounds(COLUMN1, 90, WIDTH, HEIGHT);
		panel.add(label_Brand);

		final JLabel label_Model = new JLabel("Model:");
		label_Model.setBounds(COLUMN1, 130, WIDTH, HEIGHT);
		panel.add(label_Model);

		JLabel label_Status = new JLabel("Status:");
		label_Status.setBounds(COLUMN1, 170, WIDTH, HEIGHT);
		panel.add(label_Status);

		JLabel label_DateEntry = new JLabel("DateEntry:");
		label_DateEntry.setBounds(COLUMN1, 210, WIDTH, HEIGHT);
		panel.add(label_DateEntry);
	}

	private void addTextFieldsAndComboBoxes(JPanel panel) {
		final JLabel label_Show_SerialNumber = new JLabel(monitorOld.getSerialNumber());
		label_Show_SerialNumber.setBounds(COLUMN2, 10, WIDTH, HEIGHT);
		panel.add(label_Show_SerialNumber);

		comboBox_Brand = new JComboBox<>(new Vector<>(
				options.stream().filter(o -> o.getType().equals("BRAND-MONITOR") && o.getStatus().equals("ACTIVE"))
						.map(Option::getOption).collect(Collectors.toList())));
		comboBox_Brand.setSelectedItem(monitorOld.getBrand());
		comboBox_Brand.setBounds(COLUMN2, 50, WIDTH, HEIGHT);
		panel.add(comboBox_Brand);

		comboBox_Model = new JComboBox<>(new Vector<>(
				options.stream().filter(o -> o.getType().equals("MODEL-MONITOR") && o.getStatus().equals("ACTIVE"))
						.map(Option::getOption).collect(Collectors.toList())));
		comboBox_Model.setSelectedItem(monitorOld.getModel());
		comboBox_Model.setBounds(COLUMN2, 90, WIDTH, HEIGHT);
		panel.add(comboBox_Model);

		textField_PatrimonyNumber = new JTextField(monitorOld.getPatrimonyNumber());
		textField_PatrimonyNumber.setDocument(new JTextFieldFilter(JTextFieldFilter.NUMERIC, 6));
		textField_PatrimonyNumber.setText(monitorOld.getPatrimonyNumber());
		textField_PatrimonyNumber.setBounds(COLUMN2, 130, WIDTH, HEIGHT);
		panel.add(textField_PatrimonyNumber);

		final JLabel label_Show_Status = new JLabel(monitorOld.getStatus());
		label_Show_Status.setBounds(COLUMN2, 170, WIDTH, HEIGHT);
		panel.add(label_Show_Status);

		final JLabel label_Show_DateEntry = new JLabel(sdf.format(monitorOld.getDateEntry()));
		label_Show_DateEntry.setBounds(COLUMN2, 210, WIDTH, HEIGHT);
		panel.add(label_Show_DateEntry);
	}

	private void addLabelsError(JPanel panel) {
		labelError_Brand = new JLabel();
		labelError_Brand.setForeground(Color.RED);
		labelError_Brand.setBounds(COLUMN3, 50, WIDTH + 90, HEIGHT);
		panel.add(labelError_Brand);

		labelError_Model = new JLabel();
		labelError_Model.setForeground(Color.RED);
		labelError_Model.setBounds(COLUMN3, 90, WIDTH + 90, HEIGHT);
		panel.add(labelError_Model);

		labelError_PatrimonyNumber = new JLabel();
		labelError_PatrimonyNumber.setForeground(Color.RED);
		labelError_PatrimonyNumber.setBounds(COLUMN3, 130, WIDTH + 90, HEIGHT);
		panel.add(labelError_PatrimonyNumber);
	}

	private void addButtons(JPanel panel) {
		final JButton buttonSave = new JButton("Save");
		buttonSave.setBounds(180, 270, WIDTH - 30, HEIGHT);
		buttonSave.addActionListener(new buttonSaveListener());
		panel.add(buttonSave);

		final JButton buttonClose = new JButton("Close");
		buttonClose.setBounds(320, 270, WIDTH - 30, HEIGHT);
		buttonClose.addActionListener(new buttonCloseListener());
		panel.add(buttonClose);
	}

	private class buttonSaveListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			try {
				final Monitor monitorNew = getFormData();
				MonitorService service = new MonitorService();
				service.update(monitorOld, monitorNew);
				model.updateMonitor(lineSelected, monitorNew);
				dispose();
				JOptionPane.showMessageDialog(rootPane, "Monitor successfully updated", "Success updating object",
						JOptionPane.INFORMATION_MESSAGE);
			} 
			catch (ValidationException e) {
				setErrorMessages(e.getErrors());
			} 
			catch (DBException e) {
				setErroMessagesDBException(e);
			}
		}
	}

	private class buttonCloseListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			dispose();
		}
	}

	private Monitor getFormData() {
		Monitor monitor = new Monitor();
		monitor = (Monitor) monitorOld.clone();

		ValidationException exception = new ValidationException("Validation error");

		// Validation Patrimony Number
		if (textField_PatrimonyNumber.getText() == null || textField_PatrimonyNumber.getText().trim().equals("")) {
			exception.addError("patrimonyNumber", "Field can't be empty");
		} 
		else if (textField_PatrimonyNumber.getText().length() < 4) {
			exception.addError("patrimonyNumber", "Invalid Patrimony Number - Ex: > 4");
		} 
		else {
			monitor.setPatrimonyNumber(textField_PatrimonyNumber.getText().trim().toUpperCase());
		}

		// Validation Brand
		if (comboBox_Brand.getSelectedIndex() < 0 || comboBox_Brand.getSelectedItem() == null) {
			exception.addError("brand", "Field can't be empty");
		} 
		else {
			monitor.setBrand(comboBox_Brand.getSelectedItem().toString());
		}

		// Validation Model
		if (comboBox_Model.getSelectedIndex() < 0 || comboBox_Model.getSelectedItem() == null) {
			exception.addError("model", "Field can't be empty");
		} 
		else {
			monitor.setModel(comboBox_Model.getSelectedItem().toString());
		}

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return monitor;
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		labelError_PatrimonyNumber.setText(fields.contains("patrimonyNumber") ? errors.get("patrimonyNumber") : "");
		labelError_Brand.setText(fields.contains("brand") ? errors.get("brand") : "");
		labelError_Model.setText(fields.contains("model") ? errors.get("model") : "");
	}

	private void setErroMessagesDBException(DBException e) {
		if (e.getMessage().contains("Duplicate entry")) {
			if (e.getMessage().contains("monitors.PRIMARY")) {
				JOptionPane.showMessageDialog(rootPane, "This serial number already exists", "Error saving object",
						JOptionPane.ERROR_MESSAGE);
			} 
			else if (e.getMessage().contains("monitors.patrimonyNumber_UNIQUE")) {
				JOptionPane.showMessageDialog(rootPane, "This patrimony number already exists", "Error saving object",
						JOptionPane.ERROR_MESSAGE);
			} 
			else {
				JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error saving object",
						JOptionPane.ERROR_MESSAGE);
			}
		} 
		else {
			JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error saving object", JOptionPane.ERROR_MESSAGE);
		}
	}
}
