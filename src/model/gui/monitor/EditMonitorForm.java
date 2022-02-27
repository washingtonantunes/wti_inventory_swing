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
import exception.ObjectException;
import exception.ValidationException;
import model.entities.Monitor;
import model.entities.Option;
import model.services.monitor.MonitorService;
import model.services.monitor.MonitorTableModel;
import model.util.JTextFieldFilter;
import model.util.Utils;

public class EditMonitorForm extends JDialog {

	private static final long serialVersionUID = 1L;

	private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private final int COLUMN1 = 20;
	private final int COLUMN2 = 160;
	private final int COLUMN3 = 370;

	private int line = 0;
	private int line_multiplier = 30;

	private final int WIDTH_LABEL = 110;
	private final int HEIGHT_LABEL = 25;

	private final int WIDTH_TEXTFIELD_COMBOBOX = 200;
	private final int HEIGHT_TEXTFIELD_COMBOBOX = 25;

	private final int WIDTH_LABEL_ERROR = 250;
	private final int HEIGHT_LABEL_ERROR = 25;

	private final int widthPanel = WIDTH_LABEL + WIDTH_TEXTFIELD_COMBOBOX + WIDTH_LABEL_ERROR + 50; //largura
	private final int heightPanel = (30 * 13) + 140; //altura

	private final Dimension DIMENSIONMAINPANEL = new Dimension(widthPanel, heightPanel);
	
	private final int positionButton = (widthPanel / 2) - 140;

	private final Color COLOR1 = new Color(0, 65, 83);
	private final Color COLOR2 = new Color(2, 101, 124);

	private JTextField textField_PatrimonyNumber;
	private JComboBox<String> comboBox_Brand;
	private JComboBox<String> comboBox_Model;
	private JComboBox<String> comboBox_CostType;
	private JTextField textField_Value;
	private JTextField textField_NoteEntry;
	private JTextField textField_Note;

	private JLabel labelError_PatrimonyNumber;
	private JLabel labelError_Brand;
	private JLabel labelError_Model;
	private JLabel labelError_CostType;
	private JLabel labelError_NoteEntry;

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
		label_SerialNumber.setForeground(COLOR1);
		label_SerialNumber.setBounds(COLUMN1, line = 30, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_SerialNumber);

		final JLabel label_PatrimonyNumber = new JLabel("Patrimony Number:");
		label_PatrimonyNumber.setForeground(COLOR1);
		label_PatrimonyNumber.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_PatrimonyNumber);

		final JLabel label_Brand = new JLabel("Brand:");
		label_Brand.setForeground(COLOR1);
		label_Brand.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_Brand);

		final JLabel label_Model = new JLabel("Model:");
		label_Model.setForeground(COLOR1);
		label_Model.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_Model);

		final JLabel label_CostType = new JLabel("Cost Type:");
		label_CostType.setForeground(COLOR1);
		label_CostType.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_CostType);

		final JLabel label_Value = new JLabel("Value:");
		label_Value.setForeground(COLOR1);
		label_Value.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_Value);

		final JLabel label_NoteEntry = new JLabel("Note Entry:");
		label_NoteEntry.setForeground(COLOR1);
		label_NoteEntry.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_NoteEntry);

		final JLabel label_Note = new JLabel("Note:");
		label_Note.setForeground(COLOR1);
		label_Note.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_Note);

		final JLabel label_Location = new JLabel("Location:");
		label_Location.setForeground(COLOR1);
		label_Location.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_Location);

		final JLabel label_Status = new JLabel("Status:");
		label_Status.setForeground(COLOR1);
		label_Status.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_Status);

		final JLabel label_DateEntry = new JLabel("Date Entry:");
		label_DateEntry.setForeground(COLOR1);
		label_DateEntry.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_DateEntry);

		final JLabel label_User = new JLabel("User:");
		label_User.setForeground(COLOR1);
		label_User.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_User);

		final JLabel label_WorkPosition = new JLabel("Work Position:");
		label_WorkPosition.setForeground(COLOR1);
		label_WorkPosition.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_WorkPosition);
	}

	private void addTextFieldsAndComboBoxes(JPanel panel) {
		final JLabel label_Show_SerialNumber = new JLabel(monitorOld.getSerialNumber());
		label_Show_SerialNumber.setBounds(COLUMN2, line = 30, WIDTH_TEXTFIELD_COMBOBOX, HEIGHT_TEXTFIELD_COMBOBOX);
		panel.add(label_Show_SerialNumber);

		textField_PatrimonyNumber = new JTextField();
		textField_PatrimonyNumber.setDocument(new JTextFieldFilter(JTextFieldFilter.NUMERIC, 6));
		textField_PatrimonyNumber.setText(monitorOld.getPatrimonyNumber());
		textField_PatrimonyNumber.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX,
				HEIGHT_TEXTFIELD_COMBOBOX);
		panel.add(textField_PatrimonyNumber);

		comboBox_Brand = new JComboBox<>(new Vector<>(
				options.stream().filter(o -> o.getType().equals("BRAND-MONITOR") && o.getStatus().equals("ACTIVE"))
						.map(Option::getOption).collect(Collectors.toList())));
		comboBox_Brand.setSelectedItem(monitorOld.getBrand());
		comboBox_Brand.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX, HEIGHT_TEXTFIELD_COMBOBOX);
		panel.add(comboBox_Brand);

		comboBox_Model = new JComboBox<>(new Vector<>(
				options.stream().filter(o -> o.getType().equals("MODEL-MONITOR") && o.getStatus().equals("ACTIVE"))
						.map(Option::getOption).collect(Collectors.toList())));
		comboBox_Model.setSelectedItem(monitorOld.getModel());
		comboBox_Model.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX, HEIGHT_TEXTFIELD_COMBOBOX);
		panel.add(comboBox_Model);

		comboBox_CostType = new JComboBox<>(new Vector<>(
				options.stream().filter(o -> o.getType().equals("COST-TYPE") && o.getStatus().equals("ACTIVE"))
						.map(Option::getOption).collect(Collectors.toList())));
		comboBox_CostType.setSelectedItem(monitorOld.getCostType());
		comboBox_CostType.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX,
				HEIGHT_TEXTFIELD_COMBOBOX);
		panel.add(comboBox_CostType);

		textField_Value = new JTextField("0.0");
		textField_Value.setDocument(new JTextFieldFilter(JTextFieldFilter.DECIMAL, 5));
		textField_Value.setText(String.valueOf(monitorOld.getValue()));
		textField_Value.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX,
				HEIGHT_TEXTFIELD_COMBOBOX);
		panel.add(textField_Value);

		textField_NoteEntry = new JTextField();
		textField_NoteEntry.setDocument(new JTextFieldFilter(JTextFieldFilter.ALPHA_NUMERIC, 12));
		textField_NoteEntry.setText(monitorOld.getNoteEntry());
		textField_NoteEntry.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX,
				HEIGHT_TEXTFIELD_COMBOBOX);
		panel.add(textField_NoteEntry);

		textField_Note = new JTextField();
		textField_Note.setText(monitorOld.getNote());
		textField_Note.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX, HEIGHT_TEXTFIELD_COMBOBOX);
		panel.add(textField_Note);

		final JLabel label_Show_Location = new JLabel(monitorOld.getLocation());
		label_Show_Location.setForeground(COLOR2);
		label_Show_Location.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX,
				HEIGHT_TEXTFIELD_COMBOBOX);
		panel.add(label_Show_Location);

		final JLabel label_Show_Status = new JLabel(monitorOld.getStatus());
		label_Show_Status.setForeground(COLOR2);
		label_Show_Status.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX,
				HEIGHT_TEXTFIELD_COMBOBOX);
		panel.add(label_Show_Status);

		final JLabel label_Show_DateEntry = new JLabel(sdf.format(monitorOld.getDateEntry()));
		label_Show_DateEntry.setForeground(COLOR2);
		label_Show_DateEntry.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX,
				HEIGHT_TEXTFIELD_COMBOBOX);
		panel.add(label_Show_DateEntry);

		final JLabel label_Show_User = new JLabel(monitorOld.getUser().getName());
		label_Show_User.setForeground(COLOR2);
		label_Show_User.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX + 100,
				HEIGHT_TEXTFIELD_COMBOBOX);
		panel.add(label_Show_User);

		final JLabel label_Show_WorkPosition = new JLabel(monitorOld.getWorkPosition().getWorkPoint());
		label_Show_WorkPosition.setForeground(COLOR2);
		label_Show_WorkPosition.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX,
				HEIGHT_TEXTFIELD_COMBOBOX);
		panel.add(label_Show_WorkPosition);
	}

	private void addLabelsError(JPanel panel) {
		labelError_PatrimonyNumber = new JLabel();
		labelError_PatrimonyNumber.setForeground(Color.RED);
		labelError_PatrimonyNumber.setBounds(COLUMN3, line = 60, WIDTH_LABEL_ERROR, HEIGHT_LABEL_ERROR);
		panel.add(labelError_PatrimonyNumber);

		labelError_Brand = new JLabel();
		labelError_Brand.setForeground(Color.RED);
		labelError_Brand.setBounds(COLUMN3, line += line_multiplier, WIDTH_LABEL_ERROR, HEIGHT_LABEL_ERROR);
		panel.add(labelError_Brand);

		labelError_Model = new JLabel();
		labelError_Model.setForeground(Color.RED);
		labelError_Model.setBounds(COLUMN3, line += line_multiplier, WIDTH_LABEL_ERROR, HEIGHT_LABEL_ERROR);
		panel.add(labelError_Model);

		labelError_CostType = new JLabel();
		labelError_CostType.setForeground(Color.RED);
		labelError_CostType.setBounds(COLUMN3, line += line_multiplier, WIDTH_LABEL_ERROR, HEIGHT_LABEL_ERROR);
		panel.add(labelError_CostType);

		labelError_NoteEntry = new JLabel();
		labelError_NoteEntry.setForeground(Color.RED);
		labelError_NoteEntry.setBounds(COLUMN3, line += (line_multiplier + line_multiplier), WIDTH_LABEL_ERROR,
				HEIGHT_LABEL_ERROR);
		panel.add(labelError_NoteEntry);
	}

	private void addButtons(JPanel panel) {
		final JButton buttonSave = new JButton("Save");
		buttonSave.setBounds(positionButton, 450, 120, 25);
		buttonSave.addActionListener(new buttonSaveListener());
		panel.add(buttonSave);

		final JButton buttonClose = new JButton("Close");
		buttonClose.setBounds(positionButton + 160, 450, 120, 25);
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
			} catch (ValidationException e) {
				setErrorMessages(e.getErrors());
			} catch (DBException e) {
				setErroMessagesDBException(e);
			} catch (ObjectException e) {
				if (e.getMessage().contains("There is no change")) {
					JOptionPane.showMessageDialog(rootPane, "There is no change", "Error updating object",
							JOptionPane.INFORMATION_MESSAGE);
				}
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
		} else if (textField_PatrimonyNumber.getText().length() < 4) {
			exception.addError("patrimonyNumber", "Invalid Patrimony Number - Ex: > 4");
		} else {
			monitor.setPatrimonyNumber(textField_PatrimonyNumber.getText().trim().toUpperCase());
		}

		// Validation Brand
		if (comboBox_Brand.getSelectedIndex() < 0 || comboBox_Brand.getSelectedItem() == null) {
			exception.addError("brand", "Field can't be empty");
		} else {
			monitor.setBrand(comboBox_Brand.getSelectedItem().toString());
		}

		// Validation Model
		if (comboBox_Model.getSelectedIndex() < 0 || comboBox_Model.getSelectedItem() == null) {
			exception.addError("model", "Field can't be empty");
		} else {
			monitor.setModel(comboBox_Model.getSelectedItem().toString());
		}

		// Validation CostType
		if (comboBox_CostType.getSelectedIndex() < 0 || comboBox_CostType.getSelectedItem() == null) {
			exception.addError("costType", "Field can't be empty");
		} else {
			monitor.setCostType(comboBox_CostType.getSelectedItem().toString());
		}

		// Insert Value
		monitor.setValue(Utils.tryParseToDouble(textField_Value.getText()));

		// Validation Note Entry
		if (textField_NoteEntry.getText() == null || textField_NoteEntry.getText().trim().equals("")) {
			exception.addError("noteEntry", "Field can't be empty");
		} else if (textField_NoteEntry.getText().length() < 4) {
			exception.addError("noteEntry", "Invalid Note Entry - Ex: > 4");
		} else {
			monitor.setNoteEntry(textField_NoteEntry.getText().trim().toUpperCase());
		}

		// Insert Note
		monitor.setNote(textField_Note.getText().trim().toUpperCase());

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
		labelError_CostType.setText(fields.contains("costType") ? errors.get("costType") : "");
		labelError_NoteEntry.setText(fields.contains("noteEntry") ? errors.get("noteEntry") : "");
	}

	private void setErroMessagesDBException(DBException e) {
		if (e.getMessage().contains("Duplicate entry")) {
			if (e.getMessage().contains("monitors.patrimonyNumber_UNIQUE")) {
				JOptionPane.showMessageDialog(rootPane, "This patrimony number already exists", "Error update object",
						JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error update object",
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error update object", JOptionPane.ERROR_MESSAGE);
		}
	}
}
